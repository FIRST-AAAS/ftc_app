package org.aaas.stem.first.ftc.opmodes;

import org.aaas.stem.first.ftc.robots.DemoBot;
import org.aaas.stem.first.ftc.tasksAuto.EncoderMotorTask;

public class DemoBotAutoMode1 extends AAASOpMode {

  private DemoBot demoBot;

  private EncoderMotorTask motorTask;

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

    onInit();

    waitForStart();
    getTelemetryUtil().reset();

    int step = 1;

    while (opModeIsActive()) {

      if (demoBot.getTouch1().isPressed()) {
        if ( motorTask.isRunning() ) {
          motorTask.stop();
        }
        step = 99;
      }

      switch (step) {
        case 1:

          if (! motorTask.isRunning()) {
            //full power , forward for 8880
            motorTask.startMotor("step1-motor1", 1, 8880 , EncoderMotorTask.Direction.FORWARD);
          }
          if (motorTask.targetReached()) {
            motorTask.stop();
            step++;
          }
          break;

        case 2:

          if (! motorTask.isRunning()) {
            //  1/4 power backward for 1000
            motorTask.startMotor("step2-motor1",0.25, 1000 , EncoderMotorTask.Direction.BACKWARD);
          }
          if (motorTask.targetReached()) {
            motorTask.stop();
            step++;
          }
          break;

        case 99:

          getTelemetryUtil().addData("Opmode Status", "Robot Stopped.  Kill switch activated");
          break;


        default:
          getTelemetryUtil().addData("Opmode Status", "Tasks completed");
          break;

      }


      getTelemetryUtil().sendTelemetry();

      waitOneFullHardwareCycle();
    }
  }



}
