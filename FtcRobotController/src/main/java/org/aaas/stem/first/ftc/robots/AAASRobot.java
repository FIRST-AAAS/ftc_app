package org.aaas.stem.first.ftc.robots;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Telemetry;

import java.util.Map;


/**
 *
 */
public abstract class AAASRobot {


    private Telemetry telemetry;

    abstract protected void init(HardwareMap hardwareMap, Telemetry telemetry);

    protected Telemetry getTelemetry() {
        return telemetry;
    }

    protected void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    protected  HardwareDevice getHardwareOn(String name, Object o) {

        HardwareDevice hardwareDevice = null;
        try {
            HardwareMap.DeviceMapping<HardwareDevice> deviceMapping  =  (HardwareMap.DeviceMapping<HardwareDevice>) o;
            hardwareDevice = (HardwareDevice)deviceMapping.get(name);
        }
        catch (Exception e)
        {
            getTelemetry().addData("Exception", e.getClass());
            getTelemetry().addData("Message", e.getLocalizedMessage());
            DbgLog.msg(e.getLocalizedMessage());

        }

        return hardwareDevice;
    }


}
