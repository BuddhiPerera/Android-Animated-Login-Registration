package com.example.sample;

import com.example.sample.model.SourceData;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApis {
    @Multipart
    @POST("upload")
    Call<SourceData> uploadImage(@Part MultipartBody.Part uploadFile);
}
