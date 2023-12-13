// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.test;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public abstract class Tester {
  protected final ShuffleboardTab testerTab;

  public Tester(String tabName) {
    this.testerTab = Shuffleboard.getTab(tabName);
  }

  public abstract void periodic();
}
