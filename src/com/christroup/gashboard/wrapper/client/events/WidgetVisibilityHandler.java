package com.christroup.gashboard.wrapper.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface WidgetVisibilityHandler extends EventHandler {
	public void onShow(WidgetVisibilityEvent event);
	public void onHide(WidgetVisibilityEvent event);
}
