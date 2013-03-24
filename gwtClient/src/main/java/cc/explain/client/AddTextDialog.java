package cc.explain.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;


public class AddTextDialog extends CafaWidget implements Dialog {


    @UiTemplate("AddTextDialog.ui.xml")
    interface AddTextDialogUiBinder extends UiBinder<Widget, AddTextDialog> {
    }

    private static AddTextDialogUiBinder uiBinder = GWT.create(AddTextDialogUiBinder.class);


    @UiField
    TextArea textArea;

    @UiField
    HTMLPanel tablePanel;

    @UiField
    Button sendTextButton;

    @UiField
    Button translateTextButton;

    @UiField
    Button translateSubtitlesButton;

    private String fileURL;


    public AddTextDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void init() {
        textArea.getElement().setPropertyString("placeholder", "Put english subtitles here.");
        sendTextButton.setVisible(true);
        textArea.setVisible(true);
        translateTextButton.setVisible(false);
        translateSubtitlesButton.setVisible(false);
        tablePanel.setVisible(false);
        initDragDropListeners();
        initDownloadSubtitleHandler();
    }

    private native void initDownloadSubtitleHandler() /*-{
        var that = this;
        $wnd.setFileUrl = function(url) {
            that.@cc.explain.client.AddTextDialog::setFileURL(Ljava/lang/String;)(url);
        }
    }-*/;

    public void setFileURL(String url) {
        this.fileURL = url;
    }

    public native void initDragDropListeners() /*-{
        var dropZone = $wnd.document.getElementById('drop_zone');
        dropZone.addEventListener('dragover', $wnd.dragDrop.handleDragOver, false);
        dropZone.addEventListener('drop', $wnd.dragDrop.handleFileSelect, false);
    }-*/;

    @UiHandler("textArea")
    public void textAreaClick(ClickEvent e) {
        textArea.setHeight("480px");
    }


    @UiHandler("sendTextButton")
    public void sendTextButtonClick(ClickEvent e) {
        setComponentVisibilityDuringProcessText();
        process(textArea.getText());
    }

    private void setComponentVisibilityDuringProcessText() {
        tablePanel.setVisible(true);
        textArea.setVisible(false);
        sendTextButton.setVisible(false);
        translateTextButton.setVisible(false);
        translateSubtitlesButton.setVisible(true);
    }

    @UiHandler("translateTextButton")
    public void translateTextButtonClick(ClickEvent e) {
        setComponentVisibility();
        translateText(textArea.getText());
    }

    @UiHandler("translateSubtitlesButton")
    public void translateSubtitlesButtonClick(ClickEvent e) {
        setComponentVisibility();
        translateSubtitles(textArea.getText(), fileURL);
    }

    private void setComponentVisibility() {
        textArea.setVisible(true);
        sendTextButton.setVisible(true);
        translateTextButton.setVisible(false);
        translateSubtitlesButton.setVisible(false);
        tablePanel.setVisible(false);
    }

    public native void process(String text) /*-{
        $wnd.EnglishTranslator.extractWords(text, function(words) {
            $wnd.popup.createTable(words);
        });
    }-*/;


    public void setText(String text) {
        textArea.setText(text);
    }


    public native String translateText(String text) /*-{
        var words = $wnd.popup.listWordsFromTable();
        var instance = this;

        $wnd.EnglishTranslator.translate(words, function(translatedWords) {
            $wnd.ajaxExecutor.loadOptions(function(optionsData) {
                var pattern = optionsData['textTemplate'];
                text = $wnd.EnglishTranslator.putTranslationInText(translatedWords, text, pattern);
                instance.@cc.explain.client.AddTextDialog::setText(Ljava/lang/String;)(text);
            });
        });
    }-*/;

    public native String translateSubtitles(String text, String fileURL) /*-{
        var words = $wnd.popup.listWordsFromTable();
        var instance = this;

        $wnd.EnglishTranslator.translate(words, function(translatedWords) {
            $wnd.ajaxExecutor.loadOptions(function(optionsData) {
                var pattern = optionsData['subtitleTemplate'];
                text = $wnd.EnglishTranslator.putTranslationInText(translatedWords, text, pattern);
                instance.@cc.explain.client.AddTextDialog::setText(Ljava/lang/String;)(text);
//                if (fileURL) {
//                    $wnd.$('#video').append('<video width="770" height="450" src="' + fileURL + '" type="video/mp4" id="player1" controls="controls"></video>');
//                    $wnd.videoSub.videosub_main(text);
//                }
            });
        });
    }-*/;


}
