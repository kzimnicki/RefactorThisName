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
    Button logout;

    @UiField
    Button register;

//    @UiField
//    Label dropDownText;

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
        handleComponentsVisibility();
    }

    private void handleComponentsVisibility() {
        if (isLogged()) {
            username.setVisible(false);
            password.setVisible(false);
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
        } else {
            username.setVisible(true);
            password.setVisible(true);
            login.setVisible(true);
            register.setVisible(true);
            logout.setVisible(false);
        }
    }

    @UiHandler("login")
    public void loginClick(ClickEvent e) {
        login(username.getText(), password.getText());
    }

    @UiHandler("register")
    public void registerClick(ClickEvent e) {
        parent.getController().goTo(DialogName.REGISTER);
         handleComponentsVisibility();
    }

    @UiHandler("logout")
    public void logoutClick(ClickEvent e) {
        logout();
         handleComponentsVisibility();
    }


    public native void logout() /*-{
        $wnd.commonUtils.removeCookie();
    }-*/;

    public native boolean isLogged() /*-{
        return $wnd.commonUtils.isLogged();
    }-*/;

    public native void login(String username, String password) /*-{
        var instance = this;
        $wnd.ajaxExecutor.login(username, password, function(data) {
            $wnd.commonUtils.saveCookie(username, password);
            instance.@com.mySampleApplication.client.LoginDropDown::handleComponentsVisibility()();
        });
    }-*/;
}
