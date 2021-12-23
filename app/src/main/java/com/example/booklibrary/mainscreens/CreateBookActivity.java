package com.example.booklibrary.mainscreens;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.booklibrary.R;
import com.example.booklibrary.database.DatabaseHelper;
import com.example.booklibrary.models.BookModel;
import com.example.booklibrary.models.CategoryModel;
import java.util.List;

public class CreateBookActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PERM = 1;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    Spinner categorySpinner;
    DatabaseHelper databaseHelper;
    EditText bookNameEditText, authorNameEditText, releaseYearEditText, pageNumberEditText;
    String bookName, authorName, releaseYear, pageNum, category;
    ImageView circleBackground, photoINCircleBackground, circleImageView;
    Button createBookButton;
    Uri imageUri;
    List<CategoryModel> allCategoriesModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        if (ContextCompat.checkSelfPermission(CreateBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERM);
        }
        databaseHelper = new DatabaseHelper(this);
        List<String> allCategoriesNames = databaseHelper.getAllCategoriesNames();
        allCategoriesModels = databaseHelper.getAllCategories();
        categorySpinner = findViewById(R.id.edit_category_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allCategoriesNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);

        bookNameEditText = findViewById(R.id.edit_book_name_edittext);
        authorNameEditText = findViewById(R.id.edit_author_name_edittext);
        releaseYearEditText = findViewById(R.id.edit_release_num_edittext);
        pageNumberEditText = findViewById(R.id.edit_page_num_edittext);
        circleBackground = findViewById(R.id.edit_circle_background);
        photoINCircleBackground = findViewById(R.id.edit_photo_in_circle);
        circleImageView = findViewById(R.id.edit_circle_image_view);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if(data!=null)
                            imageUri = ensureUriPermission(CreateBookActivity.this, data);
                            circleImageView.setImageURI(imageUri);
                        }
                    }
                });

        circleBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        photoINCircleBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        createBookButton = findViewById(R.id.edit_update_button);
        createBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookName = bookNameEditText.getText().toString();
                authorName = authorNameEditText.getText().toString();
                releaseYear = releaseYearEditText.getText().toString();
                pageNum = pageNumberEditText.getText().toString();
                category = categorySpinner.getSelectedItem() == null ? "" : categorySpinner.getSelectedItem().toString();
                if (imageUri == null) {
                    Toast.makeText(CreateBookActivity.this, "You have to pick an image for the book", Toast.LENGTH_SHORT).show();

                } else if (category.isEmpty()) {
                    Toast.makeText(CreateBookActivity.this, "You have to choose an Category!", Toast.LENGTH_SHORT).show();
                } else if (bookName.isEmpty()) {
                    Toast.makeText(CreateBookActivity.this, "Invalid Book Name!", Toast.LENGTH_SHORT).show();
                } else if (authorName.isEmpty()) {
                    Toast.makeText(CreateBookActivity.this, "Invalid Author Name!", Toast.LENGTH_SHORT).show();
                } else if (releaseYear.isEmpty() || Integer.parseInt(releaseYear) < 1850 || Integer.parseInt(releaseYear) > 2022) {
                    Toast.makeText(CreateBookActivity.this, "Invalid Release Date!", Toast.LENGTH_SHORT).show();
                } else if (pageNum.isEmpty()) {
                    Toast.makeText(CreateBookActivity.this, "Invalid Page Number!", Toast.LENGTH_SHORT).show();
                } else {
                    CategoryModel categoryModel = databaseHelper.getCategoryByName(category);
                    BookModel bookModel = new BookModel(-1, bookName, authorName, releaseYear, pageNum, categoryModel.getId(), imageUri.toString());
                    DatabaseHelper databaseHelper = new DatabaseHelper(CreateBookActivity.this);
                    databaseHelper.addToABookTable(bookModel);
                    finish();
                }
            }
        });

    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        someActivityResultLauncher.launch(intent);
    }



    public static Uri ensureUriPermission(Context context, Intent intent) {
        Uri uri = intent.getData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int takeFlags = intent.getFlags();
            takeFlags &= (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.getContentResolver().takePersistableUriPermission(uri, takeFlags);
        }
        return uri;
    }


}