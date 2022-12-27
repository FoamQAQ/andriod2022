package com.codenode.findmate.ui.home;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AppFragmentPageAdapter extends FragmentPagerAdapter {
   private final List<Fragment> fragments;

   public AppFragmentPageAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
      super(fm);
      this.fragments = fragments;
   }

   @NonNull
   @Override
   public Fragment getItem(int position) {
      return fragments.get(position);
   }

   @Override
   public int getCount() {
      return fragments.size();
   }

}