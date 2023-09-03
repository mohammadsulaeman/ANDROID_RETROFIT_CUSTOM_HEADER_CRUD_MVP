package com.example.biodatamvp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.biodatamvp.R;
import com.example.biodatamvp.databinding.ActivityMainBinding;
import com.example.biodatamvp.model.Biodata;
import com.example.biodatamvp.model.BiodataAdapter;
import com.example.biodatamvp.presenter.read.ReadPresenter;
import com.example.biodatamvp.presenter.read.ReadPresenterView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ReadPresenterView {
    ActivityMainBinding binding;
    ReadPresenter presenter;
    BiodataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fabAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insert = new Intent(getApplicationContext(), InsertBiodataActivity.class);
                startActivity(insert);
            }
        });

        binding.listItemData.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.listItemData.setHasFixedSize(true);
        presenter = new ReadPresenter(this);
        presenter.getDataResult();
    }

    @Override
    public void showProgress() {
        binding.progressMain.setVisibility(View.VISIBLE);
        binding.listItemData.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        binding.progressMain.setVisibility(View.GONE);
        binding.listItemData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSuccess(String success) {
        Toast.makeText(getApplicationContext(),success,Toast.LENGTH_LONG);
    }

    @Override
    public void onRequestError(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG);
    }

    @Override
    public void getDataResult(List<Biodata> biodataList) {
        adapter = new BiodataAdapter(getApplicationContext(),biodataList,R.layout.layout_item_list_data);
        binding.listItemData.setAdapter(adapter);
    }
}