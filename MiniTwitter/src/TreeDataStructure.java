import javax.swing.tree.DefaultTreeModel;

public interface TreeDataStructure {

  boolean addNode(User parentNode, User child);

  boolean contains(String userID);

  User getUser(String userID);

  DefaultTreeModel getModel();
}
