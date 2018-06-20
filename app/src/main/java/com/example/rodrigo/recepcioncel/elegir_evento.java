package com.example.rodrigo.recepcioncel;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class elegir_evento extends AppCompatActivity {
    private Button btn_volver;
    private int clienteId;
    private RecyclerView recyclerVU;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_evento);
        //clienteId=getIntent().getExtras().getString("clienteId");
        BaseDatos baseHelper = new BaseDatos(this, "DEMODB", null, 1);
        clienteId=baseHelper.getClienteIdSesion(baseHelper);

        this.btn_volver = (Button) findViewById(R.id.button2);
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(elegir_evento.this,usuario_logueado.class);
                intent.putExtra("clienteId", clienteId);
                startActivity(intent);
                finish();
            }
        });

        cargar();

    }

    public void finalizarClase(){finish();}

    public void cargar() {
        BaseDatos baseHelper = new BaseDatos(this, "DEMODB", null, 1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select * from eventos where eve_cliente_id="+clienteId, null);
            int cant = c.getCount();
            int i = 0;
            //creo un arreglo para cargar todos los valores y desp poder mostrarlos
            List<Evento> arreglo = new LinkedList<Evento>();
            if (c.moveToFirst()) {
                do {
                    int activo=c.getInt(7);
                    if(activo==1) {
                        Evento evento=new Evento(c.getInt(0),c.getString(1),c.getString(3));
                        arreglo.add(evento);
                    }
                } while (c.moveToNext());
            }
            //int cliId=arreglo.get(0).getCliente();
            //int cliId=Integer.parseInt(clienteId.trim());
            recyclerVU=(RecyclerView)findViewById(R.id.recyclerView1);
            //recyclerViewUser.setHasFixedSize(true);
            recyclerVU.setLayoutManager(new LinearLayoutManager(this));
            AdapterPersonalizado ua = new AdapterPersonalizado(elegir_evento.this,arreglo,clienteId);
            recyclerVU.setAdapter(ua);
        }
    }

}

       /* listView1 = (ListView) findViewById(R.id.listView);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // en item se almacena todo el texto de una fila
                String item = ((TextView)view).getText().toString();
                String[] itemAux=item.split("-");
                String codigo=itemAux[0];
                String descripcion=itemAux[1];
                // en cad guardo la informacion que quiero llevar al otro activity
                String cad="EventoId "+codigo+" "+descripcion;

                Intent intent= new Intent(elegir_evento.this,ver_invitados.class);
                intent.putExtra("eventoId", codigo);
                intent.putExtra("clienteId", clienteId);
                startActivity(intent);
                finish();

            }
        });*/
