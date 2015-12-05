package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.dao.UnionServiceAssociationDao;
import it.screwdrivers.payroll.dao.UnionServiceDao;
import it.screwdrivers.payroll.pojo.union.Union;
import it.screwdrivers.payroll.pojo.union.UnionService;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UnionServiceAssociationTest extends ArquillianTest {
	
	@Inject
	UnionDao union_dao;
	
	@Inject
	UnionServiceDao union_service_dao;
	
	@Inject
	UnionServiceAssociationDao union_service_association_dao;

	@Test
	public void testAddingUnionServiceAssociation() {
		
		// ============ CREATE A UNION FOR THE TEST=============
		Union union = new Union();
		union.setName("CISL");
		union.setPhone_number("3388194740");
		union_dao.add(union);
		// =====================================================
		
		// =========== CREATE A UNIONSERVICE FOR THE TEST=============
		UnionService union_service = new UnionService();
		union_service .setDescription("descrizione_di_test");
		union_service .setName("servizio_di_test");
		union_service_dao.add(union_service );
		// ===========================================================
		
		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion_id(union.getId());
		usa.setUnion_service_id(union_service.getId());
		usa.setPrice(999);
		
		Boolean test = false;
		union_service_association_dao.add(usa);

		List<UnionServiceAssociation> union_service_associations = union_service_association_dao.findAll();

		for (UnionServiceAssociation union_service_association : union_service_associations) {

			if (union_service_association.getPrice() == 999) {
				test = true;
				//Once you have verified that the employee was written is deleted from the db
				//union_service_association_dao.remove(usa);
				//union_dao.remove(union);
				//union_service_dao.remove(union_service);
			}
		}

		// test = true;
		assertTrue(test);
	}
	
}

