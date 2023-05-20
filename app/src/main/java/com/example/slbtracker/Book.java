package com.example.slbtracker;

public abstract class Book {
    private String bookCode;
    private String title;
    private String author;
    private int numOfDays;
    private boolean isBorrowed;

    public Book(){

    }
    public Book(String bookCode, String title, String author, int numOfDays, boolean isBorrowed) {
        this.bookCode = bookCode;
        this.title = title;
        this.author = author;
        this.numOfDays = numOfDays;
        this.isBorrowed = isBorrowed;
    }

    public String getBookCode() {
        return bookCode;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
    
    public void setNumOfDays(){
    this.numOfDays = numOfDays;
    }

    public abstract double getPrice();
}
