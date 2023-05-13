package com.example.slbtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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
                    regularBooks.add(book);
                }
            }
        });

        premBooksRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    premiumBook book = document.toObject(premiumBook.class);
                    premiumBooks.add(book);
                }
            }
        });

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bCode = regBookCode.getText().toString();
                String nDaysStr = regNumDays.getText().toString().trim();
                String bCode2 = premBookCode.getText().toString();
                String nDays2Str = premNumDays.getText().toString().trim();

                boolean regBookBool = false;
                boolean premBookBool = false;

                if (!TextUtils.isEmpty(bCode) && !TextUtils.isEmpty(nDaysStr)) {
                    regBookBool = true;

                    int nDays = Integer.parseInt(nDaysStr);
                    regularBook book = findRegBook(bCode);

                    if (book != null) {
                        double price = book.calculatePrice(nDays);
                        regOutPrice.setText("P" + price);
                        regTitle.setText(book.getTitle());
                        regAuthor.setText(book.getAuthor());
                        premBookCode.setText("");
                        premNumDays.setText("");
                        premOutPrice.setText("");
                        premTitle.setText("");
                        premAuthor.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Regular Book code", Toast.LENGTH_SHORT).show();
                    }
                }

                if (!TextUtils.isEmpty(bCode2) && !TextUtils.isEmpty(nDays2Str)) {
                    premBookBool = true;

                    int nDays2 = Integer.parseInt(nDays2Str);
                    premiumBook book2 = findPremBook(bCode2);

                    if (book2 != null) {
                        double price = book2.calculatePrice(nDays2);
                        premOutPrice.setText("P" + price);
                        premTitle.setText(book2.getTitle());
                        premAuthor.setText(book2.getAuthor());
                        regBookCode.setText("");
                        regNumDays.setText("");
                        regOutPrice.setText("");
                        regTitle.setText("");
                        regAuthor.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Premium Book code", Toast.LENGTH_SHORT).show();
                    }
                }

                if (!regBookBool && !premBookBool) {
                    Toast.makeText(MainActivity.this, "Please enter a book code and number of days", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }//

    private regularBook findRegBook(String bookCode){

        for(regularBook regBook: regularBooks){
            if (regBook != null && regBook.getBookCode() != null && regBook.getBookCode().equals(bookCode)) {
                return regBook;
            }
        }
        return null;
    }//

    private premiumBook findPremBook(String bookCode) {
        for (premiumBook premBook : premiumBooks) {
            if (premBook != null && premBook.getBookCode() != null && premBook.getBookCode().equals(bookCode)) {
                return premBook;
            }
        }
        return null;
    }//

    
}