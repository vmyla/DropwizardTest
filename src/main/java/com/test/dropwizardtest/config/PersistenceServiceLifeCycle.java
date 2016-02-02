package com.test.dropwizardtest.config;

/**
 * Created by venkateswara.km on 01/02/16.
 */
public interface PersistenceServiceLifeCycle  {

  public void start() throws Exception;
  public void stop() throws Exception;
}
