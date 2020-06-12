package com.example.nationinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private final Context context;
    private List<ItemList> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, List<ItemList> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.countryCode = (TextView) convertView.findViewById(R.id.countryCode);
            holder.countryName = (TextView) convertView.findViewById(R.id.countryName);
            holder.capital = (TextView) convertView.findViewById(R.id.capital);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ItemList country = (ItemList) this.getItem(position);
        holder.countryCode.setText(country.getCountryCode());
        holder.countryName.setText(country.getCountryName());
        holder.capital.setText(country.getCapital());

        return convertView;
    }

    static class ViewHolder {
        TextView countryCode;
        TextView countryName;
        TextView capital;
    }
}
