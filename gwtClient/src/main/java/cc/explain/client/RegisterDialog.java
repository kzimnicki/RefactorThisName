package cc.explain.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RegisterDialog extends CafaWidget implements Dialog {

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox pass;

    @UiField
    PasswordTextBox repeatPass;

    @UiField
    Button register;

    private MainDialog mainDialog;

    @UiTemplate("RegisterDialog.ui.xml")
    interface RegisterDialogUiBinder extends UiBinder<Widget, RegisterDialog> {
    }

    private static RegisterDialogUiBinder uiBinder = GWT.create(RegisterDialogUiBinder.class);

    public RegisterDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void init() {
         getController().getMainDialog().getLoginDropDown().logoutClick(null);
    }

    @UiHandler("register")
    public void registerClick(ClickEvent e) {
        if(pass.getText().equals(repeatPass.getText())){
            register(username.getText(), pass.getText());
        }else{
            getController().getMainDialog().handleError("Passwords should be the same.");
        }
    }

    public native void logout() /*-{
        $wnd.commonUtils.removeCookie();
    }-*/;

    void registerCallback(String data) {
        if("SUCCESS".equals(data)){
            getController().goTo(DialogName.CONTACT);
        }
    }

    public native void register(String username, String password) /*-{
        var instance = this;
        var passHash = $wnd.crypto.md5(username+password);
        $wnd.ajaxExecutor.register(username, passHash, function(data) {
            instance.@cc.explain.client.RegisterDialog::registerCallback(Ljava/lang/String;)(data);
        });
    }-*/;
}
