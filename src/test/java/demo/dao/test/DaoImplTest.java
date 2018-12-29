package demo.dao.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import demo.dao.DaoImp;
import demo.model.City;
import demo.model.Coach;
import demo.model.Day;
import demo.model.Employee;
import demo.model.HalfYear;
import demo.model.HalfYearPeriod;
import demo.model.Month;
import demo.model.Quarter;
import demo.model.QuarterPeriod;
import demo.model.Store;

@RunWith(MockitoJUnitRunner.class)
public class DaoImplTest {
	@InjectMocks
	private DaoImp daoImpl;
	@Mock
	private Employee empl1;
	@Mock
	private Employee empl2;
	@Mock
	private Employee empl3;
	@Mock
	private Coach coach1;
	@Mock
	private City city1;
	@Mock
	private Store store1;
	@Mock
	private Day day1;
	@Mock
	private Day day2;
	@Mock
	private Day day3;
	@Mock
	private Day day4;
	@Mock
	private Month month1;
	@Mock
	private Month month2;
	@Mock
	private Month month3;
	@Mock
	private Month month4;
	@Mock
	private Quarter quarter1;
	@Mock
	private Quarter quarter2;
	@Mock
	private Quarter quarter3;
	@Mock
	private Quarter quarter4;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testPopulateDay() {

		LocalDate date1 = LocalDate.now();
		Mockito.doReturn(date1.minusDays(2)).when(empl1).getDataOfEmployment();
		Mockito.doReturn(date1.minusDays(4)).when(empl2).getDataOfEmployment();
		Mockito.doReturn(date1.minusDays(6)).when(empl3).getDataOfEmployment();
		Mockito.doReturn(date1.minusDays(6)).when(empl3).getDataDismissal();
		Mockito.doReturn(coach1).when(empl1).getCoach();
		Mockito.doReturn(city1).when(empl1).getCity();
		Mockito.doReturn(store1).when(empl1).getStore();
		Mockito.doReturn(city1).when(empl2).getCity();
		Mockito.doReturn(store1).when(empl2).getStore();
		Mockito.doReturn(coach1).when(empl2).getCoach();
		Mockito.doReturn(city1).when(empl3).getCity();
		Mockito.doReturn(store1).when(empl3).getStore();
		Mockito.doReturn(coach1).when(empl3).getCoach();

		List<Employee> list = new ArrayList<>(Arrays.asList(empl1, empl2, empl3));

		List<Day> resultList = daoImpl.populateDay(list, new ArrayList<Day>());
		int sizeList = resultList.size();
		int count = resultList.stream().map(e -> e.getEmployeeListAll()).flatMap(Collection::stream)
				.collect(Collectors.toList()).size();

		assertEquals(6, sizeList);
		assertEquals(2 + (2 * 3) + (1 * 1), count);
	}

	@Test
	public void testPopulateMonth_Input4Entity_ListSizeEquals3() {
		LocalDate date1 = LocalDate.now();
		Mockito.doReturn(date1.minusMonths(2)).when(day1).getDate();
		Mockito.doReturn(date1.minusMonths(4)).when(day2).getDate();
		Mockito.doReturn(date1.minusMonths(4)).when(day4).getDate();
		Mockito.doReturn(date1.minusMonths(12 + 4)).when(day3).getDate();

		List<Day> list = new ArrayList<>(Arrays.asList(day1, day2, day3, day4));

		List<Month> resultList = daoImpl.populateMonth(list, new ArrayList<Month>());
		int sizeList = resultList.size();
		int count = resultList.stream().map(Month::getDayListAll).flatMap(Collection::stream)
				.collect(Collectors.toList()).size();

		assertEquals(3, sizeList);
		assertEquals(1 + (1 * 2) + 1, count);
	}

	@Test
	public void testPopulateQuarter_Input4Entity_ListSizeEquals3() {
		QuarterPeriod date1 = QuarterPeriod.now();
		Mockito.doReturn(date1.minusQuarters(2)).when(month1).getQuarter();
		Mockito.doReturn(date1.minusQuarters(4)).when(month2).getQuarter();
		Mockito.doReturn(date1.minusQuarters(4)).when(month4).getQuarter();
		Mockito.doReturn(date1.minusQuarters(4 + 4)).when(month3).getQuarter();

		List<Month> list = new ArrayList<>(Arrays.asList(month1, month2, month3, month4));

		List<Quarter> resultList = daoImpl.populateQuarter(list, new ArrayList<Quarter>());
		int sizeList = resultList.size();
		int count = resultList.stream().map(Quarter::getMonthListAll).flatMap(Collection::stream)
				.collect(Collectors.toList()).size();

		assertEquals(3, sizeList);
		assertEquals(1 + (1 * 2) + 1, count);
	}

	@Test
	public void testPopulateHalfYear_Input4Entity_ListSizeEquals3() {

		Mockito.doReturn(HalfYearPeriod.createHalfYear(2018, 4)).when(quarter1).getHalfYear();
		Mockito.doReturn(HalfYearPeriod.createHalfYear(2018, 1)).when(quarter2).getHalfYear();
		Mockito.doReturn(HalfYearPeriod.createHalfYear(2018, 1)).when(quarter4).getHalfYear();
		Mockito.doReturn(HalfYearPeriod.createHalfYear(2017, 1)).when(quarter3).getHalfYear();

		List<Quarter> list = new ArrayList<>(Arrays.asList(quarter1, quarter2, quarter3, quarter4));

		List<HalfYear> resultList = daoImpl.populateHalfYear(list, new ArrayList<HalfYear>());
		int sizeList = resultList.size();
		int count = resultList.stream().map(HalfYear::getQuarterListAll).flatMap(Collection::stream)
				.collect(Collectors.toList()).size();

		assertEquals(3, sizeList);
		assertEquals(1 + (1 * 2) + 1, count);
	}

}
