/*
 * oxNotify is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2017, Gluu
 */

package org.gluu.oxnotify.rest;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.gluu.oxnotify.model.NotifyMetadata;
import org.gluu.oxnotify.model.conf.Configuration;
import org.slf4j.Logger;

/**
 * The endpoint at which the requester can obtain oxNotify metadata configuration
 *
 * @author Yuriy Movchan
 * @version Septempber 15, 2017
 */
public class MetadataRestServiceImpl implements MetadataRestService {

	@Inject
	private Logger log;

	@Inject
	private Configuration configuration;

	public Response getConfiguration() {
		try {
			String baseEndpointUri = configuration.getBaseEndpoint();
			String issuer = configuration.getIssuer();

			NotifyMetadata notifyMetadata = new NotifyMetadata();
			notifyMetadata.setVersion("1.0");
			notifyMetadata.setIssuer(issuer);

			notifyMetadata.setNotifyEndpoint(baseEndpointUri);
			
			log.trace("Notify metadata: {0}", notifyMetadata);

			return Response.ok(notifyMetadata).build();
		} catch (Throwable ex) {
			log.error(ex.getMessage(), ex);
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					"The notify server encountered an unexpected condition which prevented it from fulfilling the request.")
					.build());
		}
	}

}
