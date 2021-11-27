package com.example.quicklistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context mContext;
    private List<Product> mProducts;
    public ProductAdapter ( Context context, List<Product> products){
        mContext = context;
        mProducts = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_list,parent,false);
        return new ProductViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = mProducts.get(position);
        holder.textViewName.setText(currentProduct.getProductName());
        holder.textViewDescription.setText(currentProduct.getProductDescription());
        holder.textViewCategory.setText(currentProduct.getProductCategory());
        Picasso.get()
                .load(currentProduct.getProductImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName,textViewDescription,textViewCategory;
        public ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}
