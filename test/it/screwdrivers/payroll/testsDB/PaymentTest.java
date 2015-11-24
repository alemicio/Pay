package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import it.screwdrivers.payroll.pojo.payment.Paymethod;

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

	@Test
	public void testAddingBankPaymethod() {

		BankPaymethod bankpaymethod = new BankPaymethod();
		bankpaymethod.setFilial("Unicredit");
		bankpaymethod.setIBAN("45647fg3gckw57");

		paymethod_dao.add(bankpaymethod);

		List<Paymethod> paymethods = paymethod_dao.findAll();
		Boolean isAdded = false;

		for (Paymethod paymethod : paymethods) {

			if (paymethod.getId() == 7) {
				// set the flag if is retrieved
				isAdded = true;
			}
		}
		assertTrue("Aggiunto nuovo metodo di pagamento ", isAdded);

	}

	@Test
	public void testThatPaymethodReferenceIsAddedToEmployee() {

		List<Employee> employees = employee_dao.findAll();

		// employee that we are looking for
		Employee tmp = null;

		for (Employee employee : employees) {

			if (employee.getId() == 6) {

				// employee found
				tmp = employee;
			}
		}

		List<Paymethod> paymethods = paymethod_dao.findAll();


		for (Paymethod p : paymethods) {

			if (p.getId() == 7) {

				// employee found
				tmp = employee;
			}
		}

	}
}