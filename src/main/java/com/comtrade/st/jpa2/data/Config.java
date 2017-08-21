/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comtrade.st.jpa2.data;

import com.comtrade.st.jpa2.*;
import java.io.IOException;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 *
 * @author radoo
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "emfactory")/**/
@ComponentScan("com.comtrade.st.jpa2")
public class Config {

	/**
	 * <p>
	 * init ds
	 * </p>
	 *
	 * @param param param_desc
	 * @return db Changes:
	 * @author Rado 11.8.2017 created
	 */

	@Bean("dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:file:d:/Delo/Sw/Java/ST/ST/src/main/resources/h2test;DB_CLOSE_ON_EXIT=FALSE");
		return dataSource;
	}//end datasource		

	/**
	 * <p>
	 * EntityManagerFactory bean
	 * </p>
	 *
	 * @param param param_desc
	 * @return EntityManagerFactory Changes:
	 * @author author_name date created
	 */
	@Bean("emfactory")
	public LocalContainerEntityManagerFactoryBean  emfactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, Properties hibernateProperties) throws IOException {
		LocalContainerEntityManagerFactoryBean emfactory = new LocalContainerEntityManagerFactoryBean();
		emfactory.setDataSource(dataSource);
		emfactory.setJpaVendorAdapter(jpaVendorAdapter);
		emfactory.setJpaProperties(hibernateProperties);
		//emfactory.setPackagesToScan("com.comtrade.st.jpa2");
		return emfactory;
	}//end LocalContainerEntityManagerFactoryBean

	public EntityManagerFactory emf(LocalContainerEntityManagerFactoryBean emfactory){
		return emfactory.getObject();
	}

	@Bean("jpaVendorAdapter")
	JpaVendorAdapter  jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter  = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.H2);
		return hibernateJpaVendorAdapter;
	}

	@Bean("transactionManager")
	JpaTransactionManager transactionManager (EntityManagerFactory emf, DataSource dataSource){
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();	
		jpaTransactionManager.setDataSource(dataSource);
		jpaTransactionManager.setEntityManagerFactory(emf);
		return jpaTransactionManager;
	}

	@Bean("hibernateProperties")
	Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("connection.driver_class", "org.h2.Driver");
		properties.setProperty("connection.url", "jdbc:h2:file:d:/Delo/Sw/Java/ST/ST/src/main/resources/h2test;DB_CLOSE_ON_EXIT=FALSE");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.setProperty("hibernate.max_fetch_depth", "3");
		properties.setProperty("hibernate.jdbc.fetch_size", "5");
		properties.setProperty("hibernate.jdbc.batch_size", "10");
		properties.setProperty("hibernate.show_sql", "true");
		//properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		//properties.setProperty("hibernate.enable_lazy_load_no_trans","true");
		return properties;
	}
}//end Config
