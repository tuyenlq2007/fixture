package com.page;

import java.io.File;

import org.configuration.SetUp;
import org.exception.StopTest;
import org.selenium.session.DriverPool;
import org.sikuli.script.Button;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class FixtureImage<T> {

	public FixtureImage() {
	}

	public String IClickOnThe(final String imageName) throws StopTest {
		final String result = "SUCCESS";
		final Screen screen = new Screen();
		final String image = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + "images" + File.separator + imageName +".PNG";
		try {
			final Pattern pattern = new Pattern(image).exact();
			screen.click(pattern);
		} catch (final Exception e) {
			try {
				DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).manage().window().maximize();
				Thread.sleep(5000);
				final Pattern pattern = new Pattern(image).exact();
				screen.click(pattern);

			} catch (final Exception e1) {
				try {
					Thread.sleep(5000);
					final Pattern pattern = new Pattern(image).similar((float) 0.5);
					screen.click(pattern);

				} catch (final Exception e2) {
					try {
						Thread.sleep(5000);
						screen.click(image, 0);
					} catch (final Exception e3) {
						try {
							Thread.sleep(5000);
							final Pattern pattern = new Pattern(image).exact();
							screen.mouseMove(pattern);
							screen.click(pattern);

						} catch (final Exception e4) {
							throw new StopTest(e4);
						}
					}
				}
			}
		}
		return result;
	}

	public String IDragAndDrop(final String imageName1, final String imageName2) throws StopTest {
		final String result = "SUCCESS";
		final Screen screen = new Screen();
		final String image1 = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator
				+ "files" + File.separator + "images" + File.separator + imageName1+".PNG";
		final String image2 = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator
				+ "files" + File.separator + "images" + File.separator + imageName2+".PNG";
		try {
			screen.find(image1);
			screen.find(image2);
			screen.dragDrop(image1, image2);
		} catch (final Exception e) {
			try {
				DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).manage().window().maximize();
				Thread.sleep(5000);
				screen.find(image1);
				screen.find(image2);
				screen.dragDrop(image1, image2);
			} catch (final Exception e1) {
				try {
					Thread.sleep(5000);
					screen.type(Key.PAGE_DOWN);
					screen.find(image1);
					screen.find(image2);
					screen.dragDrop(image1, image2);
				} catch (final Exception e4) {
					throw new StopTest(e4);
				}
			}
		}
		return result;
	}

	public String IDragXY(final String imageName1, final String x, final String y) throws StopTest {
		final String result = "SUCCESS";
		final Screen screen = new Screen();
		final String image = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator + "files"
				+ File.separator + "images" + File.separator + imageName1+".PNG";

		try {
			final Match img = screen.find(image);
			screen.click(img);
			screen.mouseDown(Button.LEFT);
			screen.mouseMove(img.offset(Integer.parseInt(x), Integer.parseInt(y)));
			screen.mouseUp(Button.LEFT);
		} catch (final Exception e) {
			try {
				DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).manage().window().maximize();
				Thread.sleep(5000);
				final Match img = screen.find(image);
				screen.click(img);
				screen.mouseDown(Button.LEFT);
				screen.mouseMove(img.offset(Integer.parseInt(x), Integer.parseInt(y)));
				screen.mouseUp(Button.LEFT);
			} catch (final Exception e4) {
				throw new StopTest(e4);
			}
		}
		return result;
	}

}