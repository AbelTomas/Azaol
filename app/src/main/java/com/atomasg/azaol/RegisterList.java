package com.atomasg.azaol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.atomasg.azaol.data.Register;
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
        DatabaseReference registerReference = FirebaseDatabase.getInstance().getReference().child("registers");

        adapter = new Adapter(Register.class,R.layout.register_item
                ,RegisterViewHolder.class,registerReference);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvRegisterList.setLayoutManager(llm);
        rvRegisterList.setAdapter(adapter);


    }
}
