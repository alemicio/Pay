package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.assertTrue;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.union.Union;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UnionTest extends ArquillianTest {

	@Inject
	UnionDao union_dao;

	@Inject
	EmployeeDao employee_dao;

	@Test
	public void TestAddingNewUnion() {

		// create a new Union object to persist in the db.
		Union union = new Union();
		union.setName("CISL");
		union.setPhone_number("3388194740");

		union_dao.add(union);

		// check if the new Union is correctly added, then remove it
		List<Union> unions = union_dao.findAll();
		boolean isAdded = false;

		for (Union u : unions) {

			// check if it is the union i recently added
			if (u.getId() == union.getId()) {
				isAdded = true;
			}
		}
		assertTrue("Union correctly added in the db", isAdded);

		// finally i remove it from the db
		union_dao.remove(union);

	}

	@Test
	public void testThatPaymethodReferenceIsAddedToEmployee() {

		// create an employee
		ContractorEmployee ce = new ContractorEmployee();
		ce.setName("Eric");
		ce.setSurname("Villa");
		ce.setPhone_number("3334455666");
		ce.setE_mail("eric@email.de");
		ce.setPostal_address("Via Roma 62/3");
		ce.setHourly_rate(10);
		employee_dao.add(ce);

		// create a new Union object to persist in the db.
		Union union = new Union();
		union.setName("CISL");
		union.setPhone_number("3388194740");
		union_dao.add(union);

		// update employye record with the union reference setted.
		ce.setUnion(union);
		employee_dao.update(ce);

		boolean condition = ce.getUnion().getId() == union.getId();

		assertTrue(
				"Union object is correctly referenced inside Employee record!",
				condition);

		employee_dao.remove(ce);
		union_dao.remove(union);
	}

}
