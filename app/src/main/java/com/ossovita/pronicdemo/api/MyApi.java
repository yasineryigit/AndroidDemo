package com.ossovita.pronicdemo.api;

import com.google.gson.JsonObject;
import com.ossovita.pronicdemo.model.Field;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {

    @GET("/api/1.0/fields")
    Call<List<Field>> getAllFields();

    @GET("/api/1.0/orders")
    Call<List<JsonObject>> getAllOrders();

}
