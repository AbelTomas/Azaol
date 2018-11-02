package com.atomasg.azaol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterList extends AppCompatActivity {

    @BindView(R.id.rvRegisteList)
    RecyclerView rvRegisterList;

    private List<Register> registerList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);
        ButterKnife.bind(this);
        registerList =new ArrayList<>();
        init();
    }

    private void init() {
        DatabaseReference registerReference = FirebaseDatabase.getInstance().getReference().child("registros");

        registerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //here is your every post
                    registerList.clear();
                    Register reg = (Register) dataSnapshot.getValue(Register.class);
                    registerList.add(reg);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvRegisterList.setLayoutManager(llm);
        adapter = new Adapter(registerList);
        rvRegisterList.setAdapter(adapter);


    }
}
