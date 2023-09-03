package com.example.biodatamvp.presenter.updatedelete;

import android.content.Context;
import android.content.Intent;

import com.example.biodatamvp.utils.api.ServiceGenerator;
import com.example.biodatamvp.utils.api.service.BiodataService;
import com.example.biodatamvp.utils.json.ResponseJson;
import com.example.biodatamvp.utils.json.UpdateBiodataRequestJson;
import com.example.biodatamvp.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeletePresenter implements UpdateDeletePresenterInterface{
    Context mContext;
    UpdateDeletePresenterView view;

    public UpdateDeletePresenter(Context mContext, UpdateDeletePresenterView view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void update(String token, String nimLama,String nim, String fullName, String photo, String email, String phone, String dob, String alamat) {
        view.showProgress();
        UpdateBiodataRequestJson requestJson = new UpdateBiodataRequestJson();
        requestJson.setNim(nim);
        requestJson.setFullName(fullName);
        requestJson.setPhone(phone);
        requestJson.setPhoto(photo);
        requestJson.setDob(dob);
        requestJson.setEmail(email);
        requestJson.setAddress(alamat);
        BiodataService service = ServiceGenerator.createService(BiodataService.class,requestJson.getNim(),requestJson.getFullName());
        service.updateBiodata(token,nimLama,requestJson).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        view.onRequestSuccess(response.body().getMessage());
                        view.hideProgress();
                        Intent main = new Intent(mContext.getApplicationContext(), MainActivity.class);
                        mContext.startActivity(main);
                    }else {
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                view.showProgress();
                view.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void delete(String nimLama) {
        BiodataService service = ServiceGenerator.createService(BiodataService.class,nimLama.toString(),nimLama.toString());
        service.deleteBiodata(nimLama).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        view.hideProgress();
                        view.onRequestSuccess(response.body().getMessage());
                        Intent main = new Intent(mContext.getApplicationContext(),MainActivity.class);
                        mContext.startActivity(main);
                    }else {
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                view.showProgress();
                view.onRequestError(t.getMessage());
            }
        });
    }
}
