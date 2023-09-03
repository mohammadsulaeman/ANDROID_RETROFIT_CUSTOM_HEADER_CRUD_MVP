package com.example.biodatamvp.utils.json;

import com.example.biodatamvp.model.Biodata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetDataAllBiodata {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<Biodata> biodataList = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Biodata> getBiodataList() {
        return biodataList;
    }

    public void setBiodataList(List<Biodata> biodataList) {
        this.biodataList = biodataList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
