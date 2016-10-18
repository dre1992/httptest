/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenpost;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

/**
 *
 * @author dre
 */
public class HttpTest {
      static String DEFAULT_USER="andreas.seficha@marketplace-analytics.de"; 
  static String DEFAULT_PASS="90ddb876111db63fd45c5227e53fdb2bXm1BPBm78xCKQTVmNGZ10v9MUrfHVWua";
   
   public static void main(String args[]) throws UnsupportedEncodingException, IOException{ 
       
System.out.println("this is a test program");
HttpPost httppost = new HttpPost("https://app.monsum.com/api/1.0/api.php");

// Request parameters and other properties.
String auth = DEFAULT_USER + ":" + DEFAULT_PASS;
byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
String authHeader = "Basic " + new String(encodedAuth);
httppost.setHeader(HttpHeaders.AUTHORIZATION, auth);


httppost.setHeader(HttpHeaders.CONTENT_TYPE,"Content-Type: application/json");
JSONObject json = new JSONObject();
json.put("SERVICE", "customer.get");
json.put("FILTER","");
StringEntity param = new StringEntity(json.toString());
httppost.setEntity(param);

HttpClient client = HttpClientBuilder.create().build();
HttpResponse response = client.execute(httppost);
 
int statusCode = response.getStatusLine().getStatusCode();
System.out.println("The status code is" + statusCode);

//Execute and get the response.
HttpEntity entity = response.getEntity();

if (entity != null) {
    InputStream instream = entity.getContent();
    try {
        // do something useful
    } finally {
        instream.close();
    }
}
}
}



