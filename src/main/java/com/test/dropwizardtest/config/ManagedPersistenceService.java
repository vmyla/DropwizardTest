package com.test.dropwizardtest.config;

import com.google.inject.Inject;
import com.google.inject.Provider;

import io.dropwizard.lifecycle.Managed;

/**
 * Created by venkateswara.km on 01/02/16.
 */
public class ManagedPersistenceService implements Managed{

  private Provider<PersistenceServiceLifeCycle> persistenceServiceLifeCycleProvider;
  @Inject
  public ManagedPersistenceService(Provider<PersistenceServiceLifeCycle> persistenceServiceLifeCycleProvider){
    this.persistenceServiceLifeCycleProvider = persistenceServiceLifeCycleProvider;
  }
  /**
   * Starts the object. Called <i>before</i> the application becomes available.
   *
   * @throws Exception if something goes wrong; this will halt the application startup.
   */
  @Override
  public void start() throws Exception {
    this.persistenceServiceLifeCycleProvider.get().start();
  }

  /**
   * Stops the object. Called <i>after</i> the application is no longer accepting requests.
   *
   * @throws Exception if something goes wrong.
   */
  @Override
  public void stop() throws Exception {
    this.persistenceServiceLifeCycleProvider.get().stop();
  }
}
