package com.christroup.gashboard.wrapper.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;

public class BackButton extends Composite implements HasText {
	
	FlowPanel backbutton = new FlowPanel();
	private String text;
	Element btnElement;

	public BackButton(String text) {
		initWidget(backbutton);
		backbutton.getElement().setId("back");
		setText(text);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		this.btnElement = setUp(getBackElement());
		
		if (!GWT.isScript()) {
			Button back = new Button(this.text);
			back.setStylePrimaryName("toFront");
			back.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					DashboardWidget.getBackElement().getStyle().setDisplay(Display.NONE);
					DashboardWidget.getFrontElement().getStyle().setDisplay(Display.BLOCK);
				}
			});
			backbutton.add(back);
		}
	}
	
	public Element getBackElement() {
		return backbutton.getElement();
	}
	
	public final native Element setUp(Element element) /*-{
		var front = @com.christroup.gashboard.wrapper.client.DashboardWidget::getFrontElement()();
    	var back = @com.christroup.gashboard.wrapper.client.DashboardWidget::getBackElement()();
    		
		var toFront = function() {
			if ($wnd.widget)
        	$wnd.widget.prepareForTransition("ToFront");

    		front.style.display="block";
    		back.style.display="none";

    		if ($wnd.widget)
        	setTimeout ('$wnd.widget.performTransition();', 0);
		}
		
		if ($wnd.AppleGlassButton)
			return new $wnd.AppleGlassButton(element, this.@com.christroup.gashboard.wrapper.client.BackButton::getText()(), toFront);
			
		return back;
	}-*/;

	
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		if (this.btnElement != null) {
			this.btnElement.setInnerText("Back");
		}
	}
}
