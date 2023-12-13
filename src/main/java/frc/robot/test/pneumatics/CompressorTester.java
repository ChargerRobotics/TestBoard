// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.test.pneumatics;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.test.Tester;

public class CompressorTester extends Tester {
  private final SendableChooser<PneumaticsModuleType> moduleTypeChooser = new SendableChooser<>();
  private final GenericEntry enabledEntry;
  private Compressor compressor;

  public CompressorTester() {
    super("Compressor");

    moduleTypeChooser.setDefaultOption("CTREPCM", PneumaticsModuleType.CTREPCM);
    moduleTypeChooser.addOption("REVPH", PneumaticsModuleType.REVPH);
    testerTab.add("Module Type", moduleTypeChooser);

    enabledEntry = testerTab.add("Enabled", false)
        .withWidget(BuiltInWidgets.kToggleSwitch)
        .getEntry();

    Command applyChangesCommand = Commands.runOnce(() -> {
      compressor.close();
      compressor = new Compressor(moduleTypeChooser.getSelected());
      enabledEntry.setBoolean(false);
    });
    applyChangesCommand.setName("Apply");

    testerTab.add("Apply Changes", applyChangesCommand);

    compressor = new Compressor(moduleTypeChooser.getSelected());
  }

  @Override
  public void periodic() {
    if (enabledEntry.getBoolean(false)) {
      compressor.enableDigital();
    } else {
      compressor.disable();
    }
  }
}
