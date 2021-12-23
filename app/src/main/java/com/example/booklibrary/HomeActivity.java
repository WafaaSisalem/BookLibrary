package com.example.booklibrary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.booklibrary.mainscreens.CreateBookActivity;
import com.example.booklibrary.mainscreens.CreateCategoryActivity;
import com.example.booklibrary.mainscreens.FavoriteActivity;
import com.example.booklibrary.mainscreens.LibraryActivity;

public class HomeActivity extends AppCompatActivity {

    final int REQUEST_CODE_PERM = 1;
    CardView libraryCardView, createBookCardView, favoriteCardView, createCategoryCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERM);
        }
        libraryCardView = findViewById(R.id.library_card_view);
        favoriteCardView = findViewById(R.id.favorite_card_view);
        createBookCardView = findViewById(R.id.create_book_card_view);
        createCategoryCardView = findViewById(R.id.create_category_card_view);

        libraryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });
        favoriteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        createBookCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateBookActivity.class);
                startActivity(intent);
            }
        });
        createCategoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateCategoryActivity.class);
                startActivity(intent);
            }
        });
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==REQUEST_CODE_PERM){
//            if(grantResults.length>0&&grantResults[0] != PackageManager.PERMISSION_GRANTED){
//
//            }
//        }
//    }
//    public void checkPermession(){
//        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERM);
//            Toast.makeText(HomeActivity.this,"the app need this permission to work",Toast.LENGTH_LONG).show();
//        }
//    }
}