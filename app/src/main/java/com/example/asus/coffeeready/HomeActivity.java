package com.example.asus.coffeeready;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeActivity extends AppCompatActivity  {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference reference=db.collection("product");
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpRecyclerView();

//        GridView gridView = (GridView) findViewById(R.id.grid_view);
    }

    private void setUpRecyclerView(){
        Query query=reference.orderBy("name",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Product> options=new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query,Product.class)
                .build();
        adapter =new Adapter(options);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setonItemClickListener(new Adapter.onItemClickLestener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Product model=documentSnapshot.toObject(Product.class);
                String id=documentSnapshot.getId();
                String path=documentSnapshot.getReference().getPath();
                Intent intent=new Intent(HomeActivity.this,CoffeeDetail.class);
                intent.putExtra("id",id);
                intent.putExtra("name",model.getName());
                intent.putExtra("description",model.getDescription());
                intent.putExtra("url", model.getUrl());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
