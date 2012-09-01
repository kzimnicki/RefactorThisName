package com.mySampleApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class IncludeWordsDialog extends CafaWidget implements Dialog {

    @UiTemplate("IncludeWordsDialog.ui.xml")
    interface IncludeWordsDialogUiBinder extends UiBinder<Widget, IncludeWordsDialog> {
    }

    private static IncludeWordsDialogUiBinder uiBinder = GWT.create(IncludeWordsDialogUiBinder.class);

    public IncludeWordsDialog() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }

     public void init() {
        loadResults();
    }

    public native void loadResults() /*-{
        $wnd.ajaxExecutor.loadIncludedWords(function(data) {
            var rows = $wnd.popup.createIncludedSiteRows(data);
            $wnd.popup.createSiteTable(rows,"Included word", "#includedWords");
        });
    }-*/;

}
