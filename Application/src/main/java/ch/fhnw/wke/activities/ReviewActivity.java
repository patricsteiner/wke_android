package ch.fhnw.wke.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.wke.R;
import ch.fhnw.wke.tasks.ImageAdderRestCall;
import ch.fhnw.wke.tasks.TransferLearningRestCall;
import ch.fhnw.wke.util.Data;

public class ReviewActivity extends AppCompatActivity {

    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        imageAdapter = new ImageAdapter(this, Data.imagesToBeAdded);
        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(imageAdapter);
    }

    public void submit(View view) {
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        ImageAdderRestCall imageAdderRestCall = new ImageAdderRestCall(Data.workpieceIdData);
        imageAdderRestCall.setOnPostExecuteAction(workpieceIdData -> {
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to add more pictures of this workpiece?")
                    .setPositiveButton(android.R.string.yes, (dialogInterface, x) -> {
                        Data.workpieceIdData = workpieceIdData;
                        cleanupAndFinish();
                    })
                    .setNegativeButton(android.R.string.no, (dialogInterface, i) -> {
                        Data.workpieceIdData = null;
                        Data.imagesAdded = 0;
                        toast.setText("Initiating Transfer Learning");
                        toast.show();
                        TransferLearningRestCall transferLearningRestCall = new TransferLearningRestCall();
                        transferLearningRestCall.execute(); // initiate the transfer learning after all images were added
                        cleanupAndFinish();
                    })
                    .show();
        });
        imageAdderRestCall.setOnProgressUpdateAction(progress -> {
            toast.setText(progress + "% uploaded");
            toast.show();
        });
        imageAdderRestCall.execute(Data.imagesToBeAdded.toArray(new Bitmap[Data.imagesToBeAdded.size()]));
    }

    public void abort(View view) {
        cleanupAndFinish();
    }

    private void cleanupAndFinish() {
        Data.imagesToBeAdded.clear();
        imageAdapter.bitmaps.clear();
        for (ImageView imageView : imageAdapter.imageViews) {
            imageView.setImageBitmap(null);
        }
        imageAdapter.imageViews.clear();
        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(null);
        imageAdapter = null;
        finish();
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private List<Bitmap> bitmaps;
        private List<ImageView> imageViews = new ArrayList<>();

        public ImageAdapter(Context context, List<Bitmap> bitmaps) {
            this.context = context;
            this.bitmaps = bitmaps;
        }

        @Override
        public int getCount() {
            return bitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        @Override
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
            imageViews.add(imageView);
            return imageView;
        }
    }

}


