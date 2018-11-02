package com.atomasg.azaol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Format;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.ivRead)
    ImageView ivRead;

    @BindView(R.id.ivWrite)
    ImageView ivWrite;

    @BindView(R.id.etStreet)
    EditText etStreet;

    @BindView(R.id.etStreetNum)
    EditText etStreetNum;

    @BindView(R.id.etTitular)
    EditText etTitular;

    @BindView(R.id.tvLecturaAnterior)
    TextView etLecturaAnterior;

    @BindView(R.id.etLectura)
    EditText etLectura;

    @BindView(R.id.etRegisterDate)
    EditText etRegisterDate;

    @BindView(R.id.etObservations)
    EditText etObservations;


    private boolean isUploaded;
    private File file;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        storage = FirebaseStorage.getInstance();

    }

    private static final int REQUEST_CAPTURE_IMAGE = 100;

    @OnClick(R.id.btnCamera)
    public void onCameraClick() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        showCameraConfirmationDialog();
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
        }
    }


    @OnClick(R.id.btnUpload)
    public void onUploadClick() {
        if (file != null) {
            uploadImage();
            file.delete();
        }
        uploadRegister();
    }

    private void uploadRegister() {
        if (checkIsAllData()) {
            saveData();
        }

    }

    private void saveData() {

    }

    private boolean checkIsAllData() {

        return true;
    }

    private void uploadImage() {

        final StorageReference storageRef = storage.getReference();

        Uri fileUri = Uri.fromFile(file);

        final StorageReference riversRef = storageRef.child("registerimages/" + fileUri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(fileUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Subida fallida", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Subida completada", Toast.LENGTH_SHORT).show();
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Toast.makeText(getApplicationContext(), "get Uri Success", Toast.LENGTH_SHORT).show();

                    Glide.with(getApplicationContext())
                            .load(downloadUri)
                            .into(ivWrite);

                } else {
                    Toast.makeText(getApplicationContext(), "get Uri Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showCameraConfirmationDialog() {
        //check if get previus imagen
        //continue

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            saveImage(data.getExtras().get("data"));
        }
    }

    private void saveImage(Object data) {
        Bitmap imageBitMap = (Bitmap) data;

        File dir = this.getCacheDir();
        if (file != null) {
            file.delete();
            file = null;
        }
        try {
            file = File.createTempFile("camera", null, dir);

            OutputStream fOut = null;
            fOut = new FileOutputStream(file);

            imageBitMap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate

            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivRead.setImageBitmap(myBitmap);
        }
    }
}
