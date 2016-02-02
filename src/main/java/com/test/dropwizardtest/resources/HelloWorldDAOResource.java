package com.test.dropwizardtest.resources;

import com.google.inject.Inject;

import com.test.dropwizardtest.bean.Employee;
import com.test.dropwizardtest.dao.EmployeeDAO;
import com.test.dropwizardtest.response.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * Created by venkateswara.km on 01/02/16.
 */
@Path("/helloworld")
public class HelloWorldDAOResource {

  private EmployeeDAO employeeDAO = null;
  @Inject
  public HelloWorldDAOResource(EmployeeDAO employeeDAO){
    this.employeeDAO = employeeDAO;
  }


  @GET
  @Path("/employees")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Employee> getEmployees(){
    List<Employee> employees = this.employeeDAO.findAll();
    return employees;
  }

  @GET
  @Path("/employees/{employee_id}")
  @Produces(MediaType.APPLICATION_JSON)
  public EmployeeResponse fetchEmployee(@PathParam("employee_id") long employeeId) throws WebApplicationException{

    List<Employee> employees = this.employeeDAO.fetchEmployeeById(employeeId);
    Response.Status status = Response.Status.OK;
    if(employees.size()==0){
      status = Response.Status.NOT_FOUND;
      throw new WebApplicationException(Response.status(status).entity("Employee with id "+employeeId+" not found").build());
    }

    EmployeeResponse response = new EmployeeResponse();
    response.setStatus(status.getStatusCode());
    response.setEmployees(employees);

    return response;
  }

  @POST
  @Path("/employees")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addEmployee(Employee employee) throws WebApplicationException{
    Long persistedEmp = this.employeeDAO.add(employee);
    String uri = "/employees/"+persistedEmp;
    Response response = Response.status(HttpServletResponse.SC_OK).entity(
        "\"uri\":" + "\"" + uri + "\"").build();
    return response;
  }
}
