package com.discoverydns.dnsapiclient.command.nameServerInterfaceSet;

import java.net.Inet4Address;
import java.net.Inet6Address;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NameServerInterface {

	@JsonProperty("order")
	private int order;
	@JsonProperty("name")
	private String name;
	@JsonProperty("ipv4Address")
	private Inet4Address ipv4Address;
	@JsonProperty("ipv6Address")
	private Inet6Address ipv6Address;

	public int getOrder() {
		return order;
	}

	public String getName() {
		return name;
	}

	public Inet4Address getIpv4Address() {
		return ipv4Address;
	}

	public Inet6Address getIpv6Address() {
		return ipv6Address;
	}

}