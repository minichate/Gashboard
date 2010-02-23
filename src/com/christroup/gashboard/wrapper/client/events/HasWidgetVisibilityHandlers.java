package com.christroup.gashboard.wrapper.client.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasWidgetVisibilityHandlers extends HasHandlers {
	HandlerRegistration addWidgetVisibilityHandler(WidgetVisibilityHandler handler);
}
