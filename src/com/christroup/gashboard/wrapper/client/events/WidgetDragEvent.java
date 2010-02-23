package com.christroup.gashboard.wrapper.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class WidgetDragEvent extends GwtEvent<WidgetDragHandler> {
	
	public static Type<WidgetDragHandler> TYPE = new Type<WidgetDragHandler>();
	private boolean isStart;
	
	public WidgetDragEvent(boolean isStart) {
		this.isStart = isStart;
	}

	@Override
	protected void dispatch(WidgetDragHandler handler) {
		if (this.isStart) {
			handler.onDragStart(this);
		} else {
			handler.onDragEnd(this);
		}
	}
	
	public GwtEvent.Type<WidgetDragHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<WidgetDragHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<WidgetDragHandler>();
		}
		return TYPE;
	}
}
