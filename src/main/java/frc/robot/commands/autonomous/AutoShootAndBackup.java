package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.powerCell.EjectPowerCellHigh;

/**
 * Condition: Starts the match lined up with the POWER PORT.
 * Condition: 3 POWER CELLS are preloaded and not touching the flywheel.
 * 
 * Shoots 3 preloaded balls at the high goal.
 * Backs up off the INITIATION LINE.
 */
public class AutoShootAndBackup extends CommandGroup {

    public AutoShootAndBackup() {
        addSequential(new EjectPowerCellHigh(), 8);
        addSequential(new DriveDistance(3), 3);
    }
}