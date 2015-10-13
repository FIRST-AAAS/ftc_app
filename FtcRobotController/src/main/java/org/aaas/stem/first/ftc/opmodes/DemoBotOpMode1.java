package org.aaas.stem.first.ftc.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.aaas.stem.first.ftc.robots.DemoBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


public class DemoBotOpMode1 extends AAASOpMode {

  // position of the servo
  private double servo1Position;

  // amount to change the servo position by
  private double servo1Delta = 0.01;

  private DemoBot demoBot;

  @Override
  protected void onInit() {

    //specify configuration name save from scan operation
    demoBot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

    getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
    getTelemetryUtil().sendTelemetry();

    // set the starting position of the servo
    servo1Position = 0.5;

    // turn the LED on in the beginning, just so user will know that the sensor is active.
    demoBot.getMrColor1().enableLed(true);


  }


  @Override
  public void runOpMode() throws InterruptedException {

    this.onInit();

    waitForStart();
    getTelemetryUtil().reset();

    while (opModeIsActive()) {
      // throttle:  left_stick_y ranges from -1 to 1, where -1 is full up,  and 1 is full down
      // direction: left_stick_x ranges from -1 to 1, where -1 is full left and 1 is full right
      float throttle  = -gamepad1.left_stick_y;
      float direction =  gamepad1.left_stick_x;
      float right = throttle - direction;
      float left  = throttle + direction;

      // clip the right/left values so that the values never exceed +/- 1
      right = Range.clip(right, -1, 1);
      left  = Range.clip(left,  -1, 1);

      if (demoBot.getTouch1().isPressed()) {
        servo1Position -= servo1Delta;
      }

      // write the values to the motors
      demoBot.getMotor1().setPower(right);
      demoBot.getMotor2().setPower(left);


      // update the position of the servo
      if (gamepad2.y) {
        servo1Position -= servo1Delta;
      }

      if (gamepad2.a) {
        servo1Position += servo1Delta;
      }

      // clip the position values so that they never exceed 0..1
      servo1Position = Range.clip(servo1Position, 0, 1);

      // write position values to the wrist and neck servo
      demoBot.getServo1().setPosition(servo1Position);

      getTelemetryUtil().addData("ods  ld", demoBot.getOds1().getLightDetected());
      getTelemetryUtil().addData("servo position", demoBot.getServo1().getPosition());
      getTelemetryUtil().sendTelemetry();

      waitOneFullHardwareCycle();
    }
  }



}
