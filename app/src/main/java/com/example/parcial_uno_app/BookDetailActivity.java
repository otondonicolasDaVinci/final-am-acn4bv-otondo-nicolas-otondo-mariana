package com.example.parcial_uno_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);


        ImageView bookCover = findViewById(R.id.book_cover);
        TextView bookTitle = findViewById(R.id.book_title);
        TextView bookAuthor = findViewById(R.id.book_author);
        TextView bookPrice = findViewById(R.id.book_price);
        TextView bookDescription = findViewById(R.id.book_description);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String description = intent.getStringExtra("description");
        float rating = intent.getFloatExtra("rating", 0);
        String price = intent.getStringExtra("price");
        int coverImage = intent.getIntExtra("coverImage", -1);

        if (coverImage != -1) {
            bookCover.setImageResource(coverImage);
        }
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookPrice.setText(price);
        bookDescription.setText(description);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        addToCartButton.setOnClickListener(v -> {
            // Implementar lógica para agregar al carrito
        });
    }
}
