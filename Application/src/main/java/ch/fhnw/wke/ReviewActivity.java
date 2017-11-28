package ch.fhnw.wke;

import android.content.Context;
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

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    public static List<Bitmap> bitmaps = new ArrayList<>();
    private ImageAdapter mImageAdapter = new ImageAdapter(this, bitmaps);

    public static void addBitmap(Bitmap bitmap) {
        bitmaps.add(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(mImageAdapter);

    }

    public void submit(View view) {
        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute(); // TODO show spinner or sth...
        finish();
    }

    public void abort(View view) {
        finish();
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

    public static class HttpRequestTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final String baseUrl = "http://192.168.1.116:8888/";
                final String newWorkpieceIdUrl = baseUrl + "new_workpiece_id";
                final String addWorkpieceImageUrl = baseUrl + "add_workpiece_image";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                JSONWorkpieceId jsonWorkpieceId = restTemplate.getForObject(newWorkpieceIdUrl, JSONWorkpieceId.class);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                for (int i = 0; i < bitmaps.size(); i++) {
                    Bitmap bitmap = bitmaps.get(i);
                    if (bitmap == null) continue;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, byteArrayOutputStream);
                    String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    byteArrayOutputStream.reset();
                    restTemplate.postForObject(addWorkpieceImageUrl, new JSONImage(i, jsonWorkpieceId.getWorkpieceId(), image), JSONWorkpieceId.class);
                }
            } catch (Exception e) {
                Log.e("ReviewActivity", e.getMessage(), e);
            }
            return null;
        }
    }
}


