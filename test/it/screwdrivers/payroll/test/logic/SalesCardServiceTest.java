package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.*;

import java.util.Date;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.SalesCardDao;
import it.screwdrivers.payroll.logic.SalesCardService;
import it.screwdrivers.payroll.model.card.SalesCard;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.test.ArquillianTest;
import it.screwdrivers.payroll.test.DbSeeder;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SalesCardServiceTest {

	@Inject
	SalesCardService sales_card_service;

	@Inject
	EmployeeDao employee_dao;

	@Inject
	SalesCardDao sales_card_dao;

	@Deployment(name = "Test")
	@OverProtocol("Servlet 3.0")
	public static Archive<?> createDeployment() {

		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test_archive.war")
				.addClass(ArquillianTest.class)
				.addClass(DbSeeder.class)
				.addPackages(true, "it.screwdrivers.payroll.controller")
				.addPackages(true, "it.screwdrivers.payroll.logic")
				.addPackages(true, "it.screwdrivers.payroll.dao")
				.addPackages(true, "it.screwdrivers.payroll.engine")
				.addPackages(true, "it.screwdrivers.payroll.model.card")
				.addPackages(true, "it.screwdrivers.payroll.model.employee")
				.addPackages(true, "it.screwdrivers.payroll.model.historical")
				.addPackages(true, "it.screwdrivers.payroll.model.payment")
				.addPackages(true, "it.screwdrivers.payroll.model.union")
				.addPackages(true, "it.screwdrivers.payroll.test.engine")
				.addPackages(true, "it.screwdrivers.payroll.test.dao")
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"));

		// archive.as(ZipExporter.class).exportTo(
		// new File("target/test_archive.war"), true);

		return archive;
	}

	@Test
	public void testRegisterSalesCard() {

		CommissionedEmployee employee1 = new CommissionedEmployee();
		employee1.setName("gianpaolo");
		employee1.setSurname("molinelli");
		employee1.setUsername("moli");
		employee1.setPassword("moli");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setMonthly_salary(2000);
		employee1.setSale_rate(2);
		employee_dao.add(employee1);

		SalesCard sales_card = new SalesCard();
		sales_card.setDate(new Date());
		sales_card.setCommissioned_employee(employee1);
		sales_card.setCustomer("test_customer");
		sales_card.setAmount(350);

		SalesCard sales_card2 = new SalesCard();
		sales_card2.setDate(new Date());
		sales_card2.setCommissioned_employee(employee1);
		sales_card2.setCustomer("test_customer");
		sales_card2.setAmount(350);

		SalesCard sales_card3 = new SalesCard();
		sales_card3.setDate(new Date());
		sales_card3.setCommissioned_employee(employee1);
		sales_card3.setCustomer("test_customer_2");
		sales_card3.setAmount(350);

		String first_response = sales_card_service.registerTimeCard(employee1,
				sales_card);

		// Since this day another SalesCard with the same customer was already
		// registered,
		// the registration of this SalesCard will fail
		String second_response = sales_card_service.registerTimeCard(employee1,
				sales_card2);

		// This time card has to be registered since, although another SalesCard
		// was
		// already sent in the same day, the specified customer is different
		// from the
		// previous SalesCard's one
		String third_response = sales_card_service.registerTimeCard(employee1,
				sales_card3);

		sales_card_dao.remove(sales_card);
		sales_card_dao.remove(sales_card2);
		sales_card_dao.remove(sales_card3);
		employee_dao.remove(employee1);

		assertEquals(first_response, "success");
		assertEquals(second_response, "failed");
		assertEquals(third_response, "success");
	}
}
