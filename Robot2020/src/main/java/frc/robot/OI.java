package frc.robot;

import java.util.function.BooleanSupplier;

import com.thegongoliers.hardware.Hardware;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Button;
import frc.robot.DPadButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.StopAll;
import frc.robot.commands.controlPanel.RotatePanelSpinner;
import frc.robot.commands.controlPanel.RotatePanelSpinnerToColor;
import frc.robot.commands.controlPanel.StopPanelSpinner;
import frc.robot.commands.climber.ExtendDelivery;
import frc.robot.commands.climber.RaiseWinch;
import frc.robot.commands.climber.RetractDelivery;
import frc.robot.commands.climber.StopClimber;
import frc.robot.commands.climber.StopClimberWinch;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.powerCell.IntakePowerCell;
import frc.robot.commands.powerCell.OuttakePowerCell;
import frc.robot.commands.powerCell.ShootPowerCellHigh;
import frc.robot.commands.powerCell.ShootPowerCellLow;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private static final int DRIVER_JOYSTICK_PORT = 0;
    private static final int MANIPULATOR_XBOX_PORT = 1;

    public static Joystick driverJoystick;
    public static XboxController xboxController;

    public OI() {

        driverJoystick = new Joystick(DRIVER_JOYSTICK_PORT);
        xboxController = new XboxController(MANIPULATOR_XBOX_PORT);

        //// Driver Joystick setup

        Button driverTrigger = new JoystickButton(driverJoystick, 1);
        driverTrigger.whenPressed(new SetTurboDrivetrain(true));
        driverTrigger.whenReleased(new SetTurboDrivetrain(false));

        Button driverStopAll = new JoystickButton(driverJoystick, 11);
        driverStopAll.whenPressed(new StopAll());

        Button driveStickMoved = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Math.abs(driverJoystick.getY()) > 0.3 || Math.abs(driverJoystick.getZ()) > 0.1;
            }
        });
        driveStickMoved.whenPressed(new DrivetrainOperatorContol());

        //// TODO: Manipulator Xbox Controller setup
        Button manipulatorStopAll = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getBackButtonPressed() || xboxController.getStartButtonPressed();
            }
        });
        manipulatorStopAll.whenPressed(new StopAll());

        Button manipulatorIntake = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getAButtonPressed();
            }
        });
        manipulatorIntake.whenPressed(new IntakePowerCell()); // TODO

        Button manipulatorShootHigh = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getXButtonPressed();
            }
        });
        manipulatorShootHigh.whenPressed(new ShootPowerCellHigh());

        Button manipulatorShootLow = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getBButtonPressed();
            }
        });
        manipulatorShootLow.whenPressed(new ShootPowerCellLow());

        Button manipulatorOuttake = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getYButtonPressed();
            }
        });
        manipulatorOuttake.whenPressed(new OuttakePowerCell());

        Button manipulatorDeployPanelSpinner = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getBumperPressed(Hand.kRight);
            }
        });
        manipulatorDeployPanelSpinner.whenPressed(new StopPanelSpinner()); // TODO

        Button manipulatorResetPanelSpinner = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getBumperPressed(Hand.kLeft);
            }
        });
        manipulatorResetPanelSpinner.whenPressed(new StopPanelSpinner()); // TODO

        Button manipulatorRotateFast = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getTriggerAxis(Hand.kLeft) > 0.6;
            }
        });
        manipulatorRotateFast.whenPressed(new RotatePanelSpinner());

        Button manipulatorRotateColor = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return xboxController.getTriggerAxis(Hand.kRight) > 0.6;
            }
        });
        manipulatorRotateColor.whenPressed(new RotatePanelSpinnerToColor());



        // Climber Manipulator Xbox Controller Setup
        // The POV angles start at 0 in the up direction, and increase clockwise (eg right is 90, * upper-left is 315)
        DPadButton upButton = new DPadButton(xboxController, DPadButton.Direction.UP);
        upButton.whenPressed(new ExtendDelivery());

        DPadButton downButton = new DPadButton(xboxController, DPadButton.Direction.DOWN);
        downButton.whenPressed(new RetractDelivery());

        DPadButton leftButton = new DPadButton(xboxController, DPadButton.Direction.LEFT);
        leftButton.whenPressed(new RaiseWinch());

        DPadButton rightButton = new DPadButton(xboxController, DPadButton.Direction.RIGHT);
        rightButton.whenPressed(new StopClimberWinch());
    }

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    //// joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
