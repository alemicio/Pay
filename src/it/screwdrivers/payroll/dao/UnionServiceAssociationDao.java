package it.screwdrivers.payroll.dao;

import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UnionServiceAssociationDao {

	@PersistenceContext
	EntityManager em;

	public void add(UnionServiceAssociation union_service_association) {
		em.persist(union_service_association);
	}

	public List<UnionServiceAssociation> findAll() {
		List<UnionServiceAssociation> union_service_associations = em.createQuery("select s from UnionServiceAssociation s", UnionServiceAssociation.class).getResultList();
		return union_service_associations;
	}

	public UnionServiceAssociation update(UnionServiceAssociation union_service_association) {
		return em.merge(union_service_association);
	}

	public void remove(UnionServiceAssociation union_service_association) {
		em.remove(em.merge(union_service_association));
	}
	
}
