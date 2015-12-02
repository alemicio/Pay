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
				.addPackages(true, "it.screwdrivers.payroll.pojo.card")
				.addPackages(true, "it.screwdrivers.payroll.pojo.employee")
				.addPackages(true, "it.screwdrivers.payroll.pojo.payment")
				.addPackages(true, "it.screwdrivers.payroll.pojo.union")
				.addPackages(true, "it.screwdrivers.payroll.dao")
				.addPackages(true,  "it.screwdrivers.payroll.pojo.historical")
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
