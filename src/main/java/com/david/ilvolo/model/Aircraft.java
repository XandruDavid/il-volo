package com.david.ilvolo.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity()
@Table(name = "aircraft")
@XmlRootElement(name = "aircraft")
public class Aircraft {

	private Long id;
	private String name;
	private Integer length;
	private Integer avgSpeed;
	private Integer topSpeed;
	private Integer wingspan;
	private Integer wingArea;
	private Integer emptyWeight;
	private Integer maxWeight;
	private Integer tankCapacity;
	private Passengers passengers;
	private String engine;

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
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Column
	public Integer getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(Integer avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	@Column
	public Integer getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(Integer topSpeed) {
		this.topSpeed = topSpeed;
	}

	@Column
	public Integer getWingspan() {
		return wingspan;
	}

	public void setWingspan(Integer wingspan) {
		this.wingspan = wingspan;
	}

	@Column
	public Integer getWingArea() {
		return wingArea;
	}

	public void setWingArea(Integer wingArea) {
		this.wingArea = wingArea;
	}

	@Column
	public Integer getEmptyWeight() {
		return emptyWeight;
	}

	public void setEmptyWeight(Integer emptyWeight) {
		this.emptyWeight = emptyWeight;
	}

	@Column
	public Integer getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Integer maxWeight) {
		this.maxWeight = maxWeight;
	}

	@Column
	public Integer getTankCapacity() {
		return tankCapacity;
	}

	public void setTankCapacity(Integer tankCapacity) {
		this.tankCapacity = tankCapacity;
	}

	@Embedded
	public Passengers getPassengers() {
		if (passengers == null) {
			passengers = new Passengers();
		}
		return passengers;
	}

	public void setPassengers(Passengers passengers) {
		this.passengers = passengers;
	}

	@Column
	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

}
