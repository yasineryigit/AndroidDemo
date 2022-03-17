package com.ossovita.pronicdemo.api;

import com.ossovita.pronicdemo.model.Field;
import com.ossovita.pronicdemo.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {

    @GET("/api/1.0/fields")
    Call<List<Field>> getAllFields();

    @GET("/api/1.0/orders")
    Call<List<Order>> getAllOrderDatas();

}
