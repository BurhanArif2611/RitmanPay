package com.fil.workerappz.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fil.workerappz.ActionBarActivity;
import com.fil.workerappz.R;

/**
 * Created by HS on 08-Mar-18.
 * FIL AHM
 */

public class DoorToDoorDeliveryFragment extends BaseFragment {
  private ActionBarActivity activity;

  public DoorToDoorDeliveryFragment() {

  }

  public static DoorToDoorDeliveryFragment newInstance() {
    DoorToDoorDeliveryFragment fragment = new DoorToDoorDeliveryFragment();
    return fragment;
  }
    @Override
    public void onResume() {
        super.onResume();
        activity.onUserInteraction();
    }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.door_to_door_delivery, container, false);

    return view;
  }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ActionBarActivity) context;

    }
}