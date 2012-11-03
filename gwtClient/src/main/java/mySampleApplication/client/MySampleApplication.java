package mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        MainDialog mainDialog = new MainDialog();
        Controller controller = new Controller(mainDialog);
        mainDialog.setController(controller);
        mainDialog.setDefaultDialog(DialogName.CONTACT);
        RootPanel.get("et").add(mainDialog);
    }
}
