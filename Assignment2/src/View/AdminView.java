package View;

import Model.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.JComponent;

/*
 * Applies Singleton pattern to the AdminView:
 * 1. Static instance variable
 * 2. Private Constructor
 * 3. Static GetInstance
 */
public class AdminView {	

	private static AdminView instance;
	private JFrame frame;
	private JTextField txtUserId, txtGroupId;
	private JTree tree;
	private Group root;
	private DefaultMutableTreeNode rootNode;

	private AdminView() {
		root = new Group("root");
		build();
	}
	
	/*
	 * Only one instance is allowed. 
	 * If there is none, creates one instance.
	 * Else, returns the current instance.
	 */
	public static AdminView getInstance() {
		if (instance == null) {
			instance = new AdminView();
		}
		return instance;
	}

	/*
	 * Display the frame with all the GUI components on it
	 */
	public void show() {
		instance.frame.setVisible(true);
	}

	/*
	 * Creates UI components and add to the frame of Admin Control Panel
	 */
	private void build() {		
		// Create a frame to display the GUI components
		frame = new JFrame("Admin Control Panel");
		frame.setBounds(100, 100, 1003, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		// Create the JTextField to input the user's ID
		txtUserId = new JTextField();
		txtUserId.setBounds(415, 13, 275, 55);		
		txtUserId.setColumns(10);
		changeFont(txtUserId);

		// Create "Add User" Button
		JButton addUserButton = new JButton("Add User");
		addUserButton.setBounds(702, 13, 275, 55);
		changeFont(addUserButton);
		// Add a user using the text inputted in the txtUserID (JTextField)
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addUser(txtUserId.getText());
				txtUserId.setText(null);
			}
		});

		// Create the JTextField to input the group's ID
		txtGroupId = new JTextField();
		txtGroupId.setBounds(415, 81, 275, 55);
		txtGroupId.setColumns(10);
		changeFont(txtGroupId);
		
		// Create the "Add Group" button
		JButton addGroupButton = new JButton("Add Group");
		addGroupButton.setBounds(702, 81, 275, 55);	
		changeFont(addGroupButton);	
		// Add a group using the text inputted in the txtGroupID (JTextField)
		addGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addGroup(txtGroupId.getText());
				txtGroupId.setText(null);
			}
		});

		// Create the "Open User View" button
		JButton openUserViewButton = new JButton("Open User View");
		openUserViewButton.setBounds(415, 149, 562, 55);
		changeFont(openUserViewButton);	
		/*
	 	 * Checks if the selected node is a user.
	 	 * If yes, open the User View GUI. 
	 	 * If not, shows error message.
	 	 */	 
		openUserViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (getSelected() != null && getSelected().getUserObject() instanceof User) {
					((User) getSelected().getUserObject()).openUserView();
				} else {
					error("Please select a user to see the User View.");
				}			
			}
		});

		// Create the "Show User Total" button
		JButton userTotalButton = new JButton("Show User Total");
		userTotalButton.setBounds(415, 428, 275, 55);		
		changeFont(userTotalButton);	
		// Use the UserVisitor class to get the total number of users and display it.
		userTotalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				UserVisitor uv = new UserVisitor();
				root.accept(uv);
				display("Total number of users: " + uv.getNumUsers() + ".");
			}
		});

		// Create the "Show Group Total" button
		JButton groupTotalButton = new JButton("Show Group Total");
		groupTotalButton.setBounds(702, 428, 275, 55);	
		changeFont(groupTotalButton);	
		// Use the GroupVisitor class to get the total number of groups and display it.
		groupTotalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				GroupVisitor gv = new GroupVisitor();
				root.accept(gv);
				display("Total number of groups: " + gv.getNumGroups() + ".");
			}
		});

		// Create the "Show Messages Total" button.
		JButton msgTotalButton = new JButton("Show Messages Total");
		msgTotalButton.setBounds(415, 496, 275, 55);
		changeFont(msgTotalButton);
		// Use the MsgTotalVisitor class to get the total number of messages and display it.
		msgTotalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MsgTotalVisitor mtv = new MsgTotalVisitor();
				root.accept(mtv);
				display("Total number of tweet messages: " + mtv.getMsgNum() + ".");
			}
		});

		// Create the "Show Positive Percentage" button.
		JButton positivePercentageButton = new JButton("Show Positive Percentage");
		positivePercentageButton.setBounds(702, 496, 275, 55);	
		changeFont(positivePercentageButton);	
		// Use the PositiveMsgVisitor class to get the percentage of positive messages and display it.
		positivePercentageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				PositiveMsgVisitor pmv = new PositiveMsgVisitor();
				root.accept(pmv);
				display("Percentage of positive Tweet messages: " + String.format("%.2f", pmv.getPositiveMsgNum()) + "%");
			}
		});
		
		// Create the root node
		rootNode = new DefaultMutableTreeNode(root);
		// Build a tree with the root node 
		tree = new JTree(rootNode);
		// Create a scroll pane for the tree view
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 391, 540);
		scrollPane.setViewportView(tree);
		changeFont(tree);

		// Add all GUI components to the frame
		frame.getContentPane().add(scrollPane);	

		frame.getContentPane().add(txtUserId);
		frame.getContentPane().add(addUserButton);
		frame.getContentPane().add(txtGroupId);
		frame.getContentPane().add(addGroupButton);
		frame.getContentPane().add(openUserViewButton);

		frame.getContentPane().add(userTotalButton);
		frame.getContentPane().add(groupTotalButton);
		frame.getContentPane().add(msgTotalButton);
		frame.getContentPane().add(positivePercentageButton);
	}

	/*
	 * Adds a user to the Tree. 
	 * Checks if the parent group is selected and the inputted user's ID is unique. 
	 * If yes, updates the tree. If not, display an error message.
	 */
	private void addUser(String newUserID) {
		if (newUserID.isEmpty()) {
			error("Please enter the user's ID!");
		} else {
			DefaultMutableTreeNode parentNode = getSelected();
			if (parentNode != null && parentNode.getUserObject() instanceof Group) {
				Group parentGroup = (Group) parentNode.getUserObject();

				User newUser = new User(newUserID, parentGroup);
				DefaultMutableTreeNode newUserNode = new DefaultMutableTreeNode(newUser);

				if (parentGroup.addToGroup(newUser)) {
					updateTree(newUserNode, parentNode);
				} else {
					error("The ID already exists.\nPlease enter a unique user's ID!");
				}
			} else {
				error("Please select a parent group first!");
			}
		}
	}

	/*
	 * Adds a group to the Tree. 
	 * Checks if the parent group is selected and the inputted group's ID is unique. 
	 * If yes, updates the tree. If not, display an error message.
	 */
	private void addGroup(String newGroupID) {
		if (newGroupID.isEmpty()) {
			error("Please enter the group's ID!");
		} else {
			DefaultMutableTreeNode parentNode = getSelected();
			if (parentNode != null && parentNode.getUserObject() instanceof Group) {
				Group parentGroup = (Group) parentNode.getUserObject();

				Group newGroup = new Group(newGroupID, parentGroup);
				DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(newGroup);

				if (parentGroup.addToGroup(newGroup)) {
					updateTree(newGroupNode, parentNode);
				} else {
					error("The ID already exists.\nPlease use a unique group's ID!");
				}
			} else {
				error("Please select a parent group first!");
			}
		}
	}

	/*
	 * Updates the tree in the Admin View when we try to add a new user/group,
	 * Checks to make sure the parent node is a Group. If not, the adding process fails.
	 */
	public void updateTree(DefaultMutableTreeNode nodeToAdd, DefaultMutableTreeNode parentNode) {
		if (parentNode.getUserObject() instanceof Group) {
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			model.insertNodeInto(nodeToAdd, parentNode, parentNode.getChildCount());
			// Expand the paths so that the node is displayed.
			tree.scrollPathToVisible(new TreePath(nodeToAdd.getPath()));
		} else {
			error("Please select a group first.");
		}
	}

	/*
	 * Change the font of the text for the Jbutton
	 */
	private void changeFont(JComponent component) {
		component.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
	}

	
	/*
	 * Display a pop-up information message
	 */
	private void display(String message) {
		JOptionPane.showMessageDialog(null, message, "Analysis", JOptionPane.INFORMATION_MESSAGE);
	}

	/*
	 * Display a pop-up error message
	 */
	private void error(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/*
	 * Returns the currently selected node in the JTree. 
	 * Returns null if none are selected.
	 */
	public DefaultMutableTreeNode getSelected() {
		try {
			return ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
		} 
		catch (NullPointerException e) {
			return null;
		}
	}
}
