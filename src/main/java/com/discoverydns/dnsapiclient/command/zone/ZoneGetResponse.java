package com.discoverydns.dnsapiclient.command.zone;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDateTime;
import org.xbill.DNS.Record;

import com.discoverydns.dnsapiclient.internal.views.response.ZoneGetView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Response object received by the {@link com.discoverydns.dnsapiclient.DNSAPIClient}
 * from the DNSAPI server subsequently to the sending of a {@link ZoneGetCommand},
 * describing the details of the retrieved Zone.
 * <p>
 * A Zone, belonging to an Account, will be managed by the DNSAPI architecture
 * for domain names resolution purpose.
 *
 * @author Chris Wright
 */
@JsonRootName("ZoneGetResponse")
@JsonPropertyOrder({"@uri", "id", "version", "name", "serial",
        "brandedNameServers", "dnssecSigned", "zskRollOverState", "pendingOperation", "lastPublishDate",
        "nameServerSetId", "nameServerSetName", "nameServerInterfaceSetId", "nameServerInterfaceSetName", "planId",
        "planName", "group", "sponsorAccountId", "sponsorAccountIdentifier", "createDate",
        "createAccountId", "createAccountIdentifier", "createUserId",
        "createUserName", "lastUpdateDate", "lastUpdateAccountId",
        "lastUpdateAccountIdentifier", "lastUpdateUserId",
        "lastUpdateUserName", "delegationResourceRecords",
        "ddnsResourceRecords", "resourceRecords", "axfrEnabled", "dnssecEnabled", "axfrServers",
        "axfrLastSuccessfulTransferredDate", "axfrLastSuccessfulTransferServer", "axfrLastUnsuccessfulTransferDate",
        "axfrLastUnsuccessfulTransferServer" })
public class ZoneGetResponse {

    @JsonIgnore
    private final ZoneGetView zoneGetView;

    public ZoneGetResponse(final ZoneGetView zoneGetView) {
        this.zoneGetView = zoneGetView;
    }

    /**
     * @return The URI representing the retrieved Zone on the DNSAPI server
     */
    @JsonProperty("@uri")
    public URI getURI() {
        return zoneGetView.getUri();
    }

    /**
     * @return The Zone UUID
     */
    @JsonProperty("id")
    public String getId() {
        return zoneGetView.getId();
    }

    /**
     * @return The Zone modification version
     */
    @JsonProperty("version")
    public Long getVersion() {
        return zoneGetView.getVersion();
    }

    /**
     * @return The Zone name
     */
    @JsonProperty("name")
    public String getName() {
        return zoneGetView.getName();
    }

    /**
     * @return true if the Zone uses the branded nameServers
     * {@link com.discoverydns.dnsapiclient.command.plan.PlanFeature}, false otherwise
     */
    @JsonProperty("brandedNameServers")
    public Boolean getBrandedNameServers() {
        return zoneGetView.getBrandedNameServers();
    }

    /**
     * @return The list of system-generated Resource {@link Record}s of the Zone
     */
    @JsonProperty("ddnsResourceRecords")
    public Set<Record> getDDNSResourceRecords() {
        return zoneGetView.getDDNSResourceRecords();
    }

    /**
     * @return The list of system-generated delegation Resource {@link Record}s of the Zone
     */
    @JsonProperty("delegationResourceRecords")
    public Set<Record> getDelegationResourceRecords() {
        return zoneGetView.getDelegationResourceRecords();
    }

    /**
     * @return true if the zone uses the DNSSEC signing
     * {@link com.discoverydns.dnsapiclient.command.plan.PlanFeature}, false otherwise
     */
    @JsonProperty("dnssecSigned")
    public Boolean getDNSSECSigned() {
        return zoneGetView.getDNSSECSigned();
    }

