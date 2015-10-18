package org.aaas.stem.first.ftc.tasksAuto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.aaas.stem.first.ftc.opmodes.AAASOpMode;
import org.aaas.stem.first.ftc.utils.Direction;


/**
 *
 */

public class ProximityDriveTask {

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private OpticalDistanceSensor ods;

    private AAASOpMode opMode;

    private double targetProximity;

    private boolean driving;

    private String name;



    private Direction currentDirection;

    public ProximityDriveTask(AAASOpMode opMode,
                              OpticalDistanceSensor ods,
                              DcMotor leftMotor,
                              DcMotor rightMotor) {

        this.opMode = opMode;
        this.ods = ods;
        this.leftMotor =  leftMotor;
        this.rightMotor = rightMotor;

    }


    public void drive(String name, double power, double targetProximity, Direction driveDirection)
            throws InterruptedException {

        this.leftMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        opMode.waitForNextHardwareCycle();
        this.rightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        opMode.waitForNextHardwareCycle();

        this.name = name;
        this.targetProximity = targetProximity;

        Direction motorDirection = Direction.MOTOR_FORWARD;
        if (driveDirection == Direction.DRIVE_BACKWARD ||
                driveDirection == Direction.DRIVE_LEFT) {
            motorDirection = Direction.MOTOR_BACKWARD;
        }
        double powerWithDirection = (motorDirection == Direction.MOTOR_FORWARD) ? power : -power;

        if (this.leftMotor != null) {
            opMode.getTelemetryUtil().addData(name + ": A - Starting left motor", powerWithDirection );

            this.leftMotor.setPower(powerWithDirection);
        }

        motorDirection = Direction.MOTOR_FORWARD;
        if (driveDirection == Direction.DRIVE_BACKWARD ||
                driveDirection == Direction.DRIVE_RIGHT) {
            motorDirection = Direction.MOTOR_BACKWARD;
        }
        powerWithDirection = (motorDirection == Direction.MOTOR_FORWARD) ? power : -power;

        if (this.rightMotor != null) {
            this.rightMotor.setPower(powerWithDirection);
        }
        opMode.getTelemetryUtil().addData(name + ": A - Starting right motor", powerWithDirection );

        this.driving = true;
    }

    public boolean isDriving() {
        return driving;
    }


    public void stop()   {

        this.leftMotor.setPower(0.f);
        this.rightMotor.setPower(0.f);

    }

    public boolean targetReached(String name) {

        double odsReading = this.ods.getLightDetected();
        boolean result =  odsReading >= this.targetProximity;

        opMode.getTelemetryUtil().addData(
                name + ": B -  WithinRange?", "odsReading:" + odsReading +
                        "targetProximity: " + this.targetProximity +
                        "result: " + result);

        return result;
    }



}
