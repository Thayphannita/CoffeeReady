package com.example.asus.coffeeready;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class Adapter extends FirestoreRecyclerAdapter <Product,Adapter.ViewHolder>{

    Context context;
    public static final int NO_POSITION =-1;
    private onItemClickLestener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adapter(FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, Product model) {
        holder.txtname.setText(model.getName());
        holder.setImage(model.getUrl());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname;
        ImageView url;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.name);
            url=itemView.findViewById(R.id.url);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION &&listener!=null ){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
        public void setImage(String thumbnailUrl) {
            Picasso.get()
                    .load(thumbnailUrl)
                    .into(url);
        }

    }
    public interface onItemClickLestener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }
    public void setonItemClickListener(onItemClickLestener listener){
        this.listener =listener;

    }


}
