package com.example.rodrigo.recepcioncel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rodrigo on 20/11/2017.
 */
public class AdapterPersonalizado extends RecyclerView.Adapter<HolderPersonalizado> {
    private List<Evento> userModelList;
    private Context context;
    private int clientId;

    public AdapterPersonalizado(Context context, List<Evento>userModelList,int clientId){
        this.userModelList=userModelList;
        this.context=context;
        this.clientId=clientId;
    }


    @Override
    public HolderPersonalizado onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter_personalizado,parent,false);
        HolderPersonalizado viewHolder=new HolderPersonalizado(context,v,clientId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HolderPersonalizado holder, int position) {
        TextView eventoNumeroNombreLugar=(TextView) holder.getTextViewNombre().findViewById(R.id.textRow);
        TextView eventoCodigo=(TextView) holder.getTextViewCodigo().findViewById(R.id.codigoEvento);
        int pos=position+1;
        eventoNumeroNombreLugar.setText(pos+" - "+userModelList.get(position).getNombre()+" - "+userModelList.get(position).getLugar());
        eventoCodigo.setText(userModelList.get(position).getCodigo()+"");
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

}



