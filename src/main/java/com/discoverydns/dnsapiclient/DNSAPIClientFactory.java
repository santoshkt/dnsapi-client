package com.discoverydns.dnsapiclient;

import java.net.URI;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnector;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.filter.CsrfProtectionFilter;
import org.glassfish.jersey.client.spi.Connector;

import com.discoverydns.dnsapiclient.command.account.AccountGetCommand;
import com.discoverydns.dnsapiclient.command.nameServerInterfaceSet.NameServerInterfaceSetGetCommand;
import com.discoverydns.dnsapiclient.command.nameServerSet.NameServerSetGetCommand;
import com.discoverydns.dnsapiclient.command.nameServerSet.NameServerSetListCommand;
import com.discoverydns.dnsapiclient.command.plan.PlanGetCommand;
import com.discoverydns.dnsapiclient.command.plan.PlanListCommand;
import com.discoverydns.dnsapiclient.command.user.UserGetCommand;
import com.discoverydns.dnsapiclient.command.user.UserListCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneCreateCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneDeleteCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneGetCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneGetQueryUsageCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneListCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneUpdateCommand;
import com.discoverydns.dnsapiclient.command.zone.ZoneUpdateResourceRecordsCommand;
import com.discoverydns.dnsapiclient.framework.command.BlockingCommandExecutor;
import com.discoverydns.dnsapiclient.framework.command.CommandProcessor;
import com.discoverydns.dnsapiclient.framework.impl.basic.BasicCommandProcessor;
import com.discoverydns.dnsapiclient.internal.DefaultClientTransactionIdStrategy;
import com.discoverydns.dnsapiclient.internal.DefaultObjectMapperFactory;
import com.discoverydns.dnsapiclient.internal.DefaultSSLContextFactory;
import com.discoverydns.dnsapiclient.internal.DefaultTransactionLogHandler;
import com.discoverydns.dnsapiclient.internal.command.account.AccountGetCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.nameserverinterfaceset.NameServerInterfaceSetGetCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.nameserverset.NameServerSetGetCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.nameserverset.NameServerSetListCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.plan.PlanGetCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.plan.PlanListCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.user.UserGetCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.user.UserListCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.zone.ZoneCreateCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.zone.ZoneDeleteCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.zone.ZoneGetCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.zone.ZoneGetQueryUsageCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.zone.ZoneListCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.zone.ZoneUpdateCommandHandler;
import com.discoverydns.dnsapiclient.internal.command.zone.ZoneUpdateResourceRecordsCommandHandler;
import com.discoverydns.dnsapiclient.internal.commandinterceptors.ClientTransactionIdCommandInterceptor;
import com.discoverydns.dnsapiclient.internal.commandinterceptors.StopwatchCommandInterceptor;
import com.discoverydns.dnsapiclient.internal.commandinterceptors.TransactionLogCommandInterceptor;
import com.discoverydns.dnsapiclient.internal.json.ErrorHandlingJacksonJsonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * The factory to create {@link DNSAPIClient} instances.
 * @author Chris Wright
 */
public class DNSAPIClientFactory {

	private SSLContextFactory sslContextFactory = new DefaultSSLContextFactory();
	private ObjectMapperFactory objectMapperFactory = new DefaultObjectMapperFactory();
	private ClientTransactionIdStrategy clientTransactionIdStrategy = new DefaultClientTransactionIdStrategy();

    /**
     * Sets the {@link SSLContextFactory} to be used to create {@link SSLContext} instances,
     * to establish secure communications between the {@link DNSAPIClient} instances and the DNSAPI server.
     * If not set, a {@link DefaultSSLContextFactory} will be used,
     * using the KeyStore and TrustStore files provided in the {@link DNSAPIClientConfig}.
     * @param sslContextFactory The {@link SSLContextFactory} to be used
     */
	public void setSSLContextFactory(final SSLContextFactory sslContextFactory) {
		this.sslContextFactory = sslContextFactory;
	}

    /**
     * Sets the {@link ObjectMapperFactory} to create {@link ObjectMapper} instances,
     * used by the created {@link DNSAPIClient} instances
     * to serialize/deserialize the JSON data sent to/coming from the DNSAPI server.
     * If not set, a {@link DefaultObjectMapperFactory} will be used,
     * using a set of predefined serializers and deserializers.
     * @param objectMapperFactory The {@link ObjectMapperFactory} to be used
     */
	public void setObjectMapperFactory(
			final ObjectMapperFactory objectMapperFactory) {
		this.objectMapperFactory = objectMapperFactory;
	}

