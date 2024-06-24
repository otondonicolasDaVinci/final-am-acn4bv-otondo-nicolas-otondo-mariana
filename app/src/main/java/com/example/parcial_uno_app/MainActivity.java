package com.example.parcial_uno_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial_uno_app.model.Book;
import com.example.parcial_uno_app.model.adapter.BookAdapter;
import com.example.parcial_uno_app.utils.ThemeUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView booksRecyclerView;
    private RecyclerView recommendedRecyclerView;
    ImageView cartButton;
    Button moonButton;

    private List<Book> allRecommendedBooks;
    private List<Book> allBestSellersBooks;

    private List<Book> allBooks;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializar botones de categorías
        Button inicioButton = findViewById(R.id.inicio_button);
        Button novelaButton = findViewById(R.id.novela_button);
        Button autoayudaButton = findViewById(R.id.autoayuda_button);
        Button cienciaFiccionButton = findViewById(R.id.ciencia_ficcion_button_y_fantasia_button);

        // Configura los OnClickListener
        if (inicioButton != null) {
            inicioButton.setOnClickListener(v -> resetFilters());
        }

        if (autoayudaButton != null) {
            autoayudaButton.setOnClickListener(v -> filterBooksByCategory("Autoayuda"));
        }

        if (cienciaFiccionButton != null) {
            cienciaFiccionButton.setOnClickListener(v -> filterBooksByCategory("Fantasia"));
        }

        if (novelaButton != null) {
            novelaButton.setOnClickListener(v -> filterBooksByCategory("Novela"));
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        cartButton = new ImageView(this);
        setupCartButton();

        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterBooks(s.toString());
            }
        });

        checkDarkMode();
        setupToolbar();
        setupButtons(userName);
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

    private void setupButtons(String userName) {
        TextView greetingTextView = findViewById(R.id.greeting);
        greetingTextView.setText(getString(R.string.greeting, userName));
    }

    private void setupRecyclerViews() {
        booksRecyclerView = findViewById(R.id.books_recycler_view);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Book> bestSellers = new ArrayList<>();
        bestSellers.add(new Book("Deshacer la ansiedad", "Judson Brewer", "Aprende a dominar la ansiedad", "$9.99", R.drawable.deshacer_la_ansiedad, "Autoayuda"));
        bestSellers.add(new Book("Harry Potter", "J.K. Rowling", "Harry Potter y la piedra filosofal es el primer libro de la serie.", "$8.99", R.drawable.harry_potter_piedra, "Fantasia"));
        bestSellers.add(new Book("Lo que el viento se llevó", "Margaret Mitchell", "La historia de amor más grande jamás contada.", "$7.99", R.drawable.viento_se_llevo, "Novela"));
        bestSellers.add(new Book("El nombre del viento", "Patrick Rothfuss", "Una historia de aventuras y magia.", "$10.99", R.drawable.el_nombre_del_viento, "Fantasia"));

        BookAdapter bestSellersAdapter = new BookAdapter(bestSellers, this::openBookDetail);
        booksRecyclerView.setAdapter(bestSellersAdapter);

        recommendedRecyclerView = findViewById(R.id.recommended_recycler_view);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Book> recommendedBooks = new ArrayList<>();
        recommendedBooks.add(new Book("Harry Potter", "J.K. Rowling", "Harry Potter y la piedra filosofal es el primer libro de la serie.", "$8.99", R.drawable.harry_potter_piedra, "Fantasia"));
        recommendedBooks.add(new Book("El Silmarillion", "J.R.R. Tolkien", "El Silmarillion es una recopilación de mitos y leyendas que forman parte de la historia de la Tierra Media.", "$11.99", R.drawable.el_silmarillion, "Fantasia"));
        recommendedBooks.add(new Book("Habitos Atomicos", "James Clear", "Un método fácil y comprobado para construir buenos hábitos y romper malos hábitos.", "$12.99", R.drawable.atomic_habits, "Autoayuda"));
        recommendedBooks.add(new Book("El nombre del viento", "Patrick Rothfuss", "Una historia de aventuras y magia.", "$10.99", R.drawable.el_nombre_del_viento, "Fantasia"));
        recommendedBooks.add(new Book("Deshacer la ansiedad", "David Eagleman", "Una exploración fascinante del cerebro humano.", "$9.99", R.drawable.deshacer_la_ansiedad, "Autoayuda"));
        recommendedBooks.add(new Book("El Silmarillion", "J.R.R. Tolkien", "El Silmarillion es una recopilación de mitos y leyendas que forman parte de la historia de la Tierra Media.", "$11.99", R.drawable.el_silmarillion, "Fantasia"));
        BookAdapter recommendedBooksAdapter = new BookAdapter(recommendedBooks, this::openBookDetail);
        recommendedRecyclerView.setAdapter(recommendedBooksAdapter);

        allRecommendedBooks = new ArrayList<>(recommendedBooks);
        allBestSellersBooks = new ArrayList<>(bestSellers);
        allBooks = new ArrayList<>();
        allBooks.addAll(recommendedBooks);
        allBooks.addAll(bestSellers);
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupCartButton() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        cartButton.setImageResource(R.drawable.ic_cart);
        toolbar.addView(cartButton);

        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cerrar_sesion) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkDarkMode() {
        ConstraintLayout constraintLayout = findViewById(R.id.activity_main);
        EditText searchBar = findViewById(R.id.search_bar);

        moonButton = new Button(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
        moonButton.setLayoutParams(layoutParams);
        moonButton.setBackgroundResource(R.drawable.night_mode_moon);

        moonButton.setId(View.generateViewId());

        constraintLayout.addView(moonButton);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(moonButton.getId(), ConstraintSet.START, searchBar.getId(), ConstraintSet.END, 10);
        constraintSet.connect(moonButton.getId(), ConstraintSet.TOP, searchBar.getId(), ConstraintSet.BOTTOM, -100);
        constraintSet.applyTo(constraintLayout);

        final boolean[] isBackgroundBlack = {false};

        moonButton.setOnClickListener(v -> {
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
            moonButton.setBackgroundResource(R.drawable.night_mode_moon_white);
        } else {
            moonButton.setBackgroundResource(R.drawable.night_mode_moon);
        }
    }

    private void filterBooks(String query) {
        List<Book> filteredBestSellers = new ArrayList<>();
        List<Book> filteredRecommendedBooks = new ArrayList<>();

        for (Book book : allBestSellersBooks) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                filteredBestSellers.add(book);
            }
        }

        for (Book book : allRecommendedBooks) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                filteredRecommendedBooks.add(book);
            }
        }

        BookAdapter bestSellersAdapter = new BookAdapter(filteredBestSellers, this::openBookDetail);
        booksRecyclerView.setAdapter(bestSellersAdapter);

        BookAdapter recommendedBooksAdapter = new BookAdapter(filteredRecommendedBooks, this::openBookDetail);
        recommendedRecyclerView.setAdapter(recommendedBooksAdapter);
    }

    private void filterBooksByCategory(String category) {
        List<Book> filteredBestSellers = new ArrayList<>();
        List<Book> filteredRecommendedBooks = new ArrayList<>();

        for (Book book : allBestSellersBooks) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                filteredBestSellers.add(book);
            }
        }

        for (Book book : allRecommendedBooks) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                filteredRecommendedBooks.add(book);
            }
        }

        BookAdapter bestSellersAdapter = new BookAdapter(filteredBestSellers, this::openBookDetail);
        booksRecyclerView.setAdapter(bestSellersAdapter);

        BookAdapter recommendedBooksAdapter = new BookAdapter(filteredRecommendedBooks, this::openBookDetail);
        recommendedRecyclerView.setAdapter(recommendedBooksAdapter);
    }

    private void resetFilters() {
        BookAdapter bestSellersAdapter = new BookAdapter(allBestSellersBooks, this::openBookDetail);
        booksRecyclerView.setAdapter(bestSellersAdapter);

        BookAdapter recommendedBooksAdapter = new BookAdapter(allRecommendedBooks, this::openBookDetail);
        recommendedRecyclerView.setAdapter(recommendedBooksAdapter);
    }
}
