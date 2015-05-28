package in.revealinghour.tahsensor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shail  on 21/05/15.
 */
public class Wind extends Fragment implements View.OnClickListener {
    ImageView setting;
    FrameLayout pinsetting;
    private Timer timer;
    private int PinNo = 0;
    TextView txtReading;
    RadioButton btnA0,btnA1,btnA2,btnA3,btnA4,btnA5,btnKm,btnMeter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wind_sensor, container, false);
        setting = (ImageView) view.findViewById(R.id.pinsetting);
        pinsetting = (FrameLayout) view.findViewById(R.id.framewind);
        txtReading = (TextView) view.findViewById(R.id.imgbanana);
        btnA0 = (RadioButton) view.findViewById(R.id.pinA0);
        btnA0.setOnClickListener(this);
        btnA1 = (RadioButton) view.findViewById(R.id.pinA1);
        btnA1.setOnClickListener(this);
        btnA2 = (RadioButton) view.findViewById(R.id.pinA2);
        btnA2.setOnClickListener(this);
        btnA3 = (RadioButton) view.findViewById(R.id.pinA3);
        btnA3.setOnClickListener(this);
        btnA4 = (RadioButton) view.findViewById(R.id.pinA4);
        btnA4.setOnClickListener(this);
        btnA5 = (RadioButton) view.findViewById(R.id.pinA5);
        btnA5.setOnClickListener(this);
        btnKm = (RadioButton) view.findViewById(R.id.kmhr);
        btnKm.setOnClickListener(this);
        btnMeter = (RadioButton) view.findViewById(R.id.metersecond);
        btnMeter.setOnClickListener(this);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinsetting.getVisibility() == View.VISIBLE) {
                    // pinsetting.setVisibility(View.GONE);
                    Animation out = AnimationUtils.makeOutAnimation(getActivity(), true);
                    pinsetting.startAnimation(out);
                    pinsetting.setVisibility(View.INVISIBLE);
                } else {
                    //pinsetting.setVisibility(View.VISIBLE);
                    Animation in = AnimationUtils.makeInAnimation(getActivity(), true);
                    pinsetting.startAnimation(in);
                    pinsetting.setVisibility(View.VISIBLE);

                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Selector.fragmentNumber=5;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Selector.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Running stte=================");
                        Selector.mBluetoothLeService.getTAHWindSensorUpdate(PinNo);

                    }
                });
            }
        }, 2000, 1000);
    }
    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            if (timer != null) {
                timer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //update data
    public void UpdateUI(final String data) {
        // System.out.println("========ssssss========");
        Selector.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Data[] = data.split(":");
                    String rawData = Data[1].toString();

                    if (btnKm.isChecked()){
                        txtReading.setText("" + Math.round(Float.parseFloat(rawData)) + "\n Km/hr");
                    }else if(btnMeter.isChecked()) {
                        txtReading.setText("" + Math.round(Float.parseFloat(rawData) * 0.27) + "\n m/s");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pinA0:
                PinNo = 0;
                break;
            case R.id.pinA1:
                PinNo = 1;
                break;
            case R.id.pinA2:
                PinNo = 2;
                break;
            case R.id.pinA3:
                PinNo = 3;
                break;
            case R.id.pinA4:
                PinNo = 4;
                break;
            case R.id.pinA5:
                PinNo = 5;
                break;
            case R.id.pind8:
                PinNo = 8;
                break;
            case R.id.pind9:
                PinNo = 9;
                break;
            case R.id.pin10:
                PinNo = 10;
                break;
            case R.id.pin11:
                PinNo = 11;
                break;
            case R.id.pin12:
                PinNo = 12;
                break;

            case R.id.kmhr:
                break;
            case R.id.metersecond:
                break;


        }
    }
}
