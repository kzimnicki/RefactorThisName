package cc.explain.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;

public class Controller implements ValueChangeHandler<String> {

	private static final String TAB_ACTIVE = "tab_active";
	private static final String TAB_PREFIX = "tab_";
	public HashMap<DialogName, Widget> widgets = new HashMap<DialogName, Widget>();

    private EventBus eventBus;


    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }



    private MainDialog mainDialog;

	public Controller(MainDialog mainDialog, EventBus eventBus) {
        this.mainDialog = mainDialog;
        setEventBus(eventBus);
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
        cafaWidget.initHandler();
		return cafaWidget;
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		String dialogName = event.getValue();
		if (isNotEmpty(dialogName)) {
			DialogName view = DialogName.valueOf(dialogName);
			Widget widget = getWidget(view);
			((Dialog) widget).init();
			mainDialog.getContainer().clear();
			mainDialog.getContainer().add(widget);
		}
	}
	
	//TODO refactor: jedno wspolne miejsce dla empty stringow
	private boolean isNotEmpty(String s){
		return !(s.equals(""));
	}

    public MainDialog getMainDialog(){
        return mainDialog;
    }

}
