package com.christroup.gashboard.wrapper.client.shared;

public class RpcRequestBuilder extends
		com.google.gwt.user.client.rpc.RpcRequestBuilder {

	@Override
	protected RequestBuilder doCreate(String serviceEntryPoint) {
		return new RequestBuilder(RequestBuilder.POST, serviceEntryPoint);
	}

}
