package com.example.booklibrary.mainscreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.booklibrary.R;
import com.example.booklibrary.adapter.OnItemClickListener;
import com.example.booklibrary.adapter.RecyclerBookAdapter;
import com.example.booklibrary.database.DatabaseHelper;
import com.example.booklibrary.models.BookModel;
import com.example.booklibrary.subscreens.BookDetailsActivity;
import com.example.booklibrary.subscreens.CategoryActivity;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "BOOK_ID";
    RecyclerView favoriteBooksRecycler;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        databaseHelper = new DatabaseHelper(this);
        favoriteBooksRecycler = findViewById(R.id.favorite_book_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favoriteBooksRecycler.setLayoutManager(linearLayoutManager);
       initFavoriteBook();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFavoriteBook();
    }
    public void initFavoriteBook(){
        List<BookModel> favoriteBooks = databaseHelper.getAllFavoriteBooks();
        RecyclerBookAdapter recyclerBookAdapter = new RecyclerBookAdapter(favoriteBooks);
        favoriteBooksRecycler.setAdapter(recyclerBookAdapter);
        recyclerBookAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int bookId) {
                Intent intent = new Intent(FavoriteActivity.this, BookDetailsActivity.class);
                intent.putExtra(BOOK_ID_KEY,bookId);
                startActivity(intent);
            }

        });
    }
}