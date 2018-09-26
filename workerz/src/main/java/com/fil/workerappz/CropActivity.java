package com.fil.workerappz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    //    public static Bitmap myBitmp;
    CropImageView mCropView;
    String fileUri = "";
    Button btnDone, btnCancel;
    Uri saveUri;
    ImageView btnRotate;
    ProgressDialog progressDialog;
    String FIleURI, myFileName;
    int widht = 0, height = 0;
    String imgStorepathCp = "";

    public CropActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_act);

        mCropView = (CropImageView) findViewById(R.id.cropImageView);

        btnDone = (Button) findViewById(R.id.btnDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnRotate = (ImageView) findViewById(R.id.btnRotate);


        mCropView.setHandleShowMode(CropImageView.ShowMode.SHOW_ALWAYS);
        mCropView.setGuideShowMode(CropImageView.ShowMode.SHOW_ON_TOUCH);
        mCropView.setAnimationEnabled(true); // shw animation]


        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                fileUri = getIntent().getStringExtra("fileUri");
                widht = Integer.parseInt(getIntent().getStringExtra("width"));
                height = Integer.parseInt(getIntent().getStringExtra("height"));
                imgStorepathCp = getIntent().getStringExtra("imgStrPath");
                myFileName = getIntent().getStringExtra("fileName");
                try {
                    Log.e("!_Crop_Act_DATA ", "File URI :" + fileUri + "\n Width :"
                            + widht + "\nHeight :" + height + "\nFilename :"
                            + myFileName + "\nStore Path :" + imgStorepathCp );
//                            + myFileName + "\nStore Path :" + imgStorepathCp + " ==new==>" + imgStorepath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // as per data we set the CROP
        try {
            //set aspect ratio
            mCropView.setCustomRatio(widht, height);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (fileUri.equalsIgnoreCase("") ||
                fileUri.equalsIgnoreCase(null)) {
        } else {
            LoadImageFIrst();
        }


        btnDone.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnRotate.setOnClickListener(this);

    }

    private void LoadImageFIrst() {

        mCropView.startLoad(Uri.parse(fileUri), new LoadCallback() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess() {
            }


        });
    }

    private void DismissDialog() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void ShowDialog() {
        progressDialog = new ProgressDialog(CropActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Image is Cropping...");
        progressDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v == btnRotate) {

            try {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (v == btnDone) {

            // show dialog
            try {
                ShowDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }



            mCropView.startCrop(saveUri, new CropCallback() {


                        @Override
                        public void onSuccess(Bitmap cropped) {
                            Log.e("!_@_Bitmap : ", cropped + " " + saveUri);
//
//                            if (imgStorepathCp == null) {
//                                imgStorepathCp = imgStorepath;
//                            }
                            File myDir = new File(imgStorepathCp);
                            myDir.mkdirs();
//                            File file = new File(myDir, myFileName + ".jpg");

                            if (myDir.exists())
                                myDir.delete();
                            try {
                                FileOutputStream out = new FileOutputStream(myDir);
                                cropped.compress(Bitmap.CompressFormat.JPEG, 80, out);
                                out.flush();
                                out.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            DismissDialog();
                            Intent intent = new Intent();
//                            intent.putExtra("myFileName", imgStorepathCp + myFileName + ".jpg");
                            intent.putExtra("myFileName", imgStorepathCp);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e("!_@@ onError-- 1 :: ", e.getMessage());
                            finish();
                        }

                    },

                    new SaveCallback() {


                        @Override
                        public void onSuccess(Uri outputUri) {
                            Log.e("!_@@ GEtting URI :: ", " " + outputUri);
                            finish();
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e("!_@@ onError-- 2 :: ", e.getMessage());
                            finish();
                        }

                    }
            );
        } else if (v == btnCancel) {
            finish();
        }
    }
}