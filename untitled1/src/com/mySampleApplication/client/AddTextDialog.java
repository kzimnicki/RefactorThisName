package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.TextArea;


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
    Button translateButton;

    public AddTextDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void init() {
        tablePanel.setVisible(false);
        translateButton.setVisible(false);
    }

    @UiHandler("textArea")
   public void textAreaClick(ClickEvent e) {
        textArea.setHeight("480px");
   }


     @UiHandler("sendTextButton")
	public void sendTextButtonClick(ClickEvent e) {
         tablePanel.setVisible(true);
         textArea.setVisible(false);
         sendTextButton.setVisible(false);
         translateButton.setVisible(true);
         process(textArea.getText());
	}

    @UiHandler("translateButton")
	public void translateButtonClick(ClickEvent e) {
         textArea.setVisible(true);
         sendTextButton.setVisible(true);
         translateButton.setVisible(false);
         tablePanel.setVisible(false);
         translate(textArea.getText());
	}

     public native void process(String text) /*-{
         var dataToTranslate ={"minFrequency":"0","maxFrequency":"90","text":text,"textUrl":"FROM_SITE"};
         $wnd.ajaxExecutor.ajaxExtractWordsWithFrequency(dataToTranslate, function(wordsArray) {
             $wnd.EnglishTranslator.createWordsTable(wordsArray);
         });
    }-*/;


    public void setText(String text){
        textArea.setText(text);
    }


    public native String translate(String text) /*-{
        var wordsToTranslate = $wnd.popup.listWordsFromTable();
        var instance = this;
        var googleTranslateWordParts = $wnd.EnglishTranslator.splitWordsToGoogleTranslateParts(wordsToTranslate);
            for (var x in googleTranslateWordParts) {
                var words = googleTranslateWordParts[x];
                $wnd.ajaxExecutor.translate(words, function(translatedData) {
                    var translatedMapJSON =  $wnd.EnglishTranslator.createTranslatedBrackets(words, translatedData, text);
                    text = $wnd.EnglishTranslator.putTranslationInText(translatedMapJSON, text);
                    $wnd.EnglishTranslator.sendTranslatedWords(translatedMapJSON);
                    instance.@com.mySampleApplication.client.AddTextDialog::setText(Ljava/lang/String;)(text);
            }
        );
    }

    }-*/;


}
