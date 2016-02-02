package com.test.dropwizardtest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by venkateswara.km on 29/01/16.
 */
public class Saying {

  private long id;
  private String content;

  public Saying(){

  }

  public Saying(long id, String content){
    this.id = id;
    this.content = content;
  }

  @JsonProperty
  public long getId(){
    return this.id;
  }

  @JsonProperty
  public String getContent(){
    return this.content;
  }
}
