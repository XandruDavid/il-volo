package com.david.ilvolo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity()
@Table(name = "time_slot")
@XmlRootElement(name = "timeSlot")
public class TimeSlot {

	private Long id;
	private String name;
	private Integer maxPrenotations;
	private DayOfWeek dayOfWeek;
	private String time;
	private List<Aircraft> allowedAircrafts;

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
	public Integer getMaxPrenotations() {
		return maxPrenotations;
	}

	public void setMaxPrenotations(Integer maxPrenotations) {
		this.maxPrenotations = maxPrenotations;
	}

	@Column
	@Enumerated(EnumType.STRING)
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Column
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	public List<Aircraft> getAllowedAircrafts() {
		if (this.allowedAircrafts == null) {
			this.allowedAircrafts = new ArrayList<Aircraft>();
		}
		return allowedAircrafts;
	}

	public void setAllowedAircrafts(List<Aircraft> allowedAircrafts) {
		this.allowedAircrafts = allowedAircrafts;
	}

}
