package frc.robot.test.motor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

class CloseableController<T extends MotorController & AutoCloseable> {
    private final T controller;

    public CloseableController(T controller) {
        this.controller = controller;
    }

    public T getController() {
        return controller;
    }

    public void close() {
        try {
            controller.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
