package com.example.biodatamvp.presenter.insert;

import android.content.Context;
import android.content.Intent;

import com.example.biodatamvp.utils.Log;
import com.example.biodatamvp.utils.api.ServiceGenerator;
import com.example.biodatamvp.utils.api.service.BiodataService;
import com.example.biodatamvp.utils.json.AddBiodataRequestJson;
import com.example.biodatamvp.utils.json.ResponseJson;
import com.example.biodatamvp.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertPresenter implements InsertPresenterInterface{
    InsertPresenterView view;
    Context mContext;

    public InsertPresenter(InsertPresenterView view, Context mContext) {
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void insert(String token, String nim, String fullName, String photo, String email, String phone, String dob, String alamat) {
        view.showProgress();
        Log.d(InsertPresenter.class.getName().toString(),"token : "+token);
        AddBiodataRequestJson requestJson = new AddBiodataRequestJson();
        requestJson.setNim(nim);
        requestJson.setFullName(fullName);
        requestJson.setPhoto(photo);
        requestJson.setEmail(email);
        requestJson.setDob(dob);
        requestJson.setPhone(phone);
        requestJson.setAddress(alamat);
        BiodataService service = ServiceGenerator.createService(BiodataService.class,requestJson.getFullName(),requestJson.getNim());
        service.saveBiodata(token,requestJson).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()){
                    if (response.body().getErrorCode().equalsIgnoreCase("USER_NOT_FOUND")){
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    } else if (response.body().getErrorCode().equalsIgnoreCase("USER_EMAIL_ALREADY_EXISTS")) {
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    } else if (response.body().getErrorCode().equalsIgnoreCase("INTERNET SERVER ERROR")) {
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    } else if (response.body().getStatus().equalsIgnoreCase("success")){
                        Intent home = new Intent(mContext.getApplicationContext(), MainActivity.class);
                        mContext.startActivity(home);
                        view.onRequestSuccess(response.body().getMessage());
                        view.hideProgress();
                    }else {
                        view.onRequestError(response.body().getMessage());
                        view.showProgress();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                view.onRequestError(t.getMessage());
                view.showProgress();
            }
        });
    }
}
