package com.example.parcial_uno_app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

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


        ConstraintLayout constraintLayout = findViewById(R.id.activity_main);
        EditText searchBar = findViewById(R.id.search_bar);

        // Crea un nuevo Button
        Button moonButton = new Button(this);

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

        });

        // Inicializa el DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Configura el ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        // Establece el ActionBarDrawerToggle como el listener del DrawerLayout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Llama a syncState() para sincronizar el estado del indicador del menú hamburguesa con el estado del DrawerLayout
        actionBarDrawerToggle.syncState();


        // Inicializa la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Establece la Toolbar como la barra de acciones de la actividad
        setSupportActionBar(toolbar);

        // Elimina el título de la Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Hace la Toolbar transparente
        toolbar.setBackgroundColor(Color.TRANSPARENT);

        // Muestra el botón del menú hamburguesa en la barra de acciones
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtén la referencia al TextView del saludo
        TextView greetingTextView = findViewById(R.id.greeting);

        // Define el nombre del usuario
        String userName = "Nicolas"; // Puedes obtener este valor de las preferencias del usuario o de cualquier otra fuente.

        // Establece el texto del saludo con el nombre del usuario
        greetingTextView.setText(getString(R.string.greeting, userName));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Si el ActionBarDrawerToggle maneja el evento de clic, retorna true
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // De lo contrario, deja que el comportamiento predeterminado maneje el evento de clic
        return super.onOptionsItemSelected(item);
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

}