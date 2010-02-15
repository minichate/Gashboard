package com.christroup.gashboard.wrapper.client;

public @interface WidgetOptions {

	int width() default 100;
	int height() default 100;
	
	boolean allowNetworkAccess() default false;
	String mainHTML();
	String language() default "English";
	String displayName();
	String version();
	
}
