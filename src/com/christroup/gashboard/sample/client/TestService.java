package com.christroup.gashboard.sample.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("test")
public interface TestService extends RemoteService {
	public String myTestMethod(String s);
}
