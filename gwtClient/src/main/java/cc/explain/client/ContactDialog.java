package cc.explain.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class ContactDialog extends CafaWidget implements Dialog {

    @UiTemplate("ContactDialog.ui.xml")
    interface ContactDialogUiBinder extends UiBinder<Widget, ContactDialog> {
    }

    private static ContactDialogUiBinder uiBinder = GWT.create(ContactDialogUiBinder.class);

    public ContactDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void init() {
        initDragDropListeners();
    }

    public native void initDragDropListeners() /*-{
        debugger;
         var dropZone = $wnd.document.getElementById('drop_zone');
        dropZone.addEventListener('dragover', $wnd.dragDrop.handleDragOver, false);
        dropZone.addEventListener('drop', $wnd.dragDrop.handleFileSelect, false);
    }-*/;

}