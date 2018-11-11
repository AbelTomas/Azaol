package com.atomasg.azaol;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atomasg.azaol.data.Register;
import com.atomasg.azaol.ui.CustomDialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.ivRead)
    ImageView ivRead;

    @BindView(R.id.etStreet)
    EditText etStreet;

    @BindView(R.id.etStreetNum)
    EditText etStreetNum;

    @BindView(R.id.etTitular)
    EditText etTitular;

    @BindView(R.id.etLectura)
    EditText etLectura;

    @BindView(R.id.etRegisterDate)
    EditText etRegisterDate;

    @BindView(R.id.etObservations)
    EditText etObservations;

    @BindView(R.id.registerForm)
    View viewForm;

    @BindView(R.id.upload_progress)
    View progressView;

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
        if (file != null) {
            showCameraConfirmationDialog();
        } else {
            startCamera();
        }
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
        }
    }


    @OnClick(R.id.btnUpload)
    public void onUploadClick() {
        if (checkIsAllData()) {
            showProgress(true);
            if (file != null) {
                uploadImage();
            } else {
                uploadRegister(null);
            }
        } else {
            Toast.makeText(this, "Faltan campos obligatorios", Toast.LENGTH_SHORT).show();
        }
        askClearFormat();
    }

    private void uploadRegister(String uri) {
        if (checkIsAllData()) {
            saveData(uri);
        } else {
            Toast.makeText(this, "Faltan campos obligatorios", Toast.LENGTH_SHORT).show();
        }

    }

    private void askClearFormat() {


    }

    private void saveData(String uri) {
        Register obj = new Register();
        obj.setDate(etRegisterDate.getText().toString());
        obj.setValue(etLectura.getText().toString());
        obj.setStreet(etStreet.getText().toString());
        obj.setNum(etStreetNum.getText().toString());
        obj.setOwner(etTitular.getText().toString());
        obj.setObservations(etObservations.getText().toString());
        obj.setUrl(uri);


        DatabaseReference ref = rootRef.child("registers").push();
        ref.setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("Write was successful!");

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
                !etTitular.getText().toString().isEmpty();
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

                showProgress(false);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "Subida completada", Toast.LENGTH_SHORT).show();
                showProgress(false);

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
                    uploadRegister(downloadUri.toString());
                    showProgress(false);
                }
            }
        });
    }


    private void showCameraConfirmationDialog() {
        final CustomDialog dialog = new CustomDialog(this,"¿Quieres realizar una nueva foto?");


        dialog.setConfirmationListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });

        dialog.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            viewForm.setVisibility(show ? View.GONE : View.VISIBLE);
            viewForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            viewForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
