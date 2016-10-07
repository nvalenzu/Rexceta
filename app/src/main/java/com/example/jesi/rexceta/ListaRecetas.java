package com.example.jesi.rexceta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ListaRecetas extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myadapter;
    private RecyclerView.LayoutManager mylayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_recetas);

        myRecyclerView = (RecyclerView) findViewById(R.id.recicler_view);
        myRecyclerView.setHasFixedSize(true);
        mylayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(mylayoutManager);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute(){

            }

            @Override
            protected String doInBackground(Void... params) {
                String resultado = new httpServerConnection().connectToServer("http://www.mocky.io/v2/57f7e9c2110000d91ac5ab26", 15000);
                return resultado;
            }

            @Override
            protected void onPostExecute(String result) {
                if(result != null){
                    System.out.println(result);

                    myadapter = new UIAdapter(getLista(result));
                    myRecyclerView.setAdapter(myadapter);
                }
                else
                    System.out.println("No existe el usuario ingresado");
            }

        };

        task.execute();
    }

    private List<Item_Receta> getLista(String result){
        List<Item_Receta> listarepos = new ArrayList<Item_Receta>();
        try {
            JSONArray lista = new JSONArray(result);

            int size = lista.length();
            for(int i = 0; i < size; i++){
                Item_Receta item_receta = new Item_Receta();
                JSONObject objeto = lista.getJSONObject(i);

                item_receta.setNombre(objeto.getString("titulo"));
                item_receta.setDescripcion(objeto.getString("descripcion"));
                item_receta.setValoracion(objeto.getInt("Estrellas"));
                item_receta.setVideo(objeto.getString("video"));
                item_receta.setImagen(objeto.getString("Imagen"));
                //item_receta.setDificultad(objeto.getString("dificultad"));


                listarepos.add(item_receta);
            }
            return listarepos;
        } catch (JSONException e) {
            e.printStackTrace();
            return listarepos;
        }
    }
}

