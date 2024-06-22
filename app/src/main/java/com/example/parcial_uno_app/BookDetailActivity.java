package com.example.parcial_uno_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.parcial_uno_app.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    private static List<Book> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Inicializar vistas
        ImageView bookCover = findViewById(R.id.book_cover);
        TextView bookTitle = findViewById(R.id.book_title);
        TextView bookAuthor = findViewById(R.id.book_author);
        TextView bookPrice = findViewById(R.id.book_price);
        TextView bookDescription = findViewById(R.id.book_description);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String description = intent.getStringExtra("description");
        float rating = intent.getFloatExtra("rating", 0);
        String price = intent.getStringExtra("price");
        int coverImage = intent.getIntExtra("coverImage", -1);

        // Establecer datos en las vistas
        if (coverImage != -1) {
            bookCover.setImageResource(coverImage);
        }
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookPrice.setText(price);
        bookDescription.setText(description);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        addToCartButton.setOnClickListener(v -> {
            Book book = new Book(title, author, description, rating, price, coverImage);
            cart.add(book);
            Toast.makeText(BookDetailActivity.this, "Agregado al carrito", Toast.LENGTH_SHORT).show();

            // Enviar broadcast para actualizar el carrito
            Intent intentCart = new Intent("cart_updated");
            LocalBroadcastManager.getInstance(BookDetailActivity.this).sendBroadcast(intentCart);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static List<Book> getCart() {
        return cart;
    }
}
