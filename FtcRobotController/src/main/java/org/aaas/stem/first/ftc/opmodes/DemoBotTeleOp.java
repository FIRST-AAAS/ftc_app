package org.aaas.stem.first.ftc.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.aaas.stem.first.ftc.robots.DemoBot;
import org.aaas.stem.first.ftc.tasks_tele.TankDrive;
import org.aaas.stem.first.ftc.tasks_tele.ServoDrive;


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

    this.onInit();

    waitForStart();
    getTelemetryUtil().reset();


    while (opModeIsActive()) {

      tankDrive.update(gamepad1);
      servoDrive.update(gamepad2);

      getTelemetryUtil().sendTelemetry();

      waitOneFullHardwareCycle();
    }
  }



}
