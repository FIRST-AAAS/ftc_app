package org.aaas.stem.first.ftc.robots;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.aaas.stem.first.ftc.utils.TelemetryUtil;


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
        catch (Exception e)
        {
            getTelemetryUtil().addData("Exception", e.getClass().getSimpleName());
            getTelemetryUtil().addData("Message", e.getLocalizedMessage());
            DbgLog.msg(e.getLocalizedMessage());

        }

        return hardwareDevice;
    }


}
