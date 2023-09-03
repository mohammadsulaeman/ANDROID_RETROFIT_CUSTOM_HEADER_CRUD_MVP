package com.example.biodatamvp.presenter.updatedelete;

public interface UpdateDeletePresenterView {
    void showProgress();
    void hideProgress();
    void onRequestSuccess(String message);
    void onRequestError(String error);
}
