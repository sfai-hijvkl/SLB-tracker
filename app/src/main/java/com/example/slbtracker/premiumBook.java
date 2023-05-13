package com.example.slbtracker;

public class premiumBook extends Book {

    public premiumBook(){

    }
    public premiumBook(String bookCode, String title, String author, int numOfDays, boolean isBorrowed) {
        super(bookCode, title, author, numOfDays, isBorrowed);
    }

    @Override
    public double getPrice() {
        double price = getNumOfDays() * 50.0;
        if (getNumOfDays() > 7) {
            price += (getNumOfDays() - 7) * 25.0;
        }
        return price;
    }

    public double calculatePrice(int numOfDays) {
        double price = numOfDays * 50.0;
        if (numOfDays > 7) {
            price += (numOfDays - 7) * 25.0;
        }
        return price;
    }
}
