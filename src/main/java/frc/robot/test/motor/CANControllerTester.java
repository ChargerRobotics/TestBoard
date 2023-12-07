package frc.robot.test.motor;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Robot;

public class CANControllerTester extends MotorControllerTester {
    private final SendableChooser<CANControllerType> controllerTypeChooser = new SendableChooser<>();
    private final SendableChooser<CANSparkMaxLowLevel.MotorType> motorTypeChooser = new SendableChooser<>();
    private final GenericEntry deviceId;

    public CANControllerTester() {
        super("CAN Motor Controller Tester");

        Robot.addEnumTo(controllerTypeChooser, CANControllerType.SPARK_MAX, CANControllerType.class);
        testerTab.add("Motor Controller", controllerTypeChooser);

        motorTypeChooser.setDefaultOption("Brushless", CANSparkMaxLowLevel.MotorType.kBrushless);
        motorTypeChooser.addOption("Brushed", CANSparkMaxLowLevel.MotorType.kBrushed);
        testerTab.add("Motor Type", motorTypeChooser);

        deviceId = testerTab.add("CAN ID", 0).getEntry();

        init();
    }

    @Override
    public CloseableController<?> createMotorController() {
        return controllerTypeChooser.getSelected().createController((int) deviceId.getInteger(0), motorTypeChooser.getSelected());
    }
}
