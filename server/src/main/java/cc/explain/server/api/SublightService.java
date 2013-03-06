package cc.explain.server.api;

import cc.explain.server.exception.TechnicalException;
import cc.explain.server.rest.HttpMethod;
import cc.explain.server.rest.RestClient;
import cc.explain.server.rest.RestRequest;
import cc.explain.server.rest.RestResponse;
import cc.explain.server.utils.StringUtils;
import cc.explain.server.xml.LoginResponse;
import cc.explain.server.xml.SearchResponse;
import cc.explain.server.xml.TicketResponse;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import javax.xml.bind.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;

/**
 * User: kzimnick
 * Date: 25.11.12
 * Time: 17:39
 */
@Data
public class SublightService {

    private String clientId;
    private String clientKey;

    private RestClient restClient;
    private String session;

    public void init() {
        restClient = new RestClient();
    }


    public boolean login() {
        RestRequest restRequest = new RestRequest(HttpMethod.GET)
                .setUrl("http://www.sublight.si/api/rest/login-anonymous")
                .addParam("client", clientId)
                .addParam("key", clientKey);

        RestResponse response = restClient.execute(restRequest);

        LoginResponse unmarshaledResponse = unmarshal(response.getContent(), LoginResponse.class);
        session = unmarshaledResponse.getSession();
        return StringUtils.hasText(session);
    }

    public long calculateHash(HashData data) {
        byte[] headBytes = Base64.decodeBase64(data.getHead().split(",")[1]);
        Adler32 adler32 = new Adler32();
        adler32.update(headBytes);
        return adler32.getValue();
    }

    public <I> I unmarshal(InputStream input, Class<I> clazz) {
        JAXBContext context = null;
        Unmarshaller unmarshaller = null;
        try {
            context = JAXBContext.newInstance(clazz);
            unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(
                    new ValidationEventHandler() {
                        public boolean handleEvent(ValidationEvent event) {
                            throw new RuntimeException(event.getMessage(),
                                    event.getLinkedException());
                        }
                    });
            I unmarshal = (I) unmarshaller.unmarshal(input);
            input.close();
            return unmarshal;
        } catch (JAXBException e) {
            throw new TechnicalException(e.getCause());
        } catch (IOException e) {
            throw new TechnicalException(e.getCause());
        }
    }

    public String searchSubtitleId(String hash, String fileSize) {
        RestRequest restRequest = new RestRequest(HttpMethod.GET)
                .setUrl("http://www.sublight.si/api/rest/search-subtitles")
                .addParam("session", session)
                .addParam("hash", hash)
                .addParam("file-size", fileSize)
                .addParam("languages", "english");

        RestResponse response = restClient.execute(restRequest);
        InputStream content = response.getContent();
        SearchResponse unmarshaledResponse = unmarshal(content, SearchResponse.class);
        if (unmarshaledResponse.getSubtitle() != null && unmarshaledResponse.getSubtitle().length > 0) {
            return unmarshaledResponse.getSubtitle()[0].getId();
        }
        return StringUtils.EMPTY;

    }

    public TicketResponse getWaitTicket() {
        RestRequest restRequest = new RestRequest(HttpMethod.GET)
                .setUrl("http://www.sublight.si/api/rest/get-download-ticket")
                .addParam("session", session);

        RestResponse response = restClient.execute(restRequest);
        TicketResponse unmarshaledResponse = unmarshal(response.getContent(), TicketResponse.class);
        return unmarshaledResponse;
    }

    public String downloadSubtitlesZIP(String subtitleId, String ticket) {
        RestRequest restRequest = new RestRequest(HttpMethod.GET)
                .setUrl("http://www.sublight.si/api/rest/download-subtitle")
                .addParam("session", session)
                .addParam("subtitle-id", subtitleId)
                .addParam("ticket-id", ticket);

        RestResponse response = restClient.execute(restRequest);
        return response.getZipContent();
    }

    public void logout() {
        RestRequest restRequest = new RestRequest(HttpMethod.GET)
                .setUrl("http://www.sublight.si/api/rest/logout")
                .addParam("session", session);

        restClient.execute(restRequest);
    }

    public String downloadSubtitles(HashData data) {
        String subtitle = StringUtils.EMPTY;
        if (login()) {
            long hash = calculateHash(data);
            String subtitleId = searchSubtitleId(String.valueOf(hash), data.getSize());
            TicketResponse waitTicket = getWaitTicket();
            try {
                Thread.sleep(waitTicket.getWaitTime() * 1000);
            } catch (InterruptedException e) {
                throw new TechnicalException(e.getCause());
            }
            subtitle = downloadSubtitlesZIP(subtitleId, waitTicket.getTicket());
            logout();
        }
        return subtitle;
    }
}
