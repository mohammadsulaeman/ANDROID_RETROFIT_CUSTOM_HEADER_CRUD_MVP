package com.example.biodatamvp.view;

import static com.example.biodatamvp.constants.Constant.calcHmacSha256;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.biodatamvp.constants.Constant;
import com.example.biodatamvp.databinding.ActivityInsertBiodataBinding;
import com.example.biodatamvp.presenter.insert.InsertPresenter;
import com.example.biodatamvp.presenter.insert.InsertPresenterView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class InsertBiodataActivity extends AppCompatActivity implements InsertPresenterView {

    ActivityInsertBiodataBinding binding;
    InsertPresenter presenter;

    byte[] imageByteArray;
    Bitmap decoded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertBiodataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new InsertPresenter(this,InsertBiodataActivity.this);

        binding.insertImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages();
            }
        });

        binding.btnSimpanInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageByteArray == null){
                    Toast.makeText(getApplicationContext(),"Gambar Diperlukan",Toast.LENGTH_LONG);
                } else if (TextUtils.isEmpty(binding.edtNimInsert.getText())) {
                    Toast.makeText(getApplicationContext(),"Nim Diperlukan",Toast.LENGTH_LONG);
                } else if (TextUtils.isEmpty(binding.edtNamaInsert.getText())) {
                    Toast.makeText(getApplicationContext(),"Nama Diperlukan",Toast.LENGTH_LONG);
                } else if (TextUtils.isEmpty(binding.edtPhoneInsert.getText())) {
                    Toast.makeText(getApplicationContext(),"Nomor Telepon Diperlukan",Toast.LENGTH_LONG);
                } else if (TextUtils.isEmpty(binding.edtEmailInsert.getText())) {
                    Toast.makeText(getApplicationContext(),"Email Diperlukan",Toast.LENGTH_LONG);
                } else if (TextUtils.isEmpty(binding.edtDobInsert.getText())) {
                    Toast.makeText(getApplicationContext(),"Tempat Tanggal Lahir Diperlukan",Toast.LENGTH_LONG);
                } else if (TextUtils.isEmpty(binding.edtAddressInsert.getText())) {
                    Toast.makeText(getApplicationContext(),"Alamat Tempat Tinggal Diperlukan",Toast.LENGTH_LONG);
                }else {
                    String message = binding.edtNamaInsert.getText().toString();
                    String secretKey = "BIODATA_2023";
                    byte[] hmacsha256 = calcHmacSha256(secretKey.getBytes(StandardCharsets.UTF_8),message.getBytes(StandardCharsets.UTF_8));

                    String token = "";
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        token = java.util.Base64.getEncoder().encodeToString(hmacsha256);
                    }
                    String nim = binding.edtNimInsert.getText().toString();
                    String fullName = binding.edtNamaInsert.getText().toString();
                    String photo = getStringImage(decoded);
                    String phone = binding.edtPhoneInsert.getText().toString();
                    String email = binding.edtEmailInsert.getText().toString();
                    String dob = binding.edtDobInsert.getText().toString();
                    String alamat = binding.edtAddressInsert.getText().toString();
                    presenter.insert(token,nim,fullName,photo,email,phone,dob,alamat);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                Bundle extras = data.getExtras();
                final Bitmap imagebitmap = (Bitmap) extras.get("data");
                Matrix matrix = new Matrix();
                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap,0,0,imagebitmap.getWidth(),imagebitmap.getHeight(),matrix,true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
                binding.insertImages.setImageBitmap(rotatedBitmap);
                imageByteArray = baos.toByteArray();
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        imageByteArray = baos.toByteArray();
        byte[] encodedBytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           encodedBytes = Base64.getEncoder().encode(imageByteArray);
        }
        return new String(encodedBytes,StandardCharsets.UTF_8);
    }
    private void selectImages(){
        if (checkCameraPermission()){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    }
    private boolean checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            Constant.permission_camera_code);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }
    @Override
    public void showProgress() {
        binding.progressInsert.setVisibility(View.VISIBLE);
        binding.linesInsert.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        binding.progressInsert.setVisibility(View.GONE);
        binding.progressInsert.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}