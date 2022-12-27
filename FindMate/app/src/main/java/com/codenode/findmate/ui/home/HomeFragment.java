package com.codenode.findmate.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.codenode.findmate.DetailActivity;
import com.codenode.findmate.R;
import com.codenode.findmate.adapter.Item;
import com.codenode.findmate.adapter.ItemAdapter;
import com.codenode.findmate.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Context context;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        context = getContext();
        //binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter(context);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setItemClickListener(new ItemAdapter.ItemClickListener() {
            @Override
            public void toggleAllow(Item item) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
       /* List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("test1","des1"));
        itemList.add(new Item("test2","des2"));
        itemAdapter.setData(itemList);*/
        List<Item> itemList = new ArrayList<>();
        SharedPreferences sharedPref = context.getSharedPreferences("itemList", Context.MODE_PRIVATE);
        String[] titleList = sharedPref.getString("titleList","").split(",");
        String[] desList = sharedPref.getString("desList","").split(",");
        Log.i("11111",titleList.toString());
        if(titleList.length>0){
            for(int i=0;i< titleList.length;i++){
                itemList.add(new Item(titleList[i],desList[i]));
            }
            itemAdapter.setData(itemList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}