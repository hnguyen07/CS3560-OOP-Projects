package Model;

public class UserFinderVisitor implements Visitor {
		
	private User target;
	private String targetID;	

	public User getTarget() {
		return target;
	}
	
	public UserFinderVisitor(String targetID) {
		this.targetID = targetID;
		target = null;
	}

	@Override
	public void atUser(User user) {
		if (targetID.equalsIgnoreCase(user.getID())) {
			target = user;
		}
	}

	@Override
	public void atGroup(Group user) { }

}
