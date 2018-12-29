package demo.dao.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import demo.constant.Constants;
import demo.dao.Dao;
import demo.dao.DaoEntity;
import demo.dao.DaoImp;
import demo.dao.PrepareStaff;
import demo.model.City;
import demo.model.Coach;
import demo.model.Day;
import demo.model.Employee;
import demo.model.Month;
import demo.model.Store;

public class BaseDaoTest {
	private static Logger log = LoggerFactory.getLogger("test.PrepareStafTest");

	@InjectMocks
	private DaoImp daoInject;

	private Dao dao;
	private EntityTransaction transaction;
	@Spy
	private PrepareStaff prepareStaffSpy;
	@InjectMocks
	private PrepareStaff prepareStaffInject;

	@PersistenceContext
	private EntityManager em;

	private PlatformTransactionManager transactionManager;

	@Before
	public void setUpBeforeClass() throws Exception {
		String nameMethod = "setUpBeforeClass";
		DriverAdapterCPDS cpds = new DriverAdapterCPDS();
		try {
			cpds.setDriver("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, "Error set driver for AdapterCPDS", e);
		}
		cpds.setUrl("jdbc:mysql://localhost:3306/test_fenix?useSSL=false");
		cpds.setUser("root");
		cpds.setPassword("1234");
		SharedPoolDataSource tds = new SharedPoolDataSource();
		tds.setConnectionPoolDataSource(cpds);
		tds.setMaxTotal(10);
		tds.setMaxConnLifetimeMillis(50);
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(tds);
		sessionBuilder.addAnnotatedClass(DaoEntity.class);
		sessionBuilder.addAnnotatedClass(City.class);
		sessionBuilder.addAnnotatedClass(Coach.class);
		sessionBuilder.addAnnotatedClass(Day.class);
		sessionBuilder.addAnnotatedClass(Employee.class);
//		sessionBuilder.addAnnotatedClass(HalfYear.class);
		sessionBuilder.addAnnotatedClass(Month.class);
//		sessionBuilder.addAnnotatedClass(Quarter.class);
		sessionBuilder.addAnnotatedClass(Store.class);
//		sessionBuilder.addAnnotatedClass(Year.class);
		sessionBuilder.setProperty("hibernate.dialect", "MySQL57InnoDB");
		// sessionBuilder.setProperty("hibernate.show_sql", "true");
		sessionBuilder.setProperty("hibernate.hbm2ddl.auto", "update");
		// sessionBuilder.setProperty("hibernate.use_sql_comments", "true");
		SessionFactory sessionFactory = sessionBuilder.buildSessionFactory();
		try (Session session = sessionFactory.openSession()) {
			this.em = session.getEntityManagerFactory().createEntityManager();

			HibernateTransactionManager transactionManagerHib = new HibernateTransactionManager();
			transactionManagerHib.setSessionFactory(sessionFactory);
			this.transactionManager = transactionManagerHib;
			DaoImp daoImp = new DaoImp();
			daoImp.setEntityManager(em);
			this.dao = daoImp;
			this.transaction = daoImp.getEntityManager().getTransaction();
LocalDate date = LocalDate.now();
dao.add(new Day(date.plusYears(1),new Employee()));

		} catch (Exception e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, "Error session", e);
		}

	}

	@After
	public void tearDownAfterClass() throws Exception {
		em.close();
	}

	@Test
	public void testGetEntityById_IdfromCuctomEmployee_find() {
		String nameMethod = "testUpdate_AddNewForNAme_Update";
		Employee emp = getEmployee();
		Employee updateEmpl = null;
		transaction.begin();

		try {
			dao.add(emp);

			updateEmpl = (Employee) dao.update(emp);
			Long id = updateEmpl.getId();

			Employee actualEmpl = (Employee) dao.getEntityById(Employee.class, id);
			transaction.commit();

			assertTrue(actualEmpl != null);
		} catch (Exception e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, e);
			fail();
		} finally {
			removeEmployee(updateEmpl);
		}

	}

	@Test
	public void testUpdate_AddNewForName_Update() {
		String nameMethod = "testUpdate_AddNewForNAme_Update";
		Employee emp = getEmployee();
		String oldName = emp.getName();
		Employee actualEmpl = null;
		transaction.begin();
		try {
			dao.add(emp);

			Employee updateEmpl = (Employee) dao.update(emp);
			Long id = updateEmpl.getId();
			String name = updateEmpl.getName();
			updateEmpl.setName("new" + name);
			transaction.commit();

			transaction.begin();
			actualEmpl = (Employee) dao.getEntityById(Employee.class, id);
			transaction.commit();
			String actualName = actualEmpl.getName();

			assertFalse(oldName.equals(actualName));
		} catch (Exception e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, e);
			fail();
		} finally {
			removeEmployee(actualEmpl);
		}

	}

	@Test
	public void testAdd_AddOne_SizeAfterMinusBeforeEqualsOne() {
		String nameMethod = "testAdd_AddOne_SizeAfterMinusBeforeEqualsOne";
		Employee emp = getEmployee();
		transaction.begin();
		try {
			int before = dao.getAll(Employee.class).size();

			dao.add(emp);

			transaction.commit();
			int after = dao.getAll(Employee.class).size();

			assertTrue((after - before) == 1);
		} catch (Exception e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, e);
			fail();
		} finally {
			removeEmployee(emp);
		}

	}

	@Test
	public void testFindEntityByAttributeString_FindByName_ListOfObjects() {
		String nameMethod = "testFindEntityByAttribute_FindByIdFrofFile_Find";
		Employee emp = getEmployee();
		String nameExpected = emp.getName();
		transaction.begin();
		Employee updateEmpl = null;
		try {
			dao.add(emp);
			transaction.commit();
			transaction.begin();
			
			List<Object> actualEmpl = dao.findEntityByAttributeOfString(Employee.class, "name", nameExpected);
			
			transaction.commit();
			String nameActual = null;
			for(Object employee:actualEmpl){
				nameActual=((Employee)employee).getName();
				assertTrue(nameActual.equals(nameExpected));
			}
			
		} catch (Exception e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, e);
			fail();
		} finally {
			removeEmployee(updateEmpl);
		}

	}

	private Employee getEmployee() {
		String name = "Alex";
		String phone = "78945";
		String status = "stager";
		Coach coach = new Coach("Lina");
		LocalDate dismissal = LocalDate.now();
		dismissal.minusYears(1L);
		LocalDate dateEmployement = LocalDate.now();
		dateEmployement.minusYears(2L);
		City city = new City("Kaluga");
		Store store = new Store("Plaza");
		String id = "13";
		return new Employee(name, phone, status, coach, dateEmployement, dismissal, city, store, id);
	}

	private void removeEmployee(Employee emp) {
		String nameMethod = "removeEmployee";
		if (!transaction.isActive())
			transaction.begin();
		try {
			dao.remove(emp);
		} catch (Exception e) {
			log.error(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, e);
		}
		transaction.commit();
	}

}
