package com.discoverydns.dnsapiclient.example.zone;

import ch.qos.logback.classic.Level;
import com.discoverydns.dnsapiclient.DNSAPIClient;
import com.discoverydns.dnsapiclient.DNSAPIClientFactory;
import com.discoverydns.dnsapiclient.Response;
import com.discoverydns.dnsapiclient.command.zone.ZoneListCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneListResponse;
import com.discoverydns.dnsapiclient.command.zone.ZoneRecord;
import com.discoverydns.dnsapiclient.config.DefaultSSLContextFactoryConfig;
import com.discoverydns.dnsapiclient.config.DefaultTransactionLogHandlerConfig;
import com.discoverydns.dnsapiclient.config.DNSAPIClientConfig;
import com.discoverydns.dnsapiclient.example.ExampleDNSAPIClientConfig;
import com.discoverydns.dnsapiclient.example.ExampleDefaultSSLContextFactoryConfig;
import com.discoverydns.dnsapiclient.example.ExampleDefaultTransactionLogHandlerConfig;

/**
 * Example of sending a ZoneListCommand and receiving an ZoneListResponse
 * @author Arnaud Dumont
 */
public class ZoneListCommandExample {

    public static void main(String[] args) {
        //Configure logging properties, using Logback
        final ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        //Instantiate configuration beans
        final DNSAPIClientConfig config = new ExampleDNSAPIClientConfig();
        final DefaultSSLContextFactoryConfig sslConfig = new ExampleDefaultSSLContextFactoryConfig();
        final DefaultTransactionLogHandlerConfig logConfig = new ExampleDefaultTransactionLogHandlerConfig();

        //Instantiate client instance from DNSAPIClientFactory
        final DNSAPIClientFactory dnsapiClientFactory = new DNSAPIClientFactory();
        DNSAPIClient client = null;
        try {
            client = dnsapiClientFactory.createInstanceFromDefaultProviders(config, sslConfig, logConfig);
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //Create command
        final ZoneListCommand command =
                new ZoneListCommand.Builder()
                        .build();

        //Send command to server and receive response
        try {
            Response<ZoneListResponse> response = client.process(command);

            System.out.println("== Successful response ==");
            System.out.println("== Server transaction id: " + response.getServerTransactionId());
            System.out.println("== Client transaction id: " + response.getClientTransactionId());
            System.out.println("== Processing time: " + response.getTransactionProcessingTime() + "ms");

            //The response object can now be used
            System.out.println("Search request URI: " + response.getResponseObject().getURI());
            System.out.println("Search results count: " + response.getResponseObject().getTotalRecordCount());
            for (ZoneRecord record: response.getResponseObject().getZoneRecords()) {
                System.out.println("-- Found Zone:");
                System.out.println("   Zone URI: " + record.getUri());
                System.out.println("   Zone UUID: " + record.getId());
                System.out.println("   Zone name: " + record.getName());
                System.out.println("   Zone branded nameServers: " + record.getBrandedNameServers());
                System.out.println("   Zone DNSSEC signed: " + record.getDnssecSigned());
                System.out.println("   Zone XFR enabled: " + record.getXfrEnabled());
                System.out.println("   Zone create date: " + record.getCreateDate());
                System.out.println("   Zone last update date: " + record.getLastUpdateDate());
            }
        } catch (final Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
