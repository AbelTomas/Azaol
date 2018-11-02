package com.atomasg.azaol;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RegisterViewHolder> {


    List<Register> registerList;


    public Adapter(List<Register> registerList) {
        this.registerList = registerList;
    }

    @NonNull
    @Override
    public RegisterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_item, parent, false);
        RegisterViewHolder holder = new RegisterViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterViewHolder holder, int position) {
        Register reg = registerList.get(position);
        holder.setContador(reg.getContador());
        holder.setFecha(reg.getFecha());
        holder.setLectura(reg.getLectura());
    }



    @Override
    public int getItemCount() {
        return registerList.size();
    }
}
