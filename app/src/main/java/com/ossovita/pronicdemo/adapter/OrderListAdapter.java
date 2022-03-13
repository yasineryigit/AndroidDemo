package com.ossovita.pronicdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.ossovita.pronicdemo.R;
import com.ossovita.pronicdemo.model.Field;
import com.ossovita.pronicdemo.model.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {

    private static final String TAG = "OrderListAdapter";
    private Context context;
    private List<Order> orders;
    private List<Field> fields;

    public OrderListAdapter(@NonNull Context context, List<Order> orders, List<Field> fields) {
        super(context, R.layout.adapter_view_layout, orders);
        this.context = context;
        this.orders = orders;
        this.fields = fields;
        Log.d(TAG, "OrderListAdapter: kullanılacak fields:" + fields.size() + " orders: " + orders.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.d(TAG, "getView: deki order:" + orders.get(position).toString());

        Order order = getItem(position);
        Log.d(TAG, "getItemPosition order:" + order.toString());
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_view_layout, parent, false);
        }

        LinearLayout verticalLinearLayout = convertView.findViewById(R.id.verticalLinearLayout);
        LinearLayout.LayoutParams verticalLayoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLinearLayout.setLayoutParams(verticalLayoutparams);
        verticalLinearLayout.setWeightSum(10f);

        LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        Field element = Collections.max(fields, Comparator.comparingInt(Field::getRowNumber));
        Log.d(TAG, "getView: max row:" + element.getRowNumber());

        //kaç tane row varsa o kadar horizontal layout oluştur
        for (int i = 0; i < element.getRowNumber(); i++) {
            LinearLayout myHorizontalLinearLayout = new LinearLayout(getContext());
            myHorizontalLinearLayout.setId(i);
            myHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            myHorizontalLinearLayout.setLayoutParams(horizontalLayoutParams);
            myHorizontalLinearLayout.setWeightSum(10f);


            verticalLinearLayout.addView(myHorizontalLinearLayout);

        }

        for (Field field : fields) {//fieldları layout'a yerleştir
            TextView textView = new TextView(getContext());
            textView.setTextColor(Color.parseColor(field.getColor()));
            textView.setTypeface(null, field.isBold() ? Typeface.BOLD : Typeface.NORMAL);

            //setText
            if (field.getType().equals("Static")) {
                textView.setText(field.getName());
            } else if (field.getType().equals("Data")) {
                switch (field.getName()) {
                    case "ORDERNO":
                        textView.setText(String.valueOf(order.getOrderNo()));
                        break;
                    case "DATE":
                        textView.setText(String.valueOf(order.getOrderTime()));
                        break;
                    case "CLIENTINFO":
                        textView.setText(String.valueOf(order.getClientInfo()));
                        break;
                }
            }
            //hazırlanan textView'i ilgili horizontalLayout'a ekle
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 10f / fields.size()));
            Log.d(TAG, "getView: vertical layout child number:" + verticalLinearLayout.getChildCount());
            LinearLayout linearLayout = (LinearLayout) verticalLinearLayout.getChildAt(field.getRowNumber() - 1);
            System.out.println("addView linlayout:" + linearLayout);
            linearLayout.addView(textView);


            /*if (field.getRowNumber() == 1) {
                horizontalLinearLayout.addView(textView);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 10f / fields.size()));
            } else if (field.getRowNumber() == 2) {
                verticalLinearLayout.addView(textView);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 10f / fields.size()));
            }*/

        }

        //horizontal layout list elemanlarını vertical linear layout'a ekle


        return verticalLinearLayout;
    }
}
