package it.screwdrivers.payroll.testsDB;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class ArquillianTest {

	@Deployment(name = "FirstTest")
	@OverProtocol("Servlet 3.0")
	public static Archive<?> createDeployment() {

		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "TestPayroll.war")
				.addPackages(true, "it.screwdrivers.payroll.view")
				.addPackages(true, "it.screwdrivers.payroll.controller")
				.addPackages(true, "it.screwdrivers.payroll.dao")
				.addPackages(true, "it.screwdrivers.payroll.engine")
				.addPackages(true, "it.screwdrivers.payroll.model.card")
				.addPackages(true, "it.screwdrivers.payroll.model.employee")
				.addPackages(true,  "it.screwdrivers.payroll.model.historical")
				.addPackages(true, "it.screwdrivers.payroll.model.payment")
				.addPackages(true, "it.screwdrivers.payroll.model.union")
				.addPackages(true, "it.screwdrivers.payroll.testENGINE")
				.addPackages(true, "it.screwdrivers.payroll.testsDATES")
				.addPackages(true, "it.screwdrivers.payroll.testsDB")
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"));

		// Esportazione di prova per controllo
		archive.as(ZipExporter.class).exportTo(
				new File("target/arquillianPackage.war"), true);

		return archive;
	}

}
