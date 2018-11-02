package com.atomasg.azaol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    private static Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        navigator=new Navigator(this);
    }

    @OnClick(R.id.cardView1)
    public void onCardView1ClickedAction(){
        navigator.goToRegister();
    }

    @OnClick(R.id.cardView2)
    public void onCardView2ClickedAction(){
        navigator.goToList();
    }

    @OnClick(R.id.cardView3)
    public void onCardView3ClickedAction(){
        navigator.goToRegister();
    }

    @OnClick(R.id.cardView4)
    public void onCardView4ClickedAction(){
        navigator.goToRegister();
    }
}
