package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;

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
	}
}