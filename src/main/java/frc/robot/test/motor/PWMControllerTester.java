package frc.robot.test.motor;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Robot;

public class PWMControllerTester extends MotorControllerTester {
    private final SendableChooser<PWMControllerType> controllerTypeChooser = new SendableChooser<>();
    private final GenericEntry channelEntry;
    private final GenericEntry dioEntry;

    public PWMControllerTester() {
        super("PWM Motor Controller Tester");

        Robot.addEnumTo(controllerTypeChooser, PWMControllerType.SPARK_MAX, PWMControllerType.class);
        testerTab.add("Motor Controller", controllerTypeChooser);

        channelEntry = testerTab.add("Channel", 0).getEntry();
        dioEntry = testerTab.add("DIO Channel", 0).getEntry();

        init();
    }

    @Override
    public CloseableController<?> createMotorController() {
        return controllerTypeChooser.getSelected().createController((int) channelEntry.getInteger(0), (int) dioEntry.getInteger(0));
    }
}
