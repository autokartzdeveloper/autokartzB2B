package com.autokartz.autokartz.dialoges;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetImageEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 1/29/2018.
 */

public class ImageZoomerDialog extends Dialog {


    @BindView(R.id.image_zoom_iv)
    ImageView mImageViewIv;
    @BindView(R.id.save_image_tv)
    TextView mSaveBtn;
    @BindView(R.id.delete_image_tv)
    TextView mDeleteBtn;
    private String mPath;
    private GetImageEventListener mGetImageEventListener;

    public ImageZoomerDialog(@NonNull Context context, GetImageEventListener getImageEventListener, String path) {
        super(context);
        mPath = path;
        mGetImageEventListener = getImageEventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_zoomer);
        setTitle("Product Image");
        ButterKnife.bind(this);
        mImageViewIv.setImageBitmap(BitmapFactory.decodeFile(mPath));
    }

    @OnClick({R.id.save_image_tv})
    public void onClickSaveBtn() {
        mGetImageEventListener.isImageSaved(true);
        dismiss();
    }

    @OnClick({R.id.delete_image_tv})
    public void onClickDeleteBtn() {
        mGetImageEventListener.isImageSaved(false);
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

}