    /**
     * Sets the {@link ClientTransactionIdStrategy} used by the framework
     * when a command is sent to the DNSAPI server,
     * to generate a client transaction id to be put in the meta-data.
     * If not set, a {@link DefaultClientTransactionIdStrategy} will be used,
     * generating a random {@link java.util.UUID} every time.
     * @param clientTransactionIdStrategy The {@link ClientTransactionIdStrategy} to be used
     */
	public void setClientTransactionIdStrategy(
			final ClientTransactionIdStrategy clientTransactionIdStrategy) {
		this.clientTransactionIdStrategy = clientTransactionIdStrategy;
	}

    /**
     * Creates a {@link DNSAPIClient} instance from the given {@link DNSAPIClientConfig} configuration.
     * The {@link DefaultTransactionLogHandler} will be used for transaction logging.
     * @param config The configuration to be used to create the instance
     * @return The created {@link DNSAPIClient} instance
     * @throws Exception In case of any error
     */
	public DNSAPIClient createInstance(final DNSAPIClientConfig config)
			throws Exception {
		return createInstance(config, null);
	}

    /**
     * Creates a {@link DNSAPIClient} instance from the given {@link DNSAPIClientConfig} configuration,
     * using the provided {@link TransactionLogHandler} for transaction logging.
     * @param config The configuration to be used to create the instance
     * @param transactionLogHandler The {@link TransactionLogHandler} to be used for transaction logging
     * @return The created {@link DNSAPIClient} instance
     * @throws Exception In case of any error
     */
	public DNSAPIClient createInstance(final DNSAPIClientConfig config,
			TransactionLogHandler transactionLogHandler) throws Exception {
		final ObjectMapper mapper = objectMapperFactory.createInstance();
		final Client client = createClient(config, mapper);
		final WebTarget baseWebTarget = client.target(config.getBaseUri());

		final CommandProcessor commandProcessor = new BasicCommandProcessor();
		// UserCommands
		commandProcessor.subscribe(UserGetCommand.class,
				new UserGetCommandHandler(baseWebTarget));
		commandProcessor.subscribe(UserListCommand.class,
				new UserListCommandHandler(baseWebTarget));
		// PlanCommands
		commandProcessor.subscribe(PlanGetCommand.class,
				new PlanGetCommandHandler(baseWebTarget));
		commandProcessor.subscribe(PlanListCommand.class,
				new PlanListCommandHandler(baseWebTarget));
		// NameServerSetCommands
		commandProcessor.subscribe(NameServerSetGetCommand.class,
				new NameServerSetGetCommandHandler(baseWebTarget));
		commandProcessor.subscribe(NameServerSetListCommand.class,
				new NameServerSetListCommandHandler(baseWebTarget));
		// NameServerInterfaceSetCommands
		commandProcessor.subscribe(NameServerInterfaceSetGetCommand.class,
				new NameServerInterfaceSetGetCommandHandler(baseWebTarget));
		// AccountCommands
		commandProcessor.subscribe(AccountGetCommand.class,
				new AccountGetCommandHandler(baseWebTarget));
		// ZoneCommands
		commandProcessor.subscribe(ZoneGetCommand.class,
				new ZoneGetCommandHandler(baseWebTarget));
		commandProcessor.subscribe(ZoneListCommand.class,
				new ZoneListCommandHandler(baseWebTarget));
		commandProcessor.subscribe(ZoneCreateCommand.class,
				new ZoneCreateCommandHandler(baseWebTarget));
		commandProcessor.subscribe(ZoneUpdateCommand.class,
				new ZoneUpdateCommandHandler(baseWebTarget));
		commandProcessor.subscribe(ZoneUpdateResourceRecordsCommand.class,
				new ZoneUpdateResourceRecordsCommandHandler(baseWebTarget));
		commandProcessor.subscribe(ZoneDeleteCommand.class,
				new ZoneDeleteCommandHandler(baseWebTarget));
        commandProcessor.subscribe(ZoneGetQueryUsageCommand.class,
                new ZoneGetQueryUsageCommandHandler(baseWebTarget));

		if (transactionLogHandler == null) {
			transactionLogHandler = new DefaultTransactionLogHandler(config,
					mapper);
		}

		// Interceptors
		commandProcessor
				.addCommandInterceptor(new TransactionLogCommandInterceptor(
						transactionLogHandler));
		commandProcessor
				.addCommandInterceptor(new ClientTransactionIdCommandInterceptor(
						clientTransactionIdStrategy));
		commandProcessor
				.addCommandInterceptor(new StopwatchCommandInterceptor());

		final BlockingCommandExecutor blockingCommandExecutor = new BlockingCommandExecutor(
				commandProcessor);

		return new DNSAPIClient(client, blockingCommandExecutor);
	}

