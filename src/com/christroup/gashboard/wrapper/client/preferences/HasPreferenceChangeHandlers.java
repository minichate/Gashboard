package com.christroup.gashboard.wrapper.client.preferences;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasPreferenceChangeHandlers<T> extends HasHandlers {
	HandlerRegistration addPreferenceChangeHandler(PreferenceChangeHandler<T> handler);
}
