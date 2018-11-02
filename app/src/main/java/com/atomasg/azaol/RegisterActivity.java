package com.atomasg.azaol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atomasg.azaol.data.Register;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        storage = FirebaseStorage.getInstance();

        rootRef = FirebaseDatabase.getInstance().getReference();


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
        }
        uploadRegister();
    }

    private void uploadRegister() {
        if (checkIsAllData()) {
            saveData();
        }

    }

    private void clearFormat() {

    }

    private void saveData() {
        Register obj = new Register();
        obj.setDate(etRegisterDate.getText().toString());
        obj.setMeter(etLectura.getText().toString());
        obj.setStreet(etStreet.getText().toString());
        obj.setNum(etStreetNum.getText().toString());
        obj.setOwner(etTitular.getText().toString());
        obj.setObservations(etObservations.getText().toString());


        DatabaseReference ref = rootRef.child("registers").push();
        ref.setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("Write was successful!");
                // ...
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Write failed");

            }
        });
    }

    private boolean checkIsAllData() {
        return !etRegisterDate.getText().toString().isEmpty() &&
                !etLectura.getText().toString().isEmpty() &&
                !etStreet.getText().toString().isEmpty() &&
                !etStreetNum.getText().toString().isEmpty() &&
                !etTitular.getText().toString().isEmpty() &&
                !etObservations.getText().toString().isEmpty();
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
