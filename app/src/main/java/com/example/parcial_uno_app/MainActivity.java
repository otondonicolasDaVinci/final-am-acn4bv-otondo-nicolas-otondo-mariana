package com.example.parcial_uno_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial_uno_app.model.Book;
import com.example.parcial_uno_app.model.adapter.BookAdapter;
import com.example.parcial_uno_app.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView booksRecyclerView;
    private RecyclerView recommendedRecyclerView;
    ImageView cartButton;
    Button moonButton;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cartButton = new ImageView(this);
        setupCartButton();


        checkDarkMode();
        setupToolbar();
        setupButtons();
        setupRecyclerViews();
        setupDrawer();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupButtons() {
        TextView greetingTextView = findViewById(R.id.greeting);
        String userName = "Nicolas";
        greetingTextView.setText(getString(R.string.greeting, userName));
    }

    private void setupRecyclerViews() {
        booksRecyclerView = findViewById(R.id.books_recycler_view);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Book> bestSellers = new ArrayList<>();
        bestSellers.add(new Book("Deshacer la ansiedad", "Judson Brewer", "Aprende a dominar la ansiedad", "$9.99", R.drawable.deshacer_la_ansiedad));
        bestSellers.add(new Book("Harry Potter", "J.K. Rowling", "Harry Potter y la piedra filosofal es el primer libro de la serie.", "$8.99", R.drawable.harry_potter_piedra));
        bestSellers.add(new Book("Lo que el viento se llevó", "Margaret Mitchell", "La historia de amor más grande jamás contada.", "$7.99", R.drawable.viento_se_llevo));
        bestSellers.add(new Book("El nombre del viento", "Patrick Rothfuss", "Una historia de aventuras y magia.", "$10.99", R.drawable.el_nombre_del_viento));

        BookAdapter bestSellersAdapter = new BookAdapter(bestSellers, this::openBookDetail);
        booksRecyclerView.setAdapter(bestSellersAdapter);

        recommendedRecyclerView = findViewById(R.id.recommended_recycler_view);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Book> recommendedBooks = new ArrayList<>();
        recommendedBooks.add(new Book("Harry Potter", "J.K. Rowling", "Harry Potter y la piedra filosofal es el primer libro de la serie.", "$8.99", R.drawable.harry_potter_piedra));
        recommendedBooks.add(new Book("Habitos Atomicos", "James Clear", "Un método fácil y comprobado para construir buenos hábitos y romper malos hábitos.", "$12.99", R.drawable.atomic_habits));
        recommendedBooks.add(new Book("El nombre del viento", "Patrick Rothfuss", "Una historia de aventuras y magia.", "$10.99", R.drawable.el_nombre_del_viento));
        recommendedBooks.add(new Book("Deshacer la", "David Eagleman", "Una exploración fascinante del cerebro humano.", "$9.99", R.drawable.deshacer_la_ansiedad));
        recommendedBooks.add(new Book("El Silmarillion", "J.R.R. Tolkien", "El Silmarillion es una recopilación de mitos y leyendas que forman parte de la historia de la Tierra Media.", "$11.99", R.drawable.el_silmarillion));
        BookAdapter recommendedBooksAdapter = new BookAdapter(recommendedBooks, this::openBookDetail);
        recommendedRecyclerView.setAdapter(recommendedBooksAdapter);
    }

    private void openBookDetail(Book book) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("title", book.getTitle());
        intent.putExtra("author", book.getAuthor());
        intent.putExtra("description", book.getDescription());
        intent.putExtra("price", book.getPrice());
        intent.putExtra("coverImage", book.getCoverImage());
        startActivity(intent);
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setupCartButton() {
        // Agregar un botón de carrito en la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        cartButton.setImageResource(R.drawable.ic_cart); // Reemplaza con el ícono de carrito que tengas
        toolbar.addView(cartButton);

        // Configurar el listener del botón del carrito
        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkDarkMode(){
        ConstraintLayout constraintLayout = findViewById(R.id.activity_main);
        EditText searchBar = findViewById(R.id.search_bar);

        moonButton = new Button(this);

        // Establece las propiedades del Button
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70); // Cambia estos valores para ajustar el tamaño del botón
        moonButton.setLayoutParams(layoutParams);
        moonButton.setBackgroundResource(R.drawable.night_mode_moon);

        //Seteamos ID al boton
        moonButton.setId(View.generateViewId());

        // Agrega el Button al ConstraintLayout
        constraintLayout.addView(moonButton);

        // Define las restricciones del botón para posicionarlo al lado de la barra de búsqueda
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(moonButton.getId(), ConstraintSet.START, searchBar.getId(), ConstraintSet.END, 10); // El último parámetro es la margen en dp
        constraintSet.connect(moonButton.getId(), ConstraintSet.TOP, searchBar.getId(), ConstraintSet.BOTTOM, -100); // Agrega un margen superior para mover el botón un poco más abajo
        constraintSet.applyTo(constraintLayout);

        // Crea una variable para rastrear si el fondo está actualmente en negro
        final boolean[] isBackgroundBlack = {false};

        // Agrega un OnClickListener al Button
        moonButton.setOnClickListener(v -> {
            // Cambia el color de fondo del ConstraintLayout a negro o blanco dependiendo del estado de isBackgroundBlack
            if (isBackgroundBlack[0]) {
                constraintLayout.setBackgroundColor(Color.WHITE);
                isBackgroundBlack[0] = false;
                changeTextColor(constraintLayout, Color.BLACK);
            } else {
                constraintLayout.setBackgroundColor(Color.parseColor("#16161d"));
                isBackgroundBlack[0] = true;
                changeTextColor(constraintLayout, Color.WHITE);
            }
            ThemeUtils.saveDarkModeState(MainActivity.this, isBackgroundBlack[0]);
            updateCartButtonColor();
            updateMoonColor();
        });
    }

    private void changeTextColor(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView && view.getId() != R.id.greeting) {
                ((TextView) view).setTextColor(color);
            } else if (view instanceof ViewGroup) {
                changeTextColor((ViewGroup) view, color);

            }
        }


    }

    private void updateCartButtonColor() {
        if (ThemeUtils.loadDarkModeState(this)) {
            cartButton.setColorFilter(Color.WHITE);
        } else {
            cartButton.setColorFilter(Color.BLACK);
        }

    }

    private void updateMoonColor() {
        if (ThemeUtils.loadDarkModeState(this)) {
            moonButton.setBackgroundResource(R.drawable.night_mode_light_moon);
        } else {
            moonButton.setBackgroundResource(R.drawable.night_mode_moon);
        }
    }
}