public interface Subject {

  void register(Observer o);

  void notifyObservers();

  String getUpdate(Observer o);
}
