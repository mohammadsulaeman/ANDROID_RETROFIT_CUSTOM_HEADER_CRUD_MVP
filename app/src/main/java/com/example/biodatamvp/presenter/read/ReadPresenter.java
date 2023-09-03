package com.example.biodatamvp.presenter.read;

import android.content.Context;

import com.example.biodatamvp.utils.Log;
import com.example.biodatamvp.utils.api.ServiceGenerator;
import com.example.biodatamvp.utils.api.service.BiodataService;
import com.example.biodatamvp.utils.json.GetDataAllBiodata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPresenter implements ReadPresenterInterface{


    ReadPresenterView view;

    public ReadPresenter(ReadPresenterView view) {
        this.view = view;
    }

    @Override
    public void getDataResult() {
        view.showProgress();
        BiodataService service = ServiceGenerator.createService(BiodataService.class,"admin","12345");
        service.getBiodataAll().enqueue(new Callback<GetDataAllBiodata>() {
            @Override
            public void onResponse(Call<GetDataAllBiodata> call, Response<GetDataAllBiodata> response) {
                if (response.isSuccessful()){
                    Log.e(ReadPresenter.class.getName().toString(),response.toString());
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        view.hideProgress();
                        view.getDataResult(response.body().getBiodataList());
                    }else {
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDataAllBiodata> call, Throwable t) {
                view.showProgress();
                view.onRequestError(t.getMessage());
            }
        });
    }
}
