package com.atomasg.azaol;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

class RegisterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvContador)
    TextView tvContador;

    public RegisterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    public void setContador(String contador){
        tvContador.setText(contador);
    }

    public void setFecha(String contador){

    }

    public void setLectura(String contador){

    }
}
