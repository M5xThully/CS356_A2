public class TotalGroups implements Visitor {

  private int groups = 0;

  @Override
  public void visit(User node) {
    if(node instanceof GroupUser) {
      groups++;
    } else {
      return;
    }
  }

  public int result() {
    return groups;
  }
}
