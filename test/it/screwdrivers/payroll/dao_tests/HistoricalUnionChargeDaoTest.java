package it.screwdrivers.payroll.dao_tests;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.ArquillianTest;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalUnionChargeDao;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.dao.UnionServiceAssociationDao;
import it.screwdrivers.payroll.dao.UnionServiceDao;
import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.model.union.Union;
import it.screwdrivers.payroll.model.union.UnionService;
import it.screwdrivers.payroll.model.union.UnionServiceAssociation;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HistoricalUnionChargeDaoTest extends ArquillianTest {

	@Inject
	EmployeeDao employee_dao;

	@Inject
	UnionDao union_dao;

	@Inject
	UnionServiceDao union_service_dao;

	@Inject
	UnionServiceAssociationDao union_service_association_dao;

	@Inject
	HistoricalUnionChargeDao historical_union_charge_dao;

	@Test
	public void testAddHistoricalUnionCharge() {

		boolean was_added = false;

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

		Union union = new Union();
		union.setName("CISL");
		union.setPhone_number("3388194740");
		union_dao.add(union);

		UnionService union_service = new UnionService();
		union_service.setDescription("descrizione_di_test");
		union_service.setName("servizio_di_test");
		union_service_dao.add(union_service);

		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion(union);
		usa.setUnion_service(union_service);
		usa.setPrice(999);
		union_service_association_dao.add(usa);

		HistoricalUnionCharge huc = new HistoricalUnionCharge();
		huc.setEmployee(employee_salaried);
		huc.setUnion_service_association(usa);
		huc.setDate(new Date());
		historical_union_charge_dao.add(huc);

		List<HistoricalUnionCharge> historical_union_charges = historical_union_charge_dao
				.findAll();
		for (HistoricalUnionCharge huc1 : historical_union_charges) {

			if (huc1.getEmployee().getId() == employee_salaried.getId()) {

				was_added = true;
				historical_union_charge_dao.remove(huc);
				union_service_association_dao.remove(usa);
				union_service_dao.remove(union_service);
				union_dao.remove(union);
				employee_dao.remove(employee_salaried);
			}
		}

		assertTrue(was_added);
	}

	@Test
	public void testRemoveHistoricalUnionCharges() {

		boolean test = false;
		int historical_union_charges_size_1;
		int historical_union_charges_size_2;

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

		Union union = new Union();
		union.setName("CISL");
		union.setPhone_number("3388194740");
		union_dao.add(union);

		UnionService union_service = new UnionService();
		union_service.setDescription("descrizione_di_test");
		union_service.setName("servizio_di_test");
		union_service_dao.add(union_service);

		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion(union);
		usa.setUnion_service(union_service);
		usa.setPrice(999);
		union_service_association_dao.add(usa);

		HistoricalUnionCharge huc = new HistoricalUnionCharge();
		huc.setEmployee(employee_salaried);
		huc.setUnion_service_association(usa);
		huc.setDate(new Date());
		historical_union_charge_dao.add(huc);

		historical_union_charges_size_1 = historical_union_charge_dao.findAll()
				.size();
		historical_union_charge_dao.remove(huc);
		historical_union_charges_size_2 = historical_union_charge_dao.findAll()
				.size();

		if (historical_union_charges_size_2 < historical_union_charges_size_1) {

			test = true;
			historical_union_charge_dao.remove(huc);
			union_service_association_dao.remove(usa);
			union_service_dao.remove(union_service);
			union_dao.remove(union);
			employee_dao.remove(employee_salaried);
		}

		assertTrue(test);
	}

}
