package frc.robot.test.motor;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.test.Tester;

import java.util.Map;

public abstract class MotorControllerTester extends Tester {
    protected final GenericEntry motorPowerEntry;
    protected final GenericEntry currentMotorPowerEntry;
    protected final GenericEntry enabledEntry;
    protected final GenericEntry lastUpdatedEntry;

    protected CloseableController<?> currentController;

    public MotorControllerTester(String tabName) {
        super(tabName);

        motorPowerEntry = testerTab.add("Motor Power", 0)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", -1, "max", 1))
                .getEntry();
        enabledEntry = testerTab.add("Enabled", false)
                .withWidget(BuiltInWidgets.kToggleSwitch)
                .getEntry();
        lastUpdatedEntry = testerTab.add("Last Updated", System.currentTimeMillis()).getEntry();
        currentMotorPowerEntry = testerTab.add("Current Motor Power", 0).getEntry();

        CommandBase updateControllerCommand = Commands.runOnce(() -> {
            currentController.close();
            currentController = createMotorController();
            lastUpdatedEntry.setInteger(System.currentTimeMillis());
            enabledEntry.setBoolean(false);
        });
        updateControllerCommand.setName("Apply");

        testerTab.add("Apply Changes", updateControllerCommand);
    }

    protected void init() {
        currentController = createMotorController();
    }

    @Override
    public void periodic() {
        if (!enabledEntry.getBoolean(false)) {
            currentController.getController().stopMotor();
            return;
        }

        currentController.getController().set(motorPowerEntry.getDouble(0));
    }

    public abstract CloseableController<?> createMotorController();
}
