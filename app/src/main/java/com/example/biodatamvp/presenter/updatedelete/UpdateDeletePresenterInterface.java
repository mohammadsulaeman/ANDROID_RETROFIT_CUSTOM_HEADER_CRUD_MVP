package com.example.biodatamvp.presenter.updatedelete;

public interface UpdateDeletePresenterInterface {
    void update(String token,String nimLama,String nim,String fullName,String photo,String email, String phone,String dob,String alamat);
    void delete(String nim);
}
