package com.example.ramiro.projectefinal.RecyclerView;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramiro.projectefinal.R;

import java.util.List;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.AdapterViewHolder> {

    List<Contact> contactos;
    Context c;
    public MyCustomAdapter(Context cont, List<Contact> contactos) {
        this.c = cont;
        this.contactos = contactos;
    }



    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rowlayout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        final Contact contactoAMostrar = contactos.get(position);
        String iconLayout = contactoAMostrar.getIcon();
        switch (iconLayout){
            case "0":
                holder.icon.setImageDrawable(holder.v.getResources().getDrawable(R.drawable.icon0));
                break;
            case "1":
                holder.icon.setImageDrawable(holder.v.getResources().getDrawable(R.drawable.icon1));
                break;
            case "2":
                holder.icon.setImageDrawable(holder.v.getResources().getDrawable(R.drawable.icon2));
                break;
            case "3":
                holder.icon.setImageDrawable(holder.v.getResources().getDrawable(R.drawable.icon3));
                break;
        }
        holder.usuari.setText(contactoAMostrar.getUsuari());
        holder.puntuacio.setText(contactoAMostrar.getPuntuacio());


    }

    public void addContacts(List<Contact> contactos) {
        this.contactos.addAll(contactos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView usuari;
        public TextView puntuacio;
        public View v;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.icon = (ImageView) itemView.findViewById(R.id.icon_star);
            this.usuari = (TextView) itemView.findViewById(R.id.us);
            this.puntuacio = (TextView) itemView.findViewById(R.id.puntuacio);
        }
    }


}
