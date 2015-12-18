package it.screwdrivers.payroll.testENGINE;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.PayEngine;
import it.screwdrivers.payroll.engine.PayEngineFactory;
import it.screwdrivers.payroll.engine.SalariedPayEngine;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;
import it.screwdrivers.payroll.pojo.historical.HistoricalSalary;
import it.screwdrivers.payroll.testsDB.ArquillianTest;

@RunWith(Arquillian.class)
public class PayEmployees extends ArquillianTest {
	@Inject
	HistoricalSalaryDao hs_dao;
	
	@Inject
	PayEngineFactory pay_engine_factory;
	@Inject
	PayEngine pay_engine;

	@Test
	public void PaymentSalaried(){
		
		int count = 0;
		
		//pagamento dei salaried
		pay_engine = pay_engine_factory.getPayEngine("SalariedEmployee");
		
		//System.out.println(pay_engine.toString());
		pay_engine.pay();
		
		
//		List<SalariedEmployee> s_employees = e_dao.findAllSalaried();
//		System.out.println(s_employees.size());
		
//		for(SalariedEmployee s : s_employees){
//			System.out.println(s.getName());
//		}
		
		//verifica avvenuto pagamento
//		List<HistoricalSalary> hs = hs_dao.findAll();
//		System.out.println(hs.size());
		
//		for(HistoricalSalary h: hs){
//			
//			if(h.getEmployee().getClass().getSimpleName().equals("SalariedEmployee")||
//			   h.getEmployee().getClass().getSimpleName().equals("CommissionedEmployee")){
//				count++;
//				System.out.println(h.getId()+" "+h.getAmount()+" "+h.getEmployee().getName()+" "+ h.getDate());
//			}
//		}
//		
//		List<SalariedEmployee> salaried_employees = e_dao.findAllSalaried();
//		
//		//controllo che abbia scritto lo stesso numero di stringhe
//		boolean condition = salaried_employees.size() == count ;
//		
//		assertTrue("Test if i add on the db rows for each salariedpay",condition);
		
		
	}

}
