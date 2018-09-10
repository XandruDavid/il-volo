package com.david.ilvolo.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.david.ilvolo.model.Aircraft;
import com.david.ilvolo.model.Airline;
import com.david.ilvolo.model.Route;
import com.david.ilvolo.model.TimeSlot;
import com.david.ilvolo.util.PersistenceUtil;

public class PersistenceManager {

	private EntityManager em;

	public PersistenceManager() {
		em = PersistenceUtil.getEntityManager();
	}

	public List<Aircraft> getAircrafts() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Aircraft> query = builder.createQuery(Aircraft.class);
		Root<Aircraft> root = query.from(Aircraft.class);
		query.select(root);
		TypedQuery<Aircraft> q = em.createQuery(query);
		List<Aircraft> res = q.getResultList();
		return res;
	}

	public Aircraft getAircraft(Long id) {
		return em.find(Aircraft.class, id);
	}

	public Long persistAircraft(Aircraft aircraft) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(aircraft);
		em.flush();
		et.commit();
		return aircraft.getId();
	}

	public void updateAircraft(Long id, Aircraft aircraft) {
		aircraft.setId(id);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(aircraft);
		em.flush();
		et.commit();
	}

	public void deleteAircraft(Aircraft aircraft) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(aircraft);
		em.flush();
		et.commit();
	}

	public List<Route> getRoutes() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Route> query = builder.createQuery(Route.class);
		Root<Route> root = query.from(Route.class);
		query.select(root);
		TypedQuery<Route> q = em.createQuery(query);
		List<Route> res = q.getResultList();
		return res;
	}

	public Route getRoute(Long id) {
		return em.find(Route.class, id);
	}

	public Long persistRoute(Route route) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(route);
		em.flush();
		et.commit();
		return route.getId();
	}

	public void updateRoute(Long id, Route route) {
		route.setId(id);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(route);
		em.flush();
		et.commit();
	}

	public void deleteRoute(Route route) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(route);
		em.flush();
		et.commit();
	}

	public Long persistSlotForRoute(Long routeId, TimeSlot slot) {
		Route route = em.find(Route.class, routeId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(slot);
		route.getSlots().add(slot);
		em.flush();
		et.commit();
		return slot.getId();
	}

	public void addSlotForRoute(Long routeId, Long slotId) {
		Route route = em.find(Route.class, routeId);
		TimeSlot slot = em.find(TimeSlot.class, slotId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		route.getSlots().add(slot);
		em.flush();
		et.commit();
	}

	public void updateSlot(Long id, TimeSlot slot) {
		slot.setId(id);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(slot);
		em.flush();
		et.commit();
	}

	public void deleteSlotFromRoute(Long routeId, Long slotId) {
		Route route = em.find(Route.class, routeId);
		TimeSlot slot = em.find(TimeSlot.class, slotId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		route.getSlots().remove(slot);
		em.flush();
		et.commit();
	}

	public Long persistAircraftForSlot(Long slotId, Aircraft aircraft) {
		TimeSlot slot = em.find(TimeSlot.class, slotId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(aircraft);
		slot.getAllowedAircrafts().add(aircraft);
		em.flush();
		et.commit();
		return aircraft.getId();
	}

	public void addAircraftForSlot(Long slotId, Long aircraftId) {
		TimeSlot slot = em.find(TimeSlot.class, slotId);
		Aircraft aircraft = em.find(Aircraft.class, aircraftId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		slot.getAllowedAircrafts().add(aircraft);
		em.flush();
		et.commit();
	}

	public void deleteAircraftFromSlot(Long slotId, Long aircraftId) {
		TimeSlot slot = em.find(TimeSlot.class, slotId);
		Aircraft aircraft = em.find(Aircraft.class, aircraftId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		slot.getAllowedAircrafts().remove(aircraft);
		em.flush();
		et.commit();
	}

	public List<Airline> getAirlines() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Airline> query = builder.createQuery(Airline.class);
		Root<Airline> root = query.from(Airline.class);
		query.select(root);
		TypedQuery<Airline> q = em.createQuery(query);
		List<Airline> res = q.getResultList();
		return res;
	}

	public Long persistAirline(Airline airline) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(airline);
		em.flush();
		et.commit();
		return airline.getId();
	}

	public Airline getAirline(Long id) {
		return em.find(Airline.class, id);
	}

	public void updateAirline(Long id, Airline airline) {
		airline.setId(id);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(airline);
		em.flush();
		et.commit();
	}

	public void deleteAirline(Airline airline) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(airline);
		em.flush();
		et.commit();
	}

	public Long persistAircraftForAirline(Long airlineId, Aircraft aircraft) {
		Airline airline = em.find(Airline.class, airlineId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(aircraft);
		airline.getAircrafts().add(aircraft);
		em.flush();
		et.commit();
		return aircraft.getId();
	}

	public void addAircraftForAirline(Long airlineId, Long aircraftId) {
		Airline airline = em.find(Airline.class, airlineId);
		Aircraft aircraft = em.find(Aircraft.class, aircraftId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		airline.getAircrafts().add(aircraft);
		em.flush();
		et.commit();
	}

	public void deleteAircraftFromAirline(Long airlineId, Long aircraftId) {
		Airline airline = em.find(Airline.class, airlineId);
		Aircraft aircraft = em.find(Aircraft.class, aircraftId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		airline.getAircrafts().remove(aircraft);
		em.flush();
		et.commit();
	}

	public Long persistPartnerForAirline(Long airlineId, Airline partner) {
		Airline airline = em.find(Airline.class, airlineId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(partner);
		airline.getPartners().add(partner);
		em.flush();
		et.commit();
		return partner.getId();
	}

	public void deletePartnerFromAirline(Long airlineId, Long partnerId) {
		Airline airline = em.find(Airline.class, airlineId);
		Airline partner = em.find(Airline.class, partnerId);
		EntityTransaction et = em.getTransaction();
		et.begin();
		airline.getPartners().remove(partner);
		em.flush();
		et.commit();
	}

}
