package com.example.finalproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Model.TransactionModel;
import com.example.finalproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionAdapter extends FirebaseRecyclerAdapter<TransactionModel, TransactionAdapter.TransactionViewHolder>
{

    Context parent;



    public TransactionAdapter(@NonNull FirebaseRecyclerOptions<TransactionModel> options, Context context)
    {
        super(options);
        parent = context;

    }




    @Override
    protected void onBindViewHolder(@NonNull TransactionViewHolder holder, int position, @NonNull TransactionModel model)
    {
        holder.tvWalletName.setText(model.getTransactionName());

        holder.tvCategoryName.setText(model.getTransactionCategory());




        if(model.getTransactionType().equals("Expense"))
        {
            holder.ivTransactionImage.setImageResource(R.drawable.icon_creditcard_et_red_small);
            holder.tvAmount.setText("-"+"$"+model.getTransactionAmount());
        }

        else if(model.getTransactionType().equals("Income"))
        {
            holder.ivTransactionImage.setImageResource(R.drawable.icon_creditcard_et);
            holder.tvAmount.setText("+"+"$"+model.getTransactionAmount());

        }


        if (model.getTransactionBookmarked().equals("1"))
        {
            holder.btnBookmark.setImageResource(R.drawable.icon_bookmark_rv_filled);
        } else {
            holder.btnBookmark.setImageResource(R.drawable.icon_bookmark_rv);
        }

        holder.btnBookmark.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (model.getTransactionBookmarked().equals("0"))
                {
                    holder.btnBookmark.setImageResource(R.drawable.icon_bookmark_rv_filled);
                    Toast.makeText(parent, "Bookmarked" , Toast.LENGTH_SHORT).show();
                    DatabaseReference itemRef = getRef(position);
                    itemRef.child("transactionBookmarked").setValue("1");
                } else
                {
                    holder.btnBookmark.setImageResource(R.drawable.icon_bookmark_rv);
                    Toast.makeText(parent, "Un-Bookmarked" , Toast.LENGTH_SHORT).show();
                    DatabaseReference itemRef = getRef(position); // Get the DatabaseReference for the item
                    itemRef.child("transactionBookmarked").setValue("0");
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatabaseReference currentUserCategoryRef = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Transactions")
                        .child(getRef(position).getKey());
                currentUserCategoryRef.removeValue();
            }
        });





    }



    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent
                .getContext()).inflate(R.layout.layout_recent_transections, parent, false);
        return new TransactionViewHolder(v);
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvWalletName,tvCategoryName,tvAmount;
        ImageView ivTransactionImage,btnDelete,btnBookmark;

        public TransactionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvWalletName  = itemView.findViewById(R.id.tvWalletName);
            tvCategoryName  = itemView.findViewById(R.id.tvCategoryName);
            tvAmount  = itemView.findViewById(R.id.tvAmount);
            ivTransactionImage  = itemView.findViewById(R.id.ivTransactionImage);
            btnDelete  = itemView.findViewById(R.id.btnDelete);
            btnBookmark  = itemView.findViewById(R.id.btnBookmark);

        }


    }
}
