public class TotalTweets implements Visitor {

  private int tweets = 0;

  @Override
  public void visit(User node) {
    if(node instanceof SingleUser) {
      tweets += ((SingleUser)node).getTweets().length;
    }
  }

  public int result() {
    return tweets;
  }
}
