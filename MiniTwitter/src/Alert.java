import javax.swing.*;

public class Alert {

  public void alert(String info, String title) {
    JOptionPane.showMessageDialog(null, info, title, JOptionPane.INFORMATION_MESSAGE);
  }
}
