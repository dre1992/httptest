/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenpost;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import com.google.gson.*;
import org.apache.http.Header;

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
byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
String authHeader = "Basic " + new String(encodedAuth);
//String authHeader = "Basic " +"YW5kcmVhcy5zZWZpY2hhQG1hcmtldHBsYWNlLWFuYWx5dGljcy5kZTo5MGRkYjg3NjExMWRiNjNmZDQ1YzUyMjdlNTNmZGIyYlhtMUJQQm03OHhDS1FUVm1OR1oxMHY5TVVyZkhWV3Vh";

httppost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);


httppost.setHeader(HttpHeaders.CONTENT_TYPE,"Content-Type: application/json");

Map<String, Object> params = new LinkedHashMap<>();
                params.put("SERVICE", "customer.get");
                

JSONObject json = new JSONObject();
json.put("SERVICE", "customer.get");

//Map<String, Object> params2 = new LinkedHashMap<>();

//params2.put("CUSTOMER_NUMBER","5");
 JSONObject array= new JSONObject();
 array.put("CUSTOMER_NUMBER","2");

json.put("FILTER",array);


StringEntity param = new StringEntity(json.toString());
httppost.setEntity(param);

HttpClient client = HttpClientBuilder.create().build();
HttpResponse response = client.execute(httppost);
 
int statusCode = response.getStatusLine().getStatusCode();
System.out.println("The status code is  " + statusCode);

//Execute and get the response.
HttpEntity entity = response.getEntity();

Header[] headers = response.getAllHeaders();
for (Header header : headers) {
	System.out.println("Key : " + header.getName()
	      + " ,Value : " + header.getValue());
}
 if (entity != null) {
           String retSrc = EntityUtils.toString(entity); //Discouraged better open a stream and read the data as per Apache manual                     
           // parsing JSON
           //JSONObject result = new JSONObject(retSrc);
           Gson gson = new GsonBuilder().setPrettyPrinting().create();
JsonParser jp = new JsonParser();
JsonElement je = jp.parse(retSrc);
String prettyJsonString = gson.toJson(je);
System.out.println(prettyJsonString);
 }
//if (entity != null) {
//    InputStream instream = entity.getContent();
//    try {
//  final BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(instream));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//            reader.close();
//    } finally {
//        instream.close();
//    }
//}
}
}


//prefered way to deal with responses
//CloseableHttpClient httpclient = HttpClients.createDefault();
//HttpGet httpget = new HttpGet("http://localhost/json");
//
//ResponseHandler<MyJsonObject> rh = new ResponseHandler<MyJsonObject>() {
//
//    @Override
//    public JsonObject handleResponse(
//            final HttpResponse response) throws IOException {
//        StatusLine statusLine = response.getStatusLine();
//        HttpEntity entity = response.getEntity();
//        if (statusLine.getStatusCode() >= 300) {
//            throw new HttpResponseException(
//                    statusLine.getStatusCode(),
//                    statusLine.getReasonPhrase());
//        }
//        if (entity == null) {
//            throw new ClientProtocolException("Response contains no content");
//        }
//        Gson gson = new GsonBuilder().create();
//        ContentType contentType = ContentType.getOrDefault(entity);
//        Charset charset = contentType.getCharset();
//        Reader reader = new InputStreamReader(entity.getContent(), charset);
//        return gson.fromJson(reader, MyJsonObject.class);
//    }
//};
//MyJsonObject myjson = client.execute(httpget, rh);