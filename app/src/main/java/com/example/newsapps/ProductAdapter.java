package com.example.newsapps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<ProductModel> courseModelArrayList;
    private Context context;

    public ProductAdapter(ArrayList<ProductModel> courseModelArrayList, Context context) {
        this.courseModelArrayList = courseModelArrayList != null ? courseModelArrayList : new ArrayList<>();
        this.context = context;
    }

    public void filterList(ArrayList<ProductModel> filterlist) {
        courseModelArrayList = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = courseModelArrayList.get(position);

        holder.titleTextView.setText(product.getTitle());
        holder.descriptionTextView.setText(product.getDescription());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DeatilsActivity.class);

                intent.putExtra("Title",courseModelArrayList.get(holder.getAdapterPosition()).getTitle());

                intent.putExtra("des",courseModelArrayList.get(holder.getAdapterPosition()).getDescription());

                intent.putExtra("Image", courseModelArrayList.get(holder.getAdapterPosition()).getImageUrl());

                context.startActivity(intent);

            }
        });

        Picasso.get().load(product.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            titleTextView = itemView.findViewById(R.id.item_title);
            descriptionTextView = itemView.findViewById(R.id.item_description);

            cardView=itemView.findViewById(R.id.cardviewID);
        }
    }
}
