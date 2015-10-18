package org.aaas.stem.first.ftc.opmodes;

import org.aaas.stem.first.ftc.robots.DemoBot;

import org.aaas.stem.first.ftc.tasksAuto.ColorSensorTask;
import org.aaas.stem.first.ftc.tasksAuto.EncoderDriveTask;
import org.aaas.stem.first.ftc.tasksAuto.ProximityDriveTask;
import org.aaas.stem.first.ftc.utils.Direction;


public class DemoBotAutoMode3 extends AAASOpMode {

    private DemoBot demoBot;
    private ProximityDriveTask proximityDriveTask;
    private EncoderDriveTask encoderDriveTask;
    private ColorSensorTask colorSensorTask;

    int step = 1;


  @Override
  protected void onInit() {

      //specify configuration name save from scan operation
      demoBot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

      encoderDriveTask = new EncoderDriveTask( this, demoBot.getMotor1(),demoBot.getMotor2());
      proximityDriveTask = new ProximityDriveTask( this, demoBot.getOds1(), demoBot.getMotor1(),demoBot.getMotor2() );
      colorSensorTask = new ColorSensorTask(this, demoBot.getMrColor1());

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

    colorSensorTask.enableLed(false);

    getTelemetryUtil().reset();

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

        if (step <= 3) {
            driveCloseToLocation();
        }
        else if (step == 4) {
            getTelemetryUtil().addData("step" + step + ": DriveCloseToLocation", "task completed");
            step++;
        }
        else if (step <= 6) {
            driveToProximityAndCheckColor();
        }
        else if (step == 99) {
            getTelemetryUtil().addData("step" + step + ": Opmode Status", "Robot Stopped.  Kill switch activated");
        }
        else {
            getTelemetryUtil().addData(step + ": Opmode Status", "Tasks completed");
        }

        getTelemetryUtil().sendTelemetry();

    }


  private void driveCloseToLocation( ) throws InterruptedException {


    switch (step) {
      case 1:
        //full power , forward for 40000
        handleEncodedDriveTask("step" + step, 1, 20000, Direction.DRIVE_FORWARD);
        break;

      case 2:
        //turn 90 degrees
        handleEncodedDriveTask("step" + step, 0.5, 5000, Direction.DRIVE_LEFT);
          break;

      case 3:
        //full power , forward for 5000
        handleEncodedDriveTask("step" + step, 1, 4000, Direction.DRIVE_FORWARD);
        break;

      default:
        break;
    }

  }

  private void handleEncodedDriveTask(String name, double power, double target, Direction direction) throws InterruptedException {

      if (! encoderDriveTask.isDriving()) {
          encoderDriveTask.drive(name,power, target, direction);
      }
      if (encoderDriveTask.targetReached()) {
          encoderDriveTask.stop();
        step++;
      }
  }


  private void driveToProximityAndCheckColor() throws InterruptedException {

    switch (step) {
      case 5:

        // 1/4 power , forward until target proximity >= .15
        double power = 0.25;
        double proximityStrength = 0.15;
        handleProximityDriveTask("step" + step, power, proximityStrength, Direction.DRIVE_FORWARD);
        break;

       case 6:
         boolean isRed = colorSensorTask.isRed("step" + step);
         getTelemetryUtil().addData("isRed", isRed);
         break;

        default:
          break;
    }
  }

    private void handleProximityDriveTask(String name, double power, double proximityStrength, Direction direction)
            throws InterruptedException {


      if (! proximityDriveTask.isDriving()) {
          proximityDriveTask.drive(name,  power, proximityStrength, direction);
      }
      if (proximityDriveTask.targetReached(name)) {
          proximityDriveTask.stop();
          step++;
      }



  }


  private void checkForKillSwitch() throws InterruptedException  {
      if (demoBot.getTouch1().isPressed()) {

          if (encoderDriveTask.isDriving()) {
              encoderDriveTask.stop();
          } else if (proximityDriveTask.isDriving()) {
              proximityDriveTask.stop();
          }
          step = 99;

      }

  }

}
