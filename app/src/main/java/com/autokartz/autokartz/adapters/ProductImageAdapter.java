package com.autokartz.autokartz.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.dialoges.ImageZoomerDialog;
import com.autokartz.autokartz.interfaces.GetImageEventListener;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.utils.util.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cortana on 1/17/2018.
 */

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ProductImageHolder> {


    private Activity mContext;
    private AccountDetailHolder mAccountDetailHolder;
    private static ArrayList<String> mImageList;
    private static int currentPosition;
    private Bitmap bitmap;

    public ProductImageAdapter(Activity context) {
        mContext = context;
        mImageList = new ArrayList<>();

    }

    @Override
    public ProductImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_image_layout, parent, false);
        return new ProductImageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductImageHolder holder, int position) {
        if (position == (mImageList.size() - 1)) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_plus);
        } else {
            bitmap = BitmapFactory.decodeFile(mImageList.get(position));
        }
        holder.productImageIv.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public void setImageList(ArrayList<String> imageList) {
        mImageList.clear();
        mImageList.addAll(imageList);
        mImageList.add("");
    }

    public ArrayList<String> getImageList() {
        mImageList.remove(mImageList.size() - 1);
        return mImageList;
    }

    public class ProductImageHolder extends RecyclerView.ViewHolder implements GetImageEventListener {


        @BindView(R.id.product_image_iv)
        ImageView productImageIv;

        public ProductImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.product_image_iv})
        public void onClickImage() {
            int position = getAdapterPosition();
            currentPosition = position;
            if (position == (mImageList.size() - 1)) {
                //todo open imgechooser either from camera or gallery.
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Logger.LogDebug("", ex.getMessage());
                }
                if (photoFile != null) {
                    //Uri photoUri= FileProvider.getUriForFile(mContext,"com.example.android.fileprovider",photoFile);
                    cameraIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, Uri.fromFile(photoFile));
                }
                Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Product Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
                mContext.startActivityForResult(chooserIntent, 103);

            } else {
                // todo display image in large size.
                ImageZoomerDialog imageZoomerDialog = new ImageZoomerDialog(mContext, this, mImageList.get(position));
                imageZoomerDialog.show();
            }
        }

        private File createImageFile() throws IOException {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            String currentPhotoPath = "file:" + image.getAbsolutePath();
            return image;
        }

        @Override
        public void isImageSaved(boolean isSaved) {
            if (!isSaved) {
                mImageList.remove(currentPosition);
                notifyDataSetChanged();
            }
        }

    }

}