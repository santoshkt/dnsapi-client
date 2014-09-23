package com.discoverydns.dnsapiclient.command.zone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Command sent from a {@link com.discoverydns.dnsapiclient.DNSAPIClient} to the DNSAPI server,
 * to refresh an AXFR-managed Zone.
 * <p/>
 * A Zone, belonging to an Account, will be managed by the DNSAPI architecture
 * for domain names resolution purpose.
 *
 * @author Arnaud Dumont
 */
@JsonRootName("ZoneRefreshCommand")
@JsonPropertyOrder({"id"})
public class ZoneRefreshAXFRCommand {

    @JsonProperty("id")
    private String id;

    /**
     * Builder used to build the desired command.
     */
    public static class Builder {
        private String id;

        /**
         * Sets the id of the Zone to create.
         *
         * @param id The id of the Zone to create
         * @return The {@link Builder}
         */
        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        /**
         * Builds the {@link com.discoverydns.dnsapiclient.command.zone.ZoneRefreshAXFRCommand} from the parameters
         * set on the {@link Builder}.
         *
         * @return The built {@link com.discoverydns.dnsapiclient.command.zone.ZoneRefreshAXFRCommand}
         */
        public ZoneRefreshAXFRCommand build() {
            final ZoneRefreshAXFRCommand zoneRefreshCommand = new ZoneRefreshAXFRCommand();
            zoneRefreshCommand.id = id;

            return zoneRefreshCommand;
        }

    }

    private ZoneRefreshAXFRCommand() {
    }

    /**
     * @return The id of the Zone to refresh, set on the command.
     */
    public String getId() {
        return id;
    }
}
