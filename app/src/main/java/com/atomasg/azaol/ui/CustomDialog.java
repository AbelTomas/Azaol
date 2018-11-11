package com.atomasg.azaol.ui;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.atomasg.azaol.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomDialog extends Dialog {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.btnYes)
    TextView btnYes;

    @BindView(R.id.btnNo)
    TextView btnNo;


    public CustomDialog(@NonNull Context context,String title) {
        super(context);

        View view = View.inflate(getContext(), R.layout.layout_custom_dialog, null);
        ButterKnife.bind(this, view);
        setContentView(view);

        tvTitle.setText(title);
    }

    public void setConfirmationListener(View.OnClickListener listener){
        btnYes.setOnClickListener(listener);
    }


    public void setNegativeListener(View.OnClickListener listener){
        btnNo.setOnClickListener(listener);
    }


}
