package com.example.slbtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<regularBook> regularBooks;
    private List<premiumBook> premiumBooks;

    Button borrow;
    EditText regBookCode;
    EditText regNumDays;
    TextView regOutPrice;
    TextView regTitle;
    TextView regAuthor;

    EditText premBookCode;
    EditText premNumDays;
    TextView premOutPrice;
    TextView premTitle;
    TextView premAuthor;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        borrow = findViewById(R.id.borrowButton);
        regBookCode = findViewById(R.id.bookCodeReg);
        regNumDays = findViewById(R.id.numOfDaysReg);
        regOutPrice = findViewById(R.id.outputPriceReg);
        regTitle = findViewById(R.id.titleReg);
        regAuthor = findViewById(R.id.authorReg);

        premBookCode = findViewById(R.id.bookCodePrem);
        premNumDays = findViewById(R.id.numOfDaysPrem);
        premOutPrice = findViewById(R.id.outputPricePrem);
        premTitle = findViewById(R.id.titlePrem);
        premAuthor = findViewById(R.id.authorPrem);
        CollectionReference regBooksRef = db.collection("RegularBooks");
        CollectionReference premBooksRef = db.collection("PremiumBooks");

        regularBooks = new ArrayList<>();
        premiumBooks = new ArrayList<>();

        regBooksRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                    regularBook book = document.toObject(regularBook.class);
                    if (!book.isBorrowed()) {
                        regularBooks.add(book);
                    }
                }
            }
        });

        premBooksRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    premiumBook book = document.toObject(premiumBook.class);
                    if (!book.isBorrowed()) {
                        premiumBooks.add(book);
                    }
                }
            }
        });


        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regBookCodeValue = regBookCode.getText().toString().trim();
                String regNumDaysValue = regNumDays.getText().toString().trim();
                String premBookCodeValue = premBookCode.getText().toString().trim();
                String premNumDaysValue = premNumDays.getText().toString().trim();

                // Regular Book Part
                if (!regBookCodeValue.isEmpty() && !regNumDaysValue.isEmpty()) {
                    regularBook regBook = findRegBook(regBookCodeValue);
                    if (regBook != null) {
                        if (regBook.isBorrowed()) {
                            Toast.makeText(MainActivity.this, "The book " + regBookCodeValue + " has already been borrowed", Toast.LENGTH_SHORT).show();
                            regOutPrice.setText("");
                            regTitle.setText("");
                            regAuthor.setText("");
                        } else {
                            regOutPrice.setText("Output Price: $" + regBook.getPrice());
                            regTitle.setText("Title: " + regBook.getTitle());
                            regAuthor.setText("Author: " + regBook.getAuthor());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid book code for regular book", Toast.LENGTH_SHORT).show();
                        regOutPrice.setText("");
                        regTitle.setText("");
                        regAuthor.setText("");
                    }
                }

                // Premium Book Part
                if (!premBookCodeValue.isEmpty() && !premNumDaysValue.isEmpty()) {
                    premiumBook premBook = findPremBook(premBookCodeValue);
                    if (premBook != null) {
                        if (premBook.isBorrowed()) {
                            Toast.makeText(MainActivity.this, "The book " + premBookCodeValue + " has already been borrowed", Toast.LENGTH_SHORT).show();
                            premOutPrice.setText("");
                            premTitle.setText("");
                            premAuthor.setText("");
                        } else {
                            int premNumDays = Integer.parseInt(premNumDaysValue);
                            premBook.setNumOfDays(premNumDays);
                            premOutPrice.setText("Output Price: $" + premBook.getPrice());
                            premTitle.setText("Title: " + premBook.getTitle());
                            premAuthor.setText("Author: " + premBook.getAuthor());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid book code for premium book", Toast.LENGTH_SHORT).show();
                        premOutPrice.setText("");
                        premTitle.setText("");
                        premAuthor.setText("");
                    }
                }
            }
        });





    }//

    private regularBook findRegBook(String bookCode){

        for (regularBook regBook : regularBooks) {
            if (regBook != null && regBook.getBookCode() != null && regBook.getBookCode().equals(bookCode)) {
                return regBook;
            }
        }

        Toast.makeText(MainActivity.this, "Invalid book code", Toast.LENGTH_SHORT).show();
        return null;
    }//

    private premiumBook findPremBook(String bookCode) {
        for (premiumBook premBook : premiumBooks) {
            if (premBook != null && premBook.getBookCode() != null && premBook.getBookCode().equals(bookCode)) {
                return premBook;
            }
        }

        Toast.makeText(MainActivity.this, "Invalid book code", Toast.LENGTH_SHORT).show();

        return null;
    }//
}
