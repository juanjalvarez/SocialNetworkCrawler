import java.io.Serializable;
import java.util.ArrayList;

public class CrawlerData implements Serializable {

	private static final long serialVersionUID = 9150753403501790088L;

	private int subset;
	private long target;
	private long cursor;
	private ArrayList<Long> accessTime;

	public CrawlerData() {
		System.out.println("Registering a new crawler profile");
		// Initializes the first subset of data
		subset = 1;

		// Reads the target id
		target = -1;
		do {
			try {
				System.out.println("What is the ID of the user you wish to target?");
				target = Long.parseLong(TwitterCrawler.br.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (target == -1);

		// Sets the initial cursor to -1, the default cursor
		cursor = -1;

		// Creates an empty access time list
		accessTime = new ArrayList<Long>();
	}

	public int getSubset() {
		return subset;
	}

	public void updateSubset() {
		subset++;
	}

	public long getTarget() {
		return target;
	}

	public long getCursor() {
		return cursor;
	}

	public void setCursor(long l) {
		cursor = l;
	}

	public void registerCall() {
		accessTime.add(System.currentTimeMillis());
	}

	public long canMakeCallOn() {
		long constTime = 1000 * 60 * 15;
		long currTime = System.currentTimeMillis();
		long base = currTime - constTime;
		long min = currTime;
		for (long l : accessTime)
			if (l >= base && l < min)
				min = l;
		return (min + constTime);
	}

	public boolean canMakeCall() {
		int count = 0;
		long baseTime = System.currentTimeMillis() - 1000 * 60 * 15;
		for (long l : accessTime)
			if (l >= baseTime)
				count++;
		System.out
				.printf("Identified %d API calls in the past 15 minutes, the system is %s to make more calls\n", count,
						count >= 15
								? String.format("unable (%s)",
										secondsToString((int) ((canMakeCallOn() - System.currentTimeMillis()) / 1000)))
								: "able");
		return count < 15;
	}

	private static String secondsToString(int t) {
		int minutes = t / 60;
		int seconds = t % 60;
		return String.format("%d minutes and %d seconds", minutes, seconds);
	}

	@Override
	public String toString() {
		return String.format("Subsets: %d\nTarget ID: %d\nCursor: %d\nAccess times: %d", subset, target, cursor,
				accessTime.size());
	}
}