    /**
     * This returns the state of the zone in the ZSK roll over process.
     * This process enables the zone's Zone Signing Keys to be replaced after a certain period of time,
     * for security purpose.
     * The value returned can either be:
     * - "scheduled": the process has not started yet, but will start on "zskNextActionDate".
     * - "stage1": the new ZSK has been generated and added to the zone, but will only be used on
     * "zskNextActionDate".
     * - "stage2": the new ZSK is now in use and the old one is not anymore. The latter will be definitely
     * deleted on "zskNextActionDate".
     *
     * @return The state of the zone in the ZSK roll over process ("scheduled", "stage1" or "stage2"), if the zone
     * is DNSSEC-signed (null otherwise)
     */
    @JsonProperty("zskRollOverState")
    public String getZskRollOverState() {
        return zoneGetView.getZskRollOverState();
    }

    /**
     * @return The pending operation that will soon be actioned on the zone ('zoneSigning',
     * 'zoneUnSigning' or 'delete'), or 'none'
     */
    @JsonProperty("pendingOperation")
    public String getPendingOperation() {
        return zoneGetView.getPendingOperation();
    }

    /**
     * @return The last time the zone was published on the DNS anycast cloud
     */
    @JsonProperty("lastPublishDate")
    public LocalDateTime getLastPublishDate() {
        return zoneGetView.getLastPublishDate();
    }

    /**
     * @return The UUID of the NameServer Interface Set this Zone is associated with
     */
    @JsonProperty("nameServerInterfaceSetId")
    public String getNameServerInterfaceSetId() {
        return zoneGetView.getNameServerInterfaceSetId();
    }

    /**
     * @return The name of the NameServer Interface Set this Zone is associated with
     */
    @JsonProperty("nameServerInterfaceSetName")
    public String getNameServerInterfaceSetName() {
        return zoneGetView.getNameServerInterfaceSetName();
    }

    /**
     * @return The UUID of the NameServer Set this Zone is associated with
     */
    @JsonProperty("nameServerSetId")
    public String getNameServerSetId() {
        return zoneGetView.getNameServerSetId();
    }

    /**
     * @return The name of the NameServer Set this Zone is associated with
     */
    @JsonProperty("nameServerSetName")
    public String getNameServerSetName() {
        return zoneGetView.getNameServerSetName();
    }

    /**
     * @return The UUID of the Plan this Zone is using
     */
    @JsonProperty("planId")
    public String getPlanId() {
        return zoneGetView.getPlanId();
    }

    /**
     * @return The name of the Plan this Zone is using
     */
    @JsonProperty("planName")
    public String getPlanName() {
        return zoneGetView.getPlanName();
    }

    /**
     * @return The name of the group this Zone belongs to,
     * in case it's using the "grouping" {@link com.discoverydns.dnsapiclient.command.plan.PlanFeature}.
     */
    @JsonProperty("group")
    public String getGroup() {
        return zoneGetView.getGroup();
    }

    /**
     * @return The Zone serial number
     */
    @JsonProperty("serial")
    public Long getSerial() {
        return zoneGetView.getSerial();
    }

    /**
     * @return The list of user supplied (and modifiable) Resource {@link Record}s of the Zone
     */
    @JsonProperty("resourceRecords")
    public Set<Record> getUserResourceRecords() {
        return zoneGetView.getUserResourceRecords();
    }

    /**
     * @return The UUID of the Account who sponsors this Zone
     */
    @JsonProperty("sponsorAccountId")
    public String getSponsorAccountId() {
        return zoneGetView.getSponsorAccountId();
    }

    /**
     * @return The unique identifier of the Account who sponsors this Zone
     */
    @JsonProperty("sponsorAccountIdentifier")
    public String getSponsorAccountIdentifier() {
        return zoneGetView.getSponsorAccountIdentifier();
    }

    /**
     * @return The UUID of the Account who created this Zone
     */
    @JsonProperty("createAccountId")
    public String getCreateAccountId() {
        return zoneGetView.getCreateAccountId();
    }

    /**
     * @return The unique identifier of the Account who created this Zone
     */
    @JsonProperty("createAccountIdentifier")
    public String getCreateAccountIdentifier() {
        return zoneGetView.getCreateAccountIdentifier();
    }

    /**
     * @return The UUID of the User who created this Zone
     */
    @JsonProperty("createUserId")
    public String getCreateUserId() {
        return zoneGetView.getCreateUserId();
    }

