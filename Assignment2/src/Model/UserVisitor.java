package Model;

public class UserVisitor implements Visitor {

	private int numUsers;
	
	public UserVisitor() {
		numUsers = 0;
	}
	
	public int getNumUsers() {
		return numUsers;
	}
	
	@Override
	public void atUser(User user) {
		numUsers++;
	}
	
	@Override
	public void atGroup(Group user) { }

}
