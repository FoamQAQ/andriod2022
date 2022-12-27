package com.codenode.findmate.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codenode.findmate.R;
import com.codenode.findmate.databinding.FragmentHomeBinding;

import androidx.fragment.app.Fragment;

public class FriendFragment extends Fragment {

   private FragmentHomeBinding binding;
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      View root = inflater.inflate(R.layout.fragment_friend, container, false);
      return root;

   }
}
