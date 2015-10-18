package org.aaas.stem.first.ftc.tasksAuto;


import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import org.aaas.stem.first.ftc.opmodes.AAASOpMode;


/**
 *
 */

public class ColorSensorTask {

    private String name;
    private AAASOpMode opMode;

    private ColorSensor colorSensor;


    public ColorSensorTask(AAASOpMode opMode, ColorSensor colorSensor ){

        this.opMode = opMode;
        this.colorSensor = colorSensor;
        //set to true to first illuminate
        colorSensor.enableLed(true);

    }




    public boolean isRed(String name) {


        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};
        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        opMode.getTelemetryUtil().addData(name + ": Clear", colorSensor.alpha());
        opMode.getTelemetryUtil().addData(name + ": Red  ", colorSensor.red());
        opMode.getTelemetryUtil().addData(name + ": Green", colorSensor.green());
        opMode.getTelemetryUtil().addData(name + ": Blue ", colorSensor.blue());
        opMode.getTelemetryUtil().addData(name + ": Hue", hsvValues[0]);


        return colorSensor.red() >= 1;

    }


    public void enableLed(boolean enable) {
        colorSensor.enableLed(enable);

    }
}
