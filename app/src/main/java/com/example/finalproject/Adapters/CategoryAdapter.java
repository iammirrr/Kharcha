package com.example.finalproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Model.CategoryModel;
import com.example.finalproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryAdapter extends FirebaseRecyclerAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>
{



    Context parent;

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<CategoryModel> options, Context context) {
        super(options);
        parent = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull CategoryModel model)
    {
        holder.tvCategoryName.setText(model.getCategoryName());
        holder.ivCategoryImage.setImageResource(R.drawable.icon_birefcase_et);



       holder.btnDelete.setOnClickListener(new View.OnClickListener()
      {


            @Override
            public void onClick(View v)
            {
                DatabaseReference currentUserCategoryRef = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Categories")
                        .child(getRef(position).getKey());
                currentUserCategoryRef.removeValue();
            }
        });



    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent
                .getContext()).inflate(R.layout.layout_category, parent, false);
        return new CategoryViewHolder(v);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvCategoryName;
        ImageView btnDelete;
        ImageView ivCategoryImage;

        public CategoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvCategoryName  = itemView.findViewById(R.id.tvCategoryName);
            btnDelete  = itemView.findViewById(R.id.btnDelete);
            ivCategoryImage  = itemView.findViewById(R.id.ivCategoryImage);

        }
    }
}
