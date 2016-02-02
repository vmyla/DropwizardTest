package com.test.dropwizardtest;

import com.google.inject.Stage;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.test.dropwizardtest.bean.Employee;
import com.test.dropwizardtest.config.DropwizardTestModule;
import com.test.dropwizardtest.dao.EmployeeDAO;
import com.test.dropwizardtest.resources.HelloWorldDAOResource;
import com.test.dropwizardtest.resources.HelloWorldResource;

import org.glassfish.jersey.client.ClientProperties;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by venkateswara.km on 29/01/16.
 */
public class DropwizardTestApplication extends Application<DropwizardTestConfiguration> {

  private GuiceBundle<DropwizardTestConfiguration> guiceBundle;
  private final HibernateBundle<DropwizardTestConfiguration> hibernate =
      new HibernateBundle<DropwizardTestConfiguration>(Employee.class) {
    @Override
    public DataSourceFactory getDataSourceFactory(DropwizardTestConfiguration configuration) {
      return configuration.getDatabase();
    }
  };

  public static void main(String[] args) throws Exception{
    new DropwizardTestApplication().run(args);
  }

  @Override
  public String getName() {
    return "Lync-ui";
  }

  @Override
  public void run(DropwizardTestConfiguration configuration, Environment environment){

    final HelloWorldResource helloWorldResource = new HelloWorldResource(configuration.getTemplate(),
                                                                         configuration.getDefaultName());
    //final EmployeeDAO employeeDAO = new EmployeeDAO(hibernate.getSessionFactory());

    //final HelloWorldDAOResource helloWorldDAOResource = new HelloWorldDAOResource(employeeDAO);

    environment.getObjectMapper().registerModule(new JodaModule());

    environment.getObjectMapper().setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
    environment.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    environment.getObjectMapper().setDateFormat(new SimpleDateFormat("MMM dd, yyyy"));
    environment.jersey().property(ClientProperties.CHUNKED_ENCODING_SIZE, 512);

    environment.jersey().register(helloWorldResource);
    //environment.jersey().register(helloWorldDAOResource);
  }

  @Override
  public void initialize(Bootstrap<DropwizardTestConfiguration> bootstrap){
    guiceBundle = GuiceBundle.<DropwizardTestConfiguration>newBuilder().
        setConfigClass(DropwizardTestConfiguration.class).
        enableAutoConfig("com.test.dropwizardtest").
        addModule(new DropwizardTestModule()).build(
        Stage.DEVELOPMENT);
    bootstrap.addBundle(guiceBundle);
    bootstrap.addBundle(hibernate);

  }
}
