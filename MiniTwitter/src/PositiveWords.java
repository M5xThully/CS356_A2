import java.util.Arrays;

public class PositiveWords implements Visitor {

  private int positiveMessages = 0;
  private int tweetCount = 0;
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
      Object[] array = ((SingleUser) node).getTweets();
      String[] tweets = Arrays.copyOf(array, array.length, String[].class);
      tweetCount += tweets.length;
      
      for(String currentKeyWord: positiveWords) {
        for(String currentTweet:tweets) {
          if(currentTweet.toLowerCase().contains(currentKeyWord)) {
            positiveMessages++;
          }
        }
      }
    }
  }

  public double result() {
    return (positiveMessages * 100.0 / tweetCount);
  }
}
