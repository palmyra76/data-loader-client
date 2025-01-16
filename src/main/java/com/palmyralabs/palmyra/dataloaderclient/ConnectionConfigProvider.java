package com.palmyralabs.palmyra.dataloaderclient;

public interface ConnectionConfigProvider {

	public String getDataFolder();
	
	public String getErrorFolder();
	
	public String getBaseUrl();

	public String getUsername();

	public String getPassword();
}
