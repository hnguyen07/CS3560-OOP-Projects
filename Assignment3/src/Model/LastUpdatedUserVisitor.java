package Model;

public class LastUpdatedUserVisitor implements Visitor {
	
	private String userID;
	private long latestUpdateTime;
	
	public LastUpdatedUserVisitor() {
		userID = "No users exist in the system!";
		latestUpdateTime = -1; // to indicate an error as there are no users
	}
	
	public String getLastUpdatedUser() {
		return userID;
	}
	
	/** 
	 * Check each user's last update time
	 * Compare and update the latest update time of all users
	 */
	@Override
	public void atUser(User user) {
		if (latestUpdateTime < user.getLastUpdateTime()) {
			latestUpdateTime = user.getLastUpdateTime();
			userID = user.getID();
		}
	}

	@Override
	public void atGroup(Group group) { }
	
}
