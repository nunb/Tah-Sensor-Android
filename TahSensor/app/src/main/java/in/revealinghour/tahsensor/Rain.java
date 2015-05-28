package in.revealinghour.tahsensor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adityamathur on 21/05/15.
 */
public class Rain extends Fragment implements View.OnClickListener {
    ImageView setting;
    FrameLayout pinsetting;
    ImageView raindropone, raindroptwo, raindropthree;
    private Timer timer;
    private int PinNo = 0;
    TextView txtReading;
    RadioButton btnA0, btnA1, btnA2, btnA3, btnA4, btnA5;
    EditText edtthreshold;
    int threshold=1000;
    boolean animationStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rain_sensor, container, false);
        setting = (ImageView) view.findViewById(R.id.pinsetting);
        pinsetting = (FrameLayout) view.findViewById(R.id.framerain);
        raindropone = (ImageView) view.findViewById(R.id.imgrainsmallone);
        raindroptwo = (ImageView) view.findViewById(R.id.rainsmalltwo);
        raindropthree = (ImageView) view.findViewById(R.id.rainsmallthree);
        edtthreshold = (EditText) view.findViewById(R.id.threshold);
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
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtvalue = edtthreshold.getText().toString();
                if (edtvalue != null && !edtvalue.equals("")) {
                    threshold = Integer.parseInt(edtvalue);
                    if(threshold>1023){
                        Toast.makeText(Selector.activity,"Please enter threshold below 1023.",Toast.LENGTH_LONG).show();
                    }
                }
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
        Selector.fragmentNumber = 8;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Selector.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Selector.mBluetoothLeService.getTAHRainSensorUpdate(PinNo);

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
                    System.out.println("Running stte=================" + rawData);
                    int rawrain = Integer.parseInt(rawData);
                    if (rawrain >= threshold) {
                        txtReading.setText("It's Raining");
                        if (!animationStatus) {
                            RainAnimation(true);
                        }
                    } else if (rawrain < threshold) {
                        txtReading.setText("Raining \n Stopped");
                        if (animationStatus) {
                            RainAnimation(false);
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void RainAnimation(final boolean start) {
        //raindrop one
        Animation animation = new AlphaAnimation(2, 0);
        animation.setDuration(70);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);

        if (start) {
            animationStatus = true;
            raindropone.startAnimation(animation);
            raindroptwo.startAnimation(animation);
            raindropthree.startAnimation(animation);
        } else {
            animationStatus = false;
            raindropone.clearAnimation();
            raindroptwo.clearAnimation();
            raindropthree.clearAnimation();
            raindropone.setVisibility(View.INVISIBLE);
            raindroptwo.setVisibility(View.INVISIBLE);
            raindropthree.setVisibility(View.INVISIBLE);
        }
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

        }
    }
}
