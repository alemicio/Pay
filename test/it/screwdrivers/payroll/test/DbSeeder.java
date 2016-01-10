package it.screwdrivers.payroll.test;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.dao.UnionServiceAssociationDao;
import it.screwdrivers.payroll.dao.UnionServiceDao;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.union.Union;
import it.screwdrivers.payroll.model.union.UnionService;
import it.screwdrivers.payroll.model.union.UnionServiceAssociation;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DbSeeder extends ArquillianTest {

	@Inject
	EmployeeDao employee_dao;

	@Inject
	UnionDao union_dao;

	@Inject
	UnionServiceDao union_service_dao;

	@Inject
	UnionServiceAssociationDao usa_dao;

	@Test
	public void seed() {

		// === employees ===
		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);
		employee_dao.add(employee_manager);
		SalariedEmployee employee_salaried = new SalariedEmployee();
		employee_salaried.setName("davide");
		employee_salaried.setSurname("bonamico");
		employee_salaried.setUsername("bonaz");
		employee_salaried.setPassword("bingo");
		employee_salaried.setE_mail("a@bi.it");
		employee_salaried.setPhone_number("3331112233");
		employee_salaried.setPostal_address("via roma 1");
		employee_salaried.setMonthly_salary(1000);
		employee_dao.add(employee_salaried);
		CommissionedEmployee employee_commissioned = new CommissionedEmployee();
		employee_commissioned.setName("gianpaolo");
		employee_commissioned.setSurname("molinelli");
		employee_commissioned.setUsername("moli");
		employee_commissioned.setPassword("moli");
		employee_commissioned.setE_mail("a@bi.it");
		employee_commissioned.setPhone_number("3331112233");
		employee_commissioned.setPostal_address("via roma 1");
		employee_commissioned.setMonthly_salary(2000);
		employee_commissioned.setSale_rate(2);
		employee_dao.add(employee_commissioned);
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

		// === unions ===
		Union union = new Union();
		union.setName("CISL");
		union.setPhone_number("3388194740");
		union_dao.add(union);

		// === union services ===
		UnionService union_service = new UnionService();
		union_service.setDescription("descrizione di test");
		union_service.setName("servizio1");
		union_service_dao.add(union_service);
		UnionService union_service2 = new UnionService();
		union_service2.setDescription("descrizione di test");
		union_service2.setName("servizio2");
		union_service_dao.add(union_service2);
		UnionService union_service3 = new UnionService();
		union_service3.setDescription("descrizione di test");
		union_service3.setName("servizio3");
		union_service_dao.add(union_service3);

		// === union service associations (CISL) ===
		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion(union);
		usa.setUnion_service(union_service);
		usa.setPrice(999);
		usa_dao.add(usa);
		UnionServiceAssociation usa2 = new UnionServiceAssociation();
		usa2.setUnion(union);
		usa2.setUnion_service(union_service2);
		usa2.setPrice(999);
		usa_dao.add(usa2);
		UnionServiceAssociation usa3 = new UnionServiceAssociation();
		usa3.setUnion(union);
		usa3.setUnion_service(union_service2);
		usa3.setPrice(999);
		usa_dao.add(usa3);
	}
}
