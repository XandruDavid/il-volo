package com.david.ilvolo;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.david.ilvolo.api.RestWebServicesImpl;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@ApplicationPath("")
public class HelloWorldApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		HashSet<Object> set = new HashSet<Object>();
		set.add(new RestWebServicesImpl());
		set.add(new JacksonJsonProvider());
		return set;
	}
}
