package fr.rockman18.funko.server.engine.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtils implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
	this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
	return applicationContext;
    }

}