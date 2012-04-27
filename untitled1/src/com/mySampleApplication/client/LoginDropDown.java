package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;

public class LoginDropDown extends Composite {

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox password;

    @UiField
    Button login;

    @UiField
    Button register;

    @UiField
    Label dropDownText;

    @UiTemplate("LoginDropDown.ui.xml")
    interface LoginDropDownUiBinder extends UiBinder<Widget, LoginDropDown> {
    }

    private static LoginDropDownUiBinder uiBinder = GWT.create(LoginDropDownUiBinder.class);

    private Controller controller;

    private CafaWidget parent;

    public LoginDropDown(CafaWidget parent) {
        super();
        this.parent = parent;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("login")
    public void loginClick(ClickEvent e) {
        parent.getController().goTo(DialogName.REGISTER);
        login(username.getText(), password.getText());
    }

    @UiHandler("register")
    public void registerClick(ClickEvent e) {
        parent.getController().goTo(DialogName.REGISTER);
    }

    void loginCallback(String data){
        if("SUCCESS".equals(data)){
            dropDownText.setText(username.getText());
            parent.getController().goTo(DialogName.EXCLUDE_WORDS);
        }
    }

    public native void login(String username, String password) /*-{
        var instance = this;
         $wnd.ajaxExecutor.login(username, password, function(data){
             instance.@com.mySampleApplication.client.LoginDropDown::loginCallback(Ljava/lang/String;)(data);
             $wnd.ajaxExecutor.saveCookie(username, password);
         });
    }-*/;
}
