import javax.swing.tree.DefaultTreeModel;
import java.util.Map;

public class TreeDataManager implements Visitable, TreeDataStructure {

  private DefaultTreeModel tree;
  private Map<String, User> map;
  private Alert popUp = new Alert();

  public TreeDataManager(Map<String, User> map) {
    long creationTime = System.currentTimeMillis();
    User root = new GroupUser("Root", creationTime);
    this.map = map;
    tree = new DefaultTreeModel(root);
    map.put(root.getID(), root);
  }
  
  @Override
  public User getUser(String userID) {
    if(this.contains(userID)) {
      return map.get(userID);
    } else {
      return null;
    }
  }

  @Override
  public boolean contains(String userID) {
    return map.containsKey(userID);
  }

  @Override
  public DefaultTreeModel getModel() {
    return tree;
  }

  @Override
  public void accept(Visitor visitor) {
    for(Map.Entry<String, User> entry : map.entrySet()) {
      visitor.visit(entry.getValue());
    }
  }
  
  @Override
  public boolean addNode(User parentNode, User child) {
    if(contains(child.getID())) {
      popUp.alert("Node already exists in tree.", "[Error]");
      return false;
    }
    if(!parentNode.getAllowsChildren()) {
      popUp.alert("Leaf nodes can't have children.", "[Error]");
      return false;
    }
    map.put(child.getID(), child);
    tree.insertNodeInto(child, parentNode, parentNode.getChildCount());
    return true;
  }
}
