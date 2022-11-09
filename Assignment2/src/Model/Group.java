package Model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Group implements SysEntry {

	private String id;	
	private Group parent;
	private List<SysEntry> members;
	private HashSet<String> ids;

	/**
	 * Constructor used only for the root node.
	 */
	public Group(String id) {
		this.id = id;
		parent = this;
		members = new ArrayList<SysEntry>();		
		ids = new HashSet<String>();
	}

	/**
	 * Constructor used for other groups. 
	 */
	public Group(String id, Group parent) {
		this.id = id;
		this.parent = parent;
		members = new ArrayList<SysEntry>();
		ids = new HashSet<String>();
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public Group getParent() {
		return parent;
	}
	
	/**
	 * Checks if the ID is unique before adding a member to group. 
	 * Also adds ID to the root's ID HashSet.
	 */
	public boolean addToGroup(SysEntry member) {
		if (getRoot().getIDs().contains(member.getID().toLowerCase())) {
			return false;
		}
		members.add(member);
		getRoot().getIDs().add(member.getID());
		return true;
	}
	
	public Set<String> getIDs() {
		return ids;
	}
	
	/**
	 * Checks if the ID is unique before adding a member to group. 
	 * Also adds ID to the root's ID HashSet.
	 */
	public boolean equals(Group group) {
		return this.id.equalsIgnoreCase(group.getID());
	}
	
	/**
	 * Traverses parent nodes until the root is found.
	 */
	public Group getRoot() {
		Group root = parent;
		while (!parent.getID().equalsIgnoreCase("root")) {
			root = parent.getParent();
		}
		return root;
	}

	public List<SysEntry> getMembers() {
		return members;
	}

	/**
     * For Visitor Pattern
     * Let each type of visitor applying their own methods to the members
     */
	@Override
	public void accept(Visitor visitor) {
		visitor.atGroup(this);
		for (SysEntry member : members) {
			member.accept(visitor);
		}
	}

	/**
	 * Indicates that this SysEntry is a group in the JTree.
	 */
	@Override
	public String toString() {
		return "[Group] " + id;
	}
}
