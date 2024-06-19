package com.example.parcial_uno_app.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<Book> cartItems = new ArrayList<>();

    public static void addItem(Book book) {
        cartItems.add(book);
    }

    public static List<Book> getCartItems() {
        return cartItems;
    }

    public static double getTotalPrice() {
        double total = 0;
        for (Book book : cartItems) {
            total += Double.parseDouble(book.getPrice().replace("$", "").replace(",", "."));
        }
        return total;
    }

    public static int getItemCount() {
        return cartItems.size();
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
