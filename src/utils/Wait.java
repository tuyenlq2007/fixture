package utils;

public class Wait {

	/**
	 * Wait-stop condition.
	 */
	public static interface StopCondition {

		/**
		 * Test wait-stop condition.
		 *
		 * @return true if satisfy wait-stop condition.
		 */
		boolean isSatisfied();
	}

	private static final long DEFAULT_INTERVAL = 200L;

	/**
	 * Wait instance with 200ms interval.
	 */
	public static final Wait defaultInterval = new Wait(DEFAULT_INTERVAL);

	/**
	 * Sleep without checked exception.
	 *
	 * @param millis
	 *            the length of time to sleep in milliseconds.
	 * @throws RuntimeException
	 *             wrap InterruptedException.
	 */
	public static void sleep(long millis) throws RuntimeException {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private final long interval;

	/**
	 * Constructor.
	 *
	 * @param interval
	 *            interval time (ms).
	 */
	public Wait(long interval) {
		this.interval = interval;
	}

	/**
	 * Wait until the wait-stop condition returns true or time runs out.
	 *
	 * @param startTime
	 *            start time.
	 * @param timeout
	 *            timeout (ms). (&gt;= 0)
	 * @param stopCondition
	 *            wait-stop condition.
	 * @return true if stop condition is sutisfied.
	 */
	public boolean wait(long startTime, long timeout, StopCondition stopCondition) {
		if (stopCondition.isSatisfied()) {
			return true;
		}
		do {
			final long now = System.currentTimeMillis();
			long d = timeout - (now - startTime);
			if (d <= 0) {
				return false;
			}
			if (d > interval) {
				d = interval;
			}
			sleep(d);
		} while (!stopCondition.isSatisfied());
		return true;
	}
}
