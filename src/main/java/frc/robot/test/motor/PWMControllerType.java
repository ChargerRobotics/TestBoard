package frc.robot.test.motor;

import edu.wpi.first.wpilibj.motorcontrol.*;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

public enum PWMControllerType {
    SPARK_MAX("SPARK MAX", PWMSparkMax::new),
    SPARK("SPARK", Spark::new),
    VICTOR_SPX("Victor SPX", PWMVictorSPX::new),
    VICTOR_SP("Victor SP", VictorSP::new),
    TALON_FX("Talon FX", PWMTalonFX::new),
    TALON("Talon", Talon::new),
    NIDEC_BRUSHLESS("Nidec Brushless", NidecBrushless::new),
    ;

    private final String name;
    private final BiFunction<Integer, Integer, CloseableController<?>> createControllerFunction;

    <T extends MotorController & AutoCloseable> PWMControllerType(String name, IntFunction<T> createControllerFunction) {
        this(name, (channel, dio) -> createControllerFunction.apply(channel));
    }
    <T extends MotorController & AutoCloseable> PWMControllerType(String name, BiFunction<Integer, Integer, T> createControllerFunction) {
        this.name = name;
        this.createControllerFunction = (channel, dio) -> new CloseableController<>(createControllerFunction.apply(channel, dio));
    }

    public CloseableController<?> createController(int channel, int dio) {
        return createControllerFunction.apply(channel, dio);
    }

    @Override
    public String toString() {
        return name;
    }
}
