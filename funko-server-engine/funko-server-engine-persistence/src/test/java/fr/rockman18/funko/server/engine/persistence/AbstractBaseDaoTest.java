/**
 *  CloudOMC - Rhone
 *  Copyright 2011 Thales Services SAS - All rights reserved
 *
 */

package fr.rockman18.funko.server.engine.persistence;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-pms-server-engine-persistence.xml",
		"classpath:applicationContext-pms-server-engine-persistence-resources.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public abstract class AbstractBaseDaoTest {

	/**
	 * 
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * @return the logger
	 */
	protected Logger getLogger() {
		return logger;
	}

}