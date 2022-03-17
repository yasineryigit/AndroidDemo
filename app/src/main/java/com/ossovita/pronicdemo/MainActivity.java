package com.ossovita.pronicdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ossovita.pronicdemo.adapter.OrderListAdapter;
import com.ossovita.pronicdemo.api.ApiService;
import com.ossovita.pronicdemo.api.MyApi;
import com.ossovita.pronicdemo.model.Item;
import com.ossovita.pronicdemo.model.Order;
import com.ossovita.pronicdemo.model.OrderData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ListView listView;
    List<Order> orderList = new ArrayList<>();
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
                    myApi.getAllOrderDatas().enqueue(new Callback<List<com.ossovita.pronicdemo.model.Order>>() {
                        @Override
                        public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, "onResponse: gelen order list" + response.body());
                                orderList = response.body();
                                Log.d(TAG, "onResponse: giden orders: " + orderList.size());
                                try {
                                    List<OrderData> orderDataList = fetchOrderDataList(orderList);
                                    OrderListAdapter orderListAdapter = new OrderListAdapter(MainActivity.this, orderDataList, fields);
                                    listView.setAdapter(orderListAdapter);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }


                            } else {
                                Log.d(TAG, "onResponse: error:" + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Order>> call, Throwable t) {
                            Log.d(TAG, "onFailure: error orders:" + t.getMessage());
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

    public List<OrderData> fetchOrderDataList(List<Order> orders) throws IllegalAccessException {

        List<OrderData> orderDataList = new ArrayList<>();

        for (Order order : orders) {
            List<Item> itemList = new ArrayList<>();
            for (Field field : order.getClass().getDeclaredFields()) {
                field.setAccessible(true); // You might want to set modifier to public first.
                Object value = field.get(order);
                if (value != null) {
                    System.out.println(field.getName() + "=" + value);
                    Item item = new Item(field.getName().toUpperCase(), value);
                    itemList.add(item);
                }
            }
            OrderData orderData = new OrderData(itemList);
            orderDataList.add(orderData);
        }

        return orderDataList;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}