public interface SinglePanel {

  boolean alreadyFollowingUser(User user);

  boolean followingSelf(User user);

  void follow(SingleUser user);

  void tweet(String tweet);
}
