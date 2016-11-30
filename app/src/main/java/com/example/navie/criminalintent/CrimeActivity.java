package com.example.navie.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

  private static final String EXTRA_CRIME_ID = "com.example.navie.criminalintent.crime_id";

  public static Intent newIntent(Context context,UUID id){
    Intent intent = new Intent(context,CrimeActivity.class);
    intent.putExtra(EXTRA_CRIME_ID,id);
    return intent;
  }

  @Override protected Fragment createFragment() {
    //return new CrimeFragment();
    UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);
    return CrimeFragment.newInstance(crimeId);
  }
}
