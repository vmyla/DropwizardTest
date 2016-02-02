package com.test.dropwizardtest.config;

import com.google.inject.Inject;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.inject.Provider;
import javax.sql.DataSource;

/**
 * Created by venkateswara.km on 01/02/16.
 */
public class DataSourceProxy implements DataSource {

  private Provider<DataSource> targetProvider;

  @Inject
  public void setTargetProvider(Provider<DataSource> targetProvider) {
    this.targetProvider = targetProvider;
  }

  DataSource getTarget() {
    return targetProvider.get();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return getTarget().getConnection();
  }

  @Override
  public Connection getConnection(String s, String s2) throws SQLException {
    return getTarget().getConnection(s, s2);
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return getTarget().getLogWriter();
  }

  @Override
  public void setLogWriter(PrintWriter printWriter) throws SQLException {
    getTarget().setLogWriter(printWriter);
  }

  @Override
  public void setLoginTimeout(int i) throws SQLException {
    getTarget().setLoginTimeout(i);
  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return getTarget().getLoginTimeout();
  }

  //@Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T unwrap(Class<T> tClass) throws SQLException {
    return getTarget().unwrap(tClass);
  }

  @Override
  public boolean isWrapperFor(Class<?> aClass) throws SQLException {
    return getTarget().isWrapperFor(aClass);
  }
}
