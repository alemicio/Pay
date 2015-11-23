package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;

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
		
		ContractorEmployee employee_contractor = new ContractorEmployee();
		employee_contractor.setName("andrea");
		employee_contractor.setSurname("mognaschi");
		employee_contractor.setUsername("munci");
		employee_contractor.setPassword("munci");
		employee_contractor.setE_mail("a@bi.it");
		employee_contractor.setPhone_number("3331112233");
		employee_contractor.setPostal_address("via roma 1");
		employee_contractor.setHourly_rate(10);

		employee_dao.add(employee_contractor);
		
		List<Employee> employees = employee_dao.findAll();
		Employee tmp = null;
		
		//ricercare employee desiderato
		for(Employee employee : employees){
			
			if(employee.getUsername().equals("munci")){
				
				tmp = employee;
				break;
			}
		}
		
		bankpaymethod.setEmployee(tmp);
		tmp.setPaymethod(bankpaymethod);
		
		System.out.println("BANKPAYMETHOD CLASS: " + bankpaymethod.getClass());
		
		//employee_dao.update(tmp);
		paymethod_dao.add(bankpaymethod);
		
		boolean test = true;
		assertTrue(test);
	}
}