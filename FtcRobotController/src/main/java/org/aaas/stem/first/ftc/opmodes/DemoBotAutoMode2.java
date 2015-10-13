package org.aaas.stem.first.ftc.opmodes;

import org.aaas.stem.first.ftc.robots.DemoBot;
import org.aaas.stem.first.ftc.tasks_auto.EncoderDriveTask;
import org.aaas.stem.first.ftc.tasks_auto.EncoderMotorTask;


public class DemoBotAutoMode2 extends AAASOpMode {

  private DemoBot demoBot;

  private EncoderDriveTask driveTask;

  int step = 1;

  @Override
  protected void onInit() {

    //specify configuration name save from scan operation
    demoBot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

    driveTask = new EncoderDriveTask( this, demoBot.getMotor1(),demoBot.getMotor2() );

    getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
    getTelemetryUtil().sendTelemetry();

  }


  @Override
  public void runOpMode() throws InterruptedException {

    onInit();

    waitForStart();
    getTelemetryUtil().reset();

    while (opModeIsActive()) {

      if (demoBot.getTouch1().isPressed()) {
        if ( driveTask.isDriving() ) {
          driveTask.stop();
        }
        step = 99;
      }

      switch (step) {
        case 1:
          //full power , forward for 40000
          handleDriveTask("step1" , 1,  40000, EncoderDriveTask.Direction.FORWARD);
          break;

        case 2:
          //turn 90 degrees
          handleDriveTask("step2" ,0.5, 5000, EncoderDriveTask.Direction.LEFT);
          break;

        case 3:
          //full power , forward for 5000
          handleDriveTask("step3" ,1, 4000, EncoderDriveTask.Direction.FORWARD);
          break;

        case 99:

          telemetry.addData("Opmode Status" , "Robot Stopped.  Kill switch activated");
          break;

        default:
          telemetry.addData("Opmode Status" , "Tasks completed");
          break;

      }


      getTelemetryUtil().sendTelemetry();

      waitOneFullHardwareCycle();
    }
  }

  private void handleDriveTask(String name, double power , double target , EncoderDriveTask.Direction direction) throws InterruptedException {


      if (! driveTask.isDriving()) {
        driveTask.drive(name,power, target, direction);
      }
      if (driveTask.targetReached()) {
        driveTask.stop();
        step++;
      }
  }


}
