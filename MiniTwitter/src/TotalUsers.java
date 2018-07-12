public class TotalUsers implements Visitor {

  private int users = 0;

  @Override
  public void visit(User node) {
    if(node instanceof SingleUser) {
      users++;
    }
    else {
      return;
    }
  }

  public int result() {
    return users;
  }
}
