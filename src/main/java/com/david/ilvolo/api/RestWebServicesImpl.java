package com.david.ilvolo.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.david.ilvolo.manager.PersistenceManager;
import com.david.ilvolo.model.Aircraft;
import com.david.ilvolo.model.Airline;
import com.david.ilvolo.model.Route;
import com.david.ilvolo.model.TimeSlot;
import com.david.ilvolo.util.ResponseMessage;

public class RestWebServicesImpl implements RestWebServices {

	/* URI informations used to get current context path. */
	@Context
	private UriInfo uriInfo;

	/* Hibernate PersistenceManager used for database interactions. */
	private PersistenceManager persistenceManager;

	public RestWebServicesImpl() {
		persistenceManager = new PersistenceManager();
	}

	@Override
	public List<Aircraft> getAircrafts() {
		return persistenceManager.getAircrafts();
	}

	@Override
	public Response saveAircraft(Aircraft aircraft) {

		// Return status 405 with message if request is empty.
		if (aircraft == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage("ERROR: Empty request.")).build();
		}

		// Persist Aircraf and create response with link.
		Long id = persistenceManager.persistAircraft(aircraft);
		Link link = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
		return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(link.getUri()).build();
	}

	@Override
	public Response getAircraft(Long id) {
		Aircraft aircraft = persistenceManager.getAircraft(id);

		if (aircraft == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(aircraft).build();
	}

	@Override
	public Response updateAircraft(Long id, Aircraft aircraft) {
		Aircraft oldAircraft = persistenceManager.getAircraft(id);

		if (oldAircraft == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		persistenceManager.updateAircraft(id, aircraft);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response deleteAircraft(Long id) {
		Aircraft aircraft = persistenceManager.getAircraft(id);

		if (aircraft == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		persistenceManager.deleteAircraft(aircraft);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public List<Route> getRoutes(String aircraftsFilter) {
		
		System.out.println("Called");

		// Read ids from param string.
		String[] strings = aircraftsFilter.split(",");
		List<Long> longs = new ArrayList<Long>();
		for (String s : strings) {
			longs.add(Long.valueOf(s));
		}

		List<Route> res = new ArrayList<>();
		for (Route r : persistenceManager.getRoutes()) {
			for (TimeSlot t : r.getSlots()) {
				for (Aircraft a : t.getAllowedAircrafts()) {
					for (Long l : longs) {
						if (a.getId().equals(l)) {
							res.add(r);
						}
					}
				}
			}
		}

		return res;
	}

	@Override
	public Response saveRoute(Route route) {

		// Return status 405 with message if request is empty.
		if (route == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage("ERROR: Empty request.")).build();
		}

		// Persist Route and create response with link.
		Long id = persistenceManager.persistRoute(route);
		Link link = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
		return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(link.getUri()).build();
	}

	@Override
	public Response getRoute(Long id) {
		Route route = persistenceManager.getRoute(id);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(route).build();
	}

	@Override
	public Response updateRoute(Long id, Route route) {
		Route oldRoute = persistenceManager.getRoute(id);

		if (oldRoute == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		persistenceManager.updateRoute(id, route);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response deleteRoute(Long id) {
		Route route = persistenceManager.getRoute(id);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		persistenceManager.deleteRoute(route);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response getSlotsByRoute(Long routeId) {
		Route route = persistenceManager.getRoute(routeId);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		return Response.status(Response.Status.OK).entity(route.getSlots()).build();
	}

	@Override
	public Response saveSlotForRoute(Long routeId, TimeSlot slot) {
		Route route = persistenceManager.getRoute(routeId);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		// Return status 405 with message if request is empty.
		if (slot == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage("ERROR: Empty request.")).build();
		}

		// IF not persisted before, persist Slot and create response with link.
		if (slot.getId() == null) {
			Long id = persistenceManager.persistSlotForRoute(routeId, slot);
			Link link = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
			return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(link.getUri()).build();
		}

		persistenceManager.addSlotForRoute(routeId, slot.getId());
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response getSlotFromRoute(Long routeId, Long slotId) {
		Route route = persistenceManager.getRoute(routeId);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		TimeSlot slot = null;
		for (TimeSlot s : route.getSlots()) {
			if (s.getId().equals(slotId)) {
				slot = s;
			}
		}

		if (slot == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route doesn't contain requested slot.")).build();
		}

		return Response.status(Response.Status.OK).entity(slot).build();
	}

	@Override
	public Response updateSlotFromRoute(Long routeId, Long slotId, TimeSlot slot) {
		Route route = persistenceManager.getRoute(routeId);
		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		TimeSlot oldSlot = null;
		for (TimeSlot s : route.getSlots()) {
			if (s.getId().equals(slotId)) {
				oldSlot = s;
			}
		}

		if (oldSlot == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route doesn't contain requested slot.")).build();
		}

		persistenceManager.updateSlot(slotId, slot);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response deleteSlotFromRoute(Long routeId, Long slotId) {
		Route route = persistenceManager.getRoute(routeId);
		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		TimeSlot slot = null;
		for (TimeSlot s : route.getSlots()) {
			if (s.getId().equals(slotId)) {
				slot = s;
			}
		}

		if (slot == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route doesn't contain requested slot.")).build();
		}

		persistenceManager.deleteSlotFromRoute(routeId, slotId);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response getAircraftsBySlot(Long routeId, Long slotId) {
		Route route = persistenceManager.getRoute(routeId);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		TimeSlot slot = null;
		for (TimeSlot s : route.getSlots()) {
			if (s.getId().equals(slotId)) {
				slot = s;
			}
		}

		if (slot == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route doesn't contain requested slot.")).build();
		}

		return Response.status(Response.Status.OK).entity(slot.getAllowedAircrafts()).build();
	}

	@Override
	public Response saveAicraftForSlot(Long routeId, Long slotId, Aircraft aircraft) {
		Route route = persistenceManager.getRoute(routeId);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		TimeSlot slot = null;
		for (TimeSlot s : route.getSlots()) {
			if (s.getId().equals(slotId)) {
				slot = s;
			}
		}

		if (slot == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route doesn't contain requested slot.")).build();
		}

		// Return status 405 with message if request is empty.
		if (aircraft == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage("ERROR: Empty request.")).build();
		}

		// If not ersisted before, persist Aircraf and create response with link.
		if (aircraft.getId() == null) {
			Long id = persistenceManager.persistAircraftForSlot(slotId, aircraft);
			Link link = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
			return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(link.getUri()).build();
		}

		persistenceManager.addAircraftForSlot(slotId, aircraft.getId());
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response getAircraftFromSlot(Long routeId, Long slotId, Long aircraftId) {
		Route route = persistenceManager.getRoute(routeId);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		TimeSlot slot = null;
		for (TimeSlot s : route.getSlots()) {
			if (s.getId().equals(slotId)) {
				slot = s;
			}
		}

		if (slot == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route doesn't contain requested slot.")).build();
		}

		Aircraft aircraft = null;
		for (Aircraft a : slot.getAllowedAircrafts()) {
			if (a.getId().equals(aircraftId)) {
				aircraft = a;
			}
		}

		if (aircraft == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Slot doesn't contain requested aircraft.")).build();
		}

		return Response.status(Response.Status.OK).entity(aircraft).build();
	}

	@Override
	public Response deleteAircraftFromSlot(Long routeId, Long slotId, Long aircraftId) {
		Route route = persistenceManager.getRoute(routeId);

		if (route == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route not found.")).build();
		}

		TimeSlot slot = null;
		for (TimeSlot s : route.getSlots()) {
			if (s.getId().equals(slotId)) {
				slot = s;
			}
		}

		if (slot == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Route doesn't contain requested slot.")).build();
		}

		Aircraft aircraft = null;
		for (Aircraft a : slot.getAllowedAircrafts()) {
			if (a.getId().equals(aircraftId)) {
				aircraft = a;
			}
		}

		if (aircraft == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Slot doesn't contain requested aircraft.")).build();
		}

		persistenceManager.deleteAircraftFromSlot(slotId, aircraftId);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public List<Airline> getAirlines() {
		return persistenceManager.getAirlines();
	}

	@Override
	public Response saveAirline(Airline airline) {

		// Return status 405 with message if request is empty.
		if (airline == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage("ERROR: Empty request.")).build();
		}

		// Persist Airline and create response with link.
		Long id = persistenceManager.persistAirline(airline);
		Link link = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
		return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(link.getUri()).build();
	}

	@Override
	public Response getAirline(Long id) {
		Airline airline = persistenceManager.getAirline(id);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(airline).build();
	}

	@Override
	public Response updateAirline(Long id, Airline airline) {
		Airline oldAirline = persistenceManager.getAirline(id);

		if (oldAirline == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		persistenceManager.updateAirline(id, airline);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response deleteAirline(Long id) {
		Airline airline = persistenceManager.getAirline(id);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		persistenceManager.deleteAirline(airline);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response getAircraftsByAirline(Long airlineId) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		return Response.status(Response.Status.OK).entity(airline.getAircrafts()).build();
	}

	@Override
	public Response saveAircraftForAirline(Long airlineId, Aircraft aircraft) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		// Return status 405 with message if request is empty.
		if (aircraft == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage("ERROR: Empty request.")).build();
		}

		// I not persisted before, persist Aircraf and create response with link.
		if (aircraft.getId() == null) {
			Long id = persistenceManager.persistAircraftForAirline(airlineId, aircraft);
			Link link = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
			return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(link.getUri()).build();
		}

		persistenceManager.addAircraftForAirline(airlineId, aircraft.getId());
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response getAircraftFromAirline(Long airlineId, Long aircraftId) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		Aircraft aircraft = null;
		for (Aircraft a : airline.getAircrafts()) {
			if (a.getId().equals(aircraftId)) {
				aircraft = a;
			}
		}

		if (aircraft == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline doesn't contain requested slot.")).build();
		}

		return Response.status(Response.Status.OK).entity(aircraft).build();
	}

	@Override
	public Response deleteAircraftFromAirline(Long airlineId, Long aircraftId) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		Aircraft aircraft = null;
		for (Aircraft a : airline.getAircrafts()) {
			if (a.getId().equals(aircraftId)) {
				aircraft = a;
			}
		}

		if (aircraft == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline doesn't contain requested slot.")).build();
		}

		persistenceManager.deleteAircraftFromAirline(airlineId, aircraftId);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Override
	public Response getPartnersByAirline(Long airlineId) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		return Response.status(Response.Status.OK).entity(airline.getPartners()).build();
	}

	@Override
	public Response savePartnerForAirline(Long airlineId, Airline partner) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		// Return status 405 with message if request is empty.
		if (partner == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage("ERROR: Empty request.")).build();
		}

		// If not persisted before, persist Airline and create response with link.
		if (partner.getId() == null) {
			Long id = persistenceManager.persistPartnerForAirline(airlineId, partner);
			Link link = Link.fromUri(uriInfo.getPath() + "/" + id).rel("self").build();
			return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(link.getUri()).build();
		}

		persistenceManager.addAircraftForAirline(airlineId, partner.getId());
		return Response.status(Response.Status.OK).build();
	}

	@Override
	public Response getPartnerFromAirline(Long airlineId, Long partnerId) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		Airline partner = null;
		for (Airline a : airline.getPartners()) {
			if (a.getId().equals(partnerId)) {
				partner = a;
			}
		}

		if (partner == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline doesn't contain requested partner.")).build();
		}

		return Response.status(Response.Status.OK).entity(partner).build();
	}

	@Override
	public Response deletePartnerFromAirline(Long airlineId, Long partnerId) {
		Airline airline = persistenceManager.getAirline(airlineId);

		if (airline == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline not found.")).build();
		}

		Airline partner = null;
		for (Airline a : airline.getPartners()) {
			if (a.getId().equals(partnerId)) {
				partner = a;
			}
		}

		if (partner == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("ERROR: Airline doesn't contain requested partner.")).build();
		}

		persistenceManager.deletePartnerFromAirline(airlineId, partnerId);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
