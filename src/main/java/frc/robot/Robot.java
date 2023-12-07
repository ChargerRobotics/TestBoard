// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.test.motor.CANControllerTester;
import frc.robot.test.motor.MotorControllerTester;
import frc.robot.test.motor.PWMControllerTester;

public class Robot extends TimedRobot {
    private MotorControllerTester pwmControllerTester;
    private MotorControllerTester canControllerTester;

    @Override
    public void robotInit() {
        pwmControllerTester = new PWMControllerTester();
        canControllerTester = new CANControllerTester();
    }

    @Override
    public void robotPeriodic() {
        pwmControllerTester.periodic();
        canControllerTester.periodic();
    }

    public static <T extends Enum<T>> void addEnumTo(SendableChooser<T> chooser, T def, Class<T> enumClass) {
        chooser.setDefaultOption(def.toString(), def);
        for (T constant : enumClass.getEnumConstants()) {
            if (constant == def) continue;
            chooser.addOption(constant.toString(), constant);
        }
    }
}
