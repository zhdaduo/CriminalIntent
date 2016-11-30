package com.example.navie.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.List;
import java.util.UUID;

/**
 * Created by navie on 2016/11/30.
 */

public class CrimePagerActivity extends AppCompatActivity{

  private ViewPager mViewPager;
  private List<Crime> mCrimes;
  private static final String EXTRA_CRIME_ID = "com.example.navie.criminalintent.crime_id";

  public static Intent newIntent(Context context, UUID id){
    Intent intent = new Intent(context,CrimePagerActivity.class);
    intent.putExtra(EXTRA_CRIME_ID,id);
    return  intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crime_pager);

    UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

    mViewPager = (ViewPager)findViewById(R.id.activity_crime_pager_view_pager);

    mCrimes = CrimeLab.get(this).getCrimes();
    FragmentManager fragmentManager = getSupportFragmentManager();
    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override public Fragment getItem(int position) {
        Crime crime = mCrimes.get(position);
        return CrimeFragment.newInstance(crime.getmId());
      }

      @Override public int getCount() {

        return mCrimes.size();
      }
    });
    for(int i = 0;i < mCrimes.size();i++){
      if(mCrimes.get(i).getmId().equals(crimeId)){
        mViewPager.setCurrentItem(i);
        break;
      }
    }

  }
}
