package com.example.booklibrary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.booklibrary.models.BookModel;
import com.example.booklibrary.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "booklibrary.db";
    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String COLUMN_ID_CAT = "ID_CAT";
    public static final String COLUMN_CATEGORY_NAME = "CATEGORY_NAME";
    public static final String BOOK_TABLE = "BOOK_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_BOOK_NAME = "BOOK_NAME";
    public static final String COLUMN_AUTHOR_NAME = "AUTHOR_NAME";
    public static final String COLUMN_RELEASE_NUM = "RELEASE_NAME";
    public static final String COLUMN_PAGES_NUM = "PAGES_NAME";
    public static final String COLUMN_IMAGE_URI = "IMG_URI";
    public static final String FAVORITE_TABLE = "FAVORITE_TABLE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + CATEGORY_TABLE + " (" + COLUMN_ID_CAT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CATEGORY_NAME + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
        createTableStatement = "CREATE TABLE "
                + BOOK_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BOOK_NAME + " TEXT, "
                + COLUMN_AUTHOR_NAME + " TEXT, "
                + COLUMN_RELEASE_NUM + " TEXT,"
                + COLUMN_PAGES_NUM + " TEXT,"
                + COLUMN_ID_CAT + " INT,"
                + COLUMN_IMAGE_URI + " TEXT,"
                + " FOREIGN KEY (" + COLUMN_ID_CAT + ") REFERENCES " + CATEGORY_TABLE + "(" + COLUMN_ID_CAT + "));";
        sqLiteDatabase.execSQL(createTableStatement);
        createTableStatement = "CREATE TABLE "
                + FAVORITE_TABLE + " (" + COLUMN_ID + " INT)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addToACategoryColumn(CategoryModel categoryModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME, categoryModel.getCategoryName());
        long insertResult = sqLiteDatabase.insert(CATEGORY_TABLE, null, contentValues);
        return insertResult != -1;
    }

    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> allCategories = new ArrayList<>();
        String queryString = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int categoryId = cursor.getInt(0);
                String categoryName = cursor.getString(1);
                allCategories.add(new CategoryModel(categoryId, categoryName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return allCategories;
    }
    public List<String> getAllCategoriesNames() {
        List<String> allCategoriesNames = new ArrayList<>();
        String queryString = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String categoryName = cursor.getString(1);
                allCategoriesNames.add(categoryName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return allCategoriesNames;
    }

    public CategoryModel getCategoryByName(String categoryName) {
        CategoryModel categoryModel = new CategoryModel();
        String queryString = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + COLUMN_CATEGORY_NAME + "='" + categoryName + "'";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                categoryModel = new CategoryModel(cursor.getInt(0), cursor.getString(1));

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return categoryModel;
    }

    public CategoryModel getCategoryById(int categoryId) {
        CategoryModel categoryModel = new CategoryModel();
        String queryString = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + COLUMN_ID_CAT + "='" + categoryId + "'";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                categoryModel = new CategoryModel(cursor.getInt(0), cursor.getString(1));

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return categoryModel;
    }

    public void addToABookTable(BookModel bookModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_NAME, bookModel.getBookName());
        contentValues.put(COLUMN_AUTHOR_NAME, bookModel.getAuthorName());
        contentValues.put(COLUMN_RELEASE_NUM, bookModel.getReleaseYear());
        contentValues.put(COLUMN_PAGES_NUM, bookModel.getPageNum());
        contentValues.put(COLUMN_ID_CAT, bookModel.getCategoryId());
        contentValues.put(COLUMN_IMAGE_URI, bookModel.getImageUri());
        sqLiteDatabase.insert(BOOK_TABLE, null, contentValues);
    }



    public BookModel getBookByBookId(int id) {
        BookModel bookModel = new BookModel();
        String queryString = "SELECT * FROM " + BOOK_TABLE + " WHERE " + COLUMN_ID + "='" + id + "'";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                bookModel = new BookModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return bookModel;
    }

    public List<BookModel> getBooksByCategoryId(int id) {
        List<BookModel> booksByCategory = new ArrayList<>();
        String queryString = "SELECT * FROM " + BOOK_TABLE + " WHERE " + COLUMN_ID_CAT + "='" + id + "'";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                BookModel bookModel = new BookModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
                booksByCategory.add(bookModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return booksByCategory;
    }

    public void deleteBookById(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(BOOK_TABLE, COLUMN_ID + "=" + id, null);
        deleteBookFromFavorite(id);
        sqLiteDatabase.close();
    }

    public void updateBookById(int id, BookModel bookModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_NAME, bookModel.getBookName());
        contentValues.put(COLUMN_AUTHOR_NAME, bookModel.getAuthorName());
        contentValues.put(COLUMN_RELEASE_NUM, bookModel.getReleaseYear());
        contentValues.put(COLUMN_PAGES_NUM, bookModel.getPageNum());
        contentValues.put(COLUMN_ID_CAT, bookModel.getCategoryId());
        contentValues.put(COLUMN_IMAGE_URI, bookModel.getImageUri());
        sqLiteDatabase.update(BOOK_TABLE, contentValues, COLUMN_ID + "=" + id, null);
        sqLiteDatabase.close();
    }


    public void addBookToFavoriteById(int bookId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, bookId);
        sqLiteDatabase.insert(FAVORITE_TABLE, null, contentValues);
    }

    public void deleteBookFromFavorite(int bookId){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(FAVORITE_TABLE, COLUMN_ID + "=" + bookId, null);
        sqLiteDatabase.close();
    }
    public List<Integer>getAllFavoriteBooksIds(){
        List<Integer> favoriteBooksIds =new ArrayList<>();
        String queryString = "SELECT * FROM " + FAVORITE_TABLE;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                favoriteBooksIds.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return favoriteBooksIds;
    }
    public List<BookModel>getAllFavoriteBooks(){
        List<BookModel> favoriteBooks =new ArrayList<>();
        String queryString = "SELECT * FROM " + FAVORITE_TABLE;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                BookModel bookModel = getBookByBookId(cursor.getInt(0));
                favoriteBooks.add(bookModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return favoriteBooks;
    }
    public boolean isFavorite(int bookId){
        List<Integer> favoriteBooksIds = getAllFavoriteBooksIds();
        return favoriteBooksIds.contains(bookId);
    }
}
