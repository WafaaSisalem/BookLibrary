package com.example.booklibrary.mainscreens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booklibrary.R;
import com.example.booklibrary.database.DatabaseHelper;
import com.example.booklibrary.models.CategoryModel;

import java.util.List;

public class CreateCategoryActivity extends AppCompatActivity {
    Button createNewCatButton;
    EditText categoryNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        createNewCatButton = findViewById(R.id.create_new_cat_button);
        categoryNameEditText = findViewById(R.id.category_name_edit_text);
        createNewCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categoryNameEditText.getText().toString();
                DatabaseHelper databaseHelper = new DatabaseHelper(CreateCategoryActivity.this);
                List<String> categories = databaseHelper.getAllCategoriesNames();

                if (category.isEmpty()) {
                    Toast.makeText(CreateCategoryActivity.this, "You have to add a category!", Toast.LENGTH_LONG).show();
                } else if (categories.contains(category)) {
                    Toast.makeText(CreateCategoryActivity.this, "This category is exist try to change category name!", Toast.LENGTH_LONG).show();

                } else {
                    boolean success = databaseHelper.addToACategoryColumn(new CategoryModel(-1, category));
                    Toast.makeText(CreateCategoryActivity.this, "Category Created!", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}