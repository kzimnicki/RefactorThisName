package mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class ExcludeWordsDialog extends CafaWidget implements Dialog {

    @UiTemplate("ExcludeWordsDialog.ui.xml")
    interface ExcludeWordsDialogUiBinder extends UiBinder<Widget, ExcludeWordsDialog> {
    }

    private static ExcludeWordsDialogUiBinder uiBinder = GWT.create(ExcludeWordsDialogUiBinder.class);


    @UiField
    Button exportButton;

    public ExcludeWordsDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("exportButton")
    public void exportButtonClick(ClickEvent e){
        loadCSVContent();
    }

    public void init() {
        loadResults();
    }

     public native void loadCSVContent() /*-{
        $wnd.ajaxExecutor.exportExcludedWords(function(data) {
            $wnd.popup.showPopupMessage(data);
        });
    }-*/;

    public native void loadResults() /*-{
        $wnd.ajaxExecutor.loadExcludedWords(function(data) {
            var rows = $wnd.popup.createExcludedSiteRows(data);
            $wnd.popup.createSiteTable(rows, "Excluded Word", "#excludedWords");
        });
    }-*/;
}
