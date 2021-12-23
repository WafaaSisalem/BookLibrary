package com.example.booklibrary.models;

public class CategoryModel {
    String categoryName;
    int id;
    public  CategoryModel(){}
    public CategoryModel( int id,String categoryName) {
        this.categoryName = categoryName;
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
