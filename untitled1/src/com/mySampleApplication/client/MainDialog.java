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
	Anchor about;

    @UiField
	Anchor excludeWords;

    @UiField
    LoginDropDown loginDropDown;

	public MainDialog() {
		super();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void init() {
	}

	@UiHandler("about")
	public void aboutClick(ClickEvent e) {
		getController().goTo(DialogName.REGISTER);
	}

    @UiHandler("excludeWords")
	public void excludeWordsClick(ClickEvent e) {
		getController().goTo(DialogName.EXCLUDE_WORDS);
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