	private Client createClient(final DNSAPIClientConfig config,
			final ObjectMapper mapper) throws Exception {
		final JacksonJsonProvider jacksonJsonProvider = new ErrorHandlingJacksonJsonProvider(
				mapper);
		final SSLContext sslContext = sslContextFactory
				.createSSLContext(config);

		final SSLSocketFactory sslSocketFactory = new SSLSocketFactory(
				sslContext);
		final SchemeRegistry schemeRegistry = createSchemeRegistry(config,
				sslSocketFactory);
		final HttpParams httpParams = createHttpParams();
		final PoolingClientConnectionManager clientConnectionManager = createPoolingClientConnectionManager(
				config, schemeRegistry);
		final ClientConfig clientConfig = createClientConfig(config,
				jacksonJsonProvider, httpParams, clientConnectionManager);
		final Connector connector = new ApacheConnector(clientConfig);
		clientConfig.connector(connector);
		clientConfig.register(new CsrfProtectionFilter(
				"DiscoveryDNS Reseller API Client"));
		final ClientBuilder clientBuilder = ClientBuilder.newBuilder();
		clientBuilder.withConfig(clientConfig);
		final Client client = clientBuilder.build();

		return client;
	}

	private ClientConfig createClientConfig(final DNSAPIClientConfig config,
			final JacksonJsonProvider jacksonJsonProvider,
			final HttpParams httpParams,
			final PoolingClientConnectionManager clientConnectionManager) {
		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(jacksonJsonProvider);
		clientConfig.property(
				ClientProperties.BUFFER_RESPONSE_ENTITY_ON_EXCEPTION, true);
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT,
				config.getTimeout());
		clientConfig.property(ClientProperties.FEATURE_AUTO_DISCOVERY_DISABLE,
				true);
		clientConfig.property(ClientProperties.FOLLOW_REDIRECTS, false);
		clientConfig.property(ClientProperties.JSON_PROCESSING_FEATURE_DISABLE,
				false);
		clientConfig.property(ClientProperties.METAINF_SERVICES_LOOKUP_DISABLE,
				true);
		clientConfig.property(ClientProperties.MOXY_JSON_FEATURE_DISABLE, true);
		clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER,
				clientConnectionManager);
		clientConfig.property(ApacheClientProperties.DISABLE_COOKIES, true);
		clientConfig.property(ApacheClientProperties.HTTP_PARAMS, httpParams);
		clientConfig.property(
				ApacheClientProperties.PREEMPTIVE_BASIC_AUTHENTICATION, false);
		return clientConfig;
	}

	private PoolingClientConnectionManager createPoolingClientConnectionManager(
			final DNSAPIClientConfig config, final SchemeRegistry schemeRegistry) {
		final PoolingClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager(
				schemeRegistry);
		clientConnectionManager.setMaxTotal(config.getMaxConnections());
		clientConnectionManager.setDefaultMaxPerRoute(config
				.getMaxConnections());
		return clientConnectionManager;
	}

	private HttpParams createHttpParams() {
		final HttpParams httpParams = new BasicHttpParams();
		return httpParams;
	}

	private SchemeRegistry createSchemeRegistry(
			final DNSAPIClientConfig config,
			final SSLSocketFactory sslSocketFactory) {
		final SchemeRegistry schemeRegistry = new SchemeRegistry();
		final int port = getPortFromUri(config.getBaseUri());
		schemeRegistry.register(new Scheme("https", port, sslSocketFactory));
		return schemeRegistry;
	}

	private int getPortFromUri(final URI uri) {
		int port = uri.getPort();
		if (port == -1) {
			if (uri.getScheme().equalsIgnoreCase("https")) {
				port = 443;
			} else if (uri.getScheme().equalsIgnoreCase("http")) {
				port = 80;
			}
		}
		return port;
	}

}
