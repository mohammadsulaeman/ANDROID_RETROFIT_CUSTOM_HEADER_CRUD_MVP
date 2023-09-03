package com.example.biodatamvp.presenter.insert;

public interface InsertPresenterView {
    void showProgress();
    void hideProgress();
    void onRequestSuccess(String message);
    void onRequestError(String error);

}
