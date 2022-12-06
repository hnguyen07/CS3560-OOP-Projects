package Model;

import View.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// Users are both a subject and an observer when we apply the Observer pattern
public class User implements SysEntry, Subject, Observer {

	private String id;	
	private Group parent; // The group that the user belongs to
	private List<User> followers;
	private List<User> following;
	private List<String> tweets; // their own tweets
	private List<String> newsFeed; // tweets of them and of the users that they follow
	private UserView userView; // The GUI user view of each user in the system
	private final long creationTime;
	private long lastUpdateTime;


	public User(String id, Group parent) {
		this.id = id;
		this.parent = parent;
		followers = new ArrayList<User>();
		following = new ArrayList<User>();
		tweets = new ArrayList<String>();
		newsFeed = new ArrayList<String>();		
		follow(this); // Follows self so that he/she can see their own tweets.
		creationTime = 	System.currentTimeMillis();
		lastUpdateTime = System.currentTimeMillis();
	}

	public void follow(User user) {
		user.addFollower(this); // add self to the follower list of user
		following.add(user); // add the user to following list
	}

	private void setID(String id) {
        this.id = id;
    }

    @Override
	public String getID() {
		return id;
	}

	/**
	 * Uses a UserFinderVisitor to search and return the object user with the matching ID. 
	 * It then follows the user and updates the user's follower list
	 */
	public void followUser(String userToFollow) {
		try {	
			Group root = getParent().getRoot();
			UserFinderVisitor ufv = new UserFinderVisitor(userToFollow);
			root.accept(ufv);
			follow(ufv.getTarget());
			// Update the GUI for user view
			userView.updateFollowList(userToFollow);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "User to be followed does not exist!", 
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public List<User> getFollowing() {
		return following;
	}

	/**
     * For Observer Pattern. Adds the followers
     */
	@Override
	public void addFollower(User user) {
		followers.add(user);
	}

	public List<User> getFollowers() {
		return followers;
	}

	/**
	 * Adds the new tweet and notifies the followers.
	 * For Observer Pattern
	 */
	public void tweet(String message) {
		addTweet(message);
        notifyFollowers();
	}

	/**
     * For Observer Pattern. Notifies the change to all the followers.
     */
	@Override
	public void notifyFollowers() {
		for (User user: getFollowers()) {
			user.updateTweetView(this, getLatestTweet());
		}
	}

	private void addTweet(String message) {
		tweets.add(message);
	}

	public List<String> getTweets() {
		return tweets;
	}

	/**
	 * Retrieves the latest tweet of the user
	 */
	public String getLatestTweet() {
		return tweets.get(tweets.size() - 1);
	}

	public List<String> getNewsFeed() {
		return newsFeed;
	}

	@Override
	public Group getParent() {
		return parent;
	}

	/**
	 * Creates and display the user view when the user press the button "Open User View"
	 */
	public void openUserView() {
		userView = new UserView(this);
	}

	/**
	 * Updates news feed view.
	 * For Observer Pattern
	 */
	@Override
	public void updateTweetView(User user, String message) {
		String feed = user.getID() + ": " + message;
		newsFeed.add(feed);
		userView.updateTweetList(feed);		
		lastUpdateTime = System.currentTimeMillis();
	}

	/**
     * For Visitor Pattern
     * Let each type of visitor applying their own methods to the member
     */
	@Override
	public void accept(Visitor visitor) {
		visitor.atUser(this);
	}

	/**
	 * Indicates that this SysEntry is a user in the JTree.
	 */
	@Override
	public String toString() {
		return "<User> " + id;
	}

	/**
	 * Get the creation time of the user
	 */
	@Override
	public long getCreationTime() {
		return creationTime;
	}
	
	/**
	 * Get the last update time whenever a new tweet is posted 
	 * for both the user and all the followers
	 */
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	/**
	 * Set the last update time whenever a new tweet is posted 
	 * for both the user and all the followers
	 */
	public void setLastUpdateTime(long time) {
		lastUpdateTime = time;
	}
}
