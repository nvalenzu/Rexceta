package cl.telematica.android.rexceta;


import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements HorizontalListFragment.OnFragmentInteractionListener {

    Spinner categoria;
    Spinner tiempo_preparacion;
    Spinner dificultad;

    String txt_categoria;
    String txt_tiempo;
    String txt_dificultad;

    Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // TOOLBAR SEARCHVIEW ----------------------------------------------------------------------
        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        getSupportActionBar().setTitle(R.string.my_tb_title);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar);

        Intent searchIntent = getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction()))
        {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(HomeActivity.this, "Buscando " + query, Toast.LENGTH_SHORT).show();
            Intent i_search = new Intent(HomeActivity.this, ListaRecetas.class);
            i_search.putExtra("search", query);
            startActivity(i_search);
        }
        // TOOLBAR SEARCHVIEW ---------------------------------------------------------------------
        // FRAGMENT COMUNICACION ----------------------------------------------------------------


        HorizontalListFragment fragment = new HorizontalListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();


        // FRAGMENT COMUNICACION ----------------------------------------------------------------


        // DECLARACION DE SPINNERS --------------------------------------------------------------
        categoria = (Spinner) findViewById(R.id.spinner_Categoria);
        tiempo_preparacion = (Spinner) findViewById(R.id.spinner_Tiempo);
        dificultad = (Spinner) findViewById(R.id.spinner_Dificultad);

        ArrayAdapter adapter1 =
                ArrayAdapter.createFromResource(this, R.array.Categorias, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapter2 =
                ArrayAdapter.createFromResource(this, R.array.Tiempo, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter adapter3 =
                ArrayAdapter.createFromResource(this, R.array.Dificultad, android.R.layout.simple_spinner_dropdown_item);

        categoria.setAdapter(adapter1);
        tiempo_preparacion.setAdapter(adapter2);
        dificultad.setAdapter(adapter3);

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item1 = parent.getItemAtPosition(position);
                txt_categoria = item1.toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_categoria = "*";
            }
        });

        tiempo_preparacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item2 = parent.getItemAtPosition(position);
                txt_tiempo = item2.toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_tiempo = "*";
            }
        });

        dificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item3 = parent.getItemAtPosition(position);
                txt_dificultad = item3.toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_dificultad = "*";
            }
        });
        // TERMINO DE SPINNERS --------------------------------------------------------------------

        // BOTON FILTRAR -------------------------------------------------------------------------
        Button boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ListaRecetas.class);
                i.putExtra("categoria", txt_categoria);
                i.putExtra("dificultad", txt_dificultad);
                i.putExtra("tiempo", txt_tiempo);
                startActivity(i);
            }
        });
        // TERMINO BOTON FILTRAR ----------------------------------------------------------------
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_1:
                Toast.makeText(cl.telematica.android.rexceta.HomeActivity.this, "opcion 1 del menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_2:
                Toast.makeText(cl.telematica.android.rexceta.HomeActivity.this, "opcion 2 del menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

//









