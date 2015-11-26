package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import it.screwdrivers.payroll.pojo.payment.Paymethod;
import it.screwdrivers.payroll.pojo.payment.PostalPaymethod;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PaymentTest extends ArquillianTest {

	@Inject
	PaymethodDao paymethod_dao;
	@Inject
	EmployeeDao employee_dao;

	// This method will test the presence of a new BankPaymethod object
	// in the db, after we have added it.
	@Test
	public void testAddingBankPaymethod() {

		// =============Add BankPaymethod object==============
		BankPaymethod bankpaymethod = new BankPaymethod();
		bankpaymethod.setFilial("Unicredit");
		bankpaymethod.setIBAN("865657dyr1d1y1111");

		paymethod_dao.add(bankpaymethod);
		// ===================================================

		// ==========BankPaymethod object retrieval===========
		List<Paymethod> paymethods = paymethod_dao.findAll();
		Boolean isAdded = false;

		for (Paymethod paymethod : paymethods) {

			if (paymethod.getId() == bankpaymethod.getId()) {
				// set the flag if is retrieved
				isAdded = true;
				paymethod_dao.remove(bankpaymethod);
			}
		}
		// ===================================================

		assertTrue("Paymethod object was correctly added to the db!", isAdded);
	}

	// This method tests if a Paymethod object, already in the db, is correctly
	// referenced
	// in a given employee record.
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

		// create BankPaymethod
		BankPaymethod bankpaymethod = new BankPaymethod();
		bankpaymethod.setFilial("Unicredit");
		bankpaymethod.setIBAN("865657dyr1d1y1111");
		paymethod_dao.add(bankpaymethod);

		// update employee record
		ce.setPaymethod(bankpaymethod);
		employee_dao.update(ce);

		boolean condition = ce.getPaymethod().getId() == bankpaymethod.getId();

		assertTrue(
				"Paymethod object is correctly referenced inside Employee record!",
				condition);

		employee_dao.remove(ce);
		paymethod_dao.remove(bankpaymethod);
	}

	@Test
	public void testThatPaymethodWasUpdatedCorrectly() {

		// create an employee
		ContractorEmployee ce = new ContractorEmployee();
		ce.setName("Eric");
		ce.setSurname("Villa");
		ce.setPhone_number("3334455666");
		ce.setE_mail("eric@email.de");
		ce.setPostal_address("Via Roma 62/3");
		ce.setHourly_rate(10);
		employee_dao.add(ce);

		// create BankPaymethod
		BankPaymethod bankpaymethod = new BankPaymethod();
		bankpaymethod.setFilial("Unicredit");
		bankpaymethod.setIBAN("865657dyr1d1y1111");
		paymethod_dao.add(bankpaymethod);

		// update employee record
		ce.setPaymethod(bankpaymethod);
		employee_dao.update(ce);
		
		// NB: before removing BankPaymethod from the db, we
		// have to update the Employee's foreign key by 
		// setting the new paymethod; otherwise, the Employee
		// record will reference a Paymethod that is no more in the db.

		// create BankPaymethod
		PostalPaymethod postalpaymethod = new PostalPaymethod();
		postalpaymethod.setRedidential_address("27050");
		
		paymethod_dao.add(postalpaymethod);
		
		// update employee record
		ce.setPaymethod(postalpaymethod);
		employee_dao.update(ce);
		paymethod_dao.remove(bankpaymethod);
		
		boolean condition_2 = ce.getPaymethod().getId() != bankpaymethod.getId() && (ce.getPaymethod() instanceof PostalPaymethod);
		
		assertTrue(condition_2);
		
		employee_dao.remove(ce);
		paymethod_dao.remove(postalpaymethod);
	}
}