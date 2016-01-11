package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalUnionChargeDao;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.dao.UnionServiceAssociationDao;
import it.screwdrivers.payroll.dao.UnionServiceDao;
import it.screwdrivers.payroll.logic.HistoricalUnionChargeService;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.model.union.Union;
import it.screwdrivers.payroll.model.union.UnionService;
import it.screwdrivers.payroll.model.union.UnionServiceAssociation;
import it.screwdrivers.payroll.test.ArquillianTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HistoricalUnionChargeServiceTest extends ArquillianTest {

	@Inject
	HistoricalUnionChargeService historical_union_charge_service;

	@Inject
	UnionDao union_dao;

	@Inject
	UnionServiceDao union_service_dao;

	@Inject
	UnionServiceAssociationDao union_service_association_dao;

	@Inject
	EmployeeDao employee_dao;

	@Inject
	HistoricalUnionChargeDao historical_union_charge_dao;

	//@Test
	public void testConfirmOrder() {

		boolean was_ordered = false;

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

		Union union = new Union();
		union.setName("test_union");
		union.setPhone_number("3388194740");
		union_dao.add(union);

		UnionService union_service = new UnionService();
		union_service.setDescription("description");
		union_service.setName("test_service");
		union_service_dao.add(union_service);

		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion(union);
		usa.setUnion_service(union_service);
		usa.setPrice(999);
		union_service_association_dao.add(usa);

		List<UnionServiceAssociation> selected_union_service_associations = new ArrayList<UnionServiceAssociation>();
		selected_union_service_associations.add(usa);

		// The employee "bonaz" attempts to order "test_service" from
		// "test_union"
		historical_union_charge_service.confirmOrder(employee1,
				selected_union_service_associations);

		List<HistoricalUnionCharge> historical_union_charges = historical_union_charge_dao
				.findAll();
		for (HistoricalUnionCharge huc : historical_union_charges) {

			if (huc.getEmployee().getId() == employee1.getId()
					&& huc.getUnion_service_association().getId() == usa
							.getId())
				was_ordered = true;

			historical_union_charge_dao.remove(huc);
			union_service_association_dao.remove(huc
					.getUnion_service_association());
		}

		union_service_dao.remove(union_service);
		union_dao.remove(union);
		employee_dao.remove(employee1);
		
		assertTrue(was_ordered);
	}

	//@Test
	public void testIfTheServiceWasOrderedInThisWeekItsOrderWillNotBeSaved() {

		int count = 0;

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

		Union union = new Union();
		union.setName("test_union");
		union.setPhone_number("3388194740");
		union_dao.add(union);

		UnionService union_service = new UnionService();
		union_service.setDescription("description");
		union_service.setName("test_service");
		union_service_dao.add(union_service);

		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion(union);
		usa.setUnion_service(union_service);
		usa.setPrice(999);
		union_service_association_dao.add(usa);

		List<UnionServiceAssociation> selected_union_service_associations = new ArrayList<UnionServiceAssociation>();
		selected_union_service_associations.add(usa);

		// The employee "bonaz" attempts to order "test_service" from
		// "test_union"
		historical_union_charge_service.confirmOrder(employee1,
				selected_union_service_associations);

		// The employee "bonaz" attempts to order "test_service" from
		// "test_union" the second time in this week
		historical_union_charge_service.confirmOrder(employee1,
				selected_union_service_associations);

		List<HistoricalUnionCharge> to_be_removed_after_test = new ArrayList<HistoricalUnionCharge>();
		List<HistoricalUnionCharge> historical_union_charges = historical_union_charge_dao
				.findAll();
		
		for (HistoricalUnionCharge huc : historical_union_charges) {
			if (huc.getEmployee().getId() == employee1.getId()
					&& huc.getUnion_service_association().getId() == usa
							.getId()){
				
				count ++;
				to_be_removed_after_test.add(huc);
			}
		}
		
		for(HistoricalUnionCharge huc : to_be_removed_after_test)
			historical_union_charge_dao.remove(huc);

		union_service_association_dao.remove(usa);
		union_service_dao.remove(union_service);
		union_dao.remove(union);
		employee_dao.remove(employee1);
		
		assertTrue(count == 1);
	}
	
	@Test
	public void testGetLastMonthUnionTotalChargesByEmployee(){
		
		Union union = new Union();
		union.setName("test_union");
		union.setPhone_number("3388194740");
		union_dao.add(union);
		
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

		UnionService union_service = new UnionService();
		union_service.setDescription("description");
		union_service.setName("test_service");
		union_service_dao.add(union_service);

		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion(union);
		usa.setUnion_service(union_service);
		usa.setPrice(999);
		union_service_association_dao.add(usa);

		List<UnionServiceAssociation> selected_union_service_associations = new ArrayList<UnionServiceAssociation>();
		selected_union_service_associations.add(usa);

		// The employee "bonaz" attempts to order "test_service" from
		// "test_union"
		historical_union_charge_service.confirmOrder(employee1,
				selected_union_service_associations);
		
		float last_month_union_total_charges = historical_union_charge_service.getLastMonthUnionTotalChargesByEmployee(employee1);
		
		List<HistoricalUnionCharge> historical_union_charges = historical_union_charge_dao
				.findAll();
		for (HistoricalUnionCharge huc : historical_union_charges) {
			if (huc.getEmployee().getId() == employee1.getId()
					&& huc.getUnion_service_association().getId() == usa
							.getId()){
				
				historical_union_charge_dao.remove(huc);
			}
		}
		
		union_service_association_dao.remove(usa);
		union_service_dao.remove(union_service);
		union_dao.remove(union);
		employee_dao.remove(employee1);
		
		Calendar calendar = new GregorianCalendar();
		int saturday = 7;
		int sunday = 1;
		int calendar_day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
		
		if (calendar_day_of_week != saturday && calendar_day_of_week != sunday)
			assertTrue(last_month_union_total_charges == 999);
		else
			Assert.fail("This is not a working day!");
	}
}
