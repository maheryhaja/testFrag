package fr.hdb.artibip.service.businessdelegate.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.springframework.http.client.SimpleClientHttpRequestFactory;


public class CustomSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory{
	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		HttpURLConnection.setFollowRedirects(true);
		((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
			
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return false;
			}
		});
		super.prepareConnection(connection, httpMethod);
	}
}
