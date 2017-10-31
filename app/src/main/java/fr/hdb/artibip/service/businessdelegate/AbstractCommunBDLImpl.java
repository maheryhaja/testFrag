package fr.hdb.artibip.service.businessdelegate;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.VersionInfo;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;

import android.util.Log;

import fr.hdb.artibip.service.businessdelegate.http.CustomSimpleClientHttpRequestFactory;
import fr.hdb.artibip.service.businessdelegate.http.MySSLSocketFactory;

/**
 * 
 * Implementation des appels webservices li�s � la r�cup�ration des flux des donn�es 
 * Documentation restTemplate :  http://docs.spring.io/spring-android/docs.old/1.0.x/reference/html/rest-template.html
 *
 */

public abstract class AbstractCommunBDLImpl {
	/**
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpHeaders
	 * @param urlVariables
	 * @exception HttpClientErrorException
	 * @return
	 */
	protected <T> T sendMessageForObject(String url, HttpMethod httpMethod , Class<T> responseType,HttpHeaders httpHeaders,Map<String, String> urlVariables) 
			throws HttpClientErrorException,RestClientException {
		return sendMessageForObject(null,url, httpMethod , responseType,urlVariables,null,null,null,httpHeaders,null);
	}

	/**
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpHeaders
	 * @param urlVariables
	 * @param converters
	 * @return
	 */
	protected <T> T sendMessageForObject(String url, HttpMethod httpMethod , Class<T> responseType,HttpHeaders httpHeaders,Map<String, String> urlVariables,List<HttpMessageConverter<?>> converters) 
			throws HttpClientErrorException,RestClientException{
		return sendMessageForObject(null,url, httpMethod , responseType, urlVariables,null,null,null,httpHeaders,converters);
	}

	/**
	 * @param <T>
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpHeaders
	 * @param urlVariables
	 * @param converters
	 * @return
	 */
	protected <T> ResponseEntity<T> sendMessageForEntity(String url
            , HttpMethod httpMethod
            , Class<T> responseType
            ,HttpHeaders httpHeaders
            ,Map<String, String> urlVariables
            ,List<HttpMessageConverter<?>> converters
    ) throws HttpClientErrorException,RestClientException{
		return sendMessageForEntity(null
                , url
                , httpMethod
                , responseType
                , null
                , urlVariables
                , null
                , httpHeaders
                , converters
        );
	}

	protected <T> ResponseEntity<T> sendMessageForEntity(String url
			, HttpMethod httpMethod
			, Class<T> responseType
			,HttpHeaders httpHeaders
			,Map<String
			, String> urlVariables
			,HttpAuthentication httpAuthentication
			,List<HttpMessageConverter<?>> converters
	) throws HttpClientErrorException,RestClientException{
		return sendMessageForEntity(null
				, url
				, httpMethod
				, responseType
				, null
				, urlVariables
				, httpAuthentication
				, httpHeaders
				, converters
		);
	}

	/**
	 * @param <R>
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpAuthentication
	 * @param httpHeaders
	 * @param requestObject
	 * @return
	 */
	protected <T, R> T sendMessageAuthForObject(RestTemplate restTemplate, String url, HttpMethod httpMethod , Class<T> responseType,HttpAuthentication httpAuthentication,HttpHeaders httpHeaders,R requestObject,List<HttpMessageConverter<?>> converters)
			throws HttpClientErrorException,RestClientException {
		Map<String, ?> urlVariables = new HashMap<String, Object>();
		return sendMessageForObject(restTemplate,url, httpMethod , responseType,urlVariables,httpAuthentication,null,requestObject,httpHeaders,converters);
	}

	/**
	 * @param restTemplateInput
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpAuthentication
	 * @param httpHeaders
	 * @param requestObject
	 * @param urlVariables
	 * @param converters
	 * @return
	 */
	protected <T, R ,V> HttpStatus getCallStatus(RestTemplate restTemplateInput, String url, HttpMethod httpMethod , Class<T> responseType,HttpAuthentication httpAuthentication,HttpHeaders httpHeaders,R requestObject,Map<String, V> urlVariables,List<HttpMessageConverter<?>> converters) 
			throws RestClientException {
		ResponseEntity<T> entity=null;
		try {
			entity= sendMessageForEntity(restTemplateInput, url, httpMethod, responseType, requestObject, urlVariables, httpAuthentication, httpHeaders, converters);
		} catch (HttpClientErrorException e) {
			return e.getStatusCode();
		} catch (RestClientException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
			throw e;
		}

		return entity.getStatusCode();
	}

