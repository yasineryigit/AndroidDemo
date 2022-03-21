package com.ossovita.pronicdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.ossovita.pronicdemo.adapter.OrderListAdapter;
import com.ossovita.pronicdemo.api.ApiService;
import com.ossovita.pronicdemo.api.MyApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ListView listView;
    List<JsonObject> orderList = new ArrayList<JsonObject>();
    List<com.ossovita.pronicdemo.model.Field> fields = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        MyApi myApi = ApiService.getInstanceForApi().create(MyApi.class);

        //get design details list
        myApi.getAllFields().enqueue(new Callback<List<com.ossovita.pronicdemo.model.Field>>() {
            @Override
            public void onResponse(Call<List<com.ossovita.pronicdemo.model.Field>> call, Response<List<com.ossovita.pronicdemo.model.Field>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    fields = response.body();

                    //get order list
                    myApi.getAllOrders().enqueue(new Callback<List<JsonObject>>() {
                        @Override
                        public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "onResponse: gelen order list" + response.body());
                                orderList = response.body();
                                Log.d(TAG, "onResponse: giden orders: " + orderList.size());
                                OrderListAdapter orderListAdapter = new OrderListAdapter(MainActivity.this, orderList, fields);
                                listView.setAdapter(orderListAdapter);


                            } else {
                                Log.d(TAG, "onResponse: error:" + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<JsonObject>> call, Throwable t) {

                        }
                    });

                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<com.ossovita.pronicdemo.model.Field>> call, Throwable t) {
                Log.d(TAG, "onFailure: error:" + t.getMessage());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}