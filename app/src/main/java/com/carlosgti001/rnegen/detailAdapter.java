package com.carlosgti001.rnegen;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosgti001.rnegen.database.CRUD;
import com.carlosgti001.rnegen.list.CodeStudiant;

import java.util.ArrayList;

class detailAdapter extends RecyclerView.Adapter<detailAdapter.RNEViewHolder> {

    ArrayList<CodeStudiant> listaContactos;
    Context _RNE;
    String RNE_;
    ClipboardManager _clipboardManager;

    public detailAdapter(CodeStudiant RNE, Context RNEDetail, String Rne) {
        _RNE = RNEDetail;
        RNE_ = Rne;
    }

    @NonNull
    @Override
    public RNEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rnedetailscroll, null, false);
        return new RNEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RNEViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class RNEViewHolder extends RecyclerView.ViewHolder {

        TextView Nombre, Rne, fecha;

        public RNEViewHolder(@NonNull View itemView) {
            super(itemView);

            CRUD db = new CRUD(_RNE);
            CodeStudiant contacto = db.leerRNE(RNE_);
            View layoutIncrustado = itemView.findViewById(R.id.layout);
            TextView nombre = layoutIncrustado.findViewById(R.id.nombreTextView);
            String name = contacto.getNombre();
            nombre.setText(name);
            TextView RNECode = itemView.findViewById(R.id.rneTextView);
            String Rne = contacto.getRne();
            RNECode.setText(Rne);
            TextView Date = itemView.findViewById(R.id.fechaRneTextView);
            String Fecha = contacto.getFecha();
            Date.setText(Fecha);
        }
    }
}