package org.aaas.stem.first.ftc.tasksAuto;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.aaas.stem.first.ftc.opmodes.AAASOpMode;
import org.aaas.stem.first.ftc.utils.Direction;


/**
 *
 */

public class EncoderDriveTask {

    private EncoderMotorTask leftMotorTask;
    private EncoderMotorTask rightMotorTask;

    private AAASOpMode opMode;

    private double targetEncoderValue;


    private Direction currentDirection;

    public EncoderDriveTask(AAASOpMode opMode,
                            DcMotor leftMotor,
                            DcMotor rightMotor) {

        this.opMode = opMode;

        leftMotorTask =  new EncoderMotorTask( opMode, leftMotor );
        rightMotorTask = new EncoderMotorTask( opMode, rightMotor );

    }


    public void drive(String name, double power, double targetEncoderValue, Direction driveDirection) throws InterruptedException {

        Direction motorDirection = Direction.MOTOR_FORWARD;
        if (driveDirection == Direction.DRIVE_BACKWARD ||
                driveDirection == Direction.DRIVE_LEFT) {
            motorDirection = Direction.MOTOR_BACKWARD;
        }
        leftMotorTask.startMotor(name + ": A - Left Motor", power, targetEncoderValue, motorDirection);


        motorDirection = Direction.MOTOR_FORWARD;
        if (driveDirection == Direction.DRIVE_BACKWARD ||
                driveDirection == Direction.DRIVE_RIGHT) {
            motorDirection = Direction.MOTOR_BACKWARD;
        }
        rightMotorTask.startMotor(name + ": A - Right Motor", power, targetEncoderValue, motorDirection);

    }

    public boolean isDriving() {
        return leftMotorTask.isRunning() || rightMotorTask.isRunning();
    }


    public void stop()  throws InterruptedException {

        leftMotorTask.stop();
        rightMotorTask.stop();

    }

    public boolean targetReached() {

        return leftMotorTask.targetReached() || rightMotorTask.targetReached();
    }



}
