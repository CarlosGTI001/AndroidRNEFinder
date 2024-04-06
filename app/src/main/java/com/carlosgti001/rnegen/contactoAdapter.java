package com.carlosgti001.rnegen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosgti001.rnegen.list.CodeStudiant;

import java.util.ArrayList;

class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    ArrayList<CodeStudiant> listaContactos;
    Activity _formulario;
    ClipboardManager _clipboardManager;
    public ListaContactosAdapter(ArrayList<CodeStudiant> listaContactos, ClipboardManager clipboard, Activity  formulario) {
        this.listaContactos = listaContactos;
        _clipboardManager = clipboard;
        _formulario = formulario;
    }

    public ListaContactosAdapter(ClipboardManager clipboardManager) {
        _clipboardManager = clipboardManager;

    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_rne, null, false);
        return new ContactoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.Nombre.setText(listaContactos.get(position).getNombre().split(" ")[0] + " " + listaContactos.get(position).getFecha());
        holder.Rne.setText(listaContactos.get(position).getRne());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(_formulario, RNEDetail.class);
            intent.putExtra("rne", listaContactos.get(position).getRne());
            _formulario.startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private static final int REQUEST_CODE = 1001;


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
                //TODO COPIAR ESTO EN EL OTRO FORM
//                ClipData clip = ClipData.newPlainText("Copied Text", Rne.getText());
//                _clipboardManager.setPrimaryClip(clip);
//                Snackbar.make(view, "El RNE: " + Rne.getText() + " fue copiado con exito", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(_formulario, RNEDetail.class);
                intent.putExtra("rne", Rne.getText());
                _formulario.startActivityForResult(intent, REQUEST_CODE);


            });
        }
    }
}