package org.aaas.stem.first.ftc.opmodes;

import org.aaas.stem.first.ftc.robots.DemoBot;
import org.aaas.stem.first.ftc.tasksAuto.EncoderMotorTask;
import org.aaas.stem.first.ftc.utils.Direction;

public class DemoBotAutoMode1 extends AAASOpMode {

  private DemoBot demoBot;

  private EncoderMotorTask motorTask;
  private int step;

  @Override
  protected void onInit() {

    //specify configuration name save from scan operation
    demoBot = DemoBot.newConfig(hardwareMap,  getTelemetryUtil());

    motorTask = new EncoderMotorTask( this, demoBot.getMotor1());

    getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
    getTelemetryUtil().sendTelemetry();

  }


  @Override
  public void runOpMode() throws InterruptedException {

    try {
      onInit();
    }
    catch (Throwable e ) {
      super.handleOpmodeException(e);
    }

    waitForStart();
    getTelemetryUtil().reset();

    step = 1;

    while (opModeIsActive()) {
      try {
        runActiveOpMode();
      }
      catch (Throwable e ) {
        super.handleOpmodeException(e);
      }
      waitOneFullHardwareCycle();
    }
  }


  @Override
  protected void runActiveOpMode()  throws InterruptedException {

    checkForKillSwitch();

    getTelemetryUtil().addData("AA Current Step: ", step);

    switch (step) {
      case 1:

        if (! motorTask.isRunning()) {
          //full power , forward for 8880
          motorTask.startMotor("step1-motor1", 1, 8880 , Direction.MOTOR_FORWARD);
        }
        if (motorTask.targetReached()) {
          motorTask.stop();
          step++;
        }
        break;

      case 2:

        if (! motorTask.isRunning()) {
          //  1/4 power backward for 1000
          motorTask.startMotor("step2-motor1",0.25, 1000 , Direction.MOTOR_BACKWARD);
        }
        if (motorTask.targetReached()) {
          motorTask.stop();
          step++;
        }
        break;

      case 99:

        getTelemetryUtil().addData("step" + step + " Opmode Status", "Robot Stopped.  Kill switch activated");
        break;


      default:
        getTelemetryUtil().addData("step" + step + " Opmode Status", "Tasks completed");
        break;

    }


    getTelemetryUtil().sendTelemetry();


  }




  private void checkForKillSwitch() throws InterruptedException {
    if (demoBot.getTouch1().isPressed()) {

      if (motorTask.isRunning()) {
        motorTask.stop();
      } else {
        demoBot.getMotor1().setPower(0);
        demoBot.getMotor2().setPower(0);
        step = 99;
      }

    }
  }

}
