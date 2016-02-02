package com.test.dropwizardtest.config;

import com.google.inject.Inject;

import com.codahale.metrics.MetricRegistry;

import javax.inject.Provider;
import javax.sql.DataSource;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

/**
 * Created by venkateswara.km on 01/02/16.
 */
public class DataSourceProvider implements Provider<DataSource>,Managed {

  public static final String NAME = "analytics_db";
  private final Provider<HasDataSourceFactory> dataHasSourceFactoryProvider;
  private MetricRegistry metricRegistry;
  private ManagedDataSource dataSource = null;

  @Inject
  public DataSourceProvider(javax.inject.Provider<HasDataSourceFactory> dataSourceFactoryProvider,
                            DataSourceProxy dataSourceProxy, Environment environment) {
    this.dataHasSourceFactoryProvider = dataSourceFactoryProvider;
    this.metricRegistry = environment.metrics();
    dataSourceProxy.setTargetProvider(this);
  }

  private ManagedDataSource initializeDataSource(
      javax.inject.Provider<HasDataSourceFactory> eventHandlerConfigurationProvider) {
    HasDataSourceFactory configuration = eventHandlerConfigurationProvider.get();
    DataSourceFactory dataSourceFactory = configuration.getDatabaseConfiguration();

    return dataSourceFactory.build(metricRegistry, NAME);
  }

  @Override
  public ManagedDataSource get() {
    if (dataSource == null) {
      dataSource = initializeDataSource(dataHasSourceFactoryProvider);
    }
    return dataSource;
  }

  @Override
  public void start() throws Exception {
    get().start();
  }

  @Override
  public void stop() throws Exception {
    get().stop();
  }


}
