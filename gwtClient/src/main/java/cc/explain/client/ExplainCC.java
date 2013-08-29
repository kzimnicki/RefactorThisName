package cc.explain.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.util.tools.shared.StringUtils;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class ExplainCC implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        EventBus eventBus = GWT.create(SimpleEventBus.class);
        MainDialog mainDialog = GWT.create(MainDialog.class);
        Controller controller = new Controller(mainDialog, eventBus);
        mainDialog.setController(controller);
        mainDialog.setDefaultDialog(DialogName.WATCH);
        RootPanel.get("et").add(mainDialog);
        if(isResetPasswordDialog()){
            controller.goTo(DialogName.RESET);
        }
    }

    private boolean isResetPasswordDialog(){
        String key = Window.Location.getParameter("key");
        String username = Window.Location.getParameter("username");
        return key != null && username != null;
    }
}
