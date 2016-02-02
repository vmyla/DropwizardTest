package com.test.dropwizardtest.config;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

/**
 * Created by venkateswara.km on 01/02/16.
 */
public class DynamicPersistenceProvider implements PersistenceProvider {

  private PersistenceProvider delegate = new HibernatePersistenceProvider();

  @Override
  public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
    // TODO(kamal) - need to load from persistence.xml
    System.out.println("I am here");
    PersistenceUnitInfo persistenceUnitInfo = new SimplePersistenceUnitInfo();
    @SuppressWarnings("unchecked")
    List<String> packagesToScan =
        (List<String>) map.remove("dynamicPersistenceProvider.packagesToScan");

    for (String packageToScan : packagesToScan) {
      persistenceUnitInfo.getManagedClassNames().addAll(getClassNames(packageToScan, Entity.class));
      persistenceUnitInfo.getManagedClassNames().addAll(getClassNames(packageToScan, Converter.class));
      persistenceUnitInfo.getMappingFileNames().addAll(getMappingFiles(packageToScan));
    }

    return delegate.createContainerEntityManagerFactory(persistenceUnitInfo, map);
  }

  private Set<String> getMappingFiles(String packageToScan) {
    Reflections reflections = new Reflections(packageToScan, new ResourcesScanner());
    return reflections.getResources(new Predicate<String>() {
      @Override
      public boolean apply(String input) {
        return input.endsWith(".hbm.xml");
      }
    });
  }

  private ArrayList<String> getClassNames(String packageToScan, Class clazzToFind) {
    Reflections reflections = new Reflections(packageToScan);
    return Lists.newArrayList(
        Iterables.transform(reflections.getTypesAnnotatedWith(clazzToFind),
                            new Function<Class<?>, String>() {
                              @Override
                              public String apply(Class<?> entityClass) {
                                return entityClass.getName();
                              }
                            }));
  }

  @Override
  public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info,
                                                                  Map map) {
    return delegate.createContainerEntityManagerFactory(info, map);
  }

  @Override
  public void generateSchema(PersistenceUnitInfo info, Map map) {
    delegate.generateSchema(info, map);
  }

  @Override
  public boolean generateSchema(String persistenceUnitName, Map map) {
    return delegate.generateSchema(persistenceUnitName, map);
  }

  @Override
  public ProviderUtil getProviderUtil() {
    return delegate.getProviderUtil();
  }
}
