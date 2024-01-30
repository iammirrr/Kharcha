package com.example.finalproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;


public class Category extends Fragment
{



    View v;
    public TextView tvSelectedWalletName;
    public ImageView btnSettings;
    public ImageView btnAdd;
    public RecyclerView rvCategory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v =  inflater.inflate(R.layout.fragment_category, container, false);

        tvSelectedWalletName = v.findViewById(R.id.tvSelectedWalletName);
        btnSettings = v.findViewById(R.id.btnSettings);
        btnAdd = v.findViewById(R.id.btnAdd);
        rvCategory = v.findViewById(R.id.rvWallet);

        btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}