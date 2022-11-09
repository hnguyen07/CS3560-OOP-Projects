package View;

import Model.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JComponent;

public class UserView {

	private JFrame frame;
	private JTextField followUserID, txtTweet;
	private DefaultListModel<String> followList, tweetList;
	private User user;

	/*
	 * Display user ID on JFrame's title.
	 */
	public UserView(User user) {
		this.user = user;
		build(user.getID() + "'s User View");
		// Show the User View
		frame.setVisible(true);
	}

	/*
	 * Creates UI components and add to the frame of User View
	 */
	private void build(String title) {
		// Create a frame to display the GUI components
		frame = new JFrame(title);
		frame.setBounds(100, 100, 654, 579);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		// Create the JTextField to input the user's ID to follow
		followUserID = new JTextField();
		followUserID.setBounds(12, 13, 403, 52);
		followUserID.setColumns(10);
		followUserID.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

		// Create the "Follow User" button
		JButton followUserButton = new JButton("Follow User");
		followUserButton.setBounds(427, 13, 201, 52);
		followUserButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		// Follow a user using the text inputted in the txtTweet (JTextField)
		followUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (followUserID.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter the user's ID!",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else if (user.getID().equals(followUserID.getText())) {
					JOptionPane.showMessageDialog(null, "You cannot follow yourself!",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					boolean duplicate = false;
					for (User user: user.getFollowing()) {
						if (user.getID().equals(followUserID.getText())) {
							duplicate = true;
							break;
						}
					}

					if (duplicate) {
						JOptionPane.showMessageDialog(null, "You have already followed this user!", 
									"Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						user.followUser(followUserID.getText());
					}
				}
			}
		});

		// Create the "Current Following:" label
		JLabel currentFollowLabel = new JLabel("Current Following (The newest following user on top):");
		currentFollowLabel.setBounds(12, 75, 600, 16);

		// Create a JList to show the user's following list
		followList = new DefaultListModel<String>();
		JList<String> followDisplay = new JList<>(followList);
		followDisplay.setBounds(12, 95, 616, 156);		

		// Create the JTextField to input the Tweet message
		txtTweet = new JTextField();
		txtTweet.setBounds(12, 270, 403, 52);		
		txtTweet.setColumns(10);
		txtTweet.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

		// Create the "Post Tweet" button
		JButton tweetPostButton = new JButton("Post Tweet");
		tweetPostButton.setBounds(427, 270, 201, 52);
		tweetPostButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));		
		// Action listener sent tweets based on the text in the txtTweet JTextField.
		tweetPostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				user.tweet(txtTweet.getText());
			}
		});

		// Create the "News Feed:" label
		JLabel newsfeedLabel = new JLabel("News Feed (The latest tweet on top):");
		newsfeedLabel.setBounds(12, 332, 600, 16);

		// Create the News Feed zone to display the Tweet messages
		tweetList = new DefaultListModel<String>();
		JList<String> tweetDisplay = new JList<>(tweetList);
		tweetDisplay.setBounds(12, 351, 616, 179);		

		// Add all GUI components to the frame				
		frame.getContentPane().add(followUserID);
		frame.getContentPane().add(followUserButton);
		frame.getContentPane().add(currentFollowLabel);
		frame.getContentPane().add(followDisplay);
		frame.getContentPane().add(txtTweet);	
		frame.getContentPane().add(tweetPostButton);
		frame.getContentPane().add(tweetDisplay);
		frame.getContentPane().add(newsfeedLabel);

		// Make the changes for the followList and tweetList
		refreshFollowUsers();
		refreshTweets();
	}

	// Add newest following user to the front of the following user's followList
	public void updateFollowList(String userID) {
		followList.add(0, " - " + userID);
	}	

	private void refreshFollowUsers() {
		for (User following: user.getFollowing()) {
			if (!following.getID().equals(user.getID())) {
				followList.add(0, " - " + following.getID());
			}
		}
	}

	// Add the latest tweet to the front of the list
	public void updateTweetList(String message) {
		tweetList.add(0, message);
	}

	private void refreshTweets() {
		for (String tweet: user.getNewsFeed()) {
			updateTweetList(tweet);
		}
	}
}
