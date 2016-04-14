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

import com.automotive.hhi.mileagetracker.R;
import com.automotive.hhi.mileagetracker.model.data.Car;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCarFragment newInstance(String param1, String param2) {
        AddCarFragment fragment = new AddCarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddCarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_car, container, false);
        mAddCarPresenter = new AddCarPresenter();
        mAddCarPresenter.attachView(this);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.add_car_submit)
    public void onButtonPressed() {
        if(mAddCarPresenter.validateInput(mInputContainer)){
            mAddCarPresenter.insertCar(buildCar());
            if (mListener != null) {
                mListener.onCarFragmentInteraction();
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
                    + " must implement OnFillupFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mAddCarPresenter.detachView();
    }


    private Car buildCar(){
        Car car = new Car();
        car.setName(mName.getText().toString());
        car.setMake(mMake.getText().toString());
        car.setModel(mModel.getText().toString());
        car.setYear(Integer.valueOf(mYear.getText().toString()));
        car.setAvgMpg(0.0);
        return car;
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
        // TODO: Update argument type and name
        public void onCarFragmentInteraction();
    }

}
