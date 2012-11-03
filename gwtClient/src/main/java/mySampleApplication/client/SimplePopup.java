package mySampleApplication.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: kzimnick
 * Date: 06.10.12
 * Time: 11:05
 */
public class SimplePopup extends DialogBox {

	public VerticalPanel messagePanel;

	public SimplePopup() {
		super();
		init();
	}

	public void init() {
		setAnimationEnabled(true);
		setGlassEnabled(true);
		final Button closeButton = new Button("Close");
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		messagePanel = new VerticalPanel();
		dialogVPanel.add(messagePanel);
		dialogVPanel.add(closeButton);
		setWidget(dialogVPanel);
		center();
		show();

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}

	public VerticalPanel getMessagePanel() {
		return messagePanel;
	}
}