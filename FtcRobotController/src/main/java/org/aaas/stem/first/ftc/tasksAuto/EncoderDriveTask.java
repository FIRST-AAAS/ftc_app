package org.aaas.stem.first.ftc.tasksAuto;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.aaas.stem.first.ftc.opmodes.AAASOpMode;


/**
 *
 */

public class EncoderDriveTask {

    private EncoderMotorTask leftMotorTask;
    private EncoderMotorTask rightMotorTask;

    private AAASOpMode opMode;

    private double targetEncoderValue;


    public static enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    private Direction currentDirection;

    public EncoderDriveTask(AAASOpMode opMode,
                            DcMotor leftMotor,
                            DcMotor rightMotor) {

        this.opMode = opMode;

        leftMotorTask =  new EncoderMotorTask( opMode, leftMotor );
        rightMotorTask = new EncoderMotorTask( opMode, rightMotor );

    }


    public void drive(String name , double power,double targetEncoderValue, EncoderDriveTask.Direction driveDirection) throws InterruptedException {

        EncoderMotorTask.Direction motorDirection = EncoderMotorTask.Direction.FORWARD;
        if ( driveDirection == Direction.BACKWARD ||
                driveDirection == Direction.LEFT  ) {
            motorDirection = EncoderMotorTask.Direction.BACKWARD;
        }
        leftMotorTask.startMotor(name + " left motor" , power , targetEncoderValue , motorDirection);


        motorDirection = EncoderMotorTask.Direction.FORWARD;
        if ( driveDirection == Direction.BACKWARD ||
                driveDirection == Direction.RIGHT  ) {
            motorDirection = EncoderMotorTask.Direction.BACKWARD;
        }
        rightMotorTask.startMotor(name + " right motor" ,power , targetEncoderValue , motorDirection);

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
