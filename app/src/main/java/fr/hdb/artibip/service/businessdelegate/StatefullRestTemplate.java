package fr.hdb.artibip.service.businessdelegate;

import java.net.URI;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class StatefullRestTemplate extends RestTemplate
{
	private final HttpClient httpClient;
	private final CookieStore cookieStore;
	private final HttpContext httpContext;
	private final StatefullHttpComponentsClientHttpRequestFactory statefullHttpComponentsClientHttpRequestFactory;

	public StatefullRestTemplate()
	{
		super();
		HttpParams params = new BasicHttpParams();
		HttpClientParams.setRedirecting(params, false);
		httpClient = new DefaultHttpClient(params);
		cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
		statefullHttpComponentsClientHttpRequestFactory = new StatefullHttpComponentsClientHttpRequestFactory(httpClient, httpContext);
		super.setRequestFactory(statefullHttpComponentsClientHttpRequestFactory);
	}

	public StatefullRestTemplate(boolean includeIntialConverters) {
		super(includeIntialConverters); 
		HttpParams params = new BasicHttpParams();
		HttpClientParams.setRedirecting(params, false);
		httpClient = new DefaultHttpClient(params);
		cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
		statefullHttpComponentsClientHttpRequestFactory = new StatefullHttpComponentsClientHttpRequestFactory(httpClient, httpContext);
		super.setRequestFactory(statefullHttpComponentsClientHttpRequestFactory);
	}

	public HttpClient getHttpClient()
	{
		return httpClient;
	}

	public CookieStore getCookieStore()
	{
		return cookieStore;
	}

	public HttpContext getHttpContext()
	{
		return httpContext;
	}

	public StatefullHttpComponentsClientHttpRequestFactory getStatefulHttpClientRequestFactory()
	{
		return statefullHttpComponentsClientHttpRequestFactory;
	}

	/**
	 * 
	 * @author Morad
	 *
	 */
	 public class StatefullHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory
	 {
		private final HttpContext httpContext;

		public StatefullHttpComponentsClientHttpRequestFactory(HttpClient httpClient, HttpContext httpContext)
		{
			super(httpClient);
			this.httpContext = httpContext;
		}

		@Override
		protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri)
		{
			return this.httpContext;
		}
	 }
}


