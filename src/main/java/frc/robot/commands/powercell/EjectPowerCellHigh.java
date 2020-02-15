package frc.robot.commands.powercell;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Shoots all held power cells towards the upper (outer and inner)
 * goals of the POWER PORT.
 */
public class EjectPowerCellHigh extends Command {

    public EjectPowerCellHigh() {
        requires(Robot.powerCellManipulator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.powerCellManipulator.shootHigh();
        if (Robot.powerCellManipulator.isFlywheelReady()) Robot.powerCellManipulator.intake();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false; // driver will stop it manually
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.powerCellManipulator.stopShooter();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
