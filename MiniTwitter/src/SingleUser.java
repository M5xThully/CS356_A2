import javax.swing.DefaultListModel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleUser extends User implements Observer, Subject {

  private String id;
  private long creationTime;
  private DefaultListModel<Observer> followers = new DefaultListModel<>();
  private DefaultListModel<Subject> following = new DefaultListModel<>();
  private DefaultListModel<String> newsFeed;
  private String message;
  private boolean changeState = false;
  private long lastUpdateTime;
  private SimpleDateFormat sdf;

  public SingleUser(String id, long creationTime) {
    setID(id);
    this.allowsChildren = false;
    this.newsFeed = new DefaultListModel<>();
    sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
  }

  public void tweet(String message) {
    this.message = message;
    newsFeed.addElement("[" + this.getID() + "]: " + message);
    this.changeState = true;
    Date date = new Date(lastUpdateTime);
    System.out.println("User " + this.id + " updated at: " + sdf.format(date));
    notifyObservers();
  }

  public Object[] getMessages() {
    return this.newsFeed.toArray();
  }

  public Object[] getFollowers() {
    return this.followers.toArray();
  }

  public DefaultListModel<String> getNewsFeedListModel() {
    return newsFeed;
  }

  public Object[] getFollowing() {
    return this.following.toArray();
  }

  public DefaultListModel<Subject> getFollowingListModel() {
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
    Date date = new Date(lastUpdateTime);
    System.out.println("User " + this.id + " updated at: " + sdf.format(date));
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
    return this.message;
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
      System.out.println("User " + id + " contains spaces!");
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return this.getID();
  }
}
