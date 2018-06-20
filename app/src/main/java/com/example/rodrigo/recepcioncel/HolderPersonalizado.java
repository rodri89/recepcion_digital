package com.example.rodrigo.recepcioncel;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Rodrigo on 20/11/2017.
 */
public class HolderPersonalizado extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView name,codigo;
    private Context context;
    private int clienteId;

    public HolderPersonalizado(Context context, View v,int clientId){
        super(v);
        v.setOnClickListener(this);
        this.name=(TextView) v.findViewById(R.id.textRow);
        this.codigo=(TextView) v.findViewById(R.id.codigoEvento);
        this.context=context;
        this.clienteId=clientId;
    }

    @Override
    public void onClick(View view) {
        Intent intent= new Intent(context,ver_invitados.class);
        intent.putExtra("eventoId", codigo.getText().toString());
        intent.putExtra("clienteId", clienteId);
        context.startActivity(intent);

        System.out.println("El codigo es: "+codigo.getText().toString());
    }


    public TextView getTextViewNombre(){
        return name;
    }

    public TextView getTextViewCodigo(){
        return codigo;
    }

    /**
     * Este metodo va a revisar la base de datos la asistencia del invitado, sera utilizado para controlar los colores.
     * @param nombre
     * @param apellido
     * @return 1 en caso de que este presente y 0 en caso de que no esta
     */
    public int chequearAsistencia(String nombre,String apellido){
        nombre=nombre.trim();
        apellido=apellido.trim();
        String consulta = "Select inv_id,inv_asistencia from invitados where inv_nombre = '"+nombre+ "' and inv_apellido='"+apellido+"'";
        try {
            BaseDatos baseHelper = new BaseDatos(context, "DEMODB", null, 1);
            String[][] aux = baseHelper.consulta(consulta, baseHelper);
            //System.out.println(aux[0][0]+" - "+aux[0][1]);
            return Integer.parseInt(aux[0][1]);
        }
        catch (Exception e){System.out.println(e.getMessage());return -2;}
    }
}



