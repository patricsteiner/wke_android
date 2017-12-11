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

import java.util.List;

import ch.fhnw.wke.R;
import ch.fhnw.wke.tasks.ImageAdderRestCall;
import ch.fhnw.wke.util.Data;

public class ReviewActivity extends AppCompatActivity {

    private ImageAdapter mImageAdapter = new ImageAdapter(this, Data.imagesToBeAdded);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(mImageAdapter);

    }

    public void submit(View view) {
        ImageAdderRestCall imageAdderRestCall = new ImageAdderRestCall();
        imageAdderRestCall.setOnPostExecuteAction((Void) -> finish());
        imageAdderRestCall.execute(Data.imagesToBeAdded.toArray(new Bitmap[Data.imagesToBeAdded.size()])); // TODO show spinner or sth...
    }

    public void abort(View view) {
        finish();
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private List<Bitmap> bitmaps;

        public ImageAdapter(Context context, List<Bitmap> bitmaps) {
            this.context = context;
            this.bitmaps = bitmaps;
        }

        public int getCount() {
            return bitmaps.size();
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
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth(), parent.getWidth()));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(bitmaps.get(position));
            imageView.setOnClickListener(e -> Log.i("total images: ", getCount() + ""));
            return imageView;
        }
    }

}


