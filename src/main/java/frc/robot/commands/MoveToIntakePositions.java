package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.CoralEffector;
import frc.robot.subsystems.EffectorWrist;

public class MoveToIntakePositions extends SequentialCommandGroup {
    public Elevator elevator;
    public CoralEffector coralHand;
    public EffectorWrist wrist;


    public MoveToIntakePositions(
        CoralEffector hand,
        EffectorWrist wrist,
        Elevator elevator){

        super(
            new MoveElevatorToLevel(0, wrist, elevator),
            new RotateWristToLevel(0, wrist),
            new InstantCommand(()-> hand.intake(), hand).withTimeout(3)
        );
    }
}