	/**
	 * @param restTemplateInput
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpAuthentication
	 * @param httpHeaders
	 * @param requestObject
	 * @param urlVariables
	 * @param converters
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 * @return
	 */
	protected <T, R ,V> ResponseEntity<T> getCallStatusForEntity(RestTemplate restTemplateInput, String url, HttpMethod httpMethod , Class<T> responseType,HttpAuthentication httpAuthentication,HttpHeaders httpHeaders,R requestObject,Map<String, V> urlVariables,List<HttpMessageConverter<?>> converters) 
			throws HttpClientErrorException,RestClientException {
		ResponseEntity<T> entity=null;
		try {
			entity= sendMessageForEntity(restTemplateInput, url, httpMethod, responseType, requestObject, urlVariables, httpAuthentication, httpHeaders, converters);
			return entity;
		} catch (HttpClientErrorException e) {
			Log.w(this.getClass().getName(), e.getMessage(), e);
			throw e;
		} catch (RestClientException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpAuthentication
	 * @exception HttpClientErrorException
	 * @return
	 */
	protected <T> T sendMessageAuthForObject(String url, HttpMethod httpMethod , Class<T> responseType,Map<String, ?> urlVariables , HttpAuthentication httpAuthentication,Map<String, String> headers) 
			throws HttpClientErrorException , RestClientException {
		return sendMessageForObject(null,url, httpMethod , responseType, urlVariables,httpAuthentication,headers,null,null,null);
	}

	/**
	 * @param <R>
	 * @param restTemplateInput
	 * @param url
	 * @param httpMethod
	 * @param responseType
	 * @param httpAuthentication Authentification
	 * @throws HttpClientErrorException contenant les status d'erreur
	 * @return
	 */
	private <T, R,V> T sendMessageForObject(RestTemplate restTemplateInput, String url, HttpMethod httpMethod  , Class<T> responseType, Map<String, V> urlVariables,HttpAuthentication httpAuthentication , Map<String, String> headers,R requestObject,HttpHeaders httpHeaders,List<HttpMessageConverter<?>> converters) 
			throws HttpClientErrorException , RestClientException {
		T result =null ;

		try {
			ResponseEntity<T> responseEntity = sendMessageForEntity(restTemplateInput, url, httpMethod, responseType, requestObject, urlVariables, httpAuthentication, httpHeaders, converters);
			if(responseEntity==null) {
				return null;
			}
			result = responseEntity.getBody();
		}catch( HttpClientErrorException e ) {
			throw e;
		} catch (RestClientException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
			throw e;
		}

		return result;
	}

	/**
	 * @param restTemplateInput Utiliser s'il y a besoin d'un objet RestTemplate spécifique ( Exemple avec gestion de cookie )
	 * @param url  Url Rest à appeler .
	 * @param httpMethod  POST ,GET , PUT ...
	 * @param responseType  Class Type de réponse 
	 * @param requestObject Objet à envoyer dans la requeste ( JSON , XML , FORM DATA en fonction de du Content-Type ) il faut rajouter les converters nécéssaire pour la converion XML/Json ...
	 * @param urlVariables  La liste des couple Key/Value qui remplacera les variables de existante dans l'url    http://url?id={identifiant}  ou http://url/{identifiant}/suiteUrl/
	 * @param httpAuthentication Utilisation de l'objet authenfication pour les webservices avec authentification
	 * @param httpHeaders  Initialisation des headers HTTP
	 * @param converters   Les convertes à utiliser en plus des converters par defaut .
	 * @throws HttpClientErrorException 
	 * @throws RestClientException
	 * 
	 * @return
	 */
	protected <T, R,V> ResponseEntity<T> sendMessageForEntity(RestTemplate restTemplateInput
            , String url
            , HttpMethod httpMethod
            , Class<T> responseType
            , R requestObject
            , Map<String, V> urlVariables
            ,HttpAuthentication httpAuthentication
            ,HttpHeaders httpHeaders
            ,List<HttpMessageConverter<?>> converters
    ) throws HttpClientErrorException , RestClientException{
		List<HttpMessageConverter<?>> listConverters = converters;
		if(listConverters ==null || listConverters.isEmpty() ) {
			listConverters =new ArrayList<HttpMessageConverter<?>>();
			MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();
			listConverters.add(mappingJackson2HttpMessageConverter);
		}

		if(urlVariables==null) {
			urlVariables = new HashMap<String, V>();
		}
		// Create a new RestTemplate instance
		RestTemplate restTemplate =getRestTemplateInstance(listConverters,restTemplateInput);

		HttpHeaders requestHeaders=httpHeaders;
		if(httpHeaders==null) {
			//Create Header		
			requestHeaders = getInstanceRequestHeaders(httpAuthentication);
		} else if (httpAuthentication!=null){
			requestHeaders.setAuthorization(httpAuthentication);
		}

		HttpEntity<R> requestEntity=null;
		if(requestHeaders!=null && requestObject!=null){
			requestEntity = new HttpEntity<R>(requestObject,requestHeaders);
		} else if (requestObject!=null) {
			requestEntity = new HttpEntity<R>(requestObject);
		} else if (requestHeaders!=null) {
			requestEntity = new HttpEntity<R>(requestHeaders);
		}

		ResponseEntity<T> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(url,httpMethod,requestEntity,responseType,urlVariables);
		}catch(Exception e){
			Log.e(getClass().getSimpleName(), "error: " + e.getMessage(), e);
		}

		return responseEntity;
	}

	/**
	 * Instanciation d'un objet de type RestTemplate
	 * @return
	 */
	protected  RestTemplate getRestTemplateInstance(List<HttpMessageConverter<?>> converters,RestTemplate restTemplateInput) {
		SimpleClientHttpRequestFactory clientHttpRequestFactory = new CustomSimpleClientHttpRequestFactory();
		// on met le timeout à 15 secondes
		clientHttpRequestFactory.setConnectTimeout(15000);
		// Create a new RestTemplate instance
		RestTemplate restTemplate=restTemplateInput;

		if(restTemplate==null) {
			restTemplate = new RestTemplate(true);
		}
		ClientHttpRequestFactory HttpComponentsClientHttpRequestFactory = new org.springframework.http.client.HttpComponentsClientHttpRequestFactory(getNewHttpClient()) ;
		restTemplate.setRequestFactory(HttpComponentsClientHttpRequestFactory);
		if(converters!=null) {
			List<HttpMessageConverter<?>> convertersRestTemplate = restTemplate.getMessageConverters();
			for (HttpMessageConverter<?> httpMessageConverter : converters) {
				boolean isExist=false;
				for (HttpMessageConverter<?> httpMessageConverterInput : convertersRestTemplate) {
					if( httpMessageConverterInput.getClass().getCanonicalName().equalsIgnoreCase(httpMessageConverter.getClass().getCanonicalName()) ) {
						isExist=true;
						continue;
					}
				}
				if(!isExist) {
					convertersRestTemplate.add(httpMessageConverter);
				}
			}
		}
		return restTemplate;
	}

	/**
	 * Instanciation d'un objet de type RestTemplate
	 * @return
	 */
	protected  StatefullRestTemplate getRestTemplateInstance() {
		StatefullRestTemplate restTemplate = new StatefullRestTemplate(true);
		return restTemplate;
	}

	/**
	 * Creation HttpHeaders ( a surcharger en cas de besoin )
	 * @return
	 */
	protected HttpHeaders getInstanceRequestHeaders(HttpAuthentication httpAuthentication) {
		HttpHeaders requestHeaders = new HttpHeaders();
		if(httpAuthentication!=null) {
			requestHeaders.setAuthorization(httpAuthentication);
		}
		return requestHeaders;
	}

	/**
	 * for test only to avoir problem  org.springframework.web.client.ResourceAccessException: I/O error: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
	 * @return
	 */
	public HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            VersionInfo vi = VersionInfo.loadVersionInfo("org.apache.http.client", DefaultHttpClient.class.getClassLoader());
            String release = vi != null?vi.getRelease():"UNAVAILABLE";
            HttpProtocolParams.setUserAgent(params, "Apache-HttpClient/" + release + " (java 1.5)");

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	/**
	 * get the header for the service
	 * @return
	 */
	protected HttpHeaders getHeader() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Content-Type", "application/json");
		return requestHeaders;
	}

	/**
	 * get the header for the service with token
	 * @return
	 */
	protected HttpHeaders getHeaderWithToken(String keyHeader,String token) {
		HttpHeaders headers = getHeader();
		headers.add(keyHeader,token);
		return headers;
	}

	/**
	 * get the message converter for the service
	 * @return
	 */
	protected List<HttpMessageConverter<?>> getConverter() {
		List<HttpMessageConverter<?>> listConverters; 
		listConverters =new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		listConverters.add(mappingJackson2HttpMessageConverter);
		return listConverters;
	}

	/**
	 * convert an object to a json
	 * @param objectToConvert
	 * @param formatDate
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected String getJson(Object objectToConvert, boolean formatDate) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		if(formatDate){
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.FRANCE));
		}
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(objectToConvert);
		return json;
	}

}
