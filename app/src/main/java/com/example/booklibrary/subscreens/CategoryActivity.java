package com.example.booklibrary.subscreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booklibrary.R;
import com.example.booklibrary.adapter.OnItemClickListener;
import com.example.booklibrary.adapter.RecyclerBookAdapter;
import com.example.booklibrary.adapter.RecyclerCategoryAdapter;
import com.example.booklibrary.database.DatabaseHelper;
import com.example.booklibrary.mainscreens.LibraryActivity;
import com.example.booklibrary.models.BookModel;
import com.example.booklibrary.models.CategoryModel;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "BOOK_ID";
    String categoryName;
    int categoryId;

    RecyclerView categoryBookRecycler;
    List <BookModel> bookModels;
    DatabaseHelper databaseHelper;
    RecyclerBookAdapter recyclerBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryName = extras.getString(LibraryActivity.CATEGORY_NAME_KEY);
            categoryId = extras.getInt(LibraryActivity.CATEGORY_ID_KEY);
            getSupportActionBar().setTitle(categoryName);
        }
        databaseHelper = new DatabaseHelper(this);
        categoryBookRecycler = findViewById(R.id.category_books_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        categoryBookRecycler.setLayoutManager(linearLayoutManager);
      initBookModel();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initBookModel();
    }
    public void initBookModel(){
        bookModels = databaseHelper.getBooksByCategoryId(categoryId);
        RecyclerBookAdapter recyclerBookAdapter = new RecyclerBookAdapter(bookModels);
        categoryBookRecycler.setAdapter(recyclerBookAdapter);
        recyclerBookAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int bookId) {
                TextView bookNameTextView = (TextView) view.findViewById(R.id.book_name);
                String bookName = bookNameTextView.getText().toString();
                Intent intent = new Intent(CategoryActivity.this, BookDetailsActivity.class);
                intent.putExtra(BOOK_ID_KEY,bookId);
                startActivity(intent);
            }

        });
    }
}