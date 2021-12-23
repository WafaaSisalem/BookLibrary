package com.example.booklibrary.subscreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklibrary.R;
import com.example.booklibrary.database.DatabaseHelper;
import com.example.booklibrary.mainscreens.LibraryActivity;
import com.example.booklibrary.models.BookModel;
import com.example.booklibrary.models.CategoryModel;

public class BookDetailsActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "BOOK_ID";

    BookModel bookModel;
    int bookId;
    DatabaseHelper databaseHelper;
    Button editButton;
    CategoryModel categoryModel;
    ImageView bookImageView;
    TextView bookNameTextView, bookAuthorTextView, bookReleaseDateTextView, bookPageNumTextView,bookCatTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        bookNameTextView = findViewById(R.id.book_det_name);
        bookAuthorTextView = findViewById(R.id.author_det_name);
        bookReleaseDateTextView = findViewById(R.id.release_det_date);
        bookPageNumTextView = findViewById(R.id.pages_det_num);
        bookCatTextView = findViewById(R.id.category_det);
        editButton = findViewById(R.id.edit_button);
        bookImageView = findViewById(R.id.book_det_img);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookId = extras.getInt(CategoryActivity.BOOK_ID_KEY);
        }
        databaseHelper = new DatabaseHelper(this);
        setBookDetails();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailsActivity.this,EditBookDetailsActivity.class);
                intent.putExtra(BOOK_ID_KEY,bookId);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.delete) {
            deleteBook();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteBook(){
    databaseHelper.deleteBookById(bookId);
    finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBookDetails();
    }
   public void setBookDetails(){
       bookModel =databaseHelper.getBookByBookId(bookId);
       getSupportActionBar().setTitle(bookModel.getBookName());
       bookNameTextView.setText(bookModel.getBookName());
       bookAuthorTextView.setText(bookModel.getAuthorName());
       bookReleaseDateTextView.setText(bookModel.getReleaseYear());
       bookPageNumTextView.setText(bookModel.getPageNum());
       categoryModel = databaseHelper.getCategoryById(bookModel.getCategoryId());
       bookCatTextView.setText(categoryModel.getCategoryName());
       bookImageView.setImageURI(Uri.parse(bookModel.getImageUri()));
    }
}