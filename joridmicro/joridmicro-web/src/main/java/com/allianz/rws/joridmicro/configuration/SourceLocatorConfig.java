package com.allianz.rws.joridmicro.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.allianz.rest.support.config.SourceLocatorConfigBase;

@Configuration
@Profile({ "remoteConfig" })
public class SourceLocatorConfig extends SourceLocatorConfigBase {
}
