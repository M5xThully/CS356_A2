import javax.swing.tree.DefaultMutableTreeNode;

public abstract class User extends DefaultMutableTreeNode {

  public abstract void setID(String id);

  public abstract String getID();

  public abstract String toString();
  
  public abstract void setCreationTime(long creationTime);

  public abstract long getCreationTime();
  
  public abstract boolean validateID(String id);
  
  public abstract void setLastUpdateTime(long lastUpdateTime);
  
  public abstract long getLastUpdateTime();
}
