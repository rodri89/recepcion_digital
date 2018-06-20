package com.example.rodrigo.recepcioncel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Rodrigo on 27/09/2017.
 */
public class BaseDatos extends SQLiteOpenHelper {

    String crearTablaInvitados="CREATE TABLE invitados(inv_id INTEGER PRIMARY KEY,inv_nombre Text,inv_apellido Text,inv_sexo Text,inv_tipo INTEGER, inv_mesa INTEGER, inv_asistencia INTEGER, inv_activo INTEGER,inv_eventoId INTEGER)";
    String crearTablaUsuarios="CREATE TABLE usuarios(usr_id INTEGER PRIMARY KEY,usr_mail Text,usr_pass Text, usr_clienteId INTEGER)";
    String crearTablaEventos="CREATE TABLE eventos(eve_id INTEGER PRIMARY KEY,eve_descripcion Text,eve_fecha Text,eve_lugar Text,eve_mesas INTEGER,eve_cliente_id INTEGER,eve_empresa_id INTEGER,eve_activo INTEGER)";
    String crearTablaSesion="CREATE TABLE sesion (ses_id INTEGER PRIMERY KEY,ses_mail,ses_contrasenia,ses_cliente_id,ses_fecha_creacion Text,ses_activa INTEGER)";

    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crearTablaInvitados);
        db.execSQL(crearTablaUsuarios);
        db.execSQL(crearTablaEventos);
        db.execSQL(crearTablaSesion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Me devuelve toda la informacion de los invitados en una matriz. Lo dejo como modelo, casi que no sirve.
     * @param sql
     * @param baseHelp
     * @return
     */
    public String[][] consultarInvitado(String sql, BaseDatos baseHelp){
        String[][]arreglo=new String[1][1];
        BaseDatos baseHelper = baseHelp;
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery(sql, null);
            int fila = c.getCount();
            int columna=c.getColumnCount();
            int i = 0;
            //creo un arreglo para cargar todos los valores y desp poder mostrarlos
            arreglo = new String[fila][columna];
            if (c.moveToFirst()) {
                do {
//                    String linea = c.getInt(0) + " " + c.getString(1) + " " + c.getInt(2) + " " + c.getString(3) + " " + c.getString(4) + " " + c.getInt(5);
                    arreglo[i][0] = c.getInt(0)+""; //id
                    arreglo[i][1] = c.getString(1); //nombre
                    arreglo[i][2] = c.getString(2)+""; //apellido
                    arreglo[i][3] = c.getString(3); //Sexo
                    arreglo[i][4] = c.getInt(4)+""; //Tipo
                    arreglo[i][6] = c.getInt(5)+"";//Mesa
                    arreglo[i][7] = c.getInt(5)+"";//Asistencia
                    arreglo[i][8] = c.getInt(5)+"";//Activo
                    arreglo[i++][9] = c.getInt(5)+"";//Activo
                } while (c.moveToNext());
            }
        }
        return arreglo;
    }

    /**
     * Es un metodo general que me devuelve cualquier consulta, es conveniente poner en el select los campos que quiero obtener,
     * porque de ese modo seran retornados en la matriz.
     * @param sql Consulta sql que quiera contempla los tipos de datos (Integer, Float, Text)
     * @param baseHelp Objeto BaseDatos
     * @return Una matriz con los resultados de la consulta.
     */
    public String[][] consulta(String sql, BaseDatos baseHelp){
        String[][]arreglo=new String[1][1];
        BaseDatos baseHelper = baseHelp;
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery(sql, null);
            int fila = c.getCount();
            int columna=c.getColumnCount();
            int i = 0;
            //creo un arreglo para cargar todos los valores y desp poder mostrarlos
            arreglo = new String[fila][columna];
            if (c.moveToFirst()) {
                do {
                    for(int jj=0;jj<columna;jj++) {
                        if (c.getType(jj) == 1) {
                            arreglo[i][jj] = c.getInt(jj) + "";
                        }
                        else {
                            if (c.getType(jj) == 2) {
                                arreglo[i][jj] = c.getFloat(jj) + "";
                            } else {
                                if (c.getType(jj) == 3) {
                                    arreglo[i][jj] = c.getString(jj);
                                }
                            }
                        }
                    }
                    i++;
                } while (c.moveToNext());
            }
        }
        return arreglo;
    }

    /**
     * Me permite obtener un valor entero de una columna en particular para una consulta.
     * @param consulta Query, debe tener una columna con un valor entero
     * @param columna Numero de la columna que queremos obtener su valor
     * @param baseHelp Objeto Base de datos
     * @return El valor de la columna deseada.
     */
    public int obtenerValor(String consulta,int columna,BaseDatos baseHelp){
        BaseDatos baseHelper = baseHelp;
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery(consulta, null);
            if(c.moveToFirst()){
                return c.getInt(columna);
            }
            else{
                return 0;
            }
        }
        return 0;
    }


    public void iniciarSesion(BaseDatos bd,String mail,String contrasenia){
        String sql="select usr_clienteId from usuarios where usr_mail='"+mail+"' and usr_pass='"+contrasenia+"'";
        String[][] consulta=consulta(sql,bd);
        String clienteId=consulta[0][0].trim();

        int nuevaSesion=getUltimaSesion(bd)+1;
        Calendar c = Calendar.getInstance();

        SQLiteDatabase db = bd.getWritableDatabase();
        if (db != null) {
            ContentValues registroNuevo = new ContentValues();
            //primer parametro nombre en la base, segundo el valor a ingresar
            registroNuevo.put("ses_id", nuevaSesion);
            registroNuevo.put("ses_mail", mail);
            registroNuevo.put("ses_contrasenia", contrasenia);
            registroNuevo.put("ses_cliente_id", clienteId);
            registroNuevo.put("ses_fecha_creacion", c.getTime().toString());
            registroNuevo.put("ses_activa", 1);

            long z = db.insert("sesion", null, registroNuevo);
        }
        System.out.println("IniciarSesion "+nuevaSesion+" "+clienteId+" "+c.getTime().toString() );
        System.out.println("ClienteId "+getClienteIdSesion(bd));
    }


    private int getUltimaSesion(BaseDatos bd){
        try {
            String sql = "select max(ses_id) from sesion";
            String[][] consulta = consulta(sql, bd);
            int nuevaSesion = Integer.parseInt(consulta[0][0].trim());
            return nuevaSesion;
        }catch (java.lang.NullPointerException e){return 0;}
    }

    public int getClienteIdSesion(BaseDatos bd){
        String sql = "select ses_cliente_id from sesion where ses_id="+getUltimaSesion(bd);
        String[][] consulta = consulta(sql, bd);
        int clienteId = Integer.parseInt(consulta[0][0].trim());
        return clienteId;
    }

    /**
     * Este metodo me permite vaciar una tabla del sqlite
     * @param tabla Ingreso el nombre de la tabla que quiero vaciar
     * @param baseHelp objeto BaseDatos
     */
    public void vaciarTabla(String tabla ,BaseDatos baseHelp){
        BaseDatos baseHelper = baseHelp;
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+tabla);
        if(tabla.equals("invitados")){
            db.execSQL(crearTablaInvitados);
        }
        if(tabla.equals("usuarios")){
            db.execSQL(crearTablaUsuarios);
        }
        if(tabla.equals("eventos")){
            db.execSQL(crearTablaEventos);
        }
        if(tabla.equals("sesion")){
            db.execSQL(crearTablaSesion);
        }
    }
}

/*
La tabla sesion me permite obtener el cliente id en todo momento, se decidio utilizar esta tabla porque
se caia la aplicacion cuando queria obtener el cliente id cuando pasaba de ver_invitados a elegir_evento
*/

