package com.carlosgti001.rnegen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosgti001.rnegen.R;
import com.carlosgti001.rnegen.list.contacto;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    ArrayList<contacto> listaContactos;

    public ListaContactosAdapter(ArrayList<contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_rne, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.Nombre.setText(listaContactos.get(position).getNombre() + " " + listaContactos.get(position).getApellido());
        holder.Rne.setText(listaContactos.get(position).getRne());
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView Nombre, Rne, fecha;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            Nombre = itemView.findViewById(R.id.Nombre);
            Rne = itemView.findViewById(R.id.RNE);
            itemView.setOnClickListener(view -> {
                Snackbar.make(view, Rne.getText(), Snackbar.LENGTH_SHORT).show();
            });
        }
    }
}