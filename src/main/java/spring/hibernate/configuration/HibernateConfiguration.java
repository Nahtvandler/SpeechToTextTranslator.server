package spring.hibernate.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("spring/hibernate")
@ComponentScan("spring")
//@PropertySource("classpath:props.properties")
public class HibernateConfiguration {


    @Bean
    public AbstractPlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

//    @Bean
//    public SessionFactory sessionFactory() {
//        SessionFactory sessionFactory = new org.spring.hibernate.cfg.Configuration().configure().buildSessionFactory();
//        return sessionFactory;
//    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setDataSource(dataSource());
        bean.setPersistenceUnitName("myPersistenceUnit");
        bean.setPackagesToScan("spring");

        //final Properties props = new Properties();
        //props.setProperty("spring.hibernate.dialect", "org.spring.hibernate.dialect.Oracle10gDialect");
//        props.setProperty("spring.hibernate.hbm2ddl.auto", env.getProperty("spring.hibernate.hbm2ddl.auto"));
        //props.setProperty("spring.hibernate.show_sql", "false");
//        props.setProperty("spring.hibernate.format_sql", env.getProperty("spring.hibernate.format_sql"));
//
        //bean.setJpaProperties(props);
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);


        //DataSource dataSource = dsLookup.getDataSource("jdbc/gba_ds_global");
        DataSource dataSource = dsLookup.getDataSource("jdbc/dbhtest");
        return dataSource;
    }
}
