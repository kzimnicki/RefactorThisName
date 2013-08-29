package cc.explain.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import java.awt.*;

public class ResetDialog extends CafaWidget implements Dialog {

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox newPass;

    @UiField
    PasswordTextBox repeatPass;

    @UiField
    Button reset;

    private String key;

    @UiTemplate("ResetDialog.ui.xml")
    interface ResetDialogUiBinder extends UiBinder<Widget, ResetDialog> {
    }

    private static ResetDialogUiBinder uiBinder = GWT.create(ResetDialogUiBinder.class);

    public ResetDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void init() {
        getController().getMainDialog().getLoginDropDown().logoutClick(null);
        username.setText(Window.Location.getParameter("username"));
        key =  Window.Location.getParameter("key");
    }

    @UiHandler("reset")
    public void resetClick(ClickEvent e) {
        if(newPass.getText().equals(repeatPass.getText())){
            reset(username.getText(), newPass.getText(), key);
        }else{
            getController().getMainDialog().handleError("Passwords should be the same.");
        }
    }

    void resetCallback(String data) {
        if("PASSWORD_CHANGED".equals(data)){
            Window.Location.assign("https://explain.cc");
        }
    }

    public native void reset(String username, String newPassword, String key) /*-{
        var instance = this;
        var passHash = $wnd.crypto.md5(username+newPassword);
        $wnd.ajaxExecutor.changePassword(username, passHash, key, function(data) {
            instance.@cc.explain.client.ResetDialog::resetCallback(Ljava/lang/String;)(data);
        });
    }-*/;
}
