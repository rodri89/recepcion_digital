package com.example.rodrigo.recepcioncel;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by Rodrigo on 19/07/2017.
 */
public class subir_datos_web extends ActionBarActivity {
    private ProgressDialog pDialog;
    private String url,tabla;
    private int numFactura;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "invitados";

    JSONArray products = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_invitados);

        url=getIntent().getExtras().getString("url");
        tabla=getIntent().getExtras().getString("tabla");

        // Cargar los productos en el Background Thread
        new SubirDatos().execute();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }//fin onCreate

    class SubirDatos extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(subir_datos_web.this);
            pDialog.setMessage("Cargando "+tabla+". Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         * */
        protected String doInBackground(String... args) {
            BaseDatos baseHelper = new BaseDatos(subir_datos_web.this, "DEMODB", null, 1);
            //Elimino todos los datos de la tabla producto (base sqlite tablet)
            baseHelper.vaciarTabla(tabla,baseHelper);
            System.out.println("doInBackground rodri");
            enviarPost();

            return null;
        }


        public void enviarPost() {
            System.out.println("post rodri");
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = null;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(3);
                params.add(new BasicNameValuePair("aux", "1"));
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                System.out.println("post rodri 2");
                response = httpClient.execute(httpPost, localContext);
                System.out.println("post rodri 3");
                HttpEntity httpEntity = (HttpEntity) response.getEntity();
                InputStream is = httpEntity.getContent();
                System.out.println("post rodri 4"+ is.toString());
                //String text = EntityUtils.toString(httpEntity);
                //System.out.println(text);

            }
            catch (Exception e){ System.out.println("execption rodri");}
        }



        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            Intent intent= new Intent(subir_datos_web.this,ver_invitados.class);
            startActivity(intent);
            finish();
        }
    }
}
