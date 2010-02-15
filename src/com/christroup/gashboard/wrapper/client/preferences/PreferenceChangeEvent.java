package com.christroup.gashboard.wrapper.client.preferences;

import com.google.gwt.event.shared.GwtEvent;

public class PreferenceChangeEvent<T> extends GwtEvent<PreferenceChangeHandler> {
	
	private final T preferenceKey;
	public static Type<PreferenceChangeHandler> TYPE = new Type<PreferenceChangeHandler>();
	
	public PreferenceChangeEvent(T key) {
		this.preferenceKey = key;
	}
	
	@Override
	protected void dispatch(PreferenceChangeHandler handler) {
		handler.onChange(this);
	}

	@Override
	public GwtEvent.Type<PreferenceChangeHandler> getAssociatedType() {
		return TYPE;
	}
	
	public static Type<PreferenceChangeHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<PreferenceChangeHandler>();
		}
		return TYPE;
	}

	public T getPreferenceKey() {
		return this.preferenceKey;
	}
}
