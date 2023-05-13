package com.example.slbtracker;

public class regularBook extends Book{

    public regularBook(){

    }
    public regularBook(String bookCode, String title, String author, int numOfDays, boolean isBorrowed) {
        super(bookCode, title, author, numOfDays, isBorrowed);
    }


    @Override
    public double getPrice() {
        double price = getNumOfDays() * 20.0;
        return price;
    }

    public double calculatePrice(int numOfDays) {
        double price = numOfDays * 20.0;
        return price;
    }
}
