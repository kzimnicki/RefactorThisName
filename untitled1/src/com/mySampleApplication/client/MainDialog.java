package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

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
	Anchor options;

    @UiField
	Anchor excludeWords;

    @UiField
	Anchor includeWords;

    @UiField
	Anchor submitText;

    @UiField
    LoginDropDown loginDropDown;

	public MainDialog() {
		super();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void init() {
	}

    @UiHandler("options")
	public void optionsClick(ClickEvent e) {
		getController().goTo(DialogName.OPTIONS);
	}

    @UiHandler("excludeWords")
    public void excludeWordsClick(ClickEvent e) {
        getController().goTo(DialogName.EXCLUDE_WORDS);
    }


    @UiHandler("includeWords")
    public void includeWordsClick(ClickEvent e) {
        getController().goTo(DialogName.INCLUDE_WORDS);
    }

    @UiHandler("contact")
	public void contactClick(ClickEvent e) {
		getController().goTo(DialogName.CONTACT);
	}

    @UiHandler("submitText")
	public void submitTextClick(ClickEvent e) {
		getController().goTo(DialogName.ADD_TEXT);
	}


    @UiFactory
	LoginDropDown createLoginDropDown(){
		loginDropDown= new LoginDropDown(this);
		return loginDropDown;
	}

	public SimplePanel getContainer() {
		return container;
	}
}