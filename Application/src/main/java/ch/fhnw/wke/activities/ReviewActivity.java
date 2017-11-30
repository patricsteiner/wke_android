package ch.fhnw.wke.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.wke.R;
import ch.fhnw.wke.tasks.ImageAdderTask;

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
        ImageAdderTask imageAdderTask = new ImageAdderTask();
        imageAdderTask.execute(); // TODO show spinner or sth...
        imageAdderTask.setOnPostExecuteAction((Void) -> finish());
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
            imageView.setOnClickListener(e -> Log.i("total images: ", getCount() + ""));
            return imageView;
        }
    }

}


