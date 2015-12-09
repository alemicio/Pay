package it.screwdrivers.payroll.pojo.union;

import it.screwdrivers.payroll.pojo.historical.HistoricalUnionCharge;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "UnionServiceAssociation")
public class UnionServiceAssociation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "union_id", referencedColumnName = "id")
	private Union union;

	@ManyToOne
	@JoinColumn(name = "union_service_id", referencedColumnName = "id")
	private UnionService union_service;
	
	@OneToMany(mappedBy="union_service_association")
	private List<HistoricalUnionCharge> historical_union_charge;
	
	private float price;

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public UnionService getUnion_service() {
		return union_service;
	}

	public void setUnion_service(UnionService union_service) {
		this.union_service = union_service;
	}
}
