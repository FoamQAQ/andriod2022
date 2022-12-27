package com.codenode.findmate.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codenode.findmate.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private List<Msg> msgList = new ArrayList<>();
    private EditText title;
    private EditText message;
    private Button send;
    private String titles;
    private String deses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        SharedPreferences sharedPref = getContext().getSharedPreferences("itemList", Context.MODE_PRIVATE);
        String titleList = sharedPref.getString("titleList","").toString();
        String desList = sharedPref.getString("desList","").toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        title = binding.title;
        message = binding.message;
        send = binding.post;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titles = titleList+title.getText().toString()+",";
                deses = desList+message.getText().toString()+",";
                editor.putString("titleList", titles);
                editor.putString("desList", deses);
                editor.commit();
                Toast.makeText(getContext(),"Submit success",Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}