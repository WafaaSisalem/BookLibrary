package com.example.booklibrary.mainscreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklibrary.R;
import com.example.booklibrary.adapter.OnItemClickListener;
import com.example.booklibrary.adapter.RecyclerCategoryAdapter;
import com.example.booklibrary.database.DatabaseHelper;
import com.example.booklibrary.models.CategoryModel;
import com.example.booklibrary.subscreens.CategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    public static final String CATEGORY_ID_KEY = "CATEGORY_ID";
    public static final String CATEGORY_NAME_KEY = "CATEGORY_NAME";
    RecyclerView categoryRecyclerView;
    List<CategoryModel> allCategoriesModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> allCategoriesNames = databaseHelper.getAllCategoriesNames();
        allCategoriesModels = databaseHelper.getAllCategories();
        categoryRecyclerView = findViewById(R.id.category_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerCategoryAdapter recyclerCategoryAdapter = new RecyclerCategoryAdapter(allCategoriesNames);
        categoryRecyclerView.setAdapter(recyclerCategoryAdapter);
        recyclerCategoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int id) {
                TextView categoryNameTextView = (TextView) view.findViewById(R.id.category_name_text_view);
                String categoryName = categoryNameTextView.getText().toString();
                CategoryModel categoryModel = getCategoryModelFromName(categoryName);
                Intent intent = new Intent(LibraryActivity.this, CategoryActivity.class);
                intent.putExtra(CATEGORY_ID_KEY,categoryModel.getId());
                intent.putExtra(CATEGORY_NAME_KEY,categoryName);
                startActivity(intent);
            }

        });
    }
    public CategoryModel getCategoryModelFromName(String category) {
        CategoryModel result = new CategoryModel();
        for (CategoryModel categoryModel : allCategoriesModels) {
            if (categoryModel.getCategoryName().equals(category)) {
                result = categoryModel;
            }}
        return result;
    }
}