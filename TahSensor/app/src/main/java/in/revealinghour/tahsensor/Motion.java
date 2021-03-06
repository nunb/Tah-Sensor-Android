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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shail on 22/05/15.
 */
public class Motion extends Fragment implements View.OnClickListener {
    ImageView setting;
    FrameLayout pinsetting;
    RadioButton btnd2, btnd3, btnd4, btnd5, btnd6, btnd7, btnd8, btnd9, btn10, btn11, btn12, btncms, btninch, btnft, btnmtr;
    RadioGroup sensorpin;
    HorizontalScrollView scroll;
    private Timer timer;
    private int PinNo = 2;
    TextView txtReading;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.motion_sensor, container, false);
        setting = (ImageView) view.findViewById(R.id.pinsetting);
        pinsetting = (FrameLayout) view.findViewById(R.id.framemotion);
        txtReading = (TextView) view.findViewById(R.id.imgbanana);
        btnd2 = (RadioButton) view.findViewById(R.id.pind2);
        btnd2.setOnClickListener(this);
        btnd3 = (RadioButton) view.findViewById(R.id.pind3);
        btnd3.setOnClickListener(this);
        btnd4 = (RadioButton) view.findViewById(R.id.pind4);
        btnd4.setOnClickListener(this);
        btnd5 = (RadioButton) view.findViewById(R.id.pind5);
        btnd5.setOnClickListener(this);
        btnd6 = (RadioButton) view.findViewById(R.id.pind6);
        btnd6.setOnClickListener(this);
        btnd7 = (RadioButton) view.findViewById(R.id.pind7);
        btnd7.setOnClickListener(this);
        btnd8 = (RadioButton) view.findViewById(R.id.pind8);
        btnd8.setOnClickListener(this);
        btnd9 = (RadioButton) view.findViewById(R.id.pind9);
        btnd9.setOnClickListener(this);
        btn10 = (RadioButton) view.findViewById(R.id.pin10);
        btn10.setOnClickListener(this);
        btn11 = (RadioButton) view.findViewById(R.id.pin11);
        btn11.setOnClickListener(this);
        btn12 = (RadioButton) view.findViewById(R.id.pin12);
        btn12.setOnClickListener(this);
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
        Selector.fragmentNumber=6;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Selector.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Selector.mBluetoothLeService.getTAHPIRMotionSensorUpdate(PinNo);

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
                    System.out.println("Running stte================="+rawData);
                    if (Integer.parseInt(rawData)==1){
                        txtReading.setText("Motion \n Detected");
                    }else {
                        txtReading.setText("No \n Motion");
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
            case R.id.pind2:
                PinNo = 2;
                break;
            case R.id.pind3:
                PinNo = 3;
                break;
            case R.id.pind4:
                PinNo = 4;
                break;
            case R.id.pind5:
                PinNo = 5;
                break;
            case R.id.pind6:
                PinNo = 6;
                break;
            case R.id.pind7:
                PinNo = 7;
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
        }
    }
}
