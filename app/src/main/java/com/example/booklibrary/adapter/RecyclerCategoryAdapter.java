package com.example.booklibrary.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booklibrary.R;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.CategoryViewHolder> {
    private List<String> categories;
    private OnItemClickListener onItemClickListener;
    public RecyclerCategoryAdapter(List<String> categories){
        this.categories = categories;

    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
       return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
    holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.category_name_text_view);
            if (onItemClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(view,getAdapterPosition(),0);
                    }
                });
            }
        }
        public void bind(String item){
            //bind item with view
            textView.setText(item);
        }
    }
}

