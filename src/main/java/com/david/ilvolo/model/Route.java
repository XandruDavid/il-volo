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
@Table(name = "route")
@XmlRootElement(name = "route")
public class Route {

	private Long id;
	private String name;
	private RouteType type;
	private String departingAirport;
	private String arrivalAirport;
	private List<TimeSlot> slots;

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
	@Enumerated(EnumType.STRING)
	public RouteType getType() {
		return type;
	}

	public void setType(RouteType type) {
		this.type = type;
	}

	@Column
	public String getDepartingAirport() {
		return departingAirport;
	}

	public void setDepartingAirport(String departingAirport) {
		this.departingAirport = departingAirport;
	}

	@Column
	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	@Column
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<TimeSlot> getSlots() {
		if (this.slots == null) {
			this.slots = new ArrayList<TimeSlot>();
		}
		return slots;
	}

	public void setSlots(List<TimeSlot> slots) {
		this.slots = slots;
	}

}
