package com.tuya.smart.android.demo.base.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.demo.R;
import com.tuya.smart.android.demo.base.bean.ColorBean;
import com.tuya.smart.android.demo.device.ILightDevice;
import com.tuya.smart.android.demo.device.TuyaLightDeviceAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends BaseFragment /*implements ILampView */{
    private static final String TAG = "PhotoFragment";
    private volatile static PhotoFragment photoFragment;
    private View mContentView;
    private SeekBar mLightBar;
    private int mLampColor = Color.argb(250, 255, 255,0);
    private String mDeviceId = "38042025cc50e3258448";
    ILightDevice mLightDevice;

    //ITuyaLightDevice mLightDevice;
    //protected LampPresenter mLampPresenter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int S_COLOR_LIGHT_MIN_COLOR = 11;
    private static final int S_COLOR_SATURATION_MAX = 255;
    private static final int S_COLOR_LIGHT_MAX = S_COLOR_SATURATION_MAX ;
    private static final int S_BRIGHT_MIN = 0;
    private static final int S_BRIGHT_MAX = 100;
    static Boolean on = true;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Fragment newInstance() {
        if (photoFragment == null) {
            synchronized (PhotoFragment.class) {
                if (photoFragment == null) {
                    photoFragment = new PhotoFragment();
                }
            }
        }
        return photoFragment;
    }
    public PhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContentView = inflater.inflate(R.layout.fragment_photo, container, false);

        mContentView.findViewById(R.id.imageButton).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        L.d(TAG, "clicked love button");
                        Toast.makeText(getActivity(), "Love", Toast.LENGTH_SHORT).show();

                        mLightDevice.lightOn(Boolean.TRUE);
                    }
                }
        );

        mContentView.findViewById(R.id.button).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        L.d(TAG, "clicked dislike button");
                        Toast.makeText(getActivity(), "Dislike", Toast.LENGTH_SHORT).show();

                        mLightDevice.lightOn(Boolean.FALSE);
                        on = !on;
                    }
                }
        );
        initSeekBar();
        mLightDevice = new TuyaLightDeviceAdapter();
        mLightDevice.initLightController(mDeviceId);

        return mContentView;
    }

    private void initSeekBar() {
        mLightBar = mContentView.findViewById(R.id.seekBar);
        mLightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                L.d("debug", String.valueOf(seekBar.getId()) + ": progress:"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int value = mLightBar.getProgress();
                mLightDevice.setBrightness(value);
            }
        });
        mLightBar.setMax(S_BRIGHT_MAX);
        mLightBar.setProgress(S_BRIGHT_MIN);
    }

    private void sendLampColor() {
        ColorBean bean = new ColorBean();
        //bean.setColor(/*mView.getLampColor()*/);

        bean.setValue(mLightBar.getProgress() + S_COLOR_LIGHT_MIN_COLOR);
        bean.setSaturation(1/*mSaturationBar.getProgress()*/);
        //int lampColor = mView.getLampColor();
        float[] hsv = new float[3];
        Color.colorToHSV(mLampColor, hsv);
        hsv[2] =(mLightBar.getProgress() / 255.0f);
        hsv[1] = 1;//(mSaturationBar.getProgress() / 255.0f);
        int color = Color.HSVToColor(hsv);
        bean.setColor(color);
        //mLampPresenter.syncColorToLamp(bean);
        //mView.sendLampColor(bean);
    }


}