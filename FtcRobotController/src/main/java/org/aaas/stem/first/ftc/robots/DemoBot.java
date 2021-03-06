package org.aaas.stem.first.ftc.robots;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import org.aaas.stem.first.ftc.utils.TelemetryUtil;

/**
 * DemoBot Saved Configuration
 */
public class DemoBot extends AAASRobot {

    //sensors
    private OpticalDistanceSensor ods1;
    private TouchSensor touch1;
    private ColorSensor mrColor1;

    //motors
    private DcMotor motor1;
    private DcMotor motor2;
    private Servo servo1;

    private View relativeLayout;

    public static DemoBot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        DemoBot config =  new DemoBot();
        config.init(hardwareMap,telemetryUtil);
        return config;

    }

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        setTelemetry(telemetryUtil);

        ods1 = (OpticalDistanceSensor) getHardwareOn("ods1", hardwareMap.opticalDistanceSensor);
        touch1 = (TouchSensor) getHardwareOn("touch1",  hardwareMap.touchSensor);
        mrColor1 = (ColorSensor) getHardwareOn("mrColor1",  hardwareMap.colorSensor);
        // turn the LED on in the beginning, just so user will know that the sensor is active.
        mrColor1.enableLed(true);

        servo1 = (Servo) getHardwareOn("servo1",  hardwareMap.servo);
        motor1 = (DcMotor) getHardwareOn("motor1",  hardwareMap.dcMotor);
        motor2 = (DcMotor) getHardwareOn("motor2",  hardwareMap.dcMotor);

        motor2.setDirection(DcMotor.Direction.REVERSE);

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);


    }

    public OpticalDistanceSensor getOds1() {
        return ods1;
    }

    public TouchSensor getTouch1() {
        return touch1;
    }

    public Servo getServo1() {
        return servo1;
    }

    public ColorSensor getMrColor1() {
        return mrColor1;
    }

    public DcMotor getMotor1() {
        return motor1;
    }

    public DcMotor getMotor2() {
        return motor2;
    }

    public void addTelemetry() {

      //  getTelemetryUtil().addData("servo1",  getServo1().getPosition());
       // getTelemetryUtil().addData("ods  ld", getOds1().getLightDetected());


       ColorSensor cs = getMrColor1();
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};
        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        Color.RGBToHSV(cs.red() * 8, cs.green() * 8, cs.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        getTelemetryUtil().addData("Clear", cs.alpha());
        getTelemetryUtil().addData("Red  ", cs.red());
        getTelemetryUtil().addData("Green", cs.green());
        getTelemetryUtil().addData("Blue ", cs.blue());
        getTelemetryUtil().addData("Hue", hsvValues[0]);


        // change the background color to match the color detected by the RGB sensor.
        // pass a reference to the hue, saturation, and value array as an argument
        // to the HSVToColor method.
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });


    }
}
