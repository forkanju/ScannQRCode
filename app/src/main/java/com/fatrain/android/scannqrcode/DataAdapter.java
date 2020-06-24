package com.fatrain.android.scannqrcode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<Model> dataList;

    public DataAdapter(List<Model> dataList) {
        this.dataList = dataList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View root = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dataset, parent, false);
        MyViewHolder vh = new MyViewHolder(root);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DataAdapter.MyViewHolder holder, int position) {

        holder.textView.setText(dataList.get(position).getTxtResult());


    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(View root) {
            super(root);
            textView = root.findViewById(R.id.txt_item);
        }
    }
}
