package fr.rockman18.funko.server.engine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractBaseFunkoService implements InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    // private SrmUserContextServiceHolder srmUserContextServiceHolder;

    @Override
    public void afterPropertiesSet() throws Exception {
	// Assert.notNull(srmUserContextServiceHolder);
    }

    // public void setSrmUserContextServiceHolder(SrmUserContextServiceHolder
    // srmUserContextServiceHolder) {
    // this.srmUserContextServiceHolder = srmUserContextServiceHolder;
    // }
    //
    // protected SrmUserContextServiceHolder getSrmUserContextServiceHolder() {
    // return srmUserContextServiceHolder;
    // }
    //
    // protected PmsUserContext getSrmUserContext() {
    // return srmUserContextServiceHolder.getContext();
    // }

}
