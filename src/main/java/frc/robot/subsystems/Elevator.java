package frc.robot.subsystems;


import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    SparkMax elevatorL = new SparkMax(10, MotorType.kBrushless);
    SparkMax elevatorR = new SparkMax(11, MotorType.kBrushless);
    SparkMaxConfig configL = new SparkMaxConfig();
    SparkMaxConfig configR = new SparkMaxConfig();
    RelativeEncoder encoderL = elevatorL.getEncoder();
    RelativeEncoder encoderR = elevatorL.getEncoder();
    SparkClosedLoopController pid;

    double targetReference;
    ControlType currentControlType;

    public Elevator() {
        configL.idleMode(IdleMode.kBrake).inverted(false).openLoopRampRate(0).closedLoopRampRate(0).closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(0.05, 0, 0).minOutput(-0.2).maxOutput(1);
        configL.encoder.positionConversionFactor(1).velocityConversionFactor(1);
        configR.follow(10).idleMode(IdleMode.kBrake);
        elevatorL.configure(configL, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        elevatorR.configure(configR, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        pid = elevatorL.getClosedLoopController();

        targetReference = 0;
        currentControlType = ControlType.kDutyCycle;
    }

    public void set(double speed) {
        elevatorL.set(speed);
        currentControlType = ControlType.kDutyCycle;
    }

    public void setVelocity(double velocity) {
        pid.setReference(velocity, ControlType.kVelocity);

        targetReference = velocity;
        currentControlType = ControlType.kVelocity;
    }

    public void setPosition(double position) {
        pid.setReference(position, ControlType.kPosition);

        targetReference = position;
        currentControlType = ControlType.kPosition;
    }

    public void setVoltage(double voltage) {
        elevatorL.setVoltage(voltage);
        
        currentControlType = ControlType.kVoltage;
    }

    public void setEncoderPosition(double position) {
        encoderL.setPosition(position);
        encoderR.setPosition(position);
    }

    public double getVelocity() {
        double velocity = 0;
        velocity += encoderL.getVelocity();
        velocity += encoderR.getVelocity();
        return velocity / 2;
    }

    public double getPosition() {
        double position = 0;
        
        position += encoderL.getPosition();
        position += encoderR.getPosition();
        
        return position / 2;
    }

    public boolean atTarget(double threshold) {
        if (currentControlType == ControlType.kVelocity) {
            return Math.abs(getVelocity() - targetReference) < threshold;
        } else if (currentControlType == ControlType.kPosition) {
            return Math.abs(getPosition() - targetReference) < threshold;
        } else {
            return false;
        }
    }
}