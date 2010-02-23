package com.christroup.gashboard.wrapper.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface WidgetDragHandler extends EventHandler {
	public void onDragStart(WidgetDragEvent event);
	public void onDragEnd(WidgetDragEvent event);
}
