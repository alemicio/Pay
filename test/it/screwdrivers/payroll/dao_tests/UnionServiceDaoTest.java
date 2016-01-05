package it.screwdrivers.payroll.dao_tests;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.ArquillianTest;
import it.screwdrivers.payroll.dao.UnionServiceDao;
import it.screwdrivers.payroll.model.union.UnionService;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UnionServiceDaoTest extends ArquillianTest {

	@Inject
	UnionServiceDao union_service_dao;

	@Test
	public void testAddingService() {
		
		UnionService us = new UnionService();
		us.setDescription("descrizione_di_test");
		us.setName("servizio_di_test");
		
		Boolean test = false;
		union_service_dao.add(us);

		List<UnionService> union_services = union_service_dao.findAll();

		for (UnionService union_service : union_services) {

			if (union_service.getName().equals("servizio_di_test")) {
				test = true;
				//Once you have verified that the employee was written is deleted from the db
				union_service_dao.remove(union_service);
			}
		}
		
		assertTrue(test);
	}
	
}
