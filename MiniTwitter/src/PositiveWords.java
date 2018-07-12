import java.util.Arrays;

public class PositiveWords implements Visitor {

  private int positiveMessages = 0;
  private int messageCount = 0;
  private String[] positiveWords;

  public PositiveWords(String positiveWords) {
    this.positiveWords = positiveWords.toLowerCase().split(" ");
  }

  public PositiveWords(String[] positiveWords) {
    for(int i = 0; i < positiveWords.length; i++) {
      positiveWords[i] = positiveWords[i].toLowerCase();
    }
    this.positiveWords = positiveWords;
  }

  @Override
  public void visit(User node) {
    if(node instanceof SingleUser){
      Object[] array = ((SingleUser) node).getMessages();
      String[] messages = Arrays.copyOf(array, array.length, String[].class);
      messageCount += messages.length;
      for(String currentKeyWord: positiveWords) {
        for(String currentMessage:messages) {
          if(currentMessage.toLowerCase().contains(currentKeyWord)) {
            positiveMessages++;
          }
        }
      }
    }
  }

  public double result() {
    return (positiveMessages * 100.0 / messageCount);
  }
}
