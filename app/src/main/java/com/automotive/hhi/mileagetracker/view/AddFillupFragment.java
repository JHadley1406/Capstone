package com.automotive.hhi.mileagetracker.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.presenter.AddFillupPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFillupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFillupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFillupFragment extends DialogFragment implements AddFillupView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private long mCarId;
    private long mStationId;
    private static Station mStation;

    @Bind(R.id.add_fillup_fuel_amount)
    public EditText mFuelAmount;
    @Bind(R.id.add_fillup_price)
    public EditText mFuelPrice;
    @Bind(R.id.add_fillup_octane)
    public EditText mOctane;
    @Bind(R.id.add_fillup_current_mileage)
    public EditText mMileage;
    @Bind(R.id.add_fillup_submit)
    public Button mAddFillup;
    @Bind(R.id.add_fillup_layout)
    public LinearLayout mInputContainer;
    private AddFillupPresenter mAddFillupPresenter;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param carId PK of car being filled up.
     * @param station station object at which the car is being filled up.
     * @return A new instance of fragment AddFillupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFillupFragment newInstance(long carId, Station station) {
        AddFillupFragment fragment = new AddFillupFragment();
        Bundle args = new Bundle();
        args.putLong(DataContract.FillupTable.CAR, carId);
        args.putLong(DataContract.FillupTable.STATION, station.getId());
        // FIXME: 4/6/16
        mStation = station;
        fragment.setArguments(args);
        return fragment;
    }

    public AddFillupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCarId = getArguments().getLong(DataContract.FillupTable.CAR);
            mStationId = getArguments().getLong(DataContract.FillupTable.STATION);
        }

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_fillup, container, false);
        mAddFillupPresenter = new AddFillupPresenter(mCarId, mStation, getContext());
        mAddFillupPresenter.attachView(this);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.add_fillup_submit)
    public void onButtonPressed() {
        mAddFillupPresenter.checkStation();
        mAddFillupPresenter.validateInput(mInputContainer);
        mAddFillupPresenter.insertFillup(buildFillup());
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mAddFillupPresenter.detachView();
    }


    private Fillup buildFillup(){
        Fillup fillup = new Fillup();
        fillup.setGallons(Double.parseDouble(mFuelAmount.getText().toString()));
        fillup.setFuelCost(Double.parseDouble(mFuelPrice.getText().toString()));
        fillup.setFillupMileage(Double.parseDouble(mMileage.getText().toString()));
        fillup.setOctane(Integer.valueOf(mOctane.getText().toString()));
        fillup.setDate(System.currentTimeMillis());
        return fillup;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction();
    }

}
