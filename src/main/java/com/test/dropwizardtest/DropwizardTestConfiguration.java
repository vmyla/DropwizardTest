package com.test.dropwizardtest;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Created by venkateswara.km on 29/01/16.
 */
public class DropwizardTestConfiguration extends Configuration {

  @NotEmpty
  private String template;
  @NotEmpty
  private String defaultName;

  @JsonProperty
  public String getTemplate(){
    return this.template;
  }

  public void setTemplate(String template){
    this.template = template;
  }

  @JsonProperty
  public String getDefaultName(){
    return this.defaultName;
  }

  public void setDefaultName(String defaultName){
    this.defaultName = defaultName;
  }

  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory database = new DataSourceFactory();

  public DataSourceFactory getDatabase(){
    return this.database;
  }
}
