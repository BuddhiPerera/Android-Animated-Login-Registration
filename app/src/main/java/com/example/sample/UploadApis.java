package com.example.sample;

import com.example.sample.model.Post;

import okhttp3.MultipartBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApis {
    @Multipart
//    @Headers({
//            "Content-Type: application/json"
//    })
    @POST("upload")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);
}
