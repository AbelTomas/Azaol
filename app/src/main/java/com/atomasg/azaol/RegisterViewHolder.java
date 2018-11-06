package com.atomasg.azaol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

class RegisterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvStreet)
    TextView tvContador;
    @BindView(R.id.tvFecha)
    TextView tvFecha;
    @BindView(R.id.tvLectura)
    TextView tvLectura;

    public RegisterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContador(String contador) {
        tvContador.setText(contador);
    }

    public void setFecha(String fecha) {
        tvFecha.setText(fecha);
    }

    public void setLectura(String lectura) {
        tvLectura.setText(lectura);
    }
}
