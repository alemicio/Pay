package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.logic.UnionService;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.union.Union;
import it.screwdrivers.payroll.test.ArquillianTest;
import it.screwdrivers.payroll.test.DbSeeder;

import java.util.List;

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
public class UnionServiceTest {

	@Inject
	UnionService union_service;

	@Inject
	EmployeeDao employee_dao;

	@Inject
	UnionDao union_dao;

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
	public void testSetUnion() {

		boolean was_updated = false;
		boolean is_union_set = false;

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
		union.setName("CISL");
		union.setPhone_number("3388194740");
		union_dao.add(union);

		union_service.setUnion(employee1, union_service.getUnionName(union));

		List<SalariedEmployee> salaried_employees = employee_dao
				.findAllSalaried();
		for (SalariedEmployee se : salaried_employees) {

			if (se.getId() == employee1.getId()) {
				if (se.getUnion().getId() == union.getId()) {
					was_updated = true;
				}
			}
		}

		is_union_set = union_service.isUnionSet(employee1);

		employee_dao.remove(employee1);
		union_dao.remove(union);

		assertTrue(was_updated);
		assertTrue(is_union_set);
	}
}
