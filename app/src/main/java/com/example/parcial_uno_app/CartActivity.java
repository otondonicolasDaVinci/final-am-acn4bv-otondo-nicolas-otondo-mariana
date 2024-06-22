package com.example.parcial_uno_app;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial_uno_app.model.Book;
import com.example.parcial_uno_app.model.adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    public static CartAdapter cartAdapter;
    private TextView itemCountTextView;
    private TextView totalPriceTextView;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, BookDetailActivity.getCart());
        cartRecyclerView.setAdapter(cartAdapter);

        itemCountTextView = findViewById(R.id.item_count);
        totalPriceTextView = findViewById(R.id.total_price);

        updateCartSummary();

        // Registra un BroadcastReceiver para escuchar los broadcasts de "cart_updated"
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateCartSummary();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("cart_updated"));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @SuppressLint("DefaultLocale")
    public void updateCartSummary() {
        int itemCount = cartAdapter.getTotalItemCount();
        float totalPrice = 0f;

        for (Book book : BookDetailActivity.getCart()) {
            int quantity = cartAdapter.getQuantity(book);
            totalPrice += quantity * Float.parseFloat(book.getPrice().replace("$", ""));
        }

        itemCountTextView.setText(getString(R.string.item_count, itemCount));
        totalPriceTextView.setText(getString(R.string.total_price, String.format("%.2f", totalPrice)));
    }
}
