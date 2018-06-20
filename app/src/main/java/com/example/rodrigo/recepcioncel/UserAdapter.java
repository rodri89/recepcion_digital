package com.example.rodrigo.recepcioncel;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rodrigo on 20/11/2017.
 */
public class UserAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Invitado> userModelList;
    private Context context;
    private List<ViewHolder>views;

    public UserAdapter(Context context, List<Invitado>userModelList){
        this.userModelList=userModelList;
        this.context=context;
        this.views=new LinkedList<ViewHolder>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(context,v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView x=(TextView) holder.getTextView().findViewById(R.id.textUserName);

        String nombre =userModelList.get(position).getNombre();
        String apellido =userModelList.get(position).getApellido();
        int mesa=userModelList.get(position).getMesa();
        if(mesa!=-1) {
            x.setText(mesa + " - " + nombre + ", " + apellido);
            if (chequearAsistencia(nombre, apellido) == 1)
                holder.pintarAsistencia(x,1);
                //x.setBackgroundColor(Color.GREEN);
            else
                holder.pintarAsistencia(x,0);
                //x.setBackgroundColor(Color.TRANSPARENT);
        }
        else{
            x.setText(nombre);
        }
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public void getViews(){
        System.out.println("getViews "+views.size());
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