    /**
     * @return The name of the User who created this Zone
     */
    @JsonProperty("createUserName")
    public String getCreateUserName() {
        return zoneGetView.getCreateUserName();
    }

    /**
     * @return The creation date of this Zone
     */
    @JsonProperty("createDate")
    public LocalDateTime getCreateDate() {
        return zoneGetView.getCreateDate();
    }

    /**
     * @return The UUID of the Account who last updated this Zone
     */
    @JsonProperty("lastUpdateAccountId")
    public String getLastUpdateAccountId() {
        return zoneGetView.getLastUpdateAccountId();
    }

    /**
     * @return The unique identifier of the Account who last updated this Zone
     */
    @JsonProperty("lastUpdateAccountIdentifier")
    public String getLastUpdateAccountIdentifier() {
        return zoneGetView.getLastUpdateAccountIdentifier();
    }

    /**
     * @return The UUID of the User who last updated this Zone
     */
    @JsonProperty("lastUpdateUserId")
    public String getLastUpdateUserId() {
        return zoneGetView.getLastUpdateUserId();
    }

    /**
     * @return The name of the User who last updated this Zone
     */
    @JsonProperty("lastUpdateUserName")
    public String getLastUpdateUserName() {
        return zoneGetView.getLastUpdateUserName();
    }

    /**
     * @return The last update date of this Zone
     */
    @JsonProperty("lastUpdateDate")
    public LocalDateTime getLastUpdateDate() {
        return zoneGetView.getLastUpdateDate();
    }

    /**
     * @return This is an AXFR enabled zone
     */
    @JsonProperty("axfrEnabled")
    public Boolean getAxfrEnabled() {
        return zoneGetView.getAxfrEnabled();
    }

    /**
     * @return List of servers to interrogate to do an AXFR transfer
     */
    @JsonProperty("axfrServers")
    public List<String> getAxfrServers() {
        return zoneGetView.getAxfrServers();
    }

    /**
     * @return The date of a the last successful AXFR transfer
     */
    @JsonProperty("axfrLastSuccessfulTransferredDate")
    public LocalDateTime getAxfrLastSuccessfulTransferredDate() {
        return zoneGetView.getAxfrLastSuccessfulTransferredDate();
    }

    /**
     * @return The server used for the last successful AXFR transfer
     */
    @JsonProperty("axfrLastSuccessfulTransferServer")
    public String getAxfrLastSuccessfulTransferServer() {
        return zoneGetView.getAxfrLastSuccessfulTransferServer();
    }

    /**
     * @return The date of a the last unsuccessful AXFR transfer.
     */
    @JsonProperty("axfrLastUnsuccessfulTransferDate")
    public LocalDateTime getAxfrLastUnsuccessfulTransferDate() {
        return zoneGetView.getAxfrLastUnsuccessfulTransferDate();
    }

    /**
     * @return The server used for the last unsuccessful AXFR transfer.
     */
    @JsonProperty("axfrLastUnsuccessfulTransferServer")
    public String getAxfrLastUnsuccessfulTransferServer() {
        return zoneGetView.getAxfrLastUnsuccessfulTransferServer();
    }

    /**
     * @return The name component of axfr TSIG.
     */
    @JsonProperty("axfrTsigName")
    public String getAxfrTsigName() {
        return zoneGetView.getAxfrTsigName();
    }

    /**
     * @return The algorithm component of axfr TSIG.
     */
    @JsonProperty("axfrTsigAlgorithm")
    public String getAxfrTsigAlgorithm() {
        return zoneGetView.getAxfrTsigAlgorithm();
    }

    /**
     * @return The key component of axfr TSIG.
     */
    @JsonProperty("axfrTsigKey")
    public String getAxfrTsigKey() {
        return zoneGetView.getAxfrTsigKey();
    }

    /**
     * @return Indicates the DNSSEC is enabled for the zone.
     */
    @JsonProperty("dnssecEnabled")
    public Boolean getDNSSECEnabled() {
        return zoneGetView.getDNSSECEnabled();
    }
}
