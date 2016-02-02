package com.test.dropwizardtest.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.Getter;

/**
 * Created by venkateswara.km on 29/01/16.
 */
@Entity
@Table(name="employee")
@Getter
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  @Column(name="first_name")
  private String firstName;
  @Column(name="last_name")
  private String lastName;
  @Column(name="e_position")
  private String ePosition;
  @Column(name="phone")
  private String phone;
  @Column(name="email")
  private String email;

  public Employee(){

  }

  public Employee(String firstName,
                  String lastName, String position,
                  String phone, String email){
    this.firstName = firstName;
    this.lastName = lastName;
    this.ePosition = position;
    this.phone = phone;
    this.email = email;
  }

  @JsonProperty("firstName")
  public String getFirstName(){
    return firstName;
  }
  @JsonProperty("lastName")
  public String getLastName(){
    return lastName;
  }
  @JsonProperty("ePosition")
  public String getePosition(){
    return ePosition;
  }
  @JsonProperty("phone")
  public String getPhone(){
    return phone;
  }

}
