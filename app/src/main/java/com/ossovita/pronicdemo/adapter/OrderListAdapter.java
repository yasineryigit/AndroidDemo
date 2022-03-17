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
import com.ossovita.pronicdemo.model.Item;
import com.ossovita.pronicdemo.model.OrderData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<OrderData> {

    private static final String TAG = "OrderListAdapter";
    private Context context;
    private List<OrderData> orderData;
    private List<Field> fields;
    private ViewHolder holder;

    private static class ViewHolder {
        public LinearLayout verticalLinearLayout;
    }

    public OrderListAdapter(@NonNull Context context, List<OrderData> orderData, List<Field> fields) {
        super(context, R.layout.adapter_view_layout, orderData);
        this.context = context;
        this.orderData = orderData;
        this.fields = fields;
        Log.d(TAG, "OrderListAdapter: kullanılacak fields:" + fields.size() + " orders: " + orderData.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_view_layout, null);
            holder = new ViewHolder();

            //define rootLayout
            holder.verticalLinearLayout = convertView.findViewById(R.id.verticalLinearLayout);

            OrderData orderData = this.orderData.get(position);

            if (orderData != null) {
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
                    LinearLayout myHorizontalLinearLayout = new LinearLayout(getContext());
                    myHorizontalLinearLayout.setId(i);
                    myHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalLayoutParams.setMargins(30, 30, 30, 30);
                    myHorizontalLinearLayout.setLayoutParams(horizontalLayoutParams);
                    myHorizontalLinearLayout.setWeightSum(10f);

                    holder.verticalLinearLayout.addView(myHorizontalLinearLayout);
                }

                //Place fields as text view into horizontal linear layouts
                for (Field field : fields) {
                    TextView textView = new TextView(getContext());
                    textView.setTextColor(Color.parseColor(field.getColor()));
                    textView.setTypeface(null, field.isBold() ? Typeface.BOLD : Typeface.NORMAL);
                    //Dataları doldurma zamanı
                    for (Item item : orderData.getItemList()) {

                        if (field.getType().equals("Static")) {
                            textView.setText(field.getName());
                        } else if (field.getType().equals("Data")) {
                            if (field.getName().equals(item.getName())) {
                                Object value = item.getValue();
                                if (value instanceof Double) {
                                    value = ((Double) value).intValue();
                                }
                                textView.setText(String.valueOf(value));
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

    @Override
    public int getCount() {
        return orderData.size();
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
