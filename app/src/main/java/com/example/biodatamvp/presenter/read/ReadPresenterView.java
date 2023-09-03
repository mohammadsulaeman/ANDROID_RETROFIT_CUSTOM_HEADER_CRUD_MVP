package com.example.biodatamvp.presenter.read;

import com.example.biodatamvp.model.Biodata;

import java.util.List;

public interface ReadPresenterView {
    void showProgress();
    void hideProgress();
    void onRequestSuccess(String success);
    void onRequestError(String error);
    void getDataResult(List<Biodata> biodataList);
}
