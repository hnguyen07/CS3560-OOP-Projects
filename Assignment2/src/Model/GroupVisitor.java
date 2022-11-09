package Model;

public class GroupVisitor implements Visitor{
	
	private int numGroups;
	
	public GroupVisitor() {
		numGroups = 0;
	}
	
	public int getNumGroups() {
		return numGroups;
	}
	
	public void atUser(User user) { }

	public void atGroup(Group group) {
		numGroups++;
	}

}
