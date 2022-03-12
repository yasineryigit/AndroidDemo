package com.ossovita.pronicdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ossovita.pronicdemo.R;
import com.ossovita.pronicdemo.model.Field;
import com.ossovita.pronicdemo.model.Order;

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
        verticalLinearLayout.setWeightSum(100);

        LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout horizontalLinearLayout = new LinearLayout(getContext());
        horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLinearLayout.setLayoutParams(horizontalLayoutParams);
        horizontalLinearLayout.setWeightSum(10f);
        verticalLinearLayout.addView(horizontalLinearLayout);

        for (Field field : fields) {//fieldları layout'a yerleştir
            TextView textView = new TextView(getContext());
            textView.setTextColor(Color.parseColor(field.getColor()));
            textView.setTypeface(null, field.isBold() ? Typeface.BOLD : Typeface.NORMAL);


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
            if (field.getRowNumber() == 1) {
                horizontalLinearLayout.addView(textView);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 10f / fields.size()));
            } else if (field.getRowNumber() == 2) {
                verticalLinearLayout.addView(textView);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 10f / fields.size()));
            }
        }




        return convertView;
    }
}
