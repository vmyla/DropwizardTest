package com.test.dropwizardtest.config;

import io.dropwizard.db.DataSourceFactory;

/**
 * Created by venkateswara.km on 01/02/16.
 */
public interface HasDataSourceFactory {
  DataSourceFactory getDatabaseConfiguration();
}
