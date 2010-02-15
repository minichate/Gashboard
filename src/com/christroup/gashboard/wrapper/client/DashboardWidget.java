package com.christroup.gashboard.wrapper.client;

import com.christroup.gashboard.wrapper.client.shared.WidgetResources;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class DashboardWidget implements EntryPoint {
	
	public static AbsolutePanel front = new AbsolutePanel();
	public static AbsolutePanel back = new AbsolutePanel();

	@Override
	public void onModuleLoad() {
		WidgetResources.INSTANCE.css().ensureInjected();
		
		RootPanel container = RootPanel.get();
		
		front.getElement().setId("front");
		front.setStylePrimaryName(WidgetResources.INSTANCE.css().front());
		
		back.getElement().setId("back");
		back.setStylePrimaryName(WidgetResources.INSTANCE.css().back());
		
		container.add(front);
		container.add(back);
		
		widgetFront(front);
		widgetBack(back);
		
	}
	
	protected abstract void widgetFront(AbsolutePanel front);
	protected abstract void widgetBack(AbsolutePanel back);
	
	public static Element getFrontElement() {
		return front.getElement();
	}
	
	public static Element getBackElement() {
		return back.getElement();
	}
	
}
