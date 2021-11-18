package com.test;

import java.awt.AWTException;
import java.awt.Robot;

public class Test3 {
	public static void main(String[] args) {
		try {
		    // These coordinates are screen coordinates
		    int xCoord = 208;
		    int yCoord = 93;

		    // Move the cursor
		    Robot robot = new Robot();
		    robot.mouseMove(xCoord, yCoord);
		} catch (AWTException e) {
		}
	}

}
