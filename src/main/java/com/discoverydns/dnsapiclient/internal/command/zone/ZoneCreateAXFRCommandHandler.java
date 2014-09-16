package com.discoverydns.dnsapiclient.internal.command.zone;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.discoverydns.dnsapiclient.command.zone.ZoneCreateAXFRCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneCreateAXFRResponse;
import com.discoverydns.dnsapiclient.framework.command.CommandMetaData;
import com.discoverydns.dnsapiclient.internal.command.BaseRestCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.InvocationBuildInvoker;
import com.discoverydns.dnsapiclient.internal.command.InvocationBuilderFactory;
import com.discoverydns.dnsapiclient.internal.command.Method;
import com.discoverydns.dnsapiclient.internal.command.WithEntityInvocationBuildInvoker;
import com.discoverydns.dnsapiclient.internal.command.WithEntityInvocationBuilderFactory;
import com.discoverydns.dnsapiclient.internal.views.ZoneCreateAXFRView;
import com.discoverydns.dnsapiclient.internal.views.ZoneGetView;

public class ZoneCreateAXFRCommandHandler extends
		BaseRestCommandHandler<ZoneCreateAXFRCommand, ZoneCreateAXFRResponse> {

	private final WebTarget zoneCreateAXFRTarget;

	public ZoneCreateAXFRCommandHandler(final WebTarget baseWebTarget) {
		super(Method.POST, Status.CREATED.getStatusCode(),
				MediaType.APPLICATION_JSON_TYPE);
		this.zoneCreateAXFRTarget = baseWebTarget.path("zones/axfr");
	}

	@Override
	public WebTarget getWebTarget(final ZoneCreateAXFRCommand command,
			final CommandMetaData commandMetaData) {
		return zoneCreateAXFRTarget;
	}

	@Override
	public InvocationBuilderFactory getInvocationBuilderFactory(
			final ZoneCreateAXFRCommand command,
			final CommandMetaData commandMetaData) {
		return new WithEntityInvocationBuilderFactory(
				MediaType.APPLICATION_JSON_TYPE);
	}

	@Override
	public InvocationBuildInvoker getInvocationBuildInvoker(
			final ZoneCreateAXFRCommand command,
			final CommandMetaData commandMetaData) {
		return new WithEntityInvocationBuildInvoker<ZoneCreateAXFRView>(
				new ZoneCreateAXFRView(command));
	}

	@Override
	public ZoneCreateAXFRResponse getCommandResponse(final Response restResponse,
			final CommandMetaData commandMetaData) {
		final ZoneGetView zoneGetView = getResponseEntity(ZoneGetView.class,
				restResponse);
		return new ZoneCreateAXFRResponse(zoneGetView);
	}

}
