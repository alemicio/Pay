package it.screwdrivers.payroll.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
	@PrimaryKeyJoinColumn(name = "union_id", referencedColumnName = "id")
	private Union union;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "union_service_id", referencedColumnName = "id")
	private UnionService union_service;
}
