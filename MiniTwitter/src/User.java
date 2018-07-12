import javax.swing.tree.DefaultMutableTreeNode;

public abstract class User extends DefaultMutableTreeNode {

  public abstract void setID(String id);

  public abstract String getID();

  public abstract boolean validateID(String id);

  public abstract String toString();
}
