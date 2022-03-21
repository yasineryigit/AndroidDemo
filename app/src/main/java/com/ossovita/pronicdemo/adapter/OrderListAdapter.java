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
import com.ossovita.pronicdemo.model.OrderData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<OrderData> {

    private static final String TAG = "OrderListAdapter";
    private Context context;
    private List<Order> orders;
    private List<Field> fields;
    private ViewHolder holder;

    private static class ViewHolder {
        public LinearLayout verticalLinearLayout;
    }

    public OrderListAdapter(@NonNull Context context, List<Order> orders, List<Field> fields) {
        super(context, R.layout.adapter_view_layout);
        this.context = context;
        this.orders = orders;
        this.fields = fields;
        Log.d(TAG, "OrderListAdapter: kullanılacak fields:" + fields.size() + " orders: " + orders.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_view_layout, parent, false);
            holder = new ViewHolder();

            //define rootLayout
            LinearLayout.LayoutParams verticalLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            holder.verticalLinearLayout = convertView.findViewById(R.id.verticalLinearLayout);
            holder.verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);
            holder.verticalLinearLayout.setLayoutParams(verticalLayoutParams);
            holder.verticalLinearLayout.setWeightSum(10f);


            Order orderData = this.orders.get(position);

            if (orderData != null) {

                //Find max row number & create weight array(which holds how many textView will place in a horizontal layout)
                Field element = Collections.max(fields, Comparator.comparingInt(Field::getRowNumber));
                int[] weightArray = new int[element.getRowNumber()];

                //assign values to weightArray indexes
                for (Field field : fields) {
                    weightArray[field.getRowNumber() - 1]++;
                }

                //logging values inside of weightArray @REMOVABLE
                for (int i = 0; i < weightArray.length; i++) {
                    Log.d(TAG, "getView: weightArray:" + i + " numaralı indexte " + weightArray[i] + " adet eleman var");
                }

                Log.d(TAG, "getView: max row:" + element.getRowNumber());

                //Create horizontal linear layouts for max row number count & add these horizontal linear layouts into root vertical linear layout
                for (int i = 0; i < element.getRowNumber(); i++) {
                    holder.verticalLinearLayout.addView(createHorizontalLinearLayout());
                }

                //Place fields as text view into horizontal linear layouts
                for (Field field : fields) {
                    TextView textView = new TextView(getContext());
                    textView.setTextColor(Color.parseColor(field.getColor()));
                    textView.setTypeface(null, field.isBold() ? Typeface.BOLD : Typeface.NORMAL);
                    //Dataları doldurma zamanı
                    for (java.lang.reflect.Field f : orders.get(position).getClass().getDeclaredFields()) {
                        f.setAccessible(true); // You might want to set modifier to public first.
                        Log.d(TAG, "getView: karşılaştırılacak fieldName: "+ field.getName() + " f Name:" + f.getName().toUpperCase());
                        if (field.getName().equals(f.getName().toUpperCase())) {
                            try {
                                Object value = f.get(orders.get(position));
                                textView.setText(String.valueOf(value));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    //Set text view layout weight
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 10f / weightArray[field.getRowNumber() - 1]));
                    Log.d(TAG, "getView: vertical layout child number:" + holder.verticalLinearLayout.getChildCount());

                    //Add textViews into related horizontal linear layout
                    LinearLayout horizontalLinearLayout = (LinearLayout) holder.verticalLinearLayout.getChildAt(field.getRowNumber() - 1);
                    System.out.println("addView linearLayout:" + horizontalLinearLayout);
                    horizontalLinearLayout.addView(textView);

                }
                convertView.setTag(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private LinearLayout createHorizontalLinearLayout() {
        LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout myHorizontalLinearLayout = new LinearLayout(getContext());
        myHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayoutParams.setMargins(30, 30, 30, 30);
        myHorizontalLinearLayout.setLayoutParams(horizontalLayoutParams);
        myHorizontalLinearLayout.setWeightSum(10f);
        return myHorizontalLinearLayout;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Nullable
    @Override
    public OrderData getItem(int position) {
        return super.getItem(position);
    }

}
