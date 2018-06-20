package com.example.rodrigo.recepcioncel;

/**
 * Created by Rodrigo on 05/07/2017.
 */
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class sincronizar_con_web extends ActionBarActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
    private String url,tabla,clienteId;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // url to get all products list

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String URL_INVITADOS = "http://cimarrones.hol.es/recepcion_tablet/obtener_invitados.php";
    private static final String URL_EVENTOS = "http://cimarrones.hol.es/recepcion_tablet/obtener_eventos.php";


    // products JSONArray
    JSONArray objects = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sincronizar_con_web);

        clienteId=getIntent().getExtras().getString("clienteId");

        // Cargar los productos en el Background Thread
        new cargarDatos().execute();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }//fin onCreate


    class cargarDatos extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(sincronizar_con_web.this);
            pDialog.setMessage("Recuperando Datos. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los invitados
         * */
        protected String doInBackground(String... args) {
            BaseDatos baseHelper = new BaseDatos(sincronizar_con_web.this, "DEMODB", null, 1);
            //Elimino todos los datos de la tabla producto (base sqlite tablet)
            baseHelper.vaciarTabla("invitados",baseHelper);
            baseHelper.vaciarTabla("eventos",baseHelper);

            recuperarInvitados(baseHelper);
            recuperarEventos(baseHelper);

            return null;
        }

        public void recuperarInvitados(BaseDatos baseHelper){
            List params = new ArrayList();
            String parametros="aux="+clienteId;
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(URL_INVITADOS, "GET", params,parametros);
            // Check your log cat for JSON reponse
            System.out.println(url);
            Log.d("All Products: ", json.toString());

            cargarInvitados(baseHelper, json);
        }

        public void recuperarEventos(BaseDatos baseHelper){
            List params = new ArrayList();
            String parametros="aux="+clienteId;
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(URL_EVENTOS, "GET", params,parametros);
            // Check your log cat for JSON reponse
            System.out.println(url);
            Log.d("All Products: ", json.toString());

            cargarEventos(baseHelper, json);
        }

        public void cargarEventos(BaseDatos baseHelper,JSONObject json){
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    objects = json.getJSONArray("eventos");

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < objects.length(); i++) {
                        JSONObject c = objects.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString("id");  // la variable id es la que esta definida en el php (EJ :[{"id":"0","codigo":"61661"...)
                        String descripcion = c.getString("descripcion");
                        String fecha = c.getString("fecha");
                        String lugar = c.getString("lugar");
                        String mesas = c.getString("mesas");
                        String clienteId = c.getString("clienteId");
                        String empresaId = c.getString("empresaId");
                        String activo = c.getString("activo");

                        System.out.println(id+" "+descripcion+" "+lugar+" "+clienteId);

                        // inserto en mi tabla local
                        SQLiteDatabase db = baseHelper.getWritableDatabase();
                        if (db != null) {
                            ContentValues registroNuevo = new ContentValues();
                            //primer parametro nombre en la base, segundo el valor a ingresar
                            registroNuevo.put("eve_id", id);
                            registroNuevo.put("eve_descripcion", descripcion);
                            registroNuevo.put("eve_fecha", fecha);
                            registroNuevo.put("eve_lugar", lugar);
                            registroNuevo.put("eve_mesas", mesas);
                            registroNuevo.put("eve_cliente_id", clienteId);
                            registroNuevo.put("eve_empresa_id", empresaId);
                            registroNuevo.put("eve_activo", activo);

                            long z = db.insert("eventos", null, registroNuevo);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void cargarInvitados(BaseDatos baseHelper,JSONObject json){
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    objects = json.getJSONArray("invitados");

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < objects.length(); i++) {
                        JSONObject c = objects.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString("id");  // la variable id es la que esta definida en el php (EJ :[{"id":"0","codigo":"61661"...)
                        String nombre = c.getString("nombre");
                        String apellido = c.getString("apellido");
                        String sexo = c.getString("sexo");
                        String tipo = c.getString("tipo");
                        String mesa = c.getString("mesa");
                        String asistencia = c.getString("asistencia");
                        String activo = c.getString("activo");
                        String eventoId = c.getString("eventoId");

                        System.out.println(id+" "+eventoId+" "+apellido+" "+nombre);

                        // inserto en mi tabla local
                        SQLiteDatabase db = baseHelper.getWritableDatabase();
                        if (db != null) {
                            ContentValues registroNuevo = new ContentValues();
                            //primer parametro nombre en la base, segundo el valor a ingresar
                            registroNuevo.put("inv_id", id);
                            registroNuevo.put("inv_nombre", nombre);
                            registroNuevo.put("inv_apellido", apellido);
                            registroNuevo.put("inv_sexo", sexo);
                            registroNuevo.put("inv_tipo", tipo);
                            registroNuevo.put("inv_mesa", mesa);
                            registroNuevo.put("inv_asistencia", asistencia);
                            registroNuevo.put("inv_activo", activo);
                            registroNuevo.put("inv_eventoId", eventoId);

                            long z = db.insert("invitados", null, registroNuevo);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            Intent intent= new Intent(sincronizar_con_web.this,usuario_logueado.class);
            intent.putExtra("clienteId", clienteId);
            startActivity(intent);
            finish();
        }
    }
}