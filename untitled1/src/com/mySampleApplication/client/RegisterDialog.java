package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RegisterDialog extends CafaWidget implements Dialog {

    @UiField
    TextBox username;

    @UiField
    TextBox pass;

    @UiField
    TextBox repeatPass;

    @UiField
    Button register;

    @UiTemplate("RegisterDialog.ui.xml")
    interface AboutDialogUiBinder extends UiBinder<Widget, RegisterDialog> {
    }

    private static AboutDialogUiBinder uiBinder = GWT.create(AboutDialogUiBinder.class);

    public RegisterDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void init() {
    }

    @UiHandler("register")
    public void registerClick(ClickEvent e) {
        register(username.getText(), pass.getText());
    }

    void registerCallback(String data) {
        if("SUCCESS".equals(data)){
            getController().goTo(DialogName.EXCLUDE_WORDS);
        }
    }

    public native void register(String username, String password) /*-{
        var instance = this;
        $wnd.ajaxExecutor.register(username, password, function(data) {
            instance.@com.mySampleApplication.client.RegisterDialog::registerCallback(Ljava/lang/String;)(data);
        });
    }-*/;
}
