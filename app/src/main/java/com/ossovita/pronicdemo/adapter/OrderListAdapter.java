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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {

    private static final String TAG = "OrderListAdapter";
    private Context context;
    private final LayoutInflater inflater;
    private ViewHolder holder;
    private List<Order> orders;
    private List<Field> fields;

    private static class ViewHolder {
        LinearLayout verticalLinearLayout;
    }

    public OrderListAdapter(@NonNull Context context, List<Order> orders, List<Field> fields) {
        super(context, R.layout.adapter_view_layout, orders);
        this.context = context;
        this.orders = orders;
        this.fields = fields;
        inflater = LayoutInflater.from(context);
        Log.d(TAG, "OrderListAdapter: kullanılacak fields:" + fields.size() + " orders: " + orders.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_view_layout, parent, false);

            holder = new ViewHolder();
            holder.verticalLinearLayout = convertView.findViewById(R.id.verticalLinearLayout);


            Order order = orders.get(position);
            if (order != null) {

                LinearLayout.LayoutParams verticalLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

                holder.verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);
                holder.verticalLinearLayout.setLayoutParams(verticalLayoutParams);
                holder.verticalLinearLayout.setWeightSum(10f);

                LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                Field element = Collections.max(fields, Comparator.comparingInt(Field::getRowNumber));
                int[] weightArray = new int[element.getRowNumber()]; // combining both statements in one

                for (Field field : fields) {
                    weightArray[field.getRowNumber() - 1]++;
                }
                for (int i = 0; i < weightArray.length; i++) {
                    Log.d(TAG, "getView: weightArray:" + i + " numaralı indexte " + weightArray[i] + " adet eleman var");
                }

                Log.d(TAG, "getView: max row:" + element.getRowNumber());

                //kaç tane row varsa o kadar horizontal layout oluştur
                for (int i = 0; i < element.getRowNumber(); i++) {
                    LinearLayout myHorizontalLinearLayout = new LinearLayout(getContext());
                    myHorizontalLinearLayout.setId(i);
                    myHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalLayoutParams.setMargins(30, 30, 30, 30);
                    myHorizontalLinearLayout.setLayoutParams(horizontalLayoutParams);
                    myHorizontalLinearLayout.setWeightSum(10f);

                    holder.verticalLinearLayout.addView(myHorizontalLinearLayout);
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
                            case "ORDERPRICE":
                                textView.setText(String.valueOf(order.getOrderPrice()));
                                break;
                        }
                    }
                    //hazırlanan textView'i ilgili horizontalLayout'a ekle
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 10f / weightArray[field.getRowNumber() - 1]));
                    Log.d(TAG, "getView: vertical layout child number:" + holder.verticalLinearLayout.getChildCount());
                    LinearLayout horizontalLinearLayout = (LinearLayout) holder.verticalLinearLayout.getChildAt(field.getRowNumber() - 1);
                    System.out.println("addView linlayout:" + horizontalLinearLayout);
                    horizontalLinearLayout.addView(textView);

                }
            }

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Nullable
    @Override
    public Order getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).getOrderPk();
    }
}
