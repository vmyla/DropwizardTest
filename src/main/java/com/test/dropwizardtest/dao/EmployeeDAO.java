package com.test.dropwizardtest.dao;

import com.google.inject.Inject;

import com.test.dropwizardtest.bean.Employee;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * Created by venkateswara.km on 29/01/16.
 */
public class EmployeeDAO {

  SessionFactory sessionFactory = null;

  private Provider<EntityManager> entityManagerProvider = null;
  @Inject
  public EmployeeDAO(Provider<EntityManager> entityManagerProvider){
    this.entityManagerProvider = entityManagerProvider;
  }
  public EmployeeDAO(SessionFactory sessionFactory){
    //super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public Long add(Employee employee){
    EntityManager entityManager = entityManagerProvider.get();
    Session currentSession = ((Session)entityManager.getDelegate()).getSessionFactory().openSession();
    Long objId = (Long)currentSession.save(employee);
    return objId;
  }

  public List<Employee> fetchEmployeeById(long id){
    EntityManager entityManager = entityManagerProvider.get();
    Query query = entityManager.createQuery("select c from Employee c where id=:employeeId");
    query = query.setParameter("employeeId",id).setMaxResults(1);
    List<Employee> emp = query.getResultList();
    return emp;
  }


  public List<Employee> findAll(){
//    Session session = this.sessionFactory.openSession();
    EntityManager entityManager = entityManagerProvider.get();
    Query query = entityManagerProvider.get().createQuery("select c from Employee c");

   // List<Employee> employees = session.createQuery("select c from Employee c").list();
    List<Employee> employees = query.getResultList();
    return employees;
  }
}
