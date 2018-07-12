import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingleUserWindow extends JFrame implements SinglePanel {

  private SingleUser currentViewedUser;
  private TreeDataHandler treeDataHandler;
  private PopUp popUp = new PopUp();

  private JTextArea textUserId;
  private JTextArea textTweetMessage;

  private JList<Subject> followingList;
  private JList<String> newsFeedList;

  private JButton followButton;
  private JButton tweetButton;

  private DefaultListModel<Subject> modelFollowing;
  private JPanel border1;
  private JPanel border2;
  private JPanel border3;
  private JPanel border4;

  public SingleUserWindow(SingleUser individualUser, TreeDataHandler treeDataHandler) {
    this.currentViewedUser=individualUser;
    this.treeDataHandler=treeDataHandler;
    this.modelFollowing=currentViewedUser.getFollowingListModel();

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle(currentViewedUser.getID() + "'s " + "User View");
    setBounds(100, 100, 477, 435);
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    border1 = new JPanel();
    border1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Following", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    border1.setBounds(0, 62, 459, 119);
    contentPane.add(border1);
    border1.setLayout(null);
    followingList = new JList<>(modelFollowing);
    JScrollPane scrollPane = new JScrollPane(followingList);
    scrollPane.setBounds(10, 18, 443, 92);
    border1.add(scrollPane);

    border2 = new JPanel();
    border2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "News Feed", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    border2.setBounds(0, 235, 459, 154);
    contentPane.add(border2);
    border2.setLayout(null);
    newsFeedList = new JList<>(currentViewedUser.getNewsFeedListModel());
    JScrollPane scrollPane_1 = new JScrollPane(newsFeedList);
    scrollPane_1.setBounds(10, 21, 443, 126);
    border2.add(scrollPane_1);

    border3 = new JPanel();
    border3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Message", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    border3.setBounds(0, 179, 290, 58);
    contentPane.add(border3);
    border3.setLayout(null);

    textTweetMessage = new JTextArea();
    textTweetMessage.setBounds(6, 16, 274, 32);
    border3.add(textTweetMessage);


    border4 = new JPanel();
    border4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "User ID", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    border4.setBounds(0, 0, 290, 58);
    contentPane.add(border4);
    border4.setLayout(null);


    textUserId = new JTextArea();
    textUserId.setBounds(6, 16, 274, 32);
    border4.add(textUserId);

    Handler handler = new Handler();

    tweetButton = new JButton("Post Tweet");
    tweetButton.setBounds(322, 193, 125, 35);
    tweetButton.addActionListener(handler);
    getContentPane().add(tweetButton);

    followButton = new JButton("Follow User");
    followButton.setBounds(322, 12, 125, 35);
    followButton.addActionListener(handler);
    getContentPane().add(followButton);

    setVisible(true);
  }

  private boolean errorFollowingUser(User node) {
    if(alreadyFollowingUser(node)) {
      popUp.infoBox("You are already following this user.", "ERROR");
      return true;
    }
    if(followingSelf(node)) {
      popUp.infoBox("You cannot follow yourself.", "ERROR");
      return true;
    }
    return false;
  }

  @Override
  public boolean alreadyFollowingUser(User user) {
    Object[] arr = currentViewedUser.getFollowing();
    for(Object u : arr) {
      if(user.equals(u)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean followingSelf(User user) {
    return user.getID().equals(currentViewedUser.getID());
  }

  @Override
  public void follow(SingleUser user) {
    currentViewedUser.follow(user);
    popUp.infoBox("You are now following " + user.getID() + ".", "User Found");
  }

  @Override
  public void tweet(String message) {
    currentViewedUser.tweet(message);
  }

  private class Handler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      //source of action is follow button
      if (e.getSource() == followButton) {
        String userId = textUserId.getText().trim();
        User node;

        if (treeDataHandler.getUser(userId) != null) {
          node = treeDataHandler.getUser(userId);
        } else {
          popUp.infoBox("User not found.", "ERROR");
          return;
        }
        if (errorFollowingUser(node)) {
          return;
        } else if (!(node instanceof SingleUser)) {
          popUp.infoBox("Only Single Users can be followed.", "ERROR");
        } else {
          follow((SingleUser) node);
        }
      }

      if (e.getSource() == tweetButton) {
        String msg = textTweetMessage.getText().trim();
        tweet(msg);
      }
    }
  }
}
