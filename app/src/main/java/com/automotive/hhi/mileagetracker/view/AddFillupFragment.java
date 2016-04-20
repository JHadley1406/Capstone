package com.automotive.hhi.mileagetracker.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automotive.hhi.mileagetracker.KeyContract;
import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Car;
import com.automotive.hhi.mileagetracker.model.data.Fillup;
import com.automotive.hhi.mileagetracker.model.data.Station;
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.presenter.AddFillupPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFillupFragment.OnFillupFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFillupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFillupFragment extends DialogFragment implements AddFillupView {

    @Bind(R.id.add_fillup_date)
    public TextView mDate;
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
    private OnFillupFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param car car object being filled up.
     * @param station station object at which the car is being filled up.
     * @return A new instance of fragment AddFillupFragment.
     */
    public static AddFillupFragment newInstance(Car car, Station station, Fillup fillup, boolean isEdit) {
        AddFillupFragment fragment = new AddFillupFragment();
        Bundle args = new Bundle();
        args.putParcelable(KeyContract.CAR, car);
        args.putParcelable(KeyContract.STATION, station);
        args.putParcelable(KeyContract.FILLUP, fillup);
        args.putBoolean(KeyContract.IS_EDIT, isEdit);
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
            mAddFillupPresenter =
                    new AddFillupPresenter((Fillup) getArguments().getParcelable(KeyContract.FILLUP)
                            , (Car)getArguments().getParcelable(KeyContract.CAR)
                            ,(Station) getArguments().getParcelable(KeyContract.STATION)
                            , getArguments().getBoolean(KeyContract.IS_EDIT, false)
                            , getContext());
        }

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_fillup, container, false);


        ButterKnife.bind(this, rootView);
        mAddFillupPresenter.attachView(this);
        if(mAddFillupPresenter.getIsEdit()){
            mAddFillup.setText(getResources().getString(R.string.add_fillup_edit_button));
        } else{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            mDate.setText(sdf.format(new Date()));
        }
        return rootView;
    }

    @OnClick(R.id.add_fillup_submit)
    public void onButtonPressed() {
        mAddFillupPresenter.checkStation();
        mAddFillupPresenter.validateInput(mInputContainer);
        if (mListener != null) {
            mListener.onFillupFragmentInteraction();
        }
    }

    @OnClick(R.id.add_fillup_date)
    public void showDatePickerDialog(){
        mAddFillupPresenter.buildDatePickerDialog().show();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFillupFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFillupFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mAddFillupPresenter.detachView();
    }

    @Override
    public void setFields(){
        mDate.setText(mAddFillupPresenter.getFillup().getReadableDate());
        mFuelAmount.setText(String.format("%.2f", mAddFillupPresenter.getFillup().getGallons()));
        mFuelPrice.setText(String.format("%.2f", mAddFillupPresenter.getFillup().getFuelCost()));
        mOctane.setText(String.format("%d", mAddFillupPresenter.getFillup().getOctane()));
        mMileage.setText(String.format("%.1f", mAddFillupPresenter.getFillup().getFillupMileage()));
    }

    @Override
    public void buildFillup(){
        mAddFillupPresenter.getFillup().setGallons(Double.parseDouble(mFuelAmount.getText().toString()));
        mAddFillupPresenter.getFillup().setFuelCost(Double.parseDouble(mFuelPrice.getText().toString()));
        mAddFillupPresenter.getFillup().setFillupMileage(Double.parseDouble(mMileage.getText().toString()));
        mAddFillupPresenter.getFillup().setOctane(Integer.valueOf(mOctane.getText().toString()));
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
    public interface OnFillupFragmentInteractionListener {
        void onFillupFragmentInteraction();
    }

}
