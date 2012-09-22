package mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;

public class RegisterDialog extends CafaWidget implements Dialog {

    @UiField
    TextBox username;

    @UiField
    PasswordTextBox pass;

    @UiField
    PasswordTextBox repeatPass;

    @UiField
    Button register;

    @UiField
    Label errorMessages;

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
        }else{
            errorMessages.setText(data);
        }
    }

    public native void register(String username, String password) /*-{
        var instance = this;
        $wnd.ajaxExecutor.register(username, password, function(data) {
            instance.@mySampleApplication.client.RegisterDialog::registerCallback(Ljava/lang/String;)(data);
        });
    }-*/;
}
