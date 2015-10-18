package org.aaas.stem.first.ftc.robots;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.aaas.stem.first.ftc.utils.TelemetryUtil;

import java.io.StringWriter;
import java.io.PrintWriter;



/**
 *
 */
public abstract class AAASRobot {


    private TelemetryUtil telemetryUtil;

    abstract protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil);

    protected TelemetryUtil getTelemetryUtil() {
        return telemetryUtil;
    }

    protected void setTelemetry(TelemetryUtil telemetryUtil) {
        this.telemetryUtil = telemetryUtil;
    }

    protected  HardwareDevice getHardwareOn(String name, Object o) {

        HardwareDevice hardwareDevice = null;
        try {
            HardwareMap.DeviceMapping<HardwareDevice> deviceMapping  =  (HardwareMap.DeviceMapping<HardwareDevice>) o;
            hardwareDevice = (HardwareDevice)deviceMapping.get(name);
        }
        catch (Throwable e)
        {
            getTelemetryUtil().addData("Exception", e.getClass().getSimpleName());
            getTelemetryUtil().addData("Message", e.getLocalizedMessage());
            DbgLog.msg(e.getLocalizedMessage());
            DbgLog.msg(stackTraceAsTring(e));



        }

        return hardwareDevice;
    }


    private String stackTraceAsTring(Throwable e) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();

    }


}
