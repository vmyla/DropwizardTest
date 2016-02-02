package com.test.dropwizardtest.resources;

import com.google.common.base.Optional;

import com.codahale.metrics.annotation.Timed;
import com.test.dropwizardtest.api.Saying;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by venkateswara.km on 29/01/16.
 */
@Path("/hello-world")
public class HelloWorldResource {

  private final String template;
  private final String defaultName;
  private final AtomicLong counter;

  public HelloWorldResource(String template, String defaultName){
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();
  }

  @GET
  @Path("/hello")
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public Saying sayHello(@QueryParam("name") Optional<String> name){
    final String value = String.format(template, name.or(defaultName));
    Saying saying = new Saying(this.counter.incrementAndGet(),value);
    return saying;
  }
}
