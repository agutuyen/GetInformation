package com.tmtuyen.minhtuyen.getinformation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.vlk.multimager.activities.BaseActivity;
import com.vlk.multimager.activities.GalleryActivity;
import com.vlk.multimager.activities.MultiCameraActivity;
import com.vlk.multimager.adapters.GalleryImagesAdapter;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;
import com.vlk.multimager.utils.Utils;

import java.util.ArrayList;


/**
 * Created by vansikrishna on 08/06/2016.
 */
public class CameraActivity extends BaseActivity {

    RecyclerView recyclerView;
    LinearLayout parentLayout;
    int selectedColor= Color.parseColor("#2196F3");
    Button multiCaptureButton;
    Button multiPickerButton;
    Button btnXacNhan;
    ArrayList<Image> imagesList;
    GalleryImagesAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imagesList=new ArrayList<>();
        initRecyclerView();
        parentLayout=(LinearLayout)findViewById(R.id.parentLayout);
        multiCaptureButton = (Button) findViewById(R.id.multiCaptureButton);
        multiCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.hasCameraHardware(CameraActivity.this))
                    initiateMultiCapture();
                else
                    Utils.showLongSnack(parentLayout, "Sorry. Your device does not have a camera.");
            }
        });
        multiPickerButton = (Button) findViewById(R.id.multiPickerButton);
        multiPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateMultiPicker();
            }
        });
        btnXacNhan=(Button)findViewById(R.id.btnXacNhan);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putParcelableArrayListExtra("album", imagesList);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.TYPE_MULTI_CAPTURE:
                handleResponseIntent(intent);
                break;
            case Constants.TYPE_MULTI_PICKER:
                handleResponseIntent(intent);
                break;
        }
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float thumbnailDpWidth = getResources().getDimension(R.dimen.thumbnail_width) / displayMetrics.density;
        return (int) (dpWidth / thumbnailDpWidth);
    }

    private void handleResponseIntent(Intent intent) {
        ArrayList<Image> imgList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
        for(int i=0;i<imgList.size();i++)
            imagesList.add(imgList.get(i));
        imageAdapter.notifyDataSetChanged();
    }

    private void initiateMultiCapture() {
        Intent intent = new Intent(this, MultiCameraActivity.class);
        Params params = new Params();
        params.setCaptureLimit(10);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_CAPTURE);
    }

    private void initiateMultiPicker() {
        Intent intent = new Intent(this, GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(10);
        params.setPickerLimit(10);
        params.setToolbarColor(selectedColor);
        params.setActionButtonColor(selectedColor);
        params.setButtonTextColor(selectedColor);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
    }
    private void initRecyclerView(){
        imagesList=getIntent().getParcelableArrayListExtra("album");
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getColumnCount(), GridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(mLayoutManager);
        imageAdapter = new GalleryImagesAdapter(this, imagesList, getColumnCount(), new Params());
        recyclerView.setAdapter(imageAdapter);
    }
}