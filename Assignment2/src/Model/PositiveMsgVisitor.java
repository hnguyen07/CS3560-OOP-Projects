package Model;

import java.util.ArrayList;
import java.util.List;

public class PositiveMsgVisitor implements Visitor {
	
	private int positiveMsg, totalMsg;
	private List<String> positiveWords;

	/*
	 * Initializes list of happy words.
	 */
	public PositiveMsgVisitor() {
		positiveMsg = 0;
		totalMsg = 0;
		positiveWords = new ArrayList<String>();
		positiveWords.add("good");
		positiveWords.add("great");
		positiveWords.add("excellent");
		positiveWords.add("happy");
		positiveWords.add("awesome");
	}

	public double getPositiveMsgNum() {
		if (totalMsg == 0) {
			return 0.0;
		}
		return (positiveMsg * 100.0 / totalMsg);
	}

	/*
	 * If user's tweet contains any of the listed positive words, it is labeled as positive.
	 */
	@Override
	public void atUser(User user) {
		List<String> usertweets = user.getTweets();
		for (String tweet : usertweets) {
			for (String word : positiveWords) {
				if (tweet.toLowerCase().contains(word.toLowerCase())) {
					positiveMsg++;
					break;
				}
			}
			totalMsg++;
		}
	}

	@Override
	public void atGroup(Group group) { }
}
