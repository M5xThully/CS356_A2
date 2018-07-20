public class LastUpdated implements Visitor {

  private User lastUpdatedUser;

  @Override
  public void visit(User node) {
    if(node instanceof SingleUser) {
      if(lastUpdatedUser == null) {
        lastUpdatedUser = node;
      } else {
        if(lastUpdatedUser.getLastUpdateTime() < node.getLastUpdateTime()) {
          lastUpdatedUser = node;
        }
      }
    }
  }

  public User getLastUpdatedUser() {
    return lastUpdatedUser;
  }
}
