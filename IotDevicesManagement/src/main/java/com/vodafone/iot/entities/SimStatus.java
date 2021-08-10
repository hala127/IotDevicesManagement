package com.vodafone.iot.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimStatus {

	@Id
	@Column(updatable = false, insertable = false)
	private int id;
	private String name;

	@OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
	@JsonIgnore
	@ToString.Exclude
	private Set<SimCard> simCards;

	public SimStatus(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
