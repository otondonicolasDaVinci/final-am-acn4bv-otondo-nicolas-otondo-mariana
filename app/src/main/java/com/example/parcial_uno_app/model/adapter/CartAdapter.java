package com.example.parcial_uno_app.model.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial_uno_app.R;
import com.example.parcial_uno_app.model.Book;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Book> cartItems;
    private Context context;
    private SparseIntArray quantities;

    public CartAdapter(Context context, List<Book> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
        this.quantities = new SparseIntArray(cartItems.size());
        for (int i = 0; i < cartItems.size(); i++) {
            this.quantities.put(i, 1);  // Inicializamos todas las cantidades en 1
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Book book = cartItems.get(position);
        holder.bookCover.setImageResource(book.getCoverImage());
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookPrice.setText(book.getPrice());
        holder.bookQuantity.setText(String.valueOf(quantities.get(position)));

        // Agrega la lógica para los botones de incrementar y decrementar
        holder.increaseButton.setOnClickListener(v -> {
            int newQuantity = quantities.get(position) + 1;
            quantities.put(position, newQuantity);
            holder.bookQuantity.setText(String.valueOf(newQuantity));
            updateCartSummary();
        });

        holder.decreaseButton.setOnClickListener(v -> {
            int newQuantity = quantities.get(position) - 1;
            if (newQuantity > 0) {
                quantities.put(position, newQuantity);
                holder.bookQuantity.setText(String.valueOf(newQuantity));
                updateCartSummary();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void updateCartSummary() {
        // Lógica para actualizar el resumen del carrito
        int itemCount = 0;
        double totalPrice = 0.0;

        for (int i = 0; i < cartItems.size(); i++) {
            int quantity = quantities.get(i);
            itemCount += quantity;
            totalPrice += quantity * Double.parseDouble(cartItems.get(i).getPrice().substring(1));
        }

    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView bookCover;
        TextView bookTitle, bookAuthor, bookPrice, bookQuantity;
        ImageView increaseButton, decreaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookPrice = itemView.findViewById(R.id.book_price);
            bookQuantity = itemView.findViewById(R.id.book_quantity);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
        }
    }
}
