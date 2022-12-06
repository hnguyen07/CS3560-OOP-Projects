package Model;

public class MsgTotalVisitor implements Visitor {
	
	private int numMessages;
	
	public MsgTotalVisitor() {
		numMessages = 0;
	}
	
	public int getMsgNum() {
		return numMessages;
	}

	@Override
	public void atUser(User user) {
		numMessages += user.getTweets().size();
	}

	@Override
	public void atGroup(Group user) { }
}
