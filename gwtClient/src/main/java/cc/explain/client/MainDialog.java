package cc.explain.client;

import cc.explain.client.event.AuthenticationErrorEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

public class MainDialog extends CafaWidget implements Dialog {

    @UiTemplate("MainDialog.ui.xml")
	interface MainDialogUiBinder extends UiBinder<Widget, MainDialog> {
	}

	private static MainDialogUiBinder uiBinder = GWT.create(MainDialogUiBinder.class);

	@UiField
	SimplePanel container;

	@UiField
    Anchor contact;

	@UiField
    Anchor watch;

    @UiField
	Anchor options;

    @UiField
	Anchor excludeWords;

    @UiField
	Anchor includeWords;

    @UiField
	Anchor submitText;

    @UiField
    LoginDropDown loginDropDown;

    @UiField
    HTMLPanel errorPanel;

    @UiField
    Label popupTitle;

    private Element lastClicked = new Anchor().getElement();

	public MainDialog() {
		super();
        initErrorHandler();
		initWidget(uiBinder.createAndBindUi(this));
        loginDropDown.init();
	}

    public void setDefaultDialog(DialogName dialogName){
        Widget widget = getController().getWidget(dialogName);
        container.add(widget);
    }

    @UiHandler("options")
	public void optionsClick(ClickEvent e) {
		changeDialog(options, DialogName.OPTIONS);
	}

    @UiHandler("watch")
	public void watchClick(ClickEvent e) {
		changeDialog(watch, DialogName.WATCH);
	}

    @UiHandler("excludeWords")
    public void excludeWordsClick(ClickEvent e) {
        changeDialog(excludeWords, DialogName.EXCLUDE_WORDS);
    }


    @UiHandler("includeWords")
    public void includeWordsClick(ClickEvent e) {
        changeDialog(includeWords, DialogName.INCLUDE_WORDS);
    }

    @UiHandler("contact")
	public void contactClick(ClickEvent e) {
        changeDialog(contact,DialogName.CONTACT);
	}

    @UiHandler("submitText")
	public void submitTextClick(ClickEvent e) {
		changeDialog(submitText, DialogName.ADD_TEXT);
	}

     private void changeDialog(Anchor link, DialogName dialogName) {
        getController().goTo(dialogName);
        Element parentElement = link.getElement().getParentElement();
        lastClicked.removeClassName("active");
        parentElement.setClassName("active");
        lastClicked = parentElement;
    }


    @UiFactory
	LoginDropDown createLoginDropDown(){
		loginDropDown= new LoginDropDown(this);
		return loginDropDown;
	}

    public LoginDropDown getLoginDropDown(){
        return loginDropDown;
    }

	public SimplePanel getContainer() {
		return container;
	}

    public void handleError(String errorData){
       showPopup("Error", errorData);
    }

    public void infoPopup(String title, String info){
        showPopup(title, info);
    }

    private void showPopup(String title, String info){
        DOM.getElementById("POPUP").setAttribute("style","display:block");
        errorPanel.clear();
        popupTitle.setText(title);
        String[] messages = info.split("\\|");
        for(String m : messages){
            errorPanel.add(new InlineHTML(m));
        }
        if("Bad login or password".equals(info)){
            getController().getEventBus().fireEvent(new AuthenticationErrorEvent());
        }
    }

    private native void initErrorHandler() /*-{
         var that = this;
         $wnd.errorHandler = function(errorData) {
            that.@cc.explain.client.MainDialog::handleError(Ljava/lang/String;)(errorData)
         }
     }-*/;
}