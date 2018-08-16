package com.example.juandiego.colorito;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterPuntaje extends RecyclerView.Adapter<AdapterPuntaje.PuntajesViewHolder> {

    ArrayList<PuntajeVo> listaPuntajes;

    public AdapterPuntaje(ArrayList<PuntajeVo> listaPuntajes) {
        this.listaPuntajes = listaPuntajes;
    }

    @Override
    public PuntajesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_puntajes,null,false);
        return new PuntajesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PuntajesViewHolder holder, int position) {
        holder.desplegadas.setText("Palabras desplegadas: "+listaPuntajes.get(position).getDesplegas());
        holder.correctas.setText("Palabras correctas: "+listaPuntajes.get(position).getCorrectas());
        holder.incorrectas.setText("Palabras incorrectas: "+listaPuntajes.get(position).getIncorrectas());
        holder.intentos.setText("Intentos: "+listaPuntajes.get(position).getFallidos());
    }

    @Override
    public int getItemCount() {
        return listaPuntajes.size();
    }

    public class PuntajesViewHolder extends RecyclerView.ViewHolder {
        TextView desplegadas,correctas,incorrectas,intentos;
        public PuntajesViewHolder(View itemView) {
            super(itemView);
            desplegadas=itemView.findViewById(R.id.desplegadasAdapter);
            correctas=itemView.findViewById(R.id.correctasAdapter);
            incorrectas=itemView.findViewById(R.id.incorrectasAdapter);
            intentos=itemView.findViewById(R.id.intentosAdapter);
        }
    }
}
