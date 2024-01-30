    package com.example.finalproject;

    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.FrameLayout;
    import android.widget.ImageView;
    import android.widget.RadioGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.finalproject.Adapters.CategoryAdapter;
    import com.example.finalproject.Adapters.TransactionAdapter;
    import com.example.finalproject.Adapters.WalletAdapter;
    import com.example.finalproject.Fragments.Bookmark;
    import com.example.finalproject.Fragments.Category;
    import com.example.finalproject.Fragments.Home;
    import com.example.finalproject.Fragments.Wallet;
    import com.example.finalproject.Model.CategoryModel;
    import com.example.finalproject.Model.TransactionModel;
    import com.example.finalproject.Model.WalletModel;
    import com.firebase.ui.database.FirebaseRecyclerOptions;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.navigation.NavigationBarView;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.Query;
    import com.google.firebase.database.ValueEventListener;

    import java.util.HashMap;
    import java.util.Objects;

    public class Main extends AppCompatActivity implements WalletAdapter.WalletDataChanger
    {

        public static String selectedWalletName;
        public static double selectedWalletAmount;




        int itemId;
        BottomNavigationView bottomNavigationView;
        FrameLayout fragmentContainer;

        Home homeFragment = new Home();

        Category categoryFragment = new Category();
        Bookmark bookmarkFragment = new Bookmark();
        Wallet walletFragment = new Wallet();

        Dialog dialogSettings;
        Dialog dialogNewTransaction;
        Dialog dialogNewWallet;
        Dialog dialogNewCategory;



        RecyclerView.LayoutManager manager;
        CategoryAdapter categoryAdapter;
        WalletAdapter walletAdapter;
        TransactionAdapter transactionAdapter;


        @Override
        protected void onStart()
        {
            super.onStart();

            HandleHomeFragment();
            HandleCategoryFragment();
            HandleWalletFragment();
            HandleBookmarkFragment();

            SetDefaultWallet();
            OnWalletDataChange();

            categoryAdapter.startListening();
            walletAdapter.startListening();
            transactionAdapter.startListening();
        }


        @Override
        protected void onStop()
        {
            super.onStop();
            if(categoryAdapter != null)
            {
                categoryAdapter.stopListening();
            }

            if(walletAdapter != null)
            {
                walletAdapter.stopListening();
            }

            if(transactionAdapter != null)
            {
                transactionAdapter.stopListening();
            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



            BottomNavigation();

            // Add the fragments only once
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, homeFragment, "homeFragment")
                    .add(R.id.fragmentContainer, categoryFragment, "categoryFragment")
                    .add(R.id.fragmentContainer, walletFragment, "walletFragment")
                    .add(R.id.fragmentContainer, bookmarkFragment, "bookmarkFragment")
                    .hide(categoryFragment)
                    .hide(walletFragment)
                    .hide(bookmarkFragment)
                    .commit();
        }


        public void BottomNavigation() {

            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            fragmentContainer = findViewById(R.id.fragmentContainer);

            // Initially show the home fragment
            getSupportFragmentManager().beginTransaction().show(homeFragment).commit();

            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    itemId = item.getItemId();

                    if (itemId == R.id.navHome) {
                        getSupportFragmentManager().beginTransaction().show(homeFragment).hide(categoryFragment).hide(walletFragment).hide(bookmarkFragment).commit();
                        return true;
                    } else if (itemId == R.id.navCategory) {
                        getSupportFragmentManager().beginTransaction().hide(homeFragment).show(categoryFragment).hide(walletFragment).hide(bookmarkFragment).commit();
                        return true;
                    } else if (itemId == R.id.navWallet) {
                        getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(categoryFragment).show(walletFragment).hide(bookmarkFragment).commit();
                        return true;
                    } else if (itemId == R.id.navBookmark) {
                        getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(categoryFragment).hide(walletFragment).show(bookmarkFragment).commit();
                        return true;
                    } else {
                        getSupportFragmentManager().beginTransaction().show(homeFragment).hide(categoryFragment).hide(walletFragment).hide(bookmarkFragment).commit();
                        return true;
                    }
                }
            });
        }

        public Dialog MakeDialog(Dialog newDialog, int layout_id)
        {
            newDialog = new Dialog(Main.this);
            newDialog.setContentView(layout_id);
            newDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            newDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
            newDialog.setCancelable(true);
            return newDialog;
        }

        public void HandleSettings(int fragId)
        {

            ImageView btnSettings;

            if (fragId == 1 &&homeFragment.isAdded())
            {
                btnSettings = homeFragment.btnSettings;
                dialogSettings = MakeDialog(dialogSettings, R.layout.dialog_settings);

                btnSettings.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        SetupDialogButtons(dialogSettings);
                        dialogSettings.show();
                    }
                });
            }
            else if (fragId == 2 && categoryFragment.isAdded())
            {
                btnSettings = categoryFragment.btnSettings;
                dialogSettings = MakeDialog(dialogSettings, R.layout.dialog_settings);

                btnSettings.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        SetupDialogButtons(dialogSettings);
                        dialogSettings.show();
                    }
                });

            }
            else if (fragId == 3 && walletFragment.isAdded())
            {
                btnSettings = walletFragment.btnSettings;
                dialogSettings = MakeDialog(dialogSettings, R.layout.dialog_settings);

                btnSettings.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        SetupDialogButtons(dialogSettings);
                        dialogSettings.show();
                    }
                });

            }
            else if (fragId == 4 && bookmarkFragment.isAdded())
            {
                btnSettings = bookmarkFragment.btnSettings;
                dialogSettings = MakeDialog(dialogSettings, R.layout.dialog_settings);

                btnSettings.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        SetupDialogButtons(dialogSettings);
                        dialogSettings.show();
                    }
                });

            }
            else {
                return;
            }




        }

        private void SetupDialogButtons(Dialog dialogSettings)
        {
            ImageView btnPrivacy = dialogSettings.findViewById(R.id.btnPrivacy);
            ImageView btnWebsite = dialogSettings.findViewById(R.id.btnWebsite);
            ImageView btnLogout = dialogSettings.findViewById(R.id.btnLogout);
            ImageView btnBack = dialogSettings.findViewById(R.id.btnBack);

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogSettings.dismiss();
                }
            });

            btnPrivacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Main.this, PrivacyPolicy.class);
                    startActivity(i);
                    finish();
                }
            });

            btnWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iammirrr.com"));
                    startActivity(intent);
                }
            });

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    FirebaseAuth.getInstance().signOut();


                    SharedPreferences sharedPreferences = getSharedPreferences("walletPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit().clear();
                    editor.apply();

                    Toast.makeText(Main.this, "Sign-out Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Main.this, Welcome.class);
                    startActivity(i);
                    finish();
                }
            });
        }

        public void HandleAdd(int fragId) {
            ImageView btnAdd;
            Dialog dialog;

            switch (fragId) {
                case 1:
                    btnAdd = homeFragment.btnAdd;
                    dialog = MakeDialog(dialogNewTransaction, R.layout.dialog_add_transection);
                    btnAdd.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            dialog.show();

                            TextView tvWalletName = dialog.findViewById(R.id.tvWalletName);

                            ImageView btnAdd = dialog.findViewById(R.id.btnAdd);
                            ImageView btnClose = dialog.findViewById(R.id.btnClose);

                            TextInputEditText etTransactionAmount = dialog.findViewById(R.id.etTransactionAmount);

                            TextInputEditText etNote = dialog.findViewById(R.id.etNote);
                            TextInputEditText etCategory = dialog.findViewById(R.id.etCategory);
                            RadioGroup radioButtons = dialog.findViewById(R.id.radioButtons);

                            tvWalletName.setText(selectedWalletName);
                            final int[] radioTransactionType = {0};
                            radioButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                            {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId)
                                {
                                    if(checkedId == R.id.btnExpense)
                                    {
                                        radioTransactionType[0] = 0;
                                    }
                                    else if(checkedId == R.id.btnIncome)
                                    {
                                        radioTransactionType[0] = 1;
                                    }

                                }
                            });


                            btnAdd.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    String TransactionAmount = Objects.requireNonNull(etTransactionAmount.getText()).toString().trim();
                                    String WalletName = Objects.requireNonNull(selectedWalletName);
                                    String Note = Objects.requireNonNull(etNote.getText()).toString().trim();
                                    String Category = Objects.requireNonNull(etCategory.getText()).toString().trim();

                                    String TransactionType = "Expense";
                                    if(radioTransactionType[0] == 0)
                                    {
                                        TransactionType = "Expense";
                                    }
                                    else if (radioTransactionType[0] == 1)
                                    {
                                        TransactionType = "Income";
                                    }


                                    String TransactionBookmarked = "0";

                                    HashMap<String,Object> data = new HashMap<>();
                                    data.put("transactionAmount",TransactionAmount);
                                    data.put("transactionName",WalletName);
                                    data.put("transactionNote",Note);
                                    data.put("transactionCategory",Category);
                                    data.put("transactionType",TransactionType);
                                    data.put("transactionBookmarked",TransactionBookmarked);
                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                    reference.child(firebaseUser.getUid())
                                            .child("Transactions")
                                            .push()
                                            .setValue(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Main.this, "New Transaction Added", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {
                                                    Toast.makeText(Main.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                    Double newWalletAmount = 0.0;

                                    if(TransactionType.equals("Expense")) {
                                        newWalletAmount = selectedWalletAmount - Double.valueOf(TransactionAmount);
                                    } else if (TransactionType.equals("Income")) {
                                        newWalletAmount = selectedWalletAmount + Double.valueOf(TransactionAmount);
                                    }

                                    selectedWalletAmount = newWalletAmount;

                                    HashMap<String, Object> walletUpdate = new HashMap<>();
                                    walletUpdate.put("walletName", WalletName);
                                    walletUpdate.put("walletAmount", String.valueOf(selectedWalletAmount));
                                    DatabaseReference walletRef = FirebaseDatabase.getInstance()
                                            .getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Wallets");
                                    Query walletQuery = walletRef.orderByChild("walletName").equalTo(WalletName);

                                    walletQuery.addListenerForSingleValueEvent(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                            {
                                                snapshot.getRef().updateChildren(walletUpdate);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError)
                                        {
                                            // Handle errors here
                                        }
                                    });


                                    OnWalletDataChange();

                                    tvWalletName.setText(null);
                                    etTransactionAmount.setText(null);
                                    etNote.setText(null);
                                    etCategory.setText(null);
                                    tvWalletName.setText(null);

                                    dialog.dismiss();

                                }
                            });

                            btnClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {
                                    tvWalletName.setText(null);
                                    etTransactionAmount.setText(null);
                                    etNote.setText(null);
                                    etCategory.setText(null);
                                    tvWalletName.setText(null);

                                    dialog.dismiss();
                                }
                            });




                        }
                    });

                    break;
                case 2:
                    btnAdd = categoryFragment.btnAdd;
                    dialog = MakeDialog(dialogNewCategory, R.layout.dialog_add_category);
                    btnAdd.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            dialog.show();

                            ImageView btnAdd = dialog.findViewById(R.id.btnAdd);
                            TextInputEditText etCategoryName = dialog.findViewById(R.id.etCategoryName);
                            ImageView btnClose = dialog.findViewById(R.id.btnClose);

                            btnAdd.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {


                                    String CategoryName = Objects.requireNonNull(etCategoryName.getText()).toString().trim();

                                    HashMap<String,Object> data = new HashMap<>();
                                    data.put("categoryName",CategoryName);


                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                    reference.child(firebaseUser.getUid())
                                            .child("Categories").push().setValue(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Main.this, "New Category Added", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {
                                                    Toast.makeText(Main.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    etCategoryName.setText(null);
                                    dialog.dismiss();
                                }
                            });

                            btnClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    etCategoryName.setText(null);
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    break;
                case 3:
                    btnAdd = walletFragment.btnAdd;
                    dialog = MakeDialog(dialogNewWallet, R.layout.dialog_add_wallet);
                    btnAdd.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            dialog.show();

                            ImageView btnAdd = dialog.findViewById(R.id.btnAdd);
                            TextInputEditText etWalletName = dialog.findViewById(R.id.etWalletName);
                            TextInputEditText etWalletAmount = dialog.findViewById(R.id.etWalletAmount);
                            ImageView btnClose = dialog.findViewById(R.id.btnClose);

                            btnAdd.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {


                                    String WalletName = Objects.requireNonNull(etWalletName.getText()).toString().trim();
                                    String WalletAmount = Objects.requireNonNull(etWalletAmount.getText()).toString().trim();

                                    HashMap<String,Object> data = new HashMap<>();
                                    data.put("walletName",WalletName);
                                    data.put("walletAmount",WalletAmount);


                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                    reference.child(firebaseUser.getUid())
                                            .child("Wallets").push().setValue(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Main.this, "New Wallet Added", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {
                                                    Toast.makeText(Main.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                     etWalletName.setText(null);
                                     etWalletAmount.setText(null);
                                    dialog.dismiss();
                                }
                            });

                            btnClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    etWalletName.setText(null);
                                    etWalletAmount.setText(null);
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    break;
                default:
                    return;
            }


        }

        public void HandleHomeFragment()
        {
            HandleSettings(1);
            HandleAdd(1);

            Query query = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Transactions");

            FirebaseRecyclerOptions<TransactionModel> options =
                    new FirebaseRecyclerOptions.Builder<TransactionModel>()
                            .setQuery(query, TransactionModel.class)
                            .build();

            RecyclerView transactionRecycler;

            transactionRecycler = homeFragment.rvRecentTransactions;;
            manager = new LinearLayoutManager(this);
            transactionRecycler.setLayoutManager(manager);


            transactionAdapter = new TransactionAdapter(options,this);
            transactionRecycler.setAdapter(transactionAdapter);
            transactionAdapter.startListening();
        }

        public void HandleCategoryFragment()
        {

            HandleSettings(2);
            HandleAdd(2);


            Query query = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Categories");

            FirebaseRecyclerOptions<CategoryModel> options =
                    new FirebaseRecyclerOptions.Builder<CategoryModel>()
                            .setQuery(query, CategoryModel.class)
                            .build();

            RecyclerView categoryRecycler;

            categoryRecycler = categoryFragment.rvCategory;;
            manager = new LinearLayoutManager(this);
            categoryRecycler.setLayoutManager(manager);

            categoryAdapter = new CategoryAdapter(options,this);
            categoryRecycler.setAdapter(categoryAdapter);
            categoryAdapter.startListening();
        }


        public void HandleWalletFragment()
        {

            HandleSettings(3);
            HandleAdd(3);


            Query query = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Wallets");

            FirebaseRecyclerOptions<WalletModel> options =
                    new FirebaseRecyclerOptions.Builder<WalletModel>()
                            .setQuery(query, WalletModel.class)
                            .build();

            RecyclerView walletRecycler;

            walletRecycler = walletFragment.rvWallet;;
            manager = new LinearLayoutManager(this);
            walletRecycler.setLayoutManager(manager);


            walletAdapter = new WalletAdapter(options,this);
            walletRecycler.setAdapter(walletAdapter);
            walletAdapter.startListening();




        }

        public void HandleBookmarkFragment()
        {

            HandleSettings(4);

            Query query = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Transactions")
                    .orderByChild("transactionBookmarked")
                    .equalTo("1");

            FirebaseRecyclerOptions<TransactionModel> options =
                    new FirebaseRecyclerOptions.Builder<TransactionModel>()
                            .setQuery(query, TransactionModel.class)
                            .build();

            RecyclerView bookmarkRecycler;

            bookmarkRecycler = bookmarkFragment.rvRecentTransactions;;
            manager = new LinearLayoutManager(this);
            bookmarkRecycler.setLayoutManager(manager);

            transactionAdapter = new TransactionAdapter(options,this);
            bookmarkRecycler.setAdapter(transactionAdapter);
            transactionAdapter.startListening();
        }



        public void SetDefaultWallet()
        {
            // To retrieve data
            SharedPreferences sharedPreferences = getSharedPreferences("walletPrefs", Context.MODE_PRIVATE);
            selectedWalletName = sharedPreferences.getString("selectedWalletName", "Wallet");
            selectedWalletAmount = sharedPreferences.getFloat("selectedWalletAmount", 0.0f);
        }

        @Override
        public void OnWalletDataChange()
        {
            walletFragment.tvSelectedWalletName.setText(selectedWalletName);
            walletFragment.tvSelectedWalletAmount.setText("$"+String.valueOf(selectedWalletAmount));

            homeFragment.tvSelectedWalletName.setText(selectedWalletName);
            homeFragment.tvSelectedWalletAmount.setText("$"+String.valueOf(selectedWalletAmount));

            bookmarkFragment.tvSelectedWalletName.setText(selectedWalletName);
            bookmarkFragment.tvSelectedWalletAmount.setText("$"+String.valueOf(selectedWalletAmount));

            categoryFragment.tvSelectedWalletName.setText(selectedWalletName);


            SharedPreferences sharedPreferences = getSharedPreferences("walletPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selectedWalletName", selectedWalletName);
            editor.putFloat("selectedWalletAmount", (float) selectedWalletAmount);
            editor.apply();
        }
    }