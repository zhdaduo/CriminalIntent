package com.example.navie.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * Created by navie on 2016/11/29.
 */

public class CrimeListFragment extends Fragment{

  private RecyclerView mCrimeRecyclerView;
  private CrimeAdapter mAdapter;
  private static final int REQUEST_CRIME = 1;
  private boolean mSubtitleVisible;
  private static final String SAVE_SUBTITLE_VISIBLE = "subtitle";

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_crime_list,container,false);

    mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.crime_recycler_view);
    mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    if(savedInstanceState != null){
      mSubtitleVisible = savedInstanceState.getBoolean(SAVE_SUBTITLE_VISIBLE);
    }

    updateUI();

    return view;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public void onResume() {
    super.onResume();
    updateUI();
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_crime_list,menu);

    MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
    if(mSubtitleVisible){
      subtitleItem.setTitle(R.string.hide_subtitle);
    }else{
      subtitleItem.setTitle(R.string.show_subtitle);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.menu_item_new_crime:
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getmId());
        startActivity(intent);
        return true;
      case R.id.menu_item_show_subtitle:
        mSubtitleVisible = !mSubtitleVisible;
        getActivity().invalidateOptionsMenu();
        updateSubtitle();
        return true;
      default:
          return super.onOptionsItemSelected(item);
    }
  }

  private void updateSubtitle(){
    CrimeLab crimeLab = CrimeLab.get(getActivity());
    int crimeCount = crimeLab.getCrimes().size();
    String subtitle = getString(R.string.subtitle_format);

    if(!mSubtitleVisible){
      subtitle = null;
    }

    AppCompatActivity activity = (AppCompatActivity)getActivity();
    activity.getSupportActionBar().setSubtitle(subtitle);
  }

  private void updateUI(){
    CrimeLab crimeLab = CrimeLab.get(getActivity());
    List<Crime> crimes = crimeLab.getCrimes();
    if(mAdapter == null){
      mAdapter = new CrimeAdapter(crimes);
      mCrimeRecyclerView.setAdapter(mAdapter);
    }else{
      mAdapter.notifyDataSetChanged();
    }
      updateSubtitle();
  }

  private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //public TextView mTitleTextView;
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private CheckBox mSolvedCheckBox;
    private Crime mCrime;

    public CrimeHolder(View itemView){
      super(itemView);
      itemView.setOnClickListener(this);
      mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
      mDateTextView = (TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
      mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_crime_solved_check_box);
    //  mTitleTextView = (TextView)itemView;
    }

    public void bindCrime(Crime crime){
      mCrime = crime;
      mTitleTextView.setText(mCrime.getmTitle());
      mDateTextView.setText(mCrime.getmDate().toString());
      mSolvedCheckBox.setChecked(mCrime.ismSolved());
    }

    @Override public void onClick(View view) {
      //Toast.makeText(getActivity(),mCrime.getmTitle()+"clicked.",Toast.LENGTH_SHORT).show();
      //Intent intent = new Intent(getActivity(),CrimeActivity.class);
      //Intent intent = CrimeActivity.newIntent(getActivity(),mCrime.getmId());
      //startActivity(intent);
      Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getmId());
      startActivityForResult(intent,REQUEST_CRIME);
    }

  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == REQUEST_CRIME){

    }
  }

  private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
    private List<Crime> mCrimes;

    public CrimeAdapter(List<Crime> crimes){
      mCrimes  = crimes;
    }

    @Override public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      View view = layoutInflater.inflate(R.layout.list_item_crime,parent,false);
      return new CrimeHolder(view);
    }

    @Override public void onBindViewHolder(CrimeHolder holder, int position) {
      Crime crime = mCrimes.get(position);
      //holder.mTitleTextView.setText(crime.getmTitle());
      holder.bindCrime(crime);
    }

    @Override public int getItemCount() {
      return mCrimes.size();
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(SAVE_SUBTITLE_VISIBLE,mSubtitleVisible);
  }
}
