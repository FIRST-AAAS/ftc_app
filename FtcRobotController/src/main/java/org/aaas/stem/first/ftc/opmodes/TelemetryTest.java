package org.aaas.stem.first.ftc.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.aaas.stem.first.ftc.robots.DemoBot;
import org.aaas.stem.first.ftc.tasksTele.ServoDrive;
import org.aaas.stem.first.ftc.tasksTele.TankDrive;


public class TelemetryTest extends LinearOpMode {



  protected void onInit() {

  }


  @Override
  public void runOpMode() throws InterruptedException {

    this.onInit();

    waitForStart();
   // telemetry.clearData();


int  cnt = 20;
    while (opModeIsActive()) {

      if ( cnt > 15) {
        telemetry.addData(cnt + "C", "message");
        telemetry.addData(cnt + "B", "message");
        telemetry.addData(cnt + "A", "message");
        cnt--;

      }
      else{
        if ( cnt == 15) {
          telemetry.addData("17B", "message3");
          cnt--;
        }
      }

     // getTelemetryUtil().sendTelemetry();

      waitOneFullHardwareCycle();
    }
  }



}
