package com.example.booklibrary.models;

public class BookModel {
    int bookId;
    String bookName;
    String authorName;
    String releaseYear;
    String pageNum;
    int categoryId;
    String imageUri;
public  BookModel(){}
    public BookModel(int bookId, String bookName, String authorName, String releaseYear, String pageNum, int categoryId, String imageUri) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.releaseYear = releaseYear;
        this.pageNum = pageNum;
        this.categoryId = categoryId;
        this.imageUri = imageUri;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", pageNum='" + pageNum + '\'' +
                ", categoryId=" + categoryId +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }

}
