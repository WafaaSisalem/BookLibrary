package com.example.booklibrary.adapter;


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booklibrary.R;
import com.example.booklibrary.models.BookModel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RecyclerBookAdapter extends RecyclerView.Adapter<RecyclerBookAdapter.BookViewHolder> {
    private List<BookModel> books;
    private OnItemClickListener onItemClickListener;
    public RecyclerBookAdapter(List<BookModel> book){
        this.books = book;

    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView bookName,bookReleaseDate;
        private ImageView bookImg;
        private int bookId;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = (TextView) itemView.findViewById(R.id.book_name);
            bookReleaseDate = (TextView) itemView.findViewById(R.id.book_release_date);
            bookImg = (ImageView) itemView.findViewById(R.id.book_image);

            if (onItemClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(view,getAdapterPosition(),bookId);
                    }
                });
            }
        }
        public void bind(BookModel bookModel){
            //bind item with view
            bookName.setText(bookModel.getBookName());
            bookReleaseDate.setText(bookModel.getReleaseYear());
            bookId = bookModel.getBookId();
            bookImg.setImageURI(Uri.parse(bookModel.getImageUri()));

        }
    }
}
