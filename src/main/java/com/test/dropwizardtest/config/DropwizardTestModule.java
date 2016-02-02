package com.test.dropwizardtest.config;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import com.test.dropwizardtest.DropwizardTestConfiguration;

import org.hibernate.cfg.ImprovedNamingStrategy;

import java.util.Properties;

import javax.inject.Provider;
import javax.sql.DataSource;

import io.dropwizard.db.DataSourceFactory;

/**
 * Created by venkateswara.km on 01/02/16.
 */
public class DropwizardTestModule extends AbstractModule{

  protected void configure(){
    bind(DataSource.class).toProvider(DataSourceProvider.class).in(javax.inject.Singleton.class);
    Properties properties = new Properties();
    DataSourceProxy dataSourceProxy = new DataSourceProxy();
    bind(DataSourceProxy.class).toInstance(dataSourceProxy);
    properties.put("dynamicPersistenceProvider.packagesToScan",
                   Lists.newArrayList("com.test.dropwizardtest.bean"));
    properties.put("hibernate.connection.release_mode",
                   "after_transaction");
    properties.put("hibernate.connection.datasource", dataSourceProxy);
    properties.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.INSTANCE);
    properties.put("javax.persistence.provider","com.test.dropwizardtest.config.DynamicPersistenceProvider");
    install(new JpaPersistModule("myla-test").properties(properties));
  }

  @Provides
  @Singleton
  private HasDataSourceFactory providesDataSource(final Provider<DropwizardTestConfiguration> configurationProvider){
    return new HasDataSourceFactory() {
      @Override
      public DataSourceFactory getDatabaseConfiguration() {
        return configurationProvider.get().getDatabase();
      }
    };
  }



  @Provides
  @Singleton
  private PersistenceServiceLifeCycle providesPersistenceServiceLifeCycle(
      final Provider<PersistService> persistService) {
    return new PersistenceServiceLifeCycle() {
      @Override public void start() throws Exception {
        persistService.get().start();
      }

      @Override public void stop() throws Exception {
        persistService.get().stop();
      }
    };
  }
}
