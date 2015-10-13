package org.aaas.stem.first.ftc.tasksAuto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.aaas.stem.first.ftc.opmodes.AAASOpMode;


/**
 *
 */

public class EncoderMotorTask {

    private String name;
    private DcMotor motor;
    private double power;
    private AAASOpMode opMode;

    private double targetEncoderValue;

    private boolean running = false;

    public static enum Direction {
        FORWARD,
        BACKWARD
    }
    public EncoderMotorTask(AAASOpMode opMode, DcMotor motor ) {

        this.opMode = opMode;
        this.motor = motor;

    }


    public void startMotor( String name,
                            double power,
                            double targetEncoderValue,
                            EncoderMotorTask.Direction direction) throws InterruptedException {

        running = true;
        this.name = name;
        this.targetEncoderValue = targetEncoderValue;

        double powerWithDirection = (direction == Direction.FORWARD) ? power : -power;

        resetEncoder();
        waitForEncoderToReset();

        motor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        if (motor != null) {
            motor.setPower(powerWithDirection);
        }

        opMode.getTelemetryUtil().addData("starting motor " + name, powerWithDirection );

    }

    public boolean targetReached() {

        boolean reached = false;

        if (motor != null)
        {
            int position = Math.abs (motor.getCurrentPosition ());
            opMode.getTelemetryUtil().addData(name + "current position", position);

            //
            // Has the encoder reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //

            //choosing within 3 as close enough
            if (position >= this.targetEncoderValue -3)
            {
                //
                // Set the status to a positive indication.
                //
                reached = true;
                opMode.getTelemetryUtil().addData("Target Reached " + name , position);
            }
        }

        return reached;

    }


    public void stop()  throws InterruptedException {
        running = false;
        this.motor.setPower(0.0f);

    }

    public boolean isRunning() {
        return running;
    }



    private void resetEncoder () throws InterruptedException {
        //
        // Reset the motor encoders on the drive wheels.
        //
        if (motor != null) {
            motor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        }

    }

    private void waitForEncoderToReset() throws InterruptedException {

        while(motor.getCurrentPosition() != 0 ){
            opMode.waitForNextHardwareCycle();
        }

    }



}
