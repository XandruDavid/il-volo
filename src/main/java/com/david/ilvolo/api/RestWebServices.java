package com.david.ilvolo.api;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.david.ilvolo.model.Aircraft;
import com.david.ilvolo.model.Airline;
import com.david.ilvolo.model.Route;
import com.david.ilvolo.model.TimeSlot;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface RestWebServices {

	@GET
	@Path("/aircrafts")
	public List<Aircraft> getAircrafts();

	@POST
	@Path("/aircrafts")
	public Response saveAircraft(Aircraft aircraft);

	@GET
	@Path("/aircrafts/{id}")
	public Response getAircraft(@PathParam("id") Long id);

	@PUT
	@Path("/aircrafts/{id}")
	public Response updateAircraft(@PathParam("id") Long id, Aircraft aircraft);

	@DELETE
	@Path("/aircrafts/{id}")
	public Response deleteAircraft(@PathParam("id") Long id);

	@GET
	@Path("/routes")
	public List<Route> getRoutes(@QueryParam("aircraftsFilter") String aircraftsFilter);

	@POST
	@Path("/routes")
	public Response saveRoute(Route route);

	@GET
	@Path("/routes/{id}")
	public Response getRoute(@PathParam("id") Long id);

	@PUT
	@Path("/routes/{id}")
	public Response updateRoute(@PathParam("id") Long id, Route route);

	@DELETE
	@Path("/routes/{id}")
	public Response deleteRoute(@PathParam("id") Long id);

	@GET
	@Path("/routes/{routeId}/slots")
	public Response getSlotsByRoute(@PathParam("routeId") Long routeId);

	@POST
	@Path("/routes/{routeId}/slots")
	public Response saveSlotForRoute(@PathParam("routeId") Long routeId, TimeSlot slot);

	@GET
	@Path("/routes/{routeId}/slots/{slotId}")
	public Response getSlotFromRoute(@PathParam("routeId") Long routeId, @PathParam("slotId") Long slotId);

	@PUT
	@Path("/routes/{routeId}/slots/{slotId}")
	public Response updateSlotFromRoute(@PathParam("routeId") Long routeId, @PathParam("slotId") Long slotId, TimeSlot slot);

	@DELETE
	@Path("/routes/{routeId}/slots/{slotId}")
	public Response deleteSlotFromRoute(@PathParam("routeId") Long routeId, @PathParam("slotId") Long slotId);

	@GET
	@Path("/routes/{routeId}/slots/{slotId}/allowedAircrafts")
	public Response getAircraftsBySlot(@PathParam("routeId") Long routeId, @PathParam("slotId") Long slotId);

	@POST
	@Path("/routes/{routeId}/slots/{slotId}/allowedAircrafts")
	public Response saveAicraftForSlot(@PathParam("routeId") Long routeId, @PathParam("slotId") Long slotId, Aircraft aircraft);

	@GET
	@Path("/routes/{routeId}/slots/{slotId}/allowedAircrafts/{aircraftId}")
	public Response getAircraftFromSlot(@PathParam("routeId") Long routeId, @PathParam("slotId") Long slotId, @PathParam("aircraftId") Long aircraftId);

	@DELETE
	@Path("/routes/{routeId}/slots/{slotId}/allowedAircrafts/{aircraftId}")
	public Response deleteAircraftFromSlot(@PathParam("routeId") Long routeId, @PathParam("slotId") Long slotId, @PathParam("aircraftId") Long aircraftId);

	@GET
	@Path("/airlines")
	public List<Airline> getAirlines();

	@POST
	@Path("/airlines")
	public Response saveAirline(Airline airline);

	@GET
	@Path("/airlines/{id}")
	public Response getAirline(@PathParam("id") Long id);

	@PUT
	@Path("/airlines/{id}")
	public Response updateAirline(@PathParam("id") Long id, Airline airline);

	@DELETE
	@Path("/airlines/{id}")
	public Response deleteAirline(@PathParam("id") Long id);

	@GET
	@Path("/airlines/{airlineId}/aircrafts")
	public Response getAircraftsByAirline(@PathParam("airlineId") Long airlineId);

	@POST
	@Path("/airlines/{airlineId}/aircrafts")
	public Response saveAircraftForAirline(@PathParam("airlineId") Long airlineId, Aircraft aircraft);

	@GET
	@Path("/airlines/{airlineId}/aircrafts/{aircraftId}")
	public Response getAircraftFromAirline(@PathParam("airlineId") Long airlineId, @PathParam("aircraftId") Long aircraftId);

	@DELETE
	@Path("/airlines/{airlineId}/aircrafts/{aircraftId}")
	public Response deleteAircraftFromAirline(@PathParam("airlineId") Long airlineId, @PathParam("aircraftId") Long aircraftId);

	@GET
	@Path("/airlines/{airlineId}/partners")
	public Response getPartnersByAirline(@PathParam("airlineId") Long airlineId);

	@POST
	@Path("/airlines/{airlineId}/partners")
	public Response savePartnerForAirline(@PathParam("airlineId") Long airlineId, Airline partner);

	@GET
	@Path("/airlines/{airlineId}/partners/{partnerId}")
	public Response getPartnerFromAirline(@PathParam("airlineId") Long airlineId, @PathParam("partnerId") Long partnerId);

	@DELETE
	@Path("/airlines/{airlineId}/partners/{partnerId}")
	public Response deletePartnerFromAirline(@PathParam("airlineId") Long airlineId, @PathParam("partnerId") Long partnerId);

}
