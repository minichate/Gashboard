package com.christroup.gashboard.sample.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TestServiceAsync {

	void myTestMethod(String s, AsyncCallback<String> callback);

}
