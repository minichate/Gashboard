package com.christroup.gashboard.wrapper.client.shared;

import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class RPCRequest {
	@SuppressWarnings("unchecked")
	public static <T> T crossdomain(Object sdt, String serviceEntryPoint) {
		if (!(sdt instanceof ServiceDefTarget))
			return null;

		ServiceDefTarget svc = (ServiceDefTarget) sdt;
		svc.setRpcRequestBuilder(new RpcRequestBuilder());
		
		svc.setServiceEntryPoint(serviceEntryPoint);

		return (T) sdt;
	}

}
