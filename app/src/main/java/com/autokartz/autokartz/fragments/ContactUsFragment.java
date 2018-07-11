package com.autokartz.autokartz.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.autokartz.autokartz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 1/29/2018.
 */

public class ContactUsFragment extends Fragment {
    @BindView(R.id.linear_layout_phone)
    LinearLayout mPhoneLayout;
    @BindView(R.id.linear_layout_email)
    LinearLayout mEmailLauout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Contact us");

    }

    @OnClick({R.id.linear_layout_phone})
    public void onClickOpenPhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:9707997079"));
        startActivity(callIntent);

    }

    @OnClick({R.id.linear_layout_email})
    public void onClickOpenGmail() {
        String recepientEmail = "info@autokartz.com"; // either set to destination email or leave empty
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + recepientEmail));
        startActivity(intent);

    }


}

