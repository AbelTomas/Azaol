package com.atomasg.azaol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.ivRead)
    ImageView ivRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

    }

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    @OnClick(R.id.floatingActionButtonCamera)
    public void onCameraClick(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,RESULT_OK);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent,
                    REQUEST_CAPTURE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK){

            Bitmap imageBitMap= (Bitmap) data.getExtras().get("data");
            ivRead.setImageBitmap(imageBitMap);
        }
    }
}
