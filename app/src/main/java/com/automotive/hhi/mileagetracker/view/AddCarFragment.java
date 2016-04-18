package com.automotive.hhi.mileagetracker.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
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
import com.automotive.hhi.mileagetracker.model.database.DataContract;
import com.automotive.hhi.mileagetracker.presenter.AddCarPresenter;

import javax.annotation.Resource;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddCarFragment.OnCarFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCarFragment extends DialogFragment implements AddCarView {

    @Bind(R.id.add_car_name)
    public EditText mName;
    @Bind(R.id.add_car_make)
    public EditText mMake;
    @Bind(R.id.add_car_model)
    public EditText mModel;
    @Bind(R.id.add_car_year)
    public EditText mYear;
    @Bind(R.id.add_car_submit)
    public Button mAddCar;
    @Bind(R.id.add_car_input_container)
    public LinearLayout mInputContainer;
    private AddCarPresenter mAddCarPresenter;

    private OnCarFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param car The car object being added or edited.
     * @param isEdit whether or not we're editing a car or adding a new one
     * @return A new instance of fragment AddCarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCarFragment newInstance(Car car, boolean isEdit) {
        AddCarFragment fragment = new AddCarFragment();
        Bundle args = new Bundle();
        args.putParcelable(KeyContract.CAR, car);
        args.putBoolean(KeyContract.IS_EDIT, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    public AddCarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog);


        if (getArguments() != null) {
            mAddCarPresenter = new AddCarPresenter((Car)getArguments()
                    .getParcelable(KeyContract.CAR)
                    , getArguments()
                    .getBoolean(KeyContract.IS_EDIT)
                    , getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_car, container, false);
        ButterKnife.bind(this, rootView);
        mAddCarPresenter.attachView(this);

        return rootView;
    }

    @OnClick(R.id.add_car_submit)
    public void onButtonPressed() {
        if(mAddCarPresenter.validateInput(mInputContainer)){
            mAddCarPresenter.insertCar();
            if (mListener != null) {
                mListener.onCarFragmentInteraction(mAddCarPresenter.getCar());
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCarFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCarFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mAddCarPresenter.detachView();
    }

    @Override
    public void setFields(){
        mName.setText(mAddCarPresenter.getCar().getName());
        mMake.setText(mAddCarPresenter.getCar().getMake());
        mModel.setText(mAddCarPresenter.getCar().getModel());
        mYear.setText(String.format("%d", mAddCarPresenter.getCar().getYear()));
    }

    @Override
    public void buildCar(){
        mAddCarPresenter.getCar().setName(mName.getText().toString());
        mAddCarPresenter.getCar().setMake(mMake.getText().toString());
        mAddCarPresenter.getCar().setModel(mModel.getText().toString());
        mAddCarPresenter.getCar().setYear(Integer.valueOf(mYear.getText().toString()));
        if(mAddCarPresenter.getCar().getId() == 0){
            mAddCarPresenter.getCar().setAvgMpg(0.0);
        }
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
    public interface OnCarFragmentInteractionListener {
        void onCarFragmentInteraction(Car car);
    }

}
