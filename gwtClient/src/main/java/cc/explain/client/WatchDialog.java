package cc.explain.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;


public class WatchDialog extends CafaWidget implements Dialog {

    @UiTemplate("WatchDialog.ui.xml")
    interface WatchDialogUiBinder extends UiBinder<Widget, WatchDialog> {
    }

    private static WatchDialogUiBinder uiBinder = GWT.create(WatchDialogUiBinder.class);

    @UiField
    Button start;

    @UiField
    Button more;

    public WatchDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void init() {
        initDragDropListeners();
    }

    @UiHandler("start")
    public void startClick(ClickEvent e) {
        getController().goTo(DialogName.ADD_TEXT);
    }

    @UiHandler("more")
    public void moreClick(ClickEvent e) {
        getController().goTo(DialogName.CONTACT);
    }


    public native void initDragDropListeners() /*-{
        var dropZone = $wnd.document.getElementById('drop_zone');
        dropZone.addEventListener('dragover', $wnd.dragDrop.handleDragOver, false);
        dropZone.addEventListener('drop', $wnd.dragDrop.handleFileSelect, false);
    }-*/;
}
