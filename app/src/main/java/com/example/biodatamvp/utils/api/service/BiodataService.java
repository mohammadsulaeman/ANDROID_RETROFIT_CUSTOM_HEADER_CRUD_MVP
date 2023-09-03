package com.example.biodatamvp.utils.api.service;

import com.example.biodatamvp.utils.json.AddBiodataRequestJson;
import com.example.biodatamvp.utils.json.GetDataAllBiodata;
import com.example.biodatamvp.utils.json.ResponseJson;
import com.example.biodatamvp.utils.json.UpdateBiodataRequestJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BiodataService {

    @POST("save")
    Call<ResponseJson> saveBiodata(@Header("token") String token, @Body AddBiodataRequestJson param);

    @GET("getAllBiodata")
    Call<GetDataAllBiodata> getBiodataAll();

    @GET("getBiodata/{nim}")
    Call<GetDataAllBiodata> getBiodataById(@Path("nim") String nim);

    @GET("images/{imagesName}")
    Call<ResponseJson> getGambar(@Path("imagesName") String imagesName);

    @PUT("update/{nim}")
    Call<ResponseJson> updateBiodata(@Header("token") String token,@Path("nim") String nim, @Body UpdateBiodataRequestJson params);

    @DELETE("delete/{nim}")
    Call<ResponseJson> deleteBiodata(@Path("nim") String nim);

}
