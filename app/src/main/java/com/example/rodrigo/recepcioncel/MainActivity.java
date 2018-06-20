package com.example.rodrigo.recepcioncel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText usuario,contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.usuario=(EditText) findViewById(R.id.editText);
        this.contrasenia=(EditText) findViewById(R.id.editText1);

        this.btn_login = (Button) findViewById(R.id.button);
        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr=usuario.getText().toString();
                String pass=contrasenia.getText().toString();
                if(usr.equals("admin")&&(pass.equals("adminmb"))) {
                    Intent intent = new Intent(MainActivity.this, crear_usuarios.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    try {
                        BaseDatos baseHelper = new BaseDatos(MainActivity.this, "DEMODB", null, 1);
                        String[][] aux = baseHelper.consulta("SELECT usr_pass,usr_clienteId FROM usuarios WHERE usr_mail='" + usr + "'", baseHelper);
                        String contraseniaReal = aux[0][0];
                        String clienteId = aux[0][1];
                        System.out.println(contraseniaReal + " - " + clienteId);
                        if (pass.equals(contraseniaReal)) {
                            baseHelper.iniciarSesion(baseHelper,usr,pass);
                            Intent intent = new Intent(MainActivity.this, usuario_logueado.class);
                            intent.putExtra("clienteId", clienteId);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }catch (java.lang.ArrayIndexOutOfBoundsException e){
                        Toast.makeText(MainActivity.this, "El usuario ingresado no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
