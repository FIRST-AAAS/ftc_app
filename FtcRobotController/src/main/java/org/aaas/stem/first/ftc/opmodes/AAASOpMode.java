package org.aaas.stem.first.ftc.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.aaas.stem.first.ftc.utils.TelemetryUtil;


public abstract class AAASOpMode extends LinearOpMode {

  private TelemetryUtil telemetryUtil = new TelemetryUtil(telemetry);

  abstract protected void onInit();

  public TelemetryUtil getTelemetryUtil() {
    return telemetryUtil;
  }
}
