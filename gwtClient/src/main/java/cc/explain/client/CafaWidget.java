package cc.explain.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Composite;

public class CafaWidget extends Composite {
	
	private Controller controller;

	
	public void init(){
		
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public Controller getController() {
		return controller;
    }
}
