package com.palmyralabs.palmyra.dataloaderclient;

import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.client.auth.AuthClient;
import com.palmyralabs.palmyra.client.auth.BasicAuthClient;
import com.palmyralabs.palmyra.client.impl.TupleRestClientImpl;

public class PalmyraClientFactory {
	
	public static TupleRestClient getClient(String host, String mapping) {
		AuthClient authClient = AuthClient.NoopAuthClient;
		String baseUrl = host;
		String context = "/api";

		return new TupleRestClientImpl(authClient, baseUrl, context, mapping);
	}
	
	public static TupleRestClient getClient(String host, String mapping, String username, String password) {
		AuthClient authClient = new BasicAuthClient(username, password, username);
		String baseUrl = host;
		String context = "/api";

		return new TupleRestClientImpl(authClient, baseUrl, context, mapping);
	}
}
