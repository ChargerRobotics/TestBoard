package frc.robot.test.motor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

public enum CANControllerType {
    SPARK_MAX("SPARK MAX", CANSparkMax::new),
    VICTOR_SPX("Victor SPX", WPI_VictorSPX::new),
    TALON_FX("Talon FX", (IntFunction<WPI_TalonFX>) WPI_TalonFX::new),
    ;

    private final String name;
    private final BiFunction<Integer, CANSparkMaxLowLevel.MotorType, CloseableController<?>> createControllerFunction;

    <T extends MotorController & AutoCloseable> CANControllerType(String name, IntFunction<T> createControllerFunction) {
        this(name, (id, type) -> createControllerFunction.apply(id));
    }
    <T extends MotorController & AutoCloseable> CANControllerType(String name, BiFunction<Integer, CANSparkMaxLowLevel.MotorType, T> createControllerFunction) {
        this.name = name;
        this.createControllerFunction = (id, type) -> new CloseableController<>(createControllerFunction.apply(id, type));
    }

    public CloseableController<?> createController(int channel, CANSparkMaxLowLevel.MotorType motorType) {
        return createControllerFunction.apply(channel, motorType);
    }

    @Override
    public String toString() {
        return name;
    }
}
