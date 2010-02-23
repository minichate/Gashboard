package com.christroup.gashboard.wrapper.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class WidgetVisibilityEvent extends GwtEvent<WidgetVisibilityHandler> {

	
	public static Type<WidgetVisibilityHandler> TYPE = new Type<WidgetVisibilityHandler>();
	private boolean isShow;
	
	public WidgetVisibilityEvent(boolean isShow) {
		this.isShow = isShow;
	}

	@Override
	protected void dispatch(WidgetVisibilityHandler handler) {
		if (this.isShow) {
			handler.onShow(this);
		} else {
			handler.onHide(this);
		}
	}
	
	public GwtEvent.Type<WidgetVisibilityHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<WidgetVisibilityHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<WidgetVisibilityHandler>();
		}
		return TYPE;
	}
}
