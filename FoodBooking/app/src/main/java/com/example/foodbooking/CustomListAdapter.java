package com.example.foodbooking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private List<ItemList> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private SparseBooleanArray mArray;

    public CustomListAdapter(Context aContext, List<ItemList> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        mArray = new SparseBooleanArray(listData.size());
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.foodName = (TextView) convertView.findViewById(R.id.foodName);
            holder.foodImage = (ImageView) convertView.findViewById(R.id.foodImage);
            holder.foodLink = (TextView) convertView.findViewById(R.id.foodLink);
            holder.foodLocation = (TextView) convertView.findViewById(R.id.foodLocation);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(mArray.get(position));
        }

        final ItemList food = this.listData.get(position);
        holder.foodName.setText(food.getFoodName());
        holder.foodLink.setText(food.getFoodLink().toString());
        holder.foodName.setTag(holder.foodLink.getText().toString());


        int drawableResourceId = this.context.getResources().getIdentifier(food.getFoodImage(), "drawable", MainActivity.PACKAGE_NAME);
        holder.foodImage.setImageResource(drawableResourceId);
        holder.foodLocation.setText(food.getFoodLocation().toString());
        holder.foodImage.setTag(holder.foodLocation.getText().toString());

        holder.foodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("foodUrl", food.getFoodLink());

                context.startActivity(detailIntent);
            }
        });

        holder.foodImage.setOnClickListener(itemClickListener);

        holder.checkBox.setChecked(mArray.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mArray.put(position, isChecked);
                if (isChecked) {
                    MainActivity.listOrder.add(position);
                } else {
                    MainActivity.listOrder.remove(Integer.valueOf(position));
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView foodLink;
        TextView foodLocation;
        CheckBox checkBox;
    }

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.foodName:
                    //TextView fn = (TextView)v;
                    String fl = (String) v.getTag();
                    Toast.makeText(context, fl.toString(), Toast.LENGTH_SHORT).show();
                    showView(fl.toString());
                    return;
                case R.id.foodImage:
                    ImageView fi = (ImageView) v;
                    String floc = (String) fi.getTag();
                    Toast.makeText(context, floc.toString(), Toast.LENGTH_SHORT).show();
                    showView(floc.toString());
                    return;
            }
        }
    };

    private void showView(String v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(v));
        if (intent.resolveActivity(context.getPackageManager()) != null)
            context.startActivity(intent);
    }
}
