package com.christroup.gashboard.wrapper.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class InfoButton extends Composite {
	
	FlowPanel infobutton = new FlowPanel();

	public InfoButton() {
		initWidget(infobutton);
		
		infobutton.getElement().setId("info");
		
		getElement().getStyle().setPosition(Position.ABSOLUTE);
		setPosition(30, 30);
	}
	
	public void setPosition(int bottom, int right) {
		getElement().getStyle().setBottom(bottom, Unit.PX);
		getElement().getStyle().setRight(right, Unit.PX);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		setUp(getInfoElement());
		
		if (!GWT.isScript()) {
			Button i = new Button("i");
			i.setStylePrimaryName("toBack");
			i.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					DashboardWidget.getBackElement().getStyle().setDisplay(Display.BLOCK);
					DashboardWidget.getFrontElement().getStyle().setDisplay(Display.NONE);
				}
			});
			infobutton.add(i);
		}
	}
	
	public Element getInfoElement() {
		return infobutton.getElement();
	}
	
	public final native void setUp(Element element) /*-{
		var front = @com.christroup.gashboard.wrapper.client.DashboardWidget::getFrontElement()();
    	var back = @com.christroup.gashboard.wrapper.client.DashboardWidget::getBackElement()();
    		
		var toBack = function() {
			if ($wnd.widget)
        	$wnd.widget.prepareForTransition("ToBack");

    		front.style.display="none";
    		back.style.display="block";

    		if ($wnd.widget)
        	setTimeout ('$wnd.widget.performTransition();', 0);
		}
		
		if ($wnd.AppleInfoButton)
			infoButton = new $wnd.AppleInfoButton(element, front, "black", "black", toBack);
	}-*/;
}
