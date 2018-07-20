import javax.swing.DefaultListModel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleUser extends User implements Observer, Subject {

  private String id;
  private DefaultListModel<String> newsFeed;
  private DefaultListModel<Observer> followers = new DefaultListModel<>();
  private DefaultListModel<Subject> following = new DefaultListModel<>();
  private String tweet;
  private boolean changeState = false;
  private long creationTime;

  public SingleUser(String id, long creationTime) {
    setID(id);
    this.allowsChildren = false;
    this.newsFeed = new DefaultListModel<>();
  }

  public void tweet(String tweet) {
    this.tweet = tweet;
    newsFeed.addElement("[" + this.getID() + "]: " + tweet);
    notifyObservers();
  }

  public Object[] getTweets() {
    return this.newsFeed.toArray();
  }

  public Object[] getFollowers() {
    return this.followers.toArray();
  }

  public DefaultListModel<String> getNewsFeed() {
    return newsFeed;
  }

  public Object[] getFollowing() {
    return this.following.toArray();
  }

  public DefaultListModel<Subject> getFollowingList() {
    return following;
  }

  public void follow(SingleUser user) {
    setSubject(user);
    user.register(this);
  }

  @Override
  public void update(Subject s) {
    String update = s.getUpdate(this);
    this.newsFeed.addElement("[" + s.toString() + "]: " + update);
  }

  @Override
  public void setSubject(Subject s) {
    following.addElement(s);
  }

  @Override
  public void register(Observer o) {
    followers.addElement(o);
  }
  
  @Override
  public void notifyObservers() {
    if(changeState) {
      for(Object user : followers.toArray()) {
        ((Observer) user).update(this);
      }
      changeState = false;
    }
    else {
      return;
    }
  }

  @Override
  public String getUpdate(Observer o) {
    return this.tweet;
  }

  @Override
  public void setID(String id) {
    this.id = id;
  }

  @Override
  public String getID() {
    return id;
  }

  @Override
  public boolean validateID(String id) {
    if(id.contains(" ")) {
      System.out.println("User " + id + " has spaces!");
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return this.getID();
  }
  
  @Override
  public void setCreationTime(long creationTime) {
    this.creationTime = creationTime;
  }

  @Override
  public long getCreationTime() {
    return creationTime;
  }
}
