package com.vodafone.iot.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimCard {

	@Id
	@Column(insertable = false, updatable = false)
	private int id;
	private String operatorCode;
	private String country;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "status_id")
	@JsonIgnore
	private SimStatus status;
	
	@OneToOne(fetch= FetchType.LAZY, mappedBy = "simCard")
	@JsonIgnore
	@ToString.Exclude
	private Device device;

}
