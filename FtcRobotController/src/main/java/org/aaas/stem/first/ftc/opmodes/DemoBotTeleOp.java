package org.aaas.stem.first.ftc.opmodes;

import org.aaas.stem.first.ftc.robots.DemoBot;
import org.aaas.stem.first.ftc.tasksTele.TankDrive;
import org.aaas.stem.first.ftc.tasksTele.ServoDrive;


public class DemoBotTeleOp extends AAASOpMode {

  private TankDrive tankDrive;
  private ServoDrive servoDrive;

  private DemoBot demoBot;

  @Override
  protected void onInit() {

    demoBot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

    tankDrive =  new TankDrive( this, demoBot.getMotor1(), demoBot.getMotor2());
    servoDrive = new ServoDrive(this, demoBot.getServo1(), true , 0.5);

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

    tankDrive.update(gamepad1);
    servoDrive.update(gamepad2);

    getTelemetryUtil().sendTelemetry();

  }

}
