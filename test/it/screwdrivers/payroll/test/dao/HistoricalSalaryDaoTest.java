package it.screwdrivers.payroll.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.utility.PayrollCalendar;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;
import it.screwdrivers.payroll.test.ArquillianTest;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HistoricalSalaryDaoTest extends ArquillianTest {

	@Inject
	HistoricalSalaryDao hs_dao;

	@Inject
	EmployeeDao e_dao;

	PayrollCalendar pc = new PayrollCalendar();

	@Test
	public void testAddHistoricalSalaryDao() {

		boolean was_added = false;

		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);
		e_dao.add(employee_manager);

		HistoricalSalary historical_salary = new HistoricalSalary();
		historical_salary.setDate(pc.getToday());
		historical_salary.setCommission(true);
		historical_salary.setAmount(99999);
		historical_salary.setEmployee(employee_manager);
		hs_dao.add(historical_salary);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {
			if (hs.getAmount() == 99999) {

				was_added = true;

				hs_dao.remove(hs);
				e_dao.remove(employee_manager);
			}
		}

		assertTrue(was_added);
	}

	@Test
	public void testFindAllHistoricalSalaryDao() {
		
		// Initial hypothesis: empty HistoricalSalary table

		boolean test = false;

		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);
		e_dao.add(employee_manager);

		HistoricalSalary historical_salary = new HistoricalSalary();
		historical_salary.setDate(pc.getToday());
		historical_salary.setCommission(true);
		historical_salary.setAmount(99999);
		historical_salary.setEmployee(employee_manager);
		hs_dao.add(historical_salary);

		HistoricalSalary historical_salary2 = new HistoricalSalary();
		historical_salary2.setDate(pc.getToday());
		historical_salary2.setCommission(true);
		historical_salary2.setAmount(99999);
		historical_salary2.setEmployee(employee_manager);
		hs_dao.add(historical_salary2);

		HistoricalSalary historical_salary3 = new HistoricalSalary();
		historical_salary3.setDate(pc.getToday());
		historical_salary3.setCommission(true);
		historical_salary3.setAmount(99999);
		historical_salary3.setEmployee(employee_manager);
		hs_dao.add(historical_salary3);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();

		if (historical_salaries.size() == 3) {
			test = true;
			hs_dao.remove(historical_salary3);
			hs_dao.remove(historical_salary2);
			hs_dao.remove(historical_salary);
			e_dao.remove(employee_manager);
		}

		assertTrue(test);
	}

	@Test
	public void testRemoveHistoricalSalaryDao() {
		
		// Initial hypothesis: empty HistoricalSalary table

		boolean was_removed = false;

		int historical_salaries_size_1;
		int historical_salaries_size_2;

		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);
		e_dao.add(employee_manager);

		HistoricalSalary historical_salary = new HistoricalSalary();
		historical_salary.setDate(pc.getToday());
		historical_salary.setCommission(true);
		historical_salary.setAmount(99999);
		historical_salary.setEmployee(employee_manager);
		hs_dao.add(historical_salary);

		historical_salaries_size_1 = hs_dao.findAll().size();

		if (historical_salaries_size_1 > 0)
			hs_dao.remove(historical_salary);

		historical_salaries_size_2 = hs_dao.findAll().size();

		if (historical_salaries_size_2 < historical_salaries_size_1) {

			was_removed = true;
			hs_dao.remove(historical_salary);
			e_dao.remove(employee_manager);
		}

		assertTrue(was_removed);
	}

	@Test
	public void testUpdateHistoricalSalaryDao() {

		boolean was_updated = false;

		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);
		e_dao.add(employee_manager);

		HistoricalSalary historical_salary = new HistoricalSalary();
		historical_salary.setDate(pc.getToday());
		historical_salary.setCommission(true);
		historical_salary.setAmount(99999);
		historical_salary.setEmployee(employee_manager);
		hs_dao.add(historical_salary);

		historical_salary.setAmount(99998);
		hs_dao.update(historical_salary);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getId() == employee_manager.getId()) {
				if(hs.getAmount() == 99998){
					 
					was_updated = true;
					hs_dao.remove(historical_salary);
					e_dao.remove(employee_manager);
				}
			}
		}

		assertTrue(was_updated);
	}
}
