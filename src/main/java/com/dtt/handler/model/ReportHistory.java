package com.dtt.handler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "report_history")
public class ReportHistory extends BaseEntity {

	@Column(name = "status")
	private int status;
	
	/*
	 * Modifiers reason for changing the report status.
	 */
	@Column(name = "reason")
	private String reason;
	
	@OneToOne
    @JoinColumn(name="modifier")
	private User modifier;
	
}
