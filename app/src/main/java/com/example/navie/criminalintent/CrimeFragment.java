package com.example.navie.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import java.util.Date;
import java.util.UUID;

/**
 * Created by navie on 2016/11/29.
 */

public class CrimeFragment extends Fragment{
  private Crime mCrime;
  private EditText mTitleField;
  private Button mDateButton;
  private CheckBox mSolvedCheckBox;
  private static final String ARG_CRIME_ID = "crime_id";
  private static final String DIALOG_DATE = "DialogDate";
  private static final int REQUEST_DATE = 0;

  public static CrimeFragment newInstance(UUID crimeId){
    Bundle args = new Bundle();
    args.putSerializable(ARG_CRIME_ID,crimeId);
    CrimeFragment fragment = new CrimeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public void returnResult(){
    getActivity().setResult(Activity.RESULT_OK,null);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //mCrime = new Crime();
    //UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
    UUID crimeId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
    mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_crime,container,false);
    mTitleField = (EditText)v.findViewById(R.id.crime_title);
    mTitleField.setText(mCrime.getmTitle());
    mTitleField.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
         mCrime.setmTitle(charSequence.toString());
      }

      @Override public void afterTextChanged(Editable editable) {

      }
    });

    mDateButton = (Button)v.findViewById(R.id.crime_date);
    mDateButton.setText(mCrime.getmDate().toString());
    //mDateButton.setEnabled(false);
    mDateButton.setOnClickListener(new View.OnClickListener(){
      @Override public void onClick(View view) {
        FragmentManager manager = getFragmentManager();
        //DatePickerFragment dialog = new DatePickerFragment();
        DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
        dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
        dialog.show(manager,DIALOG_DATE);
      }
    });

    mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
    mSolvedCheckBox.setChecked(mCrime.ismSolved());
    mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCrime.setmSolved(b);
      }
    });
    return  v;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode != Activity.RESULT_OK){
      return;
    }
    if(requestCode == REQUEST_DATE){
      Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
      mCrime.setmDate(date);
      mDateButton.setText(mCrime.getmDate().toString());
    }

  }
}
