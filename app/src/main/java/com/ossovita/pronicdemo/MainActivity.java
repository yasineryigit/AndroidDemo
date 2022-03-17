package com.ossovita.pronicdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ossovita.pronicdemo.adapter.OrderListAdapter;
import com.ossovita.pronicdemo.api.ApiService;
import com.ossovita.pronicdemo.api.MyApi;
import com.ossovita.pronicdemo.model.Field;
import com.ossovita.pronicdemo.model.OrderData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ListView listView;
    List<OrderData> orderData = new ArrayList<>();
    List<Field> fields = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        MyApi myApi = ApiService.getInstanceForApi().create(MyApi.class);

        //get design details list
        myApi.getAllFields().enqueue(new Callback<List<Field>>() {
            @Override
            public void onResponse(Call<List<Field>> call, Response<List<Field>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    fields = response.body();

                    //get order list
                    myApi.getAllOrderDatas().enqueue(new Callback<List<OrderData>>() {
                        @Override
                        public void onResponse(Call<List<OrderData>> call, Response<List<OrderData>> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "onResponse: gelen order list" + response.body());
                                orderData = response.body();
                                Log.d(TAG, "onResponse: giden orders: " + orderData.size());
                                OrderListAdapter orderListAdapter = new OrderListAdapter(MainActivity.this, orderData, fields);
                                listView.setAdapter(orderListAdapter);

                            } else {
                                Log.d(TAG, "onResponse: error:" + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<OrderData>> call, Throwable t) {
                            Log.d(TAG, "onFailure: error orders:" + t.getMessage());
                        }
                    });

                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Field>> call, Throwable t) {
                Log.d(TAG, "onFailure: error:" + t.getMessage());
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}