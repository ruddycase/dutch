package com.dtt.handler.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "report", indexes = { @Index(columnList = "status", name = "report_status_hidx") })
public class Report extends BaseEntity {

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private ReportStatus status;

	@Column(name = "title")
	private String title;
 
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "report_id")
	private List<ReportHistory> history = new ArrayList<>();

	/*
	 * Who is currently working on the report. Not a JOIN to the user table for
	 * efficiency and when deleting a user we do not want to delete the report.
	 */
	@Column(name = "owner")
	private Long owner;

	@Column(name = "one")
	private String one;

	@Column(name = "two")
	private String two;

	@Column(name = "three")
	private String three;

	@Column(name = "four")
	private String four;

	@Column(name = "five")
	private String five;

	@Column(name = "drugType")
	private String drugType;

	@Column(name = "six", columnDefinition = "boolean default true", nullable = false)
	private Boolean six = true;
}
