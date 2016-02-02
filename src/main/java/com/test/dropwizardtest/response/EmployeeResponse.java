package com.test.dropwizardtest.response;

import com.test.dropwizardtest.bean.Employee;

import java.util.List;

import lombok.Data;

/**
 * Created by venkateswara.km on 02/02/16.
 */
@Data
public class EmployeeResponse {

  private int status;
  private List<Employee> employees;
}
