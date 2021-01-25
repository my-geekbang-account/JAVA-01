package io.github.anyone;

import java.io.IOException;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class App {
  public static void main(String[] args) throws IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpget = new HttpGet("http://localhost:8801");

    HttpClientResponseHandler<String> respHandler = new HttpClientResponseHandler<String>() {
      @Override
      public String handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        final int status = response.getCode();
        if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
          final HttpEntity entity = response.getEntity();
          try {
            return entity != null ? EntityUtils.toString(entity) : null;
          } catch (final ParseException ex) {
            throw new ClientProtocolException(ex);
          }
        } else {
          throw new ClientProtocolException("Unexpected response status: " + status);
        }
      }
    };
    String responseBody = httpclient.execute(httpget, respHandler);
    System.out.println("----------------------------------");
    System.out.println(responseBody);
    httpclient.close();
  }
}
