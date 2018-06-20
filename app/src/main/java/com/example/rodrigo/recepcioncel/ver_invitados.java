package com.example.rodrigo.recepcioncel;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class ver_invitados extends AppCompatActivity {
    private Button btn_volver;
    private EditText edt_buscar;
    private String eventoId;
    private int clienteId;
    private ListView listView1;
    private AdapterView<?> parentGlobal;
    private RecyclerView recyclerVU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_invitados);
        this.edt_buscar = (EditText) findViewById(R.id.editText19);

        eventoId = getIntent().getExtras().getString("eventoId");
        //clienteId = getIntent().getExtras().getString("clienteId");
        BaseDatos baseHelper = new BaseDatos(this, "DEMODB", null, 1);
        clienteId=baseHelper.getClienteIdSesion(baseHelper);

        System.out.println("evento id " + eventoId);

        btn_volver = (Button) findViewById(R.id.button12);
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ver_invitados.this, elegir_evento.class);
                //intent.putExtra("clienteId", clienteId);
                startActivity(intent);
                finish();
                //Toast.makeText(MainActivity.this, "Hi Flor", Toast.LENGTH_SHORT).show();
            }
        });
        cargar();
        UserAdapter ua=(UserAdapter) recyclerVU.getAdapter();
        ua.getViews();

        edt_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String tex = edt_buscar.getText().toString();
                if (!tex.equals(""))
                    cargarFiltro(tex);
                else
                    cargar();
                //Toast.makeText(ver_producto.this, tex, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void cargar() {
        BaseDatos baseHelper = new BaseDatos(this, "DEMODB", null, 1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select * from invitados where inv_eventoId="+eventoId, null);
            int cant = c.getCount();
            int i = 0;
            //creo un arreglo para cargar todos los valores y desp poder mostrarlos
            List<Invitado> arreglo = new LinkedList<Invitado>();
            arreglo.add(i++,new Invitado("Mesa - Invitado","",-1));
            if (c.moveToFirst()) {
                do {
                    String linea = c.getInt(5) + " - " + c.getString(1) + ", " + c.getString(2);
                    arreglo.add(i++,new Invitado(c.getString(1),c.getString(2),c.getInt(5)));
                    //arreglo[i++] = new Invitado(linea);
                    System.out.println("Cargar() "+ linea);
                } while (c.moveToNext());
            }
            recyclerVU=(RecyclerView)findViewById(R.id.recyclerViewUser);
            //recyclerViewUser.setHasFixedSize(true);
            recyclerVU.setLayoutManager(new LinearLayoutManager(this));
            UserAdapter ua = new UserAdapter(ver_invitados.this,arreglo);
            recyclerVU.setAdapter(ua);
        }
    }

    public void cargarFiltro(String tex) {
        BaseDatos baseHelper = new BaseDatos(this, "DEMODB", null, 1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select * from invitados where inv_eventoId="+eventoId+" and inv_nombre like '"+tex+"%'", null);
            int cant = c.getCount();
            int i = 0;
            //creo un arreglo para cargar todos los valores y desp poder mostrarlos
            List<Invitado> arreglo = new LinkedList<Invitado>();
            arreglo.add(i++,new Invitado("Mesa - Invitado","",-1));
            if (c.moveToFirst()) {
                do {
                    String linea = c.getInt(5) + " - " + c.getString(1) + ", " + c.getString(2);
                    arreglo.add(i++,new Invitado(c.getString(1),c.getString(2),c.getInt(5)));
                } while (c.moveToNext());
            }
            recyclerVU=(RecyclerView)findViewById(R.id.recyclerViewUser);
            //recyclerViewUser.setHasFixedSize(true);
            recyclerVU.setLayoutManager(new LinearLayoutManager(this));
            UserAdapter ua = new UserAdapter(ver_invitados.this,arreglo);
            recyclerVU.setAdapter(ua);
        }
    }

}


