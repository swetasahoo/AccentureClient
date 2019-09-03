package com.niit.config;


import java.util.Properties;


import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.model.Category;
import com.niit.model.Product;
import com.niit.model.Seller;
import com.niit.model.User;

import org.apache.commons.dbcp.BasicDataSource;

@Configuration
@EnableTransactionManagement   //commit // rollback
@ComponentScan("com.niit")
public class DBconfig {
//to create beans
	
	@Bean(name="dataSource")
	public DataSource getDataSource()
	{
		BasicDataSource dataSource=new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/aamir");
		dataSource.setUsername("sa");
		dataSource.setPassword("aamir");
		
		return dataSource;
	}
	
	private Properties getHibernateProperties()
	{
		Properties properties=new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		
		return properties;
	}
	
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) 
	{
    	LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
    	sessionBuilder.addProperties(getHibernateProperties());
    	sessionBuilder.addAnnotatedClasses(User.class);
    	sessionBuilder.addAnnotatedClasses(Category.class);
    	sessionBuilder.addAnnotatedClasses(Product.class);
    	sessionBuilder.addAnnotatedClasses(Seller.class);
    	//sessionBuilder.addAnnotatedClass(UserDetails.class);
    	return sessionBuilder.buildSessionFactory();
    }
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);

		return transactionManager;
	}
	
/*	@Autowired
    @Bean(name = "userDAO")
    public UserDao getUserDao(SessionFactory sessionFactory) 
	{
    	return new UserDaoImpl(sessionFactory);
    }*/
	
	/*
	
	@Autowired
    @Bean(name = "categoryDAO")
    public CategoryDAO getCategoryDao(SessionFactory sessionFactory) 
	{
    	return new CategoryDAOImpl(sessionFactory);
    }
	
	@Autowired
    @Bean(name = "productDAO")
    public ProductDAO getProductDao(SessionFactory sessionFactory) 
	{
    	return new ProductDAOImpl(sessionFactory);
    }

	@Autowired
    @Bean(name = "supplierDAO")
    public SupplierDAO getSupplierDao(SessionFactory sessionFactory) 
	{
    	return new SupplierDAOImpl(sessionFactory);
    }

	@Autowired
	@Bean(name="userDetailsDAO")
	public UserDetailsDAO getUserDetailsDao(SessionFactory sessionFactory)
	{
		return new UserDetailsDAOImpl(sessionFactory);
	}*/
}