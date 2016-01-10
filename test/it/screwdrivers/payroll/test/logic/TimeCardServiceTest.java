package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.PaymethodDao;
import it.screwdrivers.payroll.logic.PaymethodService;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.payment.BankPaymethod;
import it.screwdrivers.payroll.model.payment.PostalPaymethod;
import it.screwdrivers.payroll.model.payment.WithDrawPaymethod;
import it.screwdrivers.payroll.test.ArquillianTest;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TimeCardServiceTest extends ArquillianTest {
	
	@Inject
	PaymethodService paymethod_service;
	
	@Inject
	EmployeeDao employee_dao;
	
	@Inject
	PaymethodDao paymethod_dao;

	@Test
	public void testSetBankPayMethod(){
		
		boolean was_updated = false;
		
		SalariedEmployee employee1 = new SalariedEmployee();
		employee1.setName("davide");
		employee1.setSurname("bonamico");
		employee1.setUsername("bonaz");
		employee1.setPassword("bingo");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setMonthly_salary(1000);
		employee_dao.add(employee1);
		
		BankPaymethod bankpaymethod = new BankPaymethod();
		bankpaymethod.setFilial("Unicredit");
		bankpaymethod.setIBAN("865657dyr1d1y1111");
		paymethod_dao.add(bankpaymethod);
		
		paymethod_service.setBankPaymethod(employee1, bankpaymethod);
		
		List<SalariedEmployee> salaried_employees = employee_dao.findAllSalaried();
		for(SalariedEmployee se : salaried_employees) {
			
			if(se.getId() == employee1.getId()) {
				if(se.getPaymethod().getId() == bankpaymethod.getId()) {
					
					was_updated = true;
				}
			}
		}
		
		employee_dao.remove(employee1);
		paymethod_dao.remove(bankpaymethod);
		
		assertTrue(was_updated);
	}
	
	@Test
	public void testSetPostalPaymethod() {
		
		boolean was_updated = false;
		
		SalariedEmployee employee1 = new SalariedEmployee();
		employee1.setName("davide");
		employee1.setSurname("bonamico");
		employee1.setUsername("bonaz");
		employee1.setPassword("bingo");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setMonthly_salary(1000);
		employee_dao.add(employee1);
		
		PostalPaymethod postalpaymethod = new PostalPaymethod();
		postalpaymethod.setRedidential_address("27050");
		paymethod_dao.add(postalpaymethod);
		
		paymethod_service.setPostalPaymethod(employee1, postalpaymethod);
		
		List<SalariedEmployee> salaried_employees = employee_dao.findAllSalaried();
		for(SalariedEmployee se : salaried_employees) {
			
			if(se.getId() == employee1.getId()) {
				if(se.getPaymethod().getId() == postalpaymethod.getId()) {
					
					was_updated = true;
				}
			}
		}
		
		employee_dao.remove(employee1);
		paymethod_dao.remove(postalpaymethod);
		
		assertTrue(was_updated);
	}
	
	@Test
	public void testSetWithDrawPaymethod() {
		
		boolean was_updated = false;
		
		SalariedEmployee employee1 = new SalariedEmployee();
		employee1.setName("davide");
		employee1.setSurname("bonamico");
		employee1.setUsername("bonaz");
		employee1.setPassword("bingo");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setMonthly_salary(1000);
		employee_dao.add(employee1);
		
		WithDrawPaymethod withdrawmethod = new WithDrawPaymethod();
		withdrawmethod.setHeadquarter("test_headquarter");
		paymethod_dao.add(withdrawmethod);
		
		paymethod_service.setWithDrawPaymethod(employee1, withdrawmethod);
		
		List<SalariedEmployee> salaried_employees = employee_dao.findAllSalaried();
		for(SalariedEmployee se : salaried_employees) {
			
			if(se.getId() == employee1.getId()) {
				if(se.getPaymethod().getId() == withdrawmethod.getId()) {
					
					was_updated = true;
				}
			}
		}
		
		employee_dao.remove(employee1);
		paymethod_dao.remove(withdrawmethod);
		
		assertTrue(was_updated);
	}
	
	@Test
	public void testIsPaymethodSet() {
		
		ContractorEmployee employee1 = new ContractorEmployee();
		employee1.setName("andrea");
		employee1.setSurname("mognaschi");
		employee1.setUsername("munci");
		employee1.setPassword("munci");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setHourly_rate(10);
		employee_dao.add(employee1);
		
		SalariedEmployee employee2 = new SalariedEmployee();
		employee2.setName("davide");
		employee2.setSurname("bonamico");
		employee2.setUsername("bonaz");
		employee2.setPassword("bingo");
		employee2.setE_mail("a@bi.it");
		employee2.setPhone_number("3331112233");
		employee2.setPostal_address("via roma 1");
		employee2.setMonthly_salary(1000);
		employee_dao.add(employee2);
		
		WithDrawPaymethod withdrawmethod = new WithDrawPaymethod();
		withdrawmethod.setHeadquarter("test_headquarter");
		paymethod_dao.add(withdrawmethod);
		
		paymethod_service.setWithDrawPaymethod(employee2, withdrawmethod);
		
		boolean is_paymethod_set_for_employee_2 = paymethod_service.isPaymethodSet(employee2);
		boolean is_paymethod_set_for_employee_1 = paymethod_service.isPaymethodSet(employee1);
		
		employee_dao.remove(employee1);
		employee_dao.remove(employee2);
		paymethod_dao.remove(withdrawmethod);
		
		assertTrue(is_paymethod_set_for_employee_2);
		assertTrue(!is_paymethod_set_for_employee_1);
	}
	
	@Test 
	public void testGetPaymethodType() {
		
		BankPaymethod bankpaymethod = new BankPaymethod();
		bankpaymethod.setFilial("Unicredit");
		bankpaymethod.setIBAN("865657dyr1d1y1111");
		
		PostalPaymethod postalpaymethod = new PostalPaymethod();
		postalpaymethod.setRedidential_address("27050");
		
		WithDrawPaymethod withdrawmethod = new WithDrawPaymethod();
		withdrawmethod.setHeadquarter("test_headquarter");
		
		assertEquals(paymethod_service.getPaymethodType(bankpaymethod), "Bank account");
		assertEquals(paymethod_service.getPaymethodType(postalpaymethod), "Postal account");
		assertEquals(paymethod_service.getPaymethodType(withdrawmethod), "WithDraw account");
		
		paymethod_dao.remove(postalpaymethod);
		paymethod_dao.remove(bankpaymethod);
		paymethod_dao.remove(withdrawmethod);
	}
}
