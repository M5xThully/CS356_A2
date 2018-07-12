import javax.swing.tree.DefaultTreeModel;
import java.util.Map;

public class TreeDataHandler implements Visitable, TreeDataStructure {

  private Map<String,User> map;
  private DefaultTreeModel tree;
  private AlertBox popUp=new AlertBox();

  public TreeDataHandler(Map<String, User> map) {
    long creation = System.currentTimeMillis();
    User root = new GroupUser("Root", creation);
    this.map = map;
    tree = new DefaultTreeModel(root);
    map.put(root.getID(), root);
  }

  @Override
  public boolean addNode(User parentNode, User child) {
    if(contains(child.getID())) {
      popUp.infoBox("Node already exists in tree.", "ERROR");
      return false;
    }
    if(!parentNode.getAllowsChildren()) {
      popUp.infoBox("Leaf nodes cannot have children.", "ERROR");
      return false;
    }
    map.put(child.getID(), child);
    tree.insertNodeInto(child, parentNode, parentNode.getChildCount());
    return true;
  }

  @Override
  public boolean contains(String userID) {
    return map.containsKey(userID);
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
  public DefaultTreeModel getModel() {
    return tree;
  }

  @Override
  public void accept(Visitor visitor) {
    for (Map.Entry<String, User> entry : map.entrySet()){
      visitor.visit(entry.getValue());
    }
  }
}
