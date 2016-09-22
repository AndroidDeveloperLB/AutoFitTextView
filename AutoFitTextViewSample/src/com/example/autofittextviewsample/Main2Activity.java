package com.example.autofittextviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
                return new ViewHolder(LayoutInflater.from(Main2Activity.this).inflate(R.layout.item, parent, false)) {
                };
            }

            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {
                StringBuilder sb = new StringBuilder("item:");
                for (int i = 0; i <= position; ++i)
                    sb.append(Integer.toString(position));
                holder.textView.setText(sb);
            }

            @Override
            public int getItemCount() {
                return 50;
            }
        });
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        public ViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
