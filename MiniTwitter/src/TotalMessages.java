public class TotalMessages implements Visitor {

  private int messages = 0;

  @Override
  public void visit(User node) {
    if(node instanceof SingleUser) {
      messages += ((SingleUser)node).getMessages().length;
    }
  }

  public int result() {
    return messages;
  }
}
