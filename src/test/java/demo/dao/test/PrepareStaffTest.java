package demo.dao.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.constant.Constants;
import demo.dao.Dao;
import demo.dao.DaoImp;
import demo.dao.PrepareStaff;
import demo.model.Employee;

@RunWith(MockitoJUnitRunner.class)
public class PrepareStaffTest {
	private static Logger log = LoggerFactory.getLogger("test.PrepareStafTest");
	@Spy
	public PrepareStaff prepareStaffSpy;
	@Mock
	private Employee empl1;
	@Mock
	private Employee empl2;
	@Mock
	private Employee empl3;
	@Mock
	private Dao dao;
	@Mock
	private DaoImp daoImp;


	@Test
	public void testCreateStaff_NormalCase_SizeListMoreZero() {
		String nameMethod = "testCreateStaff_NormalCase_SizeListMoreZero";
		String path = "D:\\Project\\Fenix\\src\\main\\resources\\staff20.xlsx";
		int size = 0;
		try {
			PrepareStaff prepareStaff = new PrepareStaff(dao);
			List<Employee> employees = prepareStaff.createStaff(path);
			size = employees.size();
		} catch (IOException e) {
			log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, e);
		}
		assertTrue(size > 0);
	}

//	@Test
//	public void testPopulateDB_TwoTrueOneWrong_SizeOne() throws Exception {
//		List<Employee> emplList = new ArrayList<>(3);
//		emplList.add(empl1);
//		emplList.add(empl2);
//		emplList.add(empl3);
//
////		doReturn(false).when(prepareStaffSpy).notInDB(empl1);
////		doReturn(true).when(prepareStaffSpy).notInDB(empl2);
////		doReturn(false).when(prepareStaffSpy).notInDB(empl3);
////		doReturn(true).when(prepareStaffSpy).recordDB(empl2);
////		when(prepareStaffSpy.getAllEmployee()).thenReturn(emplList);
//
//		List<Employee> actualList = prepareStaffSpy.populateDB();
//
//		assertEquals(1, actualList.size());
//
//	}

//	@Test
//	public void testIsFindInDB_PositiveCase_true() throws Exception {
//		List<Object> list = new ArrayList<Object>(1);
//		empl1.setId("1");
//		list.add(empl1);
//		doReturn("1").when(empl1).getId();
//		doReturn(list).when(dao).findEntityByAttributeOfString(Employee.class, "id", "1");
//		
//		Boolean result = prepareStaffSpy.isFindInDB(empl1);
//		
//		assertTrue(result);
//	}
}
