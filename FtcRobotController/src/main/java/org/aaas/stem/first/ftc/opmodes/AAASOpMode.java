package org.aaas.stem.first.ftc.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.aaas.stem.first.ftc.utils.TelemetryUtil;

import java.io.PrintWriter;
import java.io.StringWriter;


public abstract class AAASOpMode extends LinearOpMode {

  private TelemetryUtil telemetryUtil = new TelemetryUtil(telemetry);

  abstract protected void onInit();

  abstract protected void runActiveOpMode() throws InterruptedException;

  public TelemetryUtil getTelemetryUtil() {
    return telemetryUtil;
  }

  public void handleOpmodeException(Throwable e) throws InterruptedException {
    getTelemetryUtil().addData("Opmode Exception", e.getMessage());
    String stckTrace = stackTraceAsTring(e);
    getTelemetryUtil().addData("Opmode Stacktrace", stckTrace.substring(0,200));
   // DbgLog.msg(e.getLocalizedMessage());
    //if( e instanceof Exception) {
      //DbgLog.error(stckTrace);

    //}


    getTelemetryUtil().sendTelemetry();
    if ( e instanceof InterruptedException) {
      throw (InterruptedException)e;
    }
  }


  private String stackTraceAsTring(Throwable e) {

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();

  }


}
