package org.aaas.stem.first.ftc.opmodes;

import org.aaas.stem.first.ftc.robots.DemoBot;
import org.aaas.stem.first.ftc.tasksAuto.EncoderDriveTask;
import org.aaas.stem.first.ftc.utils.Direction;


public class DemoBotAutoMode2 extends AAASOpMode {

    private DemoBot demoBot;

    private EncoderDriveTask driveTask;

    int step = 1;

    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        demoBot = DemoBot.newConfig(hardwareMap, getTelemetryUtil());

        driveTask = new EncoderDriveTask(this, demoBot.getMotor1(), demoBot.getMotor2());

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

        checkForKillSwitch();

        getTelemetryUtil().addData("AA Current Step: ", step);

        switch (step) {
            case 1:
                //full power , forward for 40000
                handleDriveTask("step1", 1, 20000, Direction.DRIVE_FORWARD);
                break;

            case 2:
                //turn 90 degrees
                handleDriveTask("step2", 0.5, 5000, Direction.DRIVE_LEFT);
                break;

            case 3:
                //full power , forward for 5000
                handleDriveTask("step3", 1, 4000, Direction.DRIVE_FORWARD);
                break;

            case 99:

                getTelemetryUtil().addData("step" + step + ": Opmode Status", "Robot Stopped.  Kill switch activated");
                break;

            default:
                getTelemetryUtil().addData("step" + step + ": Opmode Status", "Tasks completed");
                break;

        }

        getTelemetryUtil().sendTelemetry();

    }

    private void handleDriveTask(String name, double power, double target, Direction direction) throws InterruptedException {


        if (!driveTask.isDriving()) {
            driveTask.drive(name, power, target, direction);
        }
        if (driveTask.targetReached()) {
            driveTask.stop();
            step++;
        }
    }


    private void checkForKillSwitch() throws InterruptedException {
        if (demoBot.getTouch1().isPressed()) {

            if (driveTask.isDriving()) {
                driveTask.stop();
            } else {
                demoBot.getMotor1().setPower(0);
                demoBot.getMotor2().setPower(0);
                step = 99;
            }

        }
    }

}
