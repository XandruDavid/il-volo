package com.david.ilvolo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity()
@Table(name = "airline")
@XmlRootElement(name = "airline")
public class Airline {

	private Long id;
	private String name;
	private String state;
	private Float shareValue;
	private List<Aircraft> aircrafts;
	private List<Airline> partners;
	private Account account;

	@Column
	@Id
	@GeneratedValue()
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column
	public Float getShareValue() {
		return shareValue;
	}

	public void setShareValue(Float shareValue) {
		this.shareValue = shareValue;
	}

	@Column
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	public List<Aircraft> getAircrafts() {
		if (this.aircrafts == null) {
			this.aircrafts = new ArrayList<Aircraft>();
		}
		return aircrafts;
	}

	public void setAircrafts(List<Aircraft> aircrafts) {
		this.aircrafts = aircrafts;
	}

	@Column
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	public List<Airline> getPartners() {
		if (this.partners == null) {
			this.partners = new ArrayList<Airline>();
		}
		return partners;
	}

	public void setPartners(List<Airline> partners) {
		this.partners = partners;
	}

	@Embedded
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
