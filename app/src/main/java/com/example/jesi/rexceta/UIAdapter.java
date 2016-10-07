package com.example.jesi.rexceta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.webkit.URLUtil;

import java.io.InputStream;
import java.util.List;

import static android.webkit.URLUtil.isDataUrl;
import static android.webkit.URLUtil.isValidUrl;


/**
 * Created by Jesi on 05-10-16.
 */

public class UIAdapter extends RecyclerView.Adapter<UIAdapter.ViewHolder> {
    private List<Item_Receta> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleView;
        public RatingBar mValorationView;
        public TextView mDescriptionView;
        public ImageView mImageView;
        public ImageView mImageViewVid;



        public ViewHolder(View v) {
            super(v);

            mTitleView = (TextView) v.findViewById(R.id.textView6);
            mValorationView = (RatingBar) v.findViewById(R.id.ratingBar3);
            mDescriptionView = (TextView) v.findViewById(R.id.textView8);
            mImageView = (ImageView) v.findViewById(R.id.imageView4);
            mImageViewVid = (ImageView) v.findViewById(R.id.imageView5);
        }
    }

    public UIAdapter(List<Item_Receta> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public UIAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item_Receta recet = mDataset.get(position);

        if(isValidUrl(recet.getImagen())){
            new DownloadImageTask(holder.mImageView).execute(recet.getImagen());
        }else{
            holder.mImageView.setImageResource(R.drawable.sinimagen);
        }

        if(isValidUrl(recet.getVideo())){
            holder.mImageViewVid.setImageResource(R.drawable.camera);
        }else{
            holder.mImageViewVid.setImageResource(0);
        }
        holder.mTitleView.setText(recet.getNombre());
        holder.mValorationView.setRating(recet.getValoracion());
        holder.mDescriptionView.setText(recet.getDescripcion());


    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



 private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

}
}
