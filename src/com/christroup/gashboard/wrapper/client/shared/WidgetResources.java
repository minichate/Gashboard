package com.christroup.gashboard.wrapper.client.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface WidgetResources extends ClientBundle {
	
	public static WidgetResources INSTANCE = GWT.create(WidgetResources.class);
	
	@Source("main.css")
	Css css();

}
