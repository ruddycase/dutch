package com.dtt.handler.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;

import lombok.Setter;
import lombok.AccessLevel;
import lombok.Data;

@Data
@MappedSuperclass
abstract class BaseEntity {

	public BaseEntity() {}
	public BaseEntity(int id, Date created, Date updated) {
		this.id = id;
		this.created = created;
		this.updated = updated;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name = "created")
    @Setter(AccessLevel.NONE) private Date created = new Date();
    @Column(name = "updated")
    @Setter(AccessLevel.NONE) private Date updated = new Date();

	@PreUpdate
	public void setLastUpdate() {  this.updated = new Date(); }

}
