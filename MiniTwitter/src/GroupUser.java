public class GroupUser extends User {

  private String groupID;

  public GroupUser(String id, long creationTime) {
    setID(id);
  }

  public void insert(User user, int childIndex) {
    super.insert(user, childIndex);
  }

  @Override
  public void setID(String id) {
    this.groupID = id;
  }

  @Override
  public String getID() {
    return this.groupID;
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
