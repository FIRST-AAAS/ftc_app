package org.aaas.stem.first.ftc.tasks_tele;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.aaas.stem.first.ftc.opmodes.AAASOpMode;


/**
 *
 */

public class ServoDrive {

    boolean upDown = false;
    private Servo servo;
    private AAASOpMode opMode;
    // position of the servo
    private double servoPosition;
    // amount to change the servo position by
    private double servoDelta = 0.01;


    public ServoDrive(  AAASOpMode opMode,  Servo servo, boolean upDown , double initialPosition) {
        this.servo = servo;
        this.upDown = upDown;
        this.opMode = opMode;

        // set the starting position of the servo
        this.servoPosition = initialPosition;

    }

    public void update(Gamepad gamepad) {

        boolean rightVal =  upDown ?  gamepad.y : gamepad.b;
        boolean leftVal =   upDown ?  gamepad.a : gamepad.x;

        opMode.getTelemetryUtil().addData("rightVal", rightVal);
        opMode.getTelemetryUtil().addData( "leftVal" , leftVal);

        // update the position of the servo
        if (rightVal) {
            servoPosition -= servoDelta;
        }

        if (leftVal) {
            servoPosition += servoDelta;
        }

        // clip the position values so that they never exceed 0..1
        servoPosition = Range.clip(servoPosition, 0, 1);

        // write position values to the wrist and neck servo
        servo.setPosition(servoPosition);

    }





}
