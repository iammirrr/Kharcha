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


public class Bookmark extends Fragment
{
    View v;
    public TextView tvSelectedWalletName;
    public TextView tvSelectedWalletAmount;
    public ImageView btnSettings;

    public RecyclerView rvRecentTransactions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_bookmark, container, false);

        tvSelectedWalletAmount = v.findViewById(R.id.tvSelectedWalletAmount);
        rvRecentTransactions = v.findViewById(R.id.rvRecentTransactions);
        tvSelectedWalletName = v.findViewById(R.id.tvSelectedWalletName);
        btnSettings = v.findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });



        return v;
    }
}