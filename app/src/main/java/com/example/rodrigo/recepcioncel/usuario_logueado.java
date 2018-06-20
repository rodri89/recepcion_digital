package com.example.rodrigo.recepcioncel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class usuario_logueado extends AppCompatActivity {
    private Button btn_misEventos,btn_sincronizar,btn_salir;
    private String clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logueado);
        clienteId=getIntent().getExtras().getString("clienteId");

        this.btn_misEventos = (Button) findViewById(R.id.button4);
        this.btn_misEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario_logueado.this, elegir_evento.class);
                //intent.putExtra("clienteId", clienteId);
                startActivity(intent);
                finish();
            }
        });

        this.btn_sincronizar = (Button) findViewById(R.id.button5);
        this.btn_sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario_logueado.this, sincronizar_con_web.class);
                intent.putExtra("clienteId", clienteId);
                startActivity(intent);
                finish();
            }
        });

        this.btn_salir = (Button) findViewById(R.id.button6);
        this.btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario_logueado.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
