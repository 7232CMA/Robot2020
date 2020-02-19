package frc.robot;

import com.thegongoliers.input.power.Battery;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.autonomous.*;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
    public static OI oi;
    public static Battery battery;

    public static Drivetrain drivetrain;
    public static PowerCellManipulator powerCellManipulator;
    public static ControlPanelManipulator controlPanelManipulator;
    public static Climber climber;
    public static Vision vision;

    private Command autonomousCommand;
    private SendableChooser<Command> autoChooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up.
     */
    @Override
    public void robotInit() {

        vision = new Vision();
        drivetrain = new Drivetrain(vision);
        powerCellManipulator = new PowerCellManipulator();
        controlPanelManipulator = new ControlPanelManipulator();
        climber = new Climber();

        battery = new Battery(10.5, 13.5, 18);

        oi = new OI();

        autoChooser.setDefaultOption("No Auto", null);
        autoChooser.addOption("Low Goal", new AutoLowGoal());
        autoChooser.addOption("Shoot 3", new AutoShootAndBackup());
        autoChooser.addOption("Shoot 6", new AutoShootCollectShoot());
        autoChooser.addOption("Full System Check", new FullSystemCheck());
        SmartDashboard.putData("Auto mode", autoChooser);
        
    }

    /**
     * This function is called every robot packet, no matter the mode.
     */
    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Match Time", Timer.getMatchTime());
        SmartDashboard.putNumber("Battery %", battery.getBatteryPercentage());
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called once each time the robot enters Autonomous mode.
     * The auto command that is run is chosen by the SmartDashboard sendableChooser.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = autoChooser.getSelected();

        // schedule the autonomous command
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called once each time the robot enters Teleop mode.
     */
    @Override
    public void teleopInit() {
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
