package com.example.booklibrary.subscreens;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.booklibrary.R;
import com.example.booklibrary.database.DatabaseHelper;
import com.example.booklibrary.mainscreens.CreateBookActivity;
import com.example.booklibrary.models.BookModel;
import com.example.booklibrary.models.CategoryModel;

import java.util.List;

public class EditBookDetailsActivity extends AppCompatActivity {
    Spinner categorySpinner;
    DatabaseHelper databaseHelper;
    EditText bookNameEditText, authorNameEditText, releaseYearEditText, pageNumberEditText;
    String bookName, authorName, releaseYear, pageNum, category;
    ImageView circleBackground, photoINCircleBackground,circleImageView;
    Button updateBookButton;
    List<CategoryModel> allCategoriesModels;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    int bookId;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_details);
        databaseHelper = new DatabaseHelper(this);
        List<String> allCategoriesNames = databaseHelper.getAllCategoriesNames();
        allCategoriesModels = databaseHelper.getAllCategories();
        categorySpinner = findViewById(R.id.edit_category_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allCategoriesNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookId = extras.getInt(BookDetailsActivity.BOOK_ID_KEY);
        }

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
                            // There are no request codes
                            Intent data = result.getData();
                            if(data!=null)
                                imageUri = ensureUriPermission(EditBookDetailsActivity.this, data);
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
        BookModel bookModel = databaseHelper.getBookByBookId(bookId);
        bookNameEditText.setText(bookModel.getBookName());
        authorNameEditText.setText(bookModel.getAuthorName());
        releaseYearEditText.setText(bookModel.getReleaseYear());
        pageNumberEditText.setText(bookModel.getPageNum());
        imageUri = Uri.parse(bookModel.getImageUri());
        circleImageView.setImageURI(imageUri);
        CategoryModel categoryModel = databaseHelper.getCategoryById(bookModel.getCategoryId());
        String categoryName = categoryModel.getCategoryName();
        if (categoryName != null) {
            int spinnerPosition = arrayAdapter.getPosition(categoryName);
            categorySpinner.setSelection(spinnerPosition);
        }

        updateBookButton = findViewById(R.id.edit_update_button);
        updateBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookName = bookNameEditText.getText().toString();
                authorName = authorNameEditText.getText().toString();
                releaseYear = releaseYearEditText.getText().toString();
                pageNum = pageNumberEditText.getText().toString();
                if (imageUri == null) {
                    Toast.makeText(EditBookDetailsActivity.this, "You have to pick an image for the book", Toast.LENGTH_SHORT).show();

                } else if (bookName.isEmpty()) {
                    Toast.makeText(EditBookDetailsActivity.this, "Invalid Book Name!", Toast.LENGTH_SHORT).show();
                } else if (authorName.isEmpty()) {
                    Toast.makeText(EditBookDetailsActivity.this, "Invalid Author Name!", Toast.LENGTH_SHORT).show();
                } else if (releaseYear.isEmpty() || Integer.parseInt(releaseYear) < 1850 || Integer.parseInt(releaseYear) > 2022) {
                    Toast.makeText(EditBookDetailsActivity.this, "Invalid Release Date!", Toast.LENGTH_SHORT).show();
                } else if (pageNum.isEmpty()) {
                    Toast.makeText(EditBookDetailsActivity.this, "Invalid Page Number!", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(EditBookDetailsActivity.this);
                    CategoryModel categoryModel = databaseHelper.getCategoryByName(categorySpinner.getSelectedItem().toString());
                    BookModel newBookModel = new BookModel(-1, bookName, authorName, releaseYear, pageNum, categoryModel.getId(), imageUri.toString());
                    databaseHelper.updateBookById(bookId, newBookModel);
                    finish();
                }
            }
        });
    }
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT );
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