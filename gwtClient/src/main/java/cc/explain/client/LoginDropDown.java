package cc.explain.client;

import cc.explain.client.event.AuthenticationErrorEventHandler;
import cc.explain.client.event.UserLoggedOutEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;

public class LoginDropDown extends Composite implements AuthenticationErrorEventHandler {

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

    public void onAuthenticationErrorEvent() {
        logout();
    }

    @UiTemplate("LoginDropDown.ui.xml")
    interface LoginDropDownUiBinder extends UiBinder<Widget, LoginDropDown> {
    }

    private static LoginDropDownUiBinder uiBinder = GWT.create(LoginDropDownUiBinder.class);

    private CafaWidget parent;

    public LoginDropDown(CafaWidget parent) {
        super();
        this.parent = parent;
        initWidget(uiBinder.createAndBindUi(this));
        handleComponentsVisibility(null);
    }

    private void handleComponentsVisibility(String usernameValue) {
        if (isLogged()) {
            username.setVisible(false);
            password.setVisible(false);
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
            logout.setText("logout "+getUsername());
        } else {
            username.setVisible(true);
            password.setVisible(true);
            login.setVisible(true);
            register.setVisible(true);
            logout.setVisible(false);
        }
    }

    public void userLoggedOut(){
      parent.getController().getEventBus().fireEvent(new UserLoggedOutEvent());
    }

    @UiHandler("login")
    public void loginClick(ClickEvent e){
        login(username.getText(), password.getText());
    }

    @UiHandler("register")
    public void registerClick(ClickEvent e) {
        parent.getController().goTo(DialogName.REGISTER);
    }

    @UiHandler("logout")
    public void logoutClick(ClickEvent e) {
        logout();
        handleComponentsVisibility(null);
    }

    public native void logout() /*-{
        $wnd.commonUtils.removeCookie();
        this.@cc.explain.client.LoginDropDown::userLoggedOut()();
    }-*/;

    public native boolean isLogged() /*-{
        return $wnd.commonUtils.isLogged();
    }-*/;

    public native String getUsername() /*-{
        return $wnd.commonUtils.getUsername();
    }-*/;

    public native void login(String username, String password) /*-{
        var instance = this;
        var passHash = $wnd.crypto.md5(username+password);
        $wnd.ajaxExecutor.login(username, passHash, function(data) {
            $wnd.commonUtils.saveCookie(username, passHash);
            instance.@cc.explain.client.LoginDropDown::handleComponentsVisibility(Ljava/lang/String;)(username);
        });
    }-*/;
}
