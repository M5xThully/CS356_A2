import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.*;

public class UIWindow extends JFrame implements UIPanel {

  private static UIWindow instance;
  private JPanel contentPane;
  private JButton addUserButton;
  private JButton addGroupButton;
  private JButton openUserViewButton;
  private JButton showTotalUsersButton;
  private JButton showTotalMessagesButton;
  private JButton showTotalGroupsButton;
  private JButton showPositiveWordsButton;
  private JButton lastUpdatedUserButton;
  private JTextArea txtrUserId;
  private JTextArea txtrUserGroupId;
  private JTree tree;
  private TreeDataHandler treeDataHandler;
  private PopUp popUp = new PopUp();
  private JPanel panel1;
  private JPanel panel2;
  private JPanel panel3;

  private UIWindow() {
    setTitle("Mini Twitter UI");
    setBounds(100, 100, 600, 477);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(7, 7, 7, 7));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    treeDataHandler = new TreeDataHandler(new HashMap<>());

    panel1 = new JPanel();
    panel1.setBorder(new TitledBorder(UIManager.getBorder("List.focusCellHighlightBorder"), "Tree View", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 225, 0)));
    panel1.setBounds(0, 11, 251, 426);
    contentPane.add(panel1);
    panel1.setLayout(null);
    tree = new JTree(treeDataHandler.getModel());
    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    tree.setBounds(0, 0, 235, 400);
    JScrollPane scrollPane = new JScrollPane(tree);
    scrollPane.setBounds(10, 21, 231, 393);
    panel1.add(scrollPane);

    Icon leafIcon = UIManager.getIcon("FileView.fileIcon");
    Icon nonLeafIcon = UIManager.getIcon("FileView.directoryIcon");
    tree.setCellRenderer(new CellRender(leafIcon, nonLeafIcon));

    Handler handler = new Handler();

    addUserButton = new JButton("Add User");
    addUserButton.addActionListener(handler);
    addUserButton.setBounds(450, 28, 120, 63);
    contentPane.add(addUserButton);

    addGroupButton = new JButton("Add Group");
    addGroupButton.addActionListener(handler);
    addGroupButton.setBounds(450, 108, 120, 63);
    contentPane.add(addGroupButton);

    openUserViewButton = new JButton("Open User View");
    openUserViewButton.addActionListener(handler);
    openUserViewButton.setBounds(262, 190, 310, 40);
    contentPane.add(openUserViewButton);

    showTotalUsersButton = new JButton("User Total");
    showTotalUsersButton.addActionListener(handler);
    showTotalUsersButton.setBounds(265, 320, 140, 40);
    contentPane.add(showTotalUsersButton);
    
    showPositiveWordsButton = new JButton("Positive   %");
    showPositiveWordsButton.addActionListener(handler);
    showPositiveWordsButton.setBounds(432, 320, 140, 40);
    contentPane.add(showPositiveWordsButton);

    showTotalMessagesButton = new JButton("Messages Total");
    showTotalMessagesButton.addActionListener(handler);
    showTotalMessagesButton.setBounds(265, 381, 140, 40);
    contentPane.add(showTotalMessagesButton);

    showTotalGroupsButton = new JButton("Group Total");
    showTotalGroupsButton.addActionListener(handler);
    showTotalGroupsButton.setBounds(432, 381, 140, 40);
    contentPane.add(showTotalGroupsButton);

    panel3 = new JPanel();
    panel3.setBorder(new TitledBorder(UIManager.getBorder("List.focusCellHighlightBorder"), "User ID", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 225, 0)));
    panel3.setBounds(261, 20, 163, 76);
    contentPane.add(panel3);
    panel3.setLayout(null);

    txtrUserId = new JTextArea();
    txtrUserId.setBounds(6, 16, 151, 50);
    panel3.add(txtrUserId);

    panel2 = new JPanel();
    panel2.setBorder(new TitledBorder(UIManager.getBorder("List.focusCellHighlightBorder"), "Group ID", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 225, 0)));
    panel2.setBounds(261, 100, 163, 76);
    contentPane.add(panel2);
    panel2.setLayout(null);

    txtrUserGroupId = new JTextArea();
    txtrUserGroupId.setBounds(6, 16, 151, 50);
    panel2.add(txtrUserGroupId);

    this.setVisible(true);
  }

  //singleton design pattern
  public static UIWindow getInstance() {
    if (instance == null) {
      instance = new UIWindow();
    }
    return instance;
  }

  @Override
  public void setIcons() {
    tree.setCellRenderer(new DefaultTreeCellRenderer() {
      private Icon groupIcon = UIManager.getIcon("FileView.directoryIcon");
      private Icon userIcon = UIManager.getIcon("FileView.fileIcon");

      @Override
      public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
        Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
        if (isLeaf) {
          setIcon(userIcon);
        } else {
          setIcon(groupIcon);
        }
        return c;
      }
    });
  }

  @Override
  public void openUserView(User user) {
    User node = getSelectedNode(this.tree);
    if (!(node instanceof SingleUser)) {
      popUp.infoBox("No user view for groups.", "ERROR");
    } else {
      new SingleUserWindow((SingleUser) node, treeDataHandler);
    }
  }

  public User getSelectedNode(JTree tree) {
    TreePath parentPath = tree.getSelectionPath();
    User selectedNode;
    if (parentPath == null) {
      selectedNode = (User)treeDataHandler.getModel().getRoot();
    } else {
      selectedNode = (User)(parentPath.getLastPathComponent());
    }
    return selectedNode;
  }

  private class Handler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      User selectedNode = getSelectedNode(tree);

      if (e.getSource() == addUserButton) {
        String userId = txtrUserId.getText().trim();
        long creation = System.currentTimeMillis();
        User newUser = new SingleUser(userId, creation);
        selectedNode = getSelectedNode(tree);
        if (userId.isEmpty()) {
          return;
        }
        if(!newUser.validateID(userId)) {
          popUp.infoBox("Invalid characters in ID.", "ERROR");
          return;
        }
        if (treeDataHandler.addNode(selectedNode, newUser)) {
          tree.scrollPathToVisible(new TreePath(newUser.getPath()));
          Date date = new Date(creation);
        } else {
          return;
        }
      }

      if (e.getSource() == addGroupButton) {
        String groupId = txtrUserGroupId.getText().trim();
        long creation = System.currentTimeMillis();
        User newUserGroup = new GroupUser(groupId, creation);
        if (groupId.isEmpty()) {
          return;
        }
        if (!newUserGroup.validateID(groupId)) {
          popUp.infoBox("Invalid characters in ID.", "ERROR");
          return;
        }
        if (treeDataHandler.addNode(selectedNode, newUserGroup)) {
          tree.scrollPathToVisible(new TreePath(newUserGroup.getPath()));
          Date date = new Date(creation);
        } else {
          return;
        }
      }

      if (e.getSource() == openUserViewButton) {
        openUserView(selectedNode);
        if(selectedNode instanceof SingleUser) {
        }
      } else {
        if (e.getSource() == showTotalUsersButton) {
          TotalUsers totalUsers = new TotalUsers();
          treeDataHandler.accept(totalUsers);
          popUp.infoBox("There are " + totalUsers.result() + " users.", "Total Users");
        }
        if (e.getSource() == showTotalGroupsButton) {
          TotalGroups totalGroups = new TotalGroups();
          treeDataHandler.accept(totalGroups);
          popUp.infoBox("There are " + totalGroups.result() + " groups.", "Total Groups");

        }
        if (e.getSource() == showTotalMessagesButton) {
          TotalMessages totalMessages = new TotalMessages();
          treeDataHandler.accept(totalMessages);
          popUp.infoBox("There are " + totalMessages.result() + " messages.", "Total Messages");
        }
        if (e.getSource() == showPositiveWordsButton) {
          PositiveWords positivePercentage = new PositiveWords("good nice awesome great amazing");
          treeDataHandler.accept(positivePercentage);
          popUp.infoBox(String.format("%.02f", positivePercentage.result()) + "% of messages are positive.",
              "Positive Percentage of Messages");
        }
      }
    }
  }
}