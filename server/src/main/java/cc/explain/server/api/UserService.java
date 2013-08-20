package cc.explain.server.api;

import cc.explain.server.core.CommonDao;
import cc.explain.server.exception.TechnicalException;
import cc.explain.server.model.Configuration;
import cc.explain.server.model.User;
import cc.explain.server.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sun.rmi.runtime.Log;

import javax.mail.MessagingException;

public class UserService {
    public static final int MAGIC_NUMBER = 13;
    public static final String URL_PATTERN = "https://explain.cc/app/activate/%s/%s";
    public static final String RESET_URL_PATTERN = "https://explain.cc/?key=%s&username=%s#RESET";

    private static Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    CommonDao commonDao;

    @Autowired
    MailService mailService;

    @Autowired
    AuthenticationManager authenticationManager;

    public static final User DUMMY_USER = new User();

    public LoginServiceResult register(User user) {
        user.setRole(Role.USER);
        commonDao.create(user);
        sendConfirmationLink(user);
        return LoginServiceResult.SUCCESS;
    }

    public LoginServiceResult login(User user) {
        Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication result = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
        return LoginServiceResult.SUCCESS;
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return DUMMY_USER;
        }
        String username = authentication.getName();
        //Todo refactor
        User user = loadUserFromDatabase(username);
        return user;
    }

    public User loadUserFromDatabase(String username){
        return (User) commonDao.getByHQL("from User u where u.username = :username", "username", username).get(0);
    }

    public User getUserById(Long id){
         User user = (User) commonDao.getByHQL("from User u where u.id = :id", "id", String.valueOf(id)).get(0);
        return user;
    }

    public void save(User user) {
        commonDao.saveOrUpdate(user);
    }

    public void clearAutentication(){
         SecurityContextHolder.clearContext();
    }

    public void save(User user, Configuration config) {
        user.getConfig().setMax(config.getMax());
        user.getConfig().setMin(config.getMin());
        user.getConfig().setSubtitleTemplate(config.getSubtitleTemplate());
        user.getConfig().setTextTemplate(config.getTextTemplate());
        user.getConfig().setPhrasalVerbAdded(config.isPhrasalVerbAdded());
        user.getConfig().setSubtitleProcessor(config.getSubtitleProcessor());
        save(user);
    }

    String generateActivationKey(String username) {
        return StringUtils.md5(username);
    }

    String generateLink(User user) {
        return String.format(URL_PATTERN,user.getId()* MAGIC_NUMBER, generateActivationKey(user.getUsername()));
    }

    boolean validateActivation(User user, String key) {
        return generateActivationKey(user.getUsername()).equals(key);
    }

    public void sendConfirmationLink(User user){
        String link = generateLink(user);
        String message = createActivationEmailMessage(link);
        try{
            mailService.send(user.getUsername(), "Explain.CC - Confirmation", message);
        }catch(MessagingException e){
            throw new TechnicalException(e);
        }
    }

    public User activate(Long id, String key) {
        long decyptedId = id / MAGIC_NUMBER;
        User user = getUserById(decyptedId);
        if(validateActivation(user, key)){
            user.setEnabled(true);
            commonDao.saveOrUpdate(user);
            return user;
        }
        return DUMMY_USER;
    }

    public void changePassword(User user, String newPassword, String key){

        user.setPassword(newPassword);
        commonDao.saveOrUpdate(user);
    }

    String createActivationEmailMessage(String link) {
        return String.format("Please confirm user account: %s", link);
    }

    public void setMailService(MailService mailService){
        this.mailService = mailService;
    }

    public String generateResetPasswordLink(User user) {
        return String.format(RESET_URL_PATTERN, generateActivationKey(user.getUsername()), user.getUsername());
    }

    public String createResetEmailMessage(String link) {
        return String.format("Please reset password: %s", link);
    }

    public boolean validateReset(User user, String key) {
        return generateActivationKey(user.getUsername()).equals(key);
    }

    public LoginServiceResult resetPassword(String username) {
        if(isUserExists(username)){
            User user = loadUserFromDatabase(username);
            try {
                mailService.send(username, "Excplain.CC - reset password", createResetEmailMessage(generateResetPasswordLink(user)));
                return LoginServiceResult.RESET_EMAIL_SENT;
            } catch (MessagingException e) {
               throw new TechnicalException(e);
            }
        }else{
            return LoginServiceResult.EMAIL_NOT_EXISTS;
        }
    }

    public LoginServiceResult changePassword(String username, String newPassword, String key) {
        if(isUserExists(username)){
            User user = loadUserFromDatabase(username);
            String serverActivationKey = generateActivationKey(username);
            if(serverActivationKey.equals(key)){
                user.setPassword(newPassword);
                commonDao.saveOrUpdate(user);
                return LoginServiceResult.PASSWORD_CHANGED;
            }else{
              return LoginServiceResult.WRONG_RESET_KEY;
            }
        }else{
            return LoginServiceResult.EMAIL_NOT_EXISTS;
        }
    }

    public boolean isUserExists(String username) {
        int count = commonDao.countByHQL("select count(*) from User u where u.username = :username", "username", username);
        LOG.info(String.valueOf(count));
        return count > 0;
    }
}
