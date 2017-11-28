package ch.fhnw.wke;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ch.fhnw.wke.R;

public class ReviewActivity extends AppCompatActivity {

    public static List<Bitmap> bitmaps = new ArrayList<>();

    public static void addBitmap(Bitmap bitmap) {
        bitmaps.add(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, bitmaps));
    }

    public void submit(View view) {
        new HttpRequestTask().execute();
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<Bitmap> mBitmaps;

        public ImageAdapter(Context context, List<Bitmap> bitmaps) {
            mContext = context;
            mBitmaps = bitmaps;
        }

        public int getCount() {
            return mBitmaps.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth(), parent.getWidth()));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            // TODO this is a bit ugly... getting images from static arr lol
            imageView.setImageBitmap(mBitmaps.get(position));
            imageView.setOnClickListener(e -> Log.i("total images: ", getCount()+ ""));
            return imageView;
        }
    }

    public static class HttpRequestTask extends AsyncTask<Void, Void, JSONId> {
        @Override
        protected JSONId doInBackground(Void... params) {
            try {
                final String url = "http://192.168.1.116:8888/add_images";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                //bitmaps.get(0).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                //String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                String encodedImage = "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";
                JSONId id = restTemplate.postForObject(url, new JSONImage(encodedImage), JSONId.class);
                return id;
            } catch (Exception e) {
                Log.e("ReviewActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONId id) {
            Log.i("ReviewActivity", "Received id = " + id);
        }

    }
}


