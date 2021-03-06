package com.christroup.gashboard.wrapper.client;

import com.christroup.gashboard.wrapper.client.events.HasWidgetDragHandlers;
import com.christroup.gashboard.wrapper.client.events.HasWidgetVisibilityHandlers;
import com.christroup.gashboard.wrapper.client.events.WidgetDragEvent;
import com.christroup.gashboard.wrapper.client.events.WidgetDragHandler;
import com.christroup.gashboard.wrapper.client.events.WidgetVisibilityEvent;
import com.christroup.gashboard.wrapper.client.events.WidgetVisibilityHandler;
import com.christroup.gashboard.wrapper.client.shared.WidgetResources;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class DashboardWidget implements EntryPoint, HasWidgetDragHandlers, HasWidgetVisibilityHandlers {
	
	private HandlerManager handlerManager = new HandlerManager(this);
	
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
		
		watchDrags();
		watchVisibility();
		
		container.add(front);
		container.add(back);
		
		widgetFront(front);
		widgetBack(back);
	}
	
	public final native void openURL(String url) /*-{
		if ($wnd.widget) {
			$wnd.widget.openURL(url);
		} else {
			@com.google.gwt.user.client.Window::open(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(url, "_blank", "");
		}
	}-*/;
	
	private final native void watchVisibility() /*-{
		var parent = this;
		var fireshow = function() {
			parent.@com.christroup.gashboard.wrapper.client.DashboardWidget::fireVisibilityShow()();
		}
		
		var firehide = function() {
			parent.@com.christroup.gashboard.wrapper.client.DashboardWidget::fireVisibilityHide()();
		}
		
		if ($wnd.widget) {
			$wnd.widget.onshow = fireshow;
			$wnd.widget.onhide = firehide;
		}
	}-*/;

	public final native void watchDrags() /*-{
		var parent = this;
		var firedragstart = function() {
			parent.@com.christroup.gashboard.wrapper.client.DashboardWidget::fireDragStartEvent()();
		}
		
		var firedragend = function() {
			parent.@com.christroup.gashboard.wrapper.client.DashboardWidget::fireDragEndEvent()();
		}
		
		if ($wnd.widget) {
			$wnd.widget.ondragstart = firedragstart;
			$wnd.widget.ondragend = firedragend;
		}
	}-*/;
	
	@SuppressWarnings("unused")
	private void fireDragStartEvent() {
		fireEvent(new WidgetDragEvent(true));
	}
	
	@SuppressWarnings("unused")
	private void fireDragEndEvent() {
		fireEvent(new WidgetDragEvent(false));
	}
	
	@SuppressWarnings("unused")
	private void fireVisibilityShow() {
		fireEvent(new WidgetVisibilityEvent(true));
	}
	
	@SuppressWarnings("unused")
	private void fireVisibilityHide() {
		fireEvent(new WidgetVisibilityEvent(false));
	}
	
	@Override
	public HandlerRegistration addWidgetDragHandler(WidgetDragHandler handler) {
		return handlerManager.addHandler(WidgetDragEvent.getType(), handler);
	}
	
	@Override
	public HandlerRegistration addWidgetVisibilityHandler(WidgetVisibilityHandler handler) {
		return handlerManager.addHandler(WidgetVisibilityEvent.getType(), handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
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
