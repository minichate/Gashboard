package com.christroup.gashboard.wrapper.client.preferences;

import com.google.gwt.event.shared.EventHandler;

public interface PreferenceChangeHandler<T> extends EventHandler {
	public void onChange(PreferenceChangeEvent<T> event);
}
