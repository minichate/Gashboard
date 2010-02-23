package com.christroup.gashboard.wrapper.client.shared;

import com.google.gwt.core.client.GWT;

public class RequestBuilder extends com.google.gwt.http.client.RequestBuilder {
	
	public RequestBuilder(Method httpMethod, String url) {
		this(httpMethod.toString(), url);
	}

	public RequestBuilder(String httpMethod, String url) {
		super(httpMethod, ((!GWT.isScript()) ? GWT.getModuleBaseURL() + "proxy" : url));
		
		if (!GWT.isScript()) {
			this.setHeader("Requested-URL", url);
		}
	}

}
