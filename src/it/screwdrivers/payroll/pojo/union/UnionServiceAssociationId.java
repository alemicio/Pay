package it.screwdrivers.payroll.pojo.union;

import java.io.Serializable;

public class UnionServiceAssociationId implements Serializable {

	private static final long serialVersionUID = 1L;

	private int union_id;
	private int union_service_id;

	// Perch� bisogna mettere questi due metodi non lo so ancora... ma vanno
	// messi.
	public int hashCode() {
		return (int) (union_id + union_service_id);
	}

	public boolean equals(Object object) {
		if (object instanceof UnionServiceAssociationId) {
			UnionServiceAssociationId otherId = (UnionServiceAssociationId) object;
			return (otherId.union_id == this.union_id)
					&& (otherId.union_service_id == this.union_service_id);
		}
		return false;
	}
}
