package cl.telematica.android.rexceta;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class RecetaMain extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String json;
    public TextView mTitulo;
    public TextView mPreparacion;
    public TextView mIngredientes;
    public RatingBar mValorationView;
    public String video;
    public TextView mDificultad;
    public TextView mTiempo;
   // public TextView mDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_main);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        json = getIntent().getStringExtra("json");

        mTitulo = (TextView) findViewById(R.id.textView2);
        mPreparacion = (TextView) findViewById(R.id.textView9);
        mIngredientes = (TextView) findViewById(R.id.textView7);
        mDificultad = (TextView) findViewById(R.id.textView11);
        mTiempo = (TextView) findViewById(R.id.textView18);

        addListenerOnRatingBar();


        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(Void... params) {
                String resultado = new httpServerConnection().connectToServer(json, 15000);
                return resultado;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String result) {
                Item_Receta itemReceta = new Item_Receta();
                if (result != null) {
                    System.out.println(result);
                    try {
                        JSONObject receta = new JSONObject(result);
                        itemReceta.setNombre(receta.getString("nombre"));
                        itemReceta.setDificultad(receta.getString("dificultad"));
                        itemReceta.setAutor(receta.getString("autor"));
                        itemReceta.setVideo(receta.getString("video"));
                        itemReceta.setImagen(receta.getString("imagen"));
                        itemReceta.setCategoria(receta.getString("categoria"));
                        itemReceta.setDescripcion(receta.getString("descripcion"));
                        itemReceta.setPreparacion(receta.getString("preparacion"));
                        itemReceta.setTiempo(receta.getString("tiempo"));


                        JSONArray jsonIngr = receta.getJSONArray("ingredientes");

                        JSONArray jsonEtiq = receta.getJSONArray("etiqueta");


                        List<String> ingredientes = new ArrayList<String>();
                        List<String> etiquetas = new ArrayList<String>();

                        int numIngr = jsonIngr.length();
                        int numEtiq = jsonEtiq.length();

                        for(int i = 0; i < numIngr; i++) {
                            ingredientes.add(jsonIngr.getString(i));
                        }

                        for(int f = 0; f < numEtiq; f++){
                            etiquetas.add(jsonIngr.getString(f));
                        }

                        itemReceta.setIngredientes(ingredientes);
                        itemReceta.setEtiquetas(etiquetas);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mTitulo.setText(itemReceta.getNombre());
                mPreparacion.setText(itemReceta.getPreparacion());
                mDificultad.setText(itemReceta.getDificultad());
                mTiempo.setText(itemReceta.getTiempo());
                StringBuilder builder = new StringBuilder();
                for (String details : itemReceta.getIngredientes()) {
                    builder.append(details + "\n");
                }
                mIngredientes.setText(builder);
                video = itemReceta.getVideo();

            }
        };

        task.execute();

    }

    private void addListenerOnRatingBar() {
        mValorationView = (RatingBar) findViewById(R.id.ratingBar);
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        mValorationView.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Toast.makeText(RecetaMain.this, "Le asignaste " + String.valueOf(rating) + " estrellas a esta receta",
                        Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(video); // Plays https://www.youtube.com/watch?v=nYupiSI9_-8
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            //String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

}
