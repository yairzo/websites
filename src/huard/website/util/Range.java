package huard.website.util;

public class Range {
	private int rangeStart;
	private int rangeEnd;

	public Range(int rangeStart, int rangeEnd) {
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;

		if (this.rangeStart > this.rangeEnd) {
			int temp = this.rangeStart;
			this.rangeStart = this.rangeEnd;
			this.rangeEnd = temp;
		}

	}

	public boolean inRange(int value) {
		return value >= rangeStart && value <= rangeEnd;
	}
}