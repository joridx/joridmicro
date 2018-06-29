package com.allianz.rws.joridmicro;

import com.allianz.rest.support.util.ArchServices;

public class ITTestBase {

	//Host where architecture services are hosted, such as oauth-server or config-server. You can change this to your localhost if you are running this services locally
	public static final String ARCH_WEBSERVICES_HOST = "http://wwwd.es.intrallianz.com";
	
	static {
		System.getProperties().setProperty(ArchServices.DOMAIN_URL, ARCH_WEBSERVICES_HOST);
	}
}