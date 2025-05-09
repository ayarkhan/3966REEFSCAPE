// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.Matrix;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAutomatedSpeedMetersPerSecond = 1;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(27);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(27);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2), // Front Left
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),// Front Right
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),// Rear Left
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2)// Rear Right
        );

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs

    
    public static final int kFrontLeftDrivingCanId = 1;
    public static final int kRearLeftDrivingCanId = 7;
    public static final int kFrontRightDrivingCanId = 3;
    public static final int kRearRightDrivingCanId = 5;

    public static final int kFrontLeftTurningCanId = 2;
    public static final int kRearLeftTurningCanId = 8;
    public static final int kFrontRightTurningCanId = 4;
    public static final int kRearRightTurningCanId = 6;
    

    public static final boolean kGyroReversed = false;
  }

  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T. This changes the drive speed of the module (a pinion gear with
    // more teeth will result in a robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = Units.inchesToMeters(3);
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
    // teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kDriveDeadband = 0.05;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    
    public static final PathConstraints constraints = new PathConstraints(
        1.0, 2.0,
        Units.degreesToRadians(360), Units.degreesToRadians(720));

    public static final Transform3d leftBranchCoral = new Transform3d(0.381, 0.381, 0, new Rotation3d());
    public static final Transform3d rightBranchCoral = new Transform3d(0.381, -0.381, 0, new Rotation3d());

    public static final Pose2d[] startPositions = new Pose2d[]{
      new Pose2d(7.286383928571428, 6.21875, new Rotation2d(Math.PI)), // S1
      new Pose2d(7.260267857142857, 4.06417, new Rotation2d(Math.PI)), // S2
      new Pose2d(7.299441964285713, 1.88348, new Rotation2d(Math.PI))  // S3 
    };
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  public static final class ElevatorConstants {
    public static final double L1Height = 7;
    public static final double L2Height = 88;
    public static final double L3Height = 148;
    public static final double L4Height = 173;
    public static final double IntakeHeight = 0.5;
  }

  public static final class WristConstants {
    public static final double L1Angle = 7.4;
    public static final double L2_3Angle = 10;
    public static final double L4Angle = 4.8;
    public static final double IntakeAngle = 0.5;
  }

  public static final class FunnelConstants {
    public static final double IntakeAngle = 0.3532;
    public static final double ClimbAngle = 0.7;
  }


  public static class VisionConstants {
    public static final String kCameraName = "gccamera";
    // Cam mounted facing forward, half a meter forward of center, half a meter up from center,
    // pitched upward.
    private static final double camPitch = Units.degreesToRadians(-20);
    private static final double camYaw = Units.degreesToRadians(-2);
    public static final Transform3d kRobotToCam =
            new Transform3d(new Translation3d(Units.inchesToMeters(11), -Units.inchesToMeters(0), Units.inchesToMeters(7.5)), new Rotation3d(0, camPitch, camYaw));
    public static final Transform3d kCamToRobot = kRobotToCam.inverse();

    // The layout of the AprilTags on the field
    public static final AprilTagFieldLayout kTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);

    // The standard deviations of our vision estimated poses, which affect correction rate
    // (Fake values. Experiment and determine estimation noise on an actual robot.)
    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);


    public static final Transform2d leftBranch = new Transform2d(0.5, 0.3, new Rotation2d());
    public static final Transform2d rightBranch = new Transform2d(0.5, -0.3, new Rotation2d());
    public static final Transform2d middleReef = new Transform2d(0.5, 0, new Rotation2d());
  }

  public static class AlignmentConstants {
    public static final double X_SETPOINT_REEF_ALIGNMENT = 0.6;
    public static final double Y_SETPOINT_REEF_ALIGNMENT = -0.2;
    public static final double ROT_SETPOINT_REEF_ALIGNMENT = Math.PI;
    
    public static final double X_TOLERANCE_REEF_ALIGNMENT = 0.1;
    public static final double Y_TOLERANCE_REEF_ALIGNMENT = 0.1;
    public static final double ROT_TOLERANCE_REEF_ALIGNMENT = 0.1;
    
    public static final double DONT_SEE_TAG_WAIT_TIME = 1;
    public static final double POSE_VALIDATION_TIME = 1;
  }
}
