package com.Desert.Configuration;

import javafx.scene.Group;
import javafx.scene.Scene;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:dpapp.properties")
public class DSConfig {

    @Value("${app.width}")
    private double width;
    @Value("${app.height}")
    private double height;

    @Value("classpath:styles.css")
    private Resource styleRsrc;
    @Value("classpath:dspace.db")
    private Resource DBRsrc;
    @Value("classpath:hibernate.properties")
    private Resource HBNRsrc;

    @Bean
    public Scene scene() throws IOException {
        Scene scene = new Scene(new Group(), width, height);
        scene.getStylesheets().add(styleRsrc.getURI().toString());
        return scene;
    }

    @Bean
    public DataSource dataSource() throws IOException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(org.sqlite.JDBC.class.getName());
        dataSource.setUrl("jdbc:sqlite:" + DBRsrc.getFile().getAbsolutePath());
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws IOException {
        Properties properties = new Properties();
        properties.load(HBNRsrc.getInputStream());

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setHibernateProperties(properties);
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.Desert.Entity");
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}
