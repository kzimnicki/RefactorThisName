package com.mySampleApplication.client;

import java.util.HashMap;
import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Controller implements ValueChangeHandler<String> {

	private static final String TAB_ACTIVE = "tab_active";
	private static final String TAB_PREFIX = "tab_";
	private SimplePanel container;
	public HashMap<DialogName, Widget> widgets = new HashMap<DialogName, Widget>();
//	private CafaServiceAsync service;

	public Controller(SimplePanel container) {
		this.container = container;
//		this.service = service;
		History.addValueChangeHandler(this);
	}

	public void goTo(DialogName view) {
		History.newItem(view.getName());
	}

	public void back() {
		History.back();
	}

	public Widget getWidget(DialogName dialogName) {
		Widget widget = widgets.get(dialogName);
		if (widget == null) {
			widget = createDialog(dialogName);
			widgets.put(dialogName, widget);
		}
		return widget;
	}

	private Widget createDialog(DialogName dialogName) {
		CafaWidget cafaWidget = dialogName.getDialog();
		cafaWidget.setController(this);
//		cafaWidget.setService(service);
		return cafaWidget;
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		String dialogName = event.getValue();
		if (isNotEmpty(dialogName)) {
			DialogName view = DialogName.valueOf(dialogName);
			Widget widget = getWidget(view);
			((Dialog) widget).init();
			container.clear();
			container.add(widget);
		}
		setTabStyle(dialogName);
	}
	
	private void setTabStyle(String selectedDialogName){
		DialogName[] names = DialogName.values();
		for(DialogName dialogName : names){
			Element elementById = DOM.getElementById(TAB_PREFIX+dialogName.getName());
			if(elementById != null){
					elementById.removeClassName(TAB_ACTIVE);
			}
		}
		DOM.getElementById(TAB_PREFIX+selectedDialogName).setClassName(TAB_ACTIVE);
	}
	
	//TODO refactor: jedno wspolne miejsce dla empty stringow
	private boolean isNotEmpty(String s){
		return !(s.equals(""));
	}

}
