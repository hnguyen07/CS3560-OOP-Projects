package Model;

/*
 * Interface for Group and User classes.
 * Applying the Composite Pattern
 */
public interface SysEntry extends Visitable {	
	
	public String getID();
	public Group getParent();
	public String toString();	
}
