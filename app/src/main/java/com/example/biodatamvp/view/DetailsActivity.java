package com.example.biodatamvp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.biodatamvp.R;
import com.example.biodatamvp.constants.Constant;
import com.example.biodatamvp.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String id = intent.getStringExtra(ID_BIODATA);
        String nim = intent.getStringExtra(NIM_BIODATA);
        String nama = intent.getStringExtra(NAMA_BIODATA);
        String phone = intent.getStringExtra(PHONE_BIODATA);
        String email = intent.getStringExtra(EMAIL_BIODATA);
        String dob = intent.getStringExtra(DOB_BIODATA);
        String photo = intent.getStringExtra(PHOTO_BIODATA);
        String alamat = intent.getStringExtra(ALAMAT_BIODATA);

        // Menampilkan Data inten putextra
        binding.imgBackRows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        binding.edtNimDetail.setText(nim);
        binding.edtNamaDetail.setText(nama);
        binding.edtPhoneDetail.setText(phone);
        binding.edtEmailDetail.setText(email);
        binding.edtDobDetail.setText(dob);
        binding.edtAddressDetail.setText(alamat);
        Picasso.get().load(Constant.IMAGES_BIODATA + photo).into(binding.detailImages);
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