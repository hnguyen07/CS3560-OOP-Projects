package Model;

import java.util.ArrayList;
import java.util.List;

public class IDCheckerVisitor implements Visitor {
	
	private List<String> invalidUserIDs;
	private List<String> invalidGroupIDs;
	
	public IDCheckerVisitor( ) {
		invalidUserIDs = new ArrayList<String>();
		invalidGroupIDs = new ArrayList<String>();
	}

	@Override
	public void atUser(User user) {
		String temp = user.getID();
		if (temp.contains(" ")) {
			invalidUserIDs.add(temp);
		}
	}

	@Override
	public void atGroup(Group group) {
		String temp = group.getID(); 
		if (temp.contains(" ")) {
			invalidGroupIDs.add(temp);
		}
	}
	
	public String getInvalidIDs() {
		String result = "Invalid User IDs:";
		if (invalidUserIDs.size() != 0) {
			result += " Yes\n";
			int i = 1;
			for (String id : invalidUserIDs) {
				result += i + ") \"" + id + "\"\n";
				++i;
			}
		} else {			
			result += " None\n";			
		}

		result += "\nInvalid Group IDs:";
		if (invalidGroupIDs.size() != 0) {
			result += " Yes\n";
			int j = 1;
			for (String id : invalidGroupIDs) {
				result += j + ") \"" + id + "\"\n";
				++j;
			}
		} else {			
			result += " None\n";			
		}

		return(result);
	}
}
