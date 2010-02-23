package com.christroup.gashboard.wrapper.client.preferences;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Cookies;

public class PreferenceManager implements HasPreferenceChangeHandlers<String> {

	private static PreferenceManager manager;
	private HandlerManager handlerManager = new HandlerManager(this);
	
	protected PreferenceManager() {}
	
	public static PreferenceManager getInstance() {
		if (manager == null) {
			manager = new PreferenceManager();
		}
		return PreferenceManager.manager;
	}
	
	 /**
     * Get a preference which has been stored
     * 
     * @param key key of the preference you want to get
     */
	public String getPreference(String key) {
		if (!GWT.isScript()) return Cookies.getCookie(key);
		return getWidgetPreference(key);
	}
	
	/**
     * Set a preference which will be stored.
     * 
     * @param key key of the preference you want to set
     * @param preference data you would like stored
     */
	public void setPreference(String key, String preference) {
		if (!GWT.isScript()) {
			Cookies.setCookie(key, preference);
		} else {
			setWidgetPreference(key, preference);
		}
		fireEvent(new PreferenceChangeEvent<String>(key));
	}
	
   
	private native String getWidgetPreference(String preference) /*-{
		if ($wnd.widget) return $wnd.widget.preferenceForKey(preference);
	}-*/;

	private native void setWidgetPreference(String key, String preference) /*-{
		if ($wnd.widget) {
			$wnd.widget.setPreferenceForKey(preference, key);
		}
	}-*/;

	public HandlerRegistration addPreferenceChangeHandler(
			PreferenceChangeHandler<String> handler) {
		return handlerManager.addHandler(PreferenceChangeEvent.getType(), handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}
	
}
