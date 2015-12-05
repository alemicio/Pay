package it.screwdrivers.payroll.pojo.union;

import it.screwdrivers.payroll.pojo.historical.HistoricalUnionCharge;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// Questa classe rappresenta l'associazione tra Union e Service. Deriva dalla
// molti a molti. Usa, come chiave primaria, una chiave composta da union_id
// e union_service_id. Creare una chiave composta in questa classe non � possibile,
// quindi dobbiamo appoggiarci su una classe UnionServiceAssociationId che diventa
// la chiave composta(vedi UnionServiceAssociationId). Questa classe che fa da chiave
// primaria composta, la linko qui dentro tramite @IdClass(UnionServiceAssociationId.class).
// A questo punto, posso definire due attributi, annotati come @Id, che corrispondono a 
// quelli definiti nella classe UnionServiceAssociationId. 
// Ma qual'� la figata?
// La figata � che uso la chiave primaria, composta da union_id e union_service_id, per
// fare la join con le tabelle Union e UnionService. Per dire che voglio usare union_id
// (che � parte della chiave primaria di UnionServiceAssociatio) come anello di congiunzione
// con la chiave primaria di Union (che � semplicemente id), uso:
//
// @PrimaryKeyJoinColumn(name = "union_id", referencedColumnName = "id")
//
// . name => nome della chiave primaria (che fa parte di quella composta) della tabella
//           corrente, ovvero UnionServiceAssociation.
//
// . referencedColumnName => nome della chiave primaria della tabella Union (che voglio JOINARE)
// 
// Nel tutorial che ho seguito, https://en.wikibooks.org/wiki/Java_Persistence/ManyToMany,
// mi dice che, se lanciando il server non riesco a creare la tabella UnionServiceAssociation,
// allora devo usare una forma alternativa al @PrimaryKeyJoinColumn, ovvero:
//
//	@JoinColumn(name = "employeeId", updatable = false, insertable = false, referencedColumnName = "id")
//
// Ho provato questa nuova versione. FUNZIONA
@Entity
@Table(name = "UnionServiceAssociation")
@IdClass(UnionServiceAssociationId.class)
public class UnionServiceAssociation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int union_id;

	@Id
	private int union_service_id;

	@ManyToOne
	@JoinColumn(name = "union_id", referencedColumnName = "id", updatable = false, insertable = false)
	private Union union;

	@ManyToOne
	@JoinColumn(name = "union_service_id", referencedColumnName = "id", updatable = false, insertable = false)
	private UnionService union_service;
	
	@OneToMany(mappedBy="union_service_association")
	private List<HistoricalUnionCharge> historical_union_charge;
	
	private float price;
	
	public int getUnion_id() {
		return union_id;
	}

	public void setUnion_id(int union_id) {
		this.union_id = union_id;
	}

	public int getUnion_service_id() {
		return union_service_id;
	}

	public void setUnion_service_id(int union_service_id) {
		this.union_service_id = union_service_id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
