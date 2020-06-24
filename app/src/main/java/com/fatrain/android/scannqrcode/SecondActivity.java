package com.fatrain.android.scannqrcode;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> arrayList;
    private FirebaseRecyclerOptions<Model> options;
    private FirebaseRecyclerAdapter<Model, DataViewHolder> adapter;
    private DatabaseReference databaseReference;
    private List<Model> dataList = new ArrayList<Model>();
    private DataAdapter mDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        arrayList = new ArrayList<Model>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Query lastQuery = databaseReference.child("result").orderByKey().limitToFirst(20);
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Model m = postSnapshot.getValue(Model.class);
                    dataList.add(m);
                }
                mDataAdapter = new DataAdapter(dataList);
                recyclerView.setAdapter(mDataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SecondActivity.this, "Data Fetching Error!", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
