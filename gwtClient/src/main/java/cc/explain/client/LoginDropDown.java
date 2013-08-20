package cc.explain.client;

import cc.explain.client.event.AuthenticationErrorEventHandler;
import cc.explain.client.event.UserLoggedOutEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.io.ObjectOutputStream;

import static com.google.gwt.user.client.Window.*;

public class LoginDropDown extends Composite implements AuthenticationErrorEventHandler, Dialog {

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox password;

    @UiField
    Button login;

    @UiField
    Button logout;

    @UiField
    Button reset;

    @UiField
    Button register;

    @UiField
    HTMLPanel loginDropDownArea;

    @UiField
    FocusPanel clickDiv;

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

    public void init() {
        username.getElement().setPropertyString("placeholder", "E-Mail");
        password.getElement().setPropertyString("placeholder", "Password");
        clickDiv.getElement().removeAttribute("tabindex");
    }

    private void handleComponentsVisibility(String usernameValue) {
        if (isLogged()) {
            loginDropDownArea.setVisible(false);
            logout.setVisible(true);
            logout.setText("logout "+getUsername());
        } else {
            loginDropDownArea.setVisible(true);
            logout.setVisible(false);
        }
    }

    private void restPasswordSuccessCallback(String data) {
        parent.getController().getMainDialog().handleError(data);
    }

    public void userLoggedOut(){
      parent.getController().getEventBus().fireEvent(new UserLoggedOutEvent());
    }

    @UiHandler("login")
    public void loginClick(ClickEvent e){
        login(username.getText(), password.getText());
    }

    @UiHandler("clickDiv")
    public void divClick(ClickEvent e){
        e.stopPropagation();
    }

    @UiHandler("reset")
    public void forgotClick(ClickEvent e){
        if(username.getText().length() == 0){
              parent.getController().getMainDialog().handleError("Please fill email field.");
        }else{
            resetPassword(username.getText());
        }
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

    private native void resetPassword(String email) /*-{
        var instance = this;
        $wnd.ajaxExecutor.resetPassword(email, function(data) {
            instance.@cc.explain.client.LoginDropDown::restPasswordSuccessCallback(Ljava/lang/String;)(data);
        });
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
