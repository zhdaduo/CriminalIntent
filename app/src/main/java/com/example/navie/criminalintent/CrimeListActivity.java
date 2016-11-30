package com.example.navie.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by navie on 2016/11/29.
 */

public class CrimeListActivity extends SingleFragmentActivity{
  @Override protected Fragment createFragment() {
    return new CrimeListFragment();
  }
}
