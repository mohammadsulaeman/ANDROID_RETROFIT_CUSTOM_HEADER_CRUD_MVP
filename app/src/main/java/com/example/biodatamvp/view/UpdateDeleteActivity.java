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

import com.example.biodatamvp.R;
import com.example.biodatamvp.constants.Constant;
import com.example.biodatamvp.databinding.ActivityUpdateDeleteBinding;
import com.example.biodatamvp.presenter.updatedelete.UpdateDeletePresenter;
import com.example.biodatamvp.presenter.updatedelete.UpdateDeletePresenterView;
import com.example.biodatamvp.utils.Log;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UpdateDeleteActivity extends AppCompatActivity implements UpdateDeletePresenterView {
    ActivityUpdateDeleteBinding binding;
    UpdateDeletePresenter presenter;
    byte[] imageByteArray;
    Bitmap decoded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String id = intent.getStringExtra(ID_BIODATA);
        String nimLama = intent.getStringExtra(NIM_BIODATA);
        String namaLama = intent.getStringExtra(NAMA_BIODATA);
        String phoneLama = intent.getStringExtra(PHONE_BIODATA);
        String emailLama = intent.getStringExtra(EMAIL_BIODATA);
        String dobLama = intent.getStringExtra(DOB_BIODATA);
        String photoLama = intent.getStringExtra(PHOTO_BIODATA);
        String alamatLama = intent.getStringExtra(ALAMAT_BIODATA);

        presenter = new UpdateDeletePresenter(UpdateDeleteActivity.this,this);

        // Menampilkan data
        binding.edtNimUpdatedel.setText(nimLama);
        binding.edtNamaUpdatedel.setText(namaLama);
        binding.edtPhoneUpdatedel.setText(phoneLama);
        binding.edtEmailUpdatedel.setText(emailLama);
        binding.edtDobUpdatedel.setText(dobLama);
        binding.edtAddressUpdatedel.setText(alamatLama);
        Picasso.get().load(Constant.IMAGES_BIODATA + photoLama).into(binding.updatedelImages);

        binding.btnDeleteUpdatedel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nimLama.isEmpty()){
                    presenter.delete(nimLama);
                }
            }
        });

        binding.updatedelImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages();
            }
        });

        binding.btnUpdateUpdatedel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageByteArray == null){
                    Toast.makeText(UpdateDeleteActivity.this, "Photo Diperlukan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.edtNimUpdatedel.getText())) {
                    Toast.makeText(UpdateDeleteActivity.this, "Nim Diperlukan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.edtNamaUpdatedel.getText())) {
                    Toast.makeText(UpdateDeleteActivity.this, "Nama Diperlukan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.edtPhoneUpdatedel.getText())) {
                    Toast.makeText(UpdateDeleteActivity.this, "phone Diperlukan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.edtEmailUpdatedel.getText())) {
                    Toast.makeText(UpdateDeleteActivity.this, "email Diperlukan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.edtDobUpdatedel.getText())) {
                    Toast.makeText(UpdateDeleteActivity.this, "Tempat Tanggal Lahir Diperlukan", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binding.edtAddressUpdatedel.getText())) {
                    Toast.makeText(UpdateDeleteActivity.this, "Alamat Diperlukan", Toast.LENGTH_SHORT).show();
                }else {
                    String message = binding.edtNamaUpdatedel.getText().toString();
                    String secretKey = "BIODATA_2023";
                    byte[] hmacsha256 = calcHmacSha256(secretKey.getBytes(StandardCharsets.UTF_8),message.getBytes(StandardCharsets.UTF_8));

                    String token = "";
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        token = java.util.Base64.getEncoder().encodeToString(hmacsha256);
                    }

                    String nim = binding.edtNimUpdatedel.getText().toString();
                    String fullName = binding.edtNamaUpdatedel.getText().toString();
                    String photo = getStringImage(decoded);
                    String phone = binding.edtPhoneUpdatedel.getText().toString();
                    String email = binding.edtEmailUpdatedel.getText().toString();
                    String dob = binding.edtDobUpdatedel.getText().toString();
                    String alamat = binding.edtAddressUpdatedel.getText().toString();
                    presenter.update(token,nimLama,nim,fullName,photo,email,phone,dob,alamat);
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
                binding.updatedelImages.setImageBitmap(rotatedBitmap);
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
        return new String(encodedBytes, StandardCharsets.UTF_8);
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

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onRequestSuccess(String message) {

    }

    @Override
    public void onRequestError(String error) {

    }

    public static final String ID_BIODATA = "ID_BIODATA";
    public static final String NIM_BIODATA = "NIM_BIODATA";
    public static final String NAMA_BIODATA = "NAMA_BIODATA";
    public static final String PHONE_BIODATA = "PHONE_BIODATA";
    public static final String EMAIL_BIODATA = "EMAIL_BIODATA";
    public static final String DOB_BIODATA = "DOB_BIODATA";
    public static final String PHOTO_BIODATA = "PHOTO_BIODATA";
    public static final String ALAMAT_BIODATA = "ALAMAT_BIODATA";

}