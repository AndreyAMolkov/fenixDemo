package demo.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import demo.constant.Constants;
import demo.dao.Dao;
import demo.dao.DaoDay;
import demo.dao.DaoEntity;
import demo.dao.DaoImp;
import demo.dao.PrepareStaff;
import demo.model.City;
import demo.model.Coach;
import demo.model.Day;
import demo.model.Employee;
import demo.model.HalfYear;
import demo.model.InfoMessage;
import demo.model.Month;
import demo.model.Quarter;
import demo.model.Store;
import demo.model.Year;

@Configuration
@EnableWebMvc
@ComponentScan("demo.*")
@EnableTransactionManagement
//@Import({ FormLoginSecurityConfig.class })
public class ApplicationContextConfig implements TransactionManagementConfigurer {
	private static Logger log = LoggerFactory.getLogger("demo.controller.ApplicationContextConfig");

	@Bean(name = "prepareStaff")
	public PrepareStaff getPrepareStaff() {
		return new PrepareStaff(getDao());
	}

	@Bean(name = "listCoaches")
	public List<Coach> getListCoaches() {
		return new ArrayList<>();
	}

	@Bean(name = "listStores")
	public List<Store> getListStores() {
		return new ArrayList<>();
	}

	@Bean(name = "listCities")
	public List<City> getListCities() {
		return new ArrayList<>();
	}

	@Bean(name = "listEmployees")
	public List<Employee> getListEmployees() {
		return new ArrayList<>();
	}

	@Bean(name = "listDays")
	public List<Day> getListDays() {
		return new ArrayList<>();
	}
	@Bean(name = "listMonths")
	public List<Month> getListMonths() {
		return new ArrayList<>();
	}
	@Bean(name = "listQuarters")
	public List<Quarter> getListQuarters() {
		return new ArrayList<>();
	}
	@Bean(name = "listHalfYears")
	public List<HalfYear> getListHalfYears() {
		return new ArrayList<>();
	}
	@Bean(name = "listYears")
	public List<Year> getListYears() {
		return new ArrayList<>();
	}

	@Bean(name = "infoMessage")
	public InfoMessage getInfoMessage() {
		return new InfoMessage();
	}

	@Bean(name = "model")
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ModelAndView getModel() {
		return new ModelAndView();
	}

	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean(name = "dataSourse")
	@Primary
	public DataSource getDataSource() {
		String nameMethod = "DataSource";
		DriverAdapterCPDS cpds = new DriverAdapterCPDS();
		try {
			cpds.setDriver("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, Constants.ERROR, e);
		}
		cpds.setUrl("jdbc:mysql://localhost:3306/fenix?useSSL=false&allowPublicKeyRetrieval=true");
		cpds.setUser("root");
		cpds.setPassword("1234");
		SharedPoolDataSource tds = new SharedPoolDataSource();
		tds.setConnectionPoolDataSource(cpds);
		tds.setMaxTotal(10);
		tds.setMaxConnLifetimeMillis(50);

		return tds;

	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addAnnotatedClass(DaoEntity.class);
		sessionBuilder.addAnnotatedClass(City.class);
		sessionBuilder.addAnnotatedClass(Coach.class);
		sessionBuilder.addAnnotatedClass(Day.class);
		sessionBuilder.addAnnotatedClass(Employee.class);
		sessionBuilder.addAnnotatedClass(HalfYear.class);
		sessionBuilder.addAnnotatedClass(Month.class);
		sessionBuilder.addAnnotatedClass(Quarter.class);
		sessionBuilder.addAnnotatedClass(Store.class);
		sessionBuilder.addAnnotatedClass(Year.class);
		sessionBuilder.setProperty("hibernate.dialect", "MySQL57InnoDB");
		sessionBuilder.setProperty("hibernate.show_sql", "false");
		sessionBuilder.setProperty("hibernate.hbm2ddl.auto", "update");
		sessionBuilder.setProperty("hibernate.use_sql_comments", "false");
		sessionBuilder.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "dao")
	public Dao<T> getDao() {
		return new DaoImp();
	}

	@Bean(name = "dayD")
	public Dao<Day> getDaoDay() {
		return new DaoDay();
	}
	@Override
	@Bean(name = "transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory(getDataSource()));
		return transactionManager;
	}
}