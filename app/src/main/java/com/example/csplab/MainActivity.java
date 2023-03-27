package com.example.csplab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRACTED_FEATURES_FILE = "mlkit_feature_dataset.csv";
    private static final String IMAGESET_FOLDER = "flickrImages/private_1/";
    private static final ImageLabeler imageLabeler = ImageLabeling.getClient(new ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.1f)
                .build());;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //initialize views of the images and the buttons
    private void initViews(){

        //dataset classification/evaluation
        findViewById(R.id.extractLabelsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readImages();
            }
        });

    }

    private void readImages() {
        // Initialized AssetManager to access assets folder
        AssetManager assetManager = getAssets();
        String[] imageFiles = new String[0];
        try {
            imageFiles = assetManager.list(IMAGESET_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> FileList = new LinkedList<String>(Arrays.asList(imageFiles));
        // Initializing BitmapFactory object for handling preprocessed image bitmap
        Bitmap bm = null;
        InputStream is = null;
        StringBuilder labelTable = new StringBuilder();

        for (int i=0; i<FileList.size(); i++){
            try {
                is = assetManager.open(IMAGESET_FOLDER + FileList.
                        get(i));
                if (is!=null) {
                    bm = BitmapFactory.decodeStream(is);
                    if (bm != null) {
                        labelImages(bm, FileList.get(i), labelTable);
                        System.out.print(i+"\n");
                    }
                    else {
                        continue;
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("labelTable: " + labelTable);
        assetManager.close();
    }


    private void labelImages(Bitmap bitmap, String filename, StringBuilder labelTable) {
//        imageLabeler = ImageLabeling.getClient(new ImageLabelerOptions.Builder()
//                .setConfidenceThreshold(0.01f)
//                .build());

        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
        //TextView textView = (TextView)findViewById(R.id.textView1);

        imageLabeler.process(inputImage).addOnSuccessListener(imageLabels -> {
            StringBuilder sb = new StringBuilder();
            sb.append(filename.substring(0, filename.indexOf(".")));
            for (ImageLabel label : imageLabels) {
                sb.append(",").append(label.getText()).append(":").append(label.getConfidence());
            }
            if (imageLabels.isEmpty()) {
                //textView.setText("Could not classify!!");
            } else {
                //System.out.print(sb.toString()+"\n");
                //textView.setText(sb.toString());
                labelTable.append(sb).append("\n");
                Log.i("", sb.toString()+"\n");
            }
        }).addOnFailureListener(e -> e.printStackTrace());
    }
}