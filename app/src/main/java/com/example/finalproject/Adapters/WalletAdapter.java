package com.example.finalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Main;
import com.example.finalproject.Model.WalletModel;
import com.example.finalproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WalletAdapter extends FirebaseRecyclerAdapter<WalletModel, WalletAdapter.WalletViewHolder>
{
    Context parent;




    WalletDataChanger myActivity;
    public WalletAdapter(@NonNull FirebaseRecyclerOptions<WalletModel> options, Context context)
    {
        super(options);
        parent = context;
        myActivity = (WalletDataChanger)context;
    }


    public interface WalletDataChanger
    {
        void OnWalletDataChange();
    }


    @Override
    protected void onBindViewHolder(@NonNull WalletViewHolder holder, int position, @NonNull WalletModel model)
    {
        holder.tvWalletName.setText(model.getWalletName());
        holder.tvWalletAmount.setText("$"+model.getWalletAmount());

        holder.btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatabaseReference currentUserCategoryRef = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Wallets")
                        .child(getRef(position).getKey());
                currentUserCategoryRef.removeValue();
            }
        });

        holder.btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.selectedWalletName = model.getWalletName();
                Main. selectedWalletAmount = Double.parseDouble(model.getWalletAmount());
                myActivity.OnWalletDataChange();
                // Update the selectedWalletAmount field
               // selectedWalletAmount = walletAmount;
            }
        });

    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent
                .getContext()).inflate(R.layout.layout_wallet, parent, false);
        return new WalletViewHolder(v);
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvWalletAmount, tvWalletName;
        ImageView btnSelected, btnDelete;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWalletName  = itemView.findViewById(R.id.tvWalletName);
            tvWalletAmount = itemView.findViewById(R.id.tvWalletAmount);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnSelected = itemView.findViewById(R.id.btnSelected);
        }
    }
}
