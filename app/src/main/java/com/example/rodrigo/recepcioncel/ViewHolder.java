package com.example.rodrigo.recepcioncel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rodrigo on 20/11/2017.
 */
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView name;
    private Context context;
    public RelativeLayout parentBodyRl;


    public ViewHolder(Context context,View v){
        super(v);
        v.setOnClickListener(this);
        this.name=(TextView) v.findViewById(R.id.textUserName);
        this.context=context;
        this.parentBodyRl=(RelativeLayout)v.findViewById(R.id.parent_body_rl);
    }

    @Override
    public void onClick(View view) {
        this.name = (TextView) view.findViewById(R.id.textUserName);
        String item = name.getText().toString();
        String[] itemAux = item.split("-");

        if (!itemAux[0].trim().equals("Mesa")) {
            String[] nombreYapellido = itemAux[1].split(",");
            String nombre = nombreYapellido[0];
            String apellido = nombreYapellido[1];
            int asistio = chequearAsistencia(nombre, apellido);

            if (asistio == 0) {
                setearAsistencia(nombre, apellido, 1);
                asistio = 1;
            } else {
                asistio = 0;
                setearAsistencia(nombre, apellido, 0);
            }
            if (asistio == 1) {
                pintarAsistencia(view,1);
            } else {
                pintarAsistencia(view,0);
            }
        }else{
            System.out.println(obtenerAsistencias().toString());
        }

    }

    public void pintarAsistencia(View view,int valor){
        //this.name = (TextView) view.findViewById(R.id.textUserName);
        if(valor==1)
            parentBodyRl.setBackgroundColor(Color.GREEN);
        else
            parentBodyRl.setBackgroundColor(Color.TRANSPARENT);
    }

    public TextView getTextView(){
        return name;
    }

    public String[] obtenerAsistencias(){
        String [] aux1=new String[1];
        int i=0;
        String consulta = "Select inv_asistencia from invitados";
        try {
            BaseDatos baseHelper = new BaseDatos(context, "DEMODB", null, 1);
            String[][] aux = baseHelper.consulta(consulta, baseHelper);
            aux1=new String[aux.length];
            for(int ii=0;ii<aux.length;ii++) {
                System.out.println("obtenerAsistieron ->" + aux[ii][0]);
                aux1[i++] = aux[ii][0];
            }
        }catch(Exception e){return null;}
        System.out.println("obtenerAsistieron "+aux1.length);
        return aux1;
    }

    /**
     * Este metodo va a setear en la base de datos el valor 1 si asistio y el 0 si no asistio
     * @param nombre
     * @param apellido
     * @param valor
     */
    public void setearAsistencia(String nombre,String apellido, int valor){
        nombre=nombre.trim();
        apellido=apellido.trim();
        BaseDatos baseHelper = new BaseDatos(context, "DEMODB", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            ContentValues registroNuevo = new ContentValues();
            //primer parametro nombre en la base, segundo el valor a ingresar
            registroNuevo.put("inv_asistencia", valor);
            long i = db.update("invitados", registroNuevo, "inv_nombre='"+nombre+"' and inv_apellido='"+apellido+"'", null);
            if (i > 0) {
                // Toast.makeText(ver_invitados.this, "Confirmar asistencia "+valor, Toast.LENGTH_SHORT).show();
            }
            else {
                System.out.println("setearAsistencia fallo");
            }
        }
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





/*
if(selectedItems.get(getAdapterPosition(),false)){
    selectedItems.delete(getAdapterPosition());
    view.setSelected(false);
    name=(TextView) view.findViewById(R.id.textUserName);
    System.out.println(name.getText().toString());
    view.setBackgroundColor(Color.TRANSPARENT);
}
else{
    selectedItems.put(getAdapterPosition(),true);
    view.setSelected(true);
    name=(TextView) view.findViewById(R.id.textUserName);
    System.out.println(name.getText().toString());
    view.setBackgroundColor(Color.GREEN);
}
    */
