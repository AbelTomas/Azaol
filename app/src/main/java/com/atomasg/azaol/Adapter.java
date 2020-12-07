package com.atomasg.azaol;

import com.atomasg.azaol.data.Register;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class Adapter extends FirebaseRecyclerAdapter<Register, RegisterViewHolder> {


    public Adapter(Class<Register> modelClass, int modelLayout, Class<RegisterViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(RegisterViewHolder holder, Register model, int position) {
        holder.setContador(model.getStreet() + ", "+ model.getNum());
        holder.setFecha(model.getDate());
        holder.setLectura(model.getValue());
    }

}
