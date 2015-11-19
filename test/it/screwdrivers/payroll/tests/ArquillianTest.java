package it.screwdrivers.payroll.tests;

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
	
	@Deployment(name = "Test")
	@OverProtocol("Servlet 3.0")
	public static Archive<?> createDeployment() {
		
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "Test.war")
        .addPackages(true, "it.screwdrivers.payroll.dao")
        .addPackages(true, "it.screwdrivers.payroll.pojo")
        .addPackages(true, "it.screwdrivers.payroll.tests")
        .addAsResource("META-INF/persistence.xml")
        .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
		
		// Esportazione di prova per controllo
		archive.as(ZipExporter.class).exportTo(new File("target/arquillianPackage.war"), true);
		
		return archive;
	}

}
