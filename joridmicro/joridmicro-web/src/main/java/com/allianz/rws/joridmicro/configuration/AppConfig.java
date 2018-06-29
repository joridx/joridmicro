package com.allianz.rws.joridmicro.configuration;


import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "ddbb")
@Configuration
public class AppConfig {

	private List<DbConnection> connections;
	

	public List<DbConnection> getConnections() {
		return connections;
	}

	public void setConnections(List<DbConnection> connections) {
		this.connections = connections;
	}
	
	
	@ConfigurationProperties(prefix = "ddbb.connections")
	@Configuration
	public static class DbConnection {

	    private String id;
	    private String url;
	    private String username;
	    private String password;
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}
}