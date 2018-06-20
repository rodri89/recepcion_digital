package com.example.rodrigo.recepcioncel;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class crear_usuarios extends AppCompatActivity {
    private EditText mail,pass,clienteId,usuarioId;
    private Button btn_nuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuarios);
        this.mail=(EditText) findViewById(R.id.editText4);
        this.pass=(EditText) findViewById(R.id.editText5);
        this.clienteId=(EditText) findViewById(R.id.editText6);
        this.usuarioId=(EditText) findViewById(R.id.editText7);

        this.btn_nuevo = (Button) findViewById(R.id.button3);
        this.btn_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDatos baseHelper = new BaseDatos(crear_usuarios.this, "DEMODB", null, 1);
                SQLiteDatabase db = baseHelper.getWritableDatabase();
                if (db != null) {
                    ContentValues registroNuevo = new ContentValues();
                    //primer parametro nombre en la base, segundo el valor a ingresar
                    registroNuevo.put("usr_mail", mail.getText().toString());
                    registroNuevo.put("usr_clienteId", Integer.parseInt(clienteId.getText().toString()));
                    registroNuevo.put("usr_pass", pass.getText().toString());
                    registroNuevo.put("usr_id", Integer.parseInt(usuarioId.getText().toString()));

                    long z = db.insert("usuarios", null, registroNuevo);
                    Intent intent= new Intent(crear_usuarios.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


}
