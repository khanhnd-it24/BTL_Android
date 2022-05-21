
package com.example.appfoodorder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appfoodorder.R;
import com.example.appfoodorder.ShowDetailActivity;
import com.example.appfoodorder.model.Category;
import com.example.appfoodorder.model.Food;
import com.example.appfoodorder.viewholder.CategoryViewHolder;
import com.example.appfoodorder.viewholder.PopularViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView nameOfUser;
    ImageView avatarUser;
    EditText searchEditText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseRecyclerAdapter adapter, adapter2, adapter3;
    private  RecyclerView recycleViewCategoryList, recyclerViewPopularList, recyclerViewFoodList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameOfUser = view.findViewById(R.id.textView4);
        avatarUser = view.findViewById(R.id.imageView3);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if(signInAccount != null){
            List<String> names = Arrays.asList(signInAccount.getDisplayName().split(" "));
            nameOfUser.setText("Hi " + names.get(names.size() - 1));
//            avatarUser.setImageURI(signInAccount.getPhotoUrl());
        }
        recycleViewCategory(view);
        recycleViewPopular(view);
        recycleViewFood(view);

        searchEditText = view.findViewById(R.id.editTextTextPersonName);
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchText = searchEditText.getText().toString();
                    firebaseSearchFood(view,searchText);
                    return true;
                }
                return false;
            }
        });
    }

    private void recycleViewCategory(@NonNull View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recycleViewCategoryList = view.findViewById(R.id.recyclerView2);
        recycleViewCategoryList.setLayoutManager(linearLayoutManager);

        Query query = FirebaseDatabase.getInstance().getReference("categories");

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Category model) {
                holder.categoryName.setText(model.getTitle());
                String picUrl = "";
                switch (position){
                    case 0: {
                        picUrl = "cat_1";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background1));
                        break;
                    }
                    case 1: {
                        picUrl = "cat_2";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background2));
                        break;
                    }
                    case 2: {
                        picUrl = "cat_3";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background3));
                        break;
                    }
                    case 3: {
                        picUrl = "cat_4";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background4));
                        break;
                    }
                    case 4: {
                        picUrl = "cat_5";
                        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background5));
                        break;
                    }
                }
                int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());
                Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .into(holder.categoryPic);
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
                return new CategoryViewHolder(inflate);
            }
        };
        adapter.startListening();
        recycleViewCategoryList.setAdapter(adapter);
    }

    private void recycleViewPopular(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = view.findViewById(R.id.recyclerView);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        Query query = FirebaseDatabase.getInstance().getReference("foods").orderByChild("popular").equalTo(true);

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();

        adapter2 = new FirebaseRecyclerAdapter<Food, PopularViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PopularViewHolder holder, int position, @NonNull Food model) {
                holder.title.setText(model.getTitle());
                holder.fee.setText(String.valueOf(model.getPrice()));

                int drawableResourceId = holder.itemView.getContext()
                        .getResources()
                        .getIdentifier(
                                model.getPic(),
                                "drawable",
                                holder.itemView.getContext().getPackageName()
                        );

                Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .into(holder.pic);

                holder.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                        intent.putExtra("object", model);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
                return new PopularViewHolder(inflate);
            }
        };

        adapter2.startListening();
        recyclerViewPopularList.setAdapter(adapter2);
    }

    private void recycleViewFood(View view) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewFoodList = view.findViewById(R.id.recyclerView3);
        recyclerViewFoodList.setLayoutManager(gridLayoutManager);

        Query query = FirebaseDatabase.getInstance().getReference("foods");

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();

        adapter3 = new FirebaseRecyclerAdapter<Food, PopularViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PopularViewHolder holder, int position, @NonNull Food model) {
                holder.title.setText(model.getTitle());
                holder.fee.setText(String.valueOf(model.getPrice()));

                int drawableResourceId = holder.itemView.getContext()
                        .getResources()
                        .getIdentifier(
                                model.getPic(),
                                "drawable",
                                holder.itemView.getContext().getPackageName()
                        );

                Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .into(holder.pic);

                holder.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                        intent.putExtra("object", model);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
                return new PopularViewHolder(inflate);
            }
        };

        adapter3.startListening();
        recyclerViewFoodList.setAdapter(adapter3);
    }


    private void firebaseSearchFood(View view, String textSearch) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewFoodList = view.findViewById(R.id.recyclerView3);
        recyclerViewFoodList.setLayoutManager(gridLayoutManager);

        Query query = FirebaseDatabase.getInstance().getReference("foods")
                .orderByChild("title")
                .startAt(textSearch)
                .endAt(textSearch + "\uf8ff");;

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();

        adapter3 = new FirebaseRecyclerAdapter<Food, PopularViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PopularViewHolder holder, int position, @NonNull Food model) {
                holder.title.setText(model.getTitle());
                holder.fee.setText(String.valueOf(model.getPrice()));

                int drawableResourceId = holder.itemView.getContext()
                        .getResources()
                        .getIdentifier(
                                model.getPic(),
                                "drawable",
                                holder.itemView.getContext().getPackageName()
                        );

                Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .into(holder.pic);

                holder.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                        intent.putExtra("object", model);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
                return new PopularViewHolder(inflate);
            }
        };

        adapter3.startListening();
        recyclerViewFoodList.setAdapter(adapter3);
    }
}