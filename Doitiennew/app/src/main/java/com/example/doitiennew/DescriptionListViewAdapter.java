package com.example.doitiennew;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DescriptionListViewAdapter extends BaseAdapter {
    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<Convert> listDescription;

    public DescriptionListViewAdapter(ArrayList<Convert> listDescription) {
        this.listDescription = listDescription;
    }

    @Override
    public int getCount() {
        return listDescription.size();
    }

    @Override
    public Object getItem(int position) {
        return listDescription.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewDescription;
        if (convertView == null) {
            viewDescription = View.inflate(parent.getContext(), R.layout.items_list_feed, null);
        } else viewDescription = convertView;

        //Bind sữ liệu phần tử vào View
        Convert convert = (Convert) getItem(position);
        ((TextView) viewDescription.findViewById(R.id.description)).setText(convert.description);


        return viewDescription;
    }
}
