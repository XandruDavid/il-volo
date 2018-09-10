package com.david.ilvolo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Passengers {

	public Integer business;
	public Integer premium;
	public Integer economy;

	@Column
	public Integer getBusiness() {
		return business;
	}

	public void setBusiness(Integer business) {
		this.business = business;
	}

	@Column
	public Integer getPremium() {
		return premium;
	}

	public void setPremium(Integer premium) {
		this.premium = premium;
	}

	@Column
	public Integer getEconomy() {
		return economy;
	}

	public void setEconomy(Integer economy) {
		this.economy = economy;
	}

}
