package com.discoverydns.dnsapiclient.command.nameServerSet;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.joda.time.LocalDateTime;

/**
 * List record representing a NameServer Set, from the list returned in the {@link NameServerSetListResponse}.
 *
 * A NameServer Set represents a logical entity of the DNSAPI architecture,
 * to link an Account with a NameServer Interface Set,
 * through the configuration of the Name Server names that will be provided to the Account's Zones.
 *
 * @author Chris Wright
 */
@JsonRootName("nameServerSet")
public class NameServerSetRecord {

	@JsonProperty("@uri")
	private URI uri;
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("createDate")
	private LocalDateTime createDate;
	@JsonProperty("lastUpdateDate")
	private LocalDateTime lastUpdateDate;

	public NameServerSetRecord() {
	}

	public NameServerSetRecord(URI uri, String id, String name,
                               LocalDateTime createDate, LocalDateTime lastUpdateDate) {
		this.uri = uri;
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
	}

    /**
     * @return The URI representing the NameServer Set list record on the DNSAPI server
     */
	public URI getUri() {
		return uri;
	}

    /**
     * @return The NameServer Set UUID
     */
	public String getId() {
		return id;
	}

    /**
     * @return The NameServer Set name
     */
	public String getName() {
		return name;
	}

    /**
     * @return The creation date of the NameServer Set
     */
	public LocalDateTime getCreateDate() {
		return createDate;
	}

    /**
     * @return The last update date of the NameServer Set
     */
	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NameServerSetRecord)) {
			return false;
		}
		NameServerSetRecord other = (NameServerSetRecord) obj;
		if (createDate == null) {
			if (other.createDate != null) {
				return false;
			}
		} else if (!createDate.equals(other.createDate)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastUpdateDate == null) {
			if (other.lastUpdateDate != null) {
				return false;
			}
		} else if (!lastUpdateDate.equals(other.lastUpdateDate)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (uri == null) {
			if (other.uri != null) {
				return false;
			}
		} else if (!uri.equals(other.uri)) {
			return false;
		}
		return true;
	}

}
