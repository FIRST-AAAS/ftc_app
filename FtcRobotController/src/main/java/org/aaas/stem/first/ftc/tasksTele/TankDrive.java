package org.aaas.stem.first.ftc.tasksTele;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.aaas.stem.first.ftc.opmodes.AAASOpMode;


/**
 *
 */

public class TankDrive {

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private AAASOpMode opMode;

    public TankDrive(AAASOpMode opMode , DcMotor leftMotor, DcMotor rightMotor) {

        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.opMode  = opMode;

    }

    public void update(Gamepad gamepad) {

        // Manage the drive wheel motors.
        float leftDivePower = scaleMotorPower(-gamepad.left_stick_y);
        float rightDrivePower = scaleMotorPower(-gamepad.right_stick_y);

        setDrivePower(leftDivePower, rightDrivePower);
    }


    /**
     * Taken from FTC SDK PushBot example
     * Scale the joystick input using a nonlinear algorithm.
     */
    private void setDrivePower (double leftPower, double rightPower)

    {
       if (this.leftMotor != null)
        {
            opMode.getTelemetryUtil().addData("setting left power", leftPower);

            this.leftMotor.setPower (leftPower);
        }
        if (this.rightMotor != null)
        {
            this.rightMotor.setPower (rightPower);
        }

    }


    /**
     * Taken from FTC DSK PushBot example
     * Scale the joystick input using a nonlinear algorithm.
     */
    private float scaleMotorPower (float p_power)
    {
        //
        // Assume no scaling.
        //
        float l_scale = 0.0f;

        //
        // Ensure the values are legal.
        //
        float l_power = Range.clip (p_power, -1, 1);

        float[] l_array =
                { 0.00f, 0.05f, 0.09f, 0.10f, 0.12f
                        , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
                        , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
                        , 1.00f, 1.00f
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int)(l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    } // scale_motor_power


}
