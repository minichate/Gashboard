package com.christroup.gashboard.sample.server;

import com.christroup.gashboard.sample.client.TestService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TestServiceImpl extends RemoteServiceServlet implements TestService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5448779318582090311L;

	@Override
	public String myTestMethod(String s) {
		return "worked: " + s;
	}

}
