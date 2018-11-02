package com.atomasg.azaol;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atomasg.azaol.data.Register;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.List;

public class Adapter extends FirebaseRecyclerAdapter<Register, RegisterViewHolder> {


    public Adapter(Class<Register> modelClass, int modelLayout, Class<RegisterViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(RegisterViewHolder holder, Register model, int position) {
        holder.setContador(model.getIdContador());
        holder.setFecha(model.getDate());
        holder.setLectura(model.getMeter());
    }

}
