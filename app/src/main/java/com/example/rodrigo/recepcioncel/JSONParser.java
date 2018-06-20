package com.example.rodrigo.recepcioncel;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

//Para que funcione debemos buscar una cookie en chrome -- no hace falta! depende del servidor, con hostinger funciona todo bien.
/*
Getting the cookie key from the web browser. I used Google Chrome for it:
From the Chrome menu in the top right corner of the browser, select Settings (configuracion).
At the bottom of the page, click Show advanced settings (mostrar configuracion avanzada)....
Under Privacy, select Content settings (configuracion de contenido)....
select All cookies and site data (todos las cookies y los datos de sitios...)....
Search for you website name. By searching "byethost" you'll find it. (busco alguna palabra de la url)
Open the cookie named __test and copy the values of content, path and expires
 */
// Debemos agregar la linea de abajo en el metodo GET (solo get porque es el que vamos a utilizar)
//httpGet.addHeader("Cookie", "__test=06410fea214b2febfb23d387de6037da; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/");
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
                                      List params,String parametros) {

        // Making HTTP request
        try {

            // check for request method
            if(method.equals("POST")){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method.equals("GET")){
                // request method is GET

                HttpClient httpClient = new DefaultHttpClient();
                //String paramString = URLEncodedUtils.format(params, "utf-8");   esto comentado es la version original
                url += "?" + parametros;//url += "?" + paramString;  lo comentado es la version original

                HttpGet httpGet = new HttpGet(url);
                //httpGet.addHeader("Cookie", "__test=06410fea214b2febfb23d387de6037da; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/");
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();System.out.println(e.getMessage());
        } catch (ClientProtocolException e) {
            e.printStackTrace();System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();System.out.println(e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();System.out.println(e.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {

            jObj = new JSONObject(json);

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String

        return jObj;

    }
}