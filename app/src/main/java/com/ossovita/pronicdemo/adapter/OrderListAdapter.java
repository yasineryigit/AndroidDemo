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

        TextView tv1 = new TextView(getContext());
        TextView tv2 = new TextView(getContext());
        TextView tv3 = new TextView(getContext());
        TextView tv4 = new TextView(getContext());

        tv1.setText(fields.get(0).getName());
        tv1.setTextColor(Color.parseColor(fields.get(0).getColor()));
        tv1.setTypeface(null, fields.get(0).isBold() ? Typeface.BOLD : Typeface.NORMAL);
        tv1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f));

        tv2.setText(String.valueOf(orders.get(position).getOrderNo()));
        tv2.setTextColor(Color.parseColor(fields.get(1).getColor()));
        tv2.setTypeface(null, fields.get(1).isBold() ? Typeface.BOLD : Typeface.NORMAL);
        tv2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f));

        tv3.setText(String.valueOf(orders.get(position).getOrderTime()));
        tv3.setTextColor(Color.parseColor(fields.get(2).getColor()));
        tv3.setTypeface(null, fields.get(2).isBold() ? Typeface.BOLD : Typeface.NORMAL);
        tv3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4f));

        tv4.setText(orders.get(position).getClientInfo());
        tv4.setTextColor(Color.parseColor(fields.get(3).getColor()));
        tv4.setTypeface(null, fields.get(3).isBold() ? Typeface.BOLD : Typeface.NORMAL);
        tv4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        horizontalLinearLayout.addView(tv1);
        horizontalLinearLayout.addView(tv2);
        horizontalLinearLayout.addView(tv3);
        verticalLinearLayout.addView(horizontalLinearLayout);
        verticalLinearLayout.addView(tv4);



        return convertView;
    }
}
