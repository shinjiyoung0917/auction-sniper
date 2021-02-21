package auctionsniper.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import auctionsniper.Main;

public class MainWindow extends JFrame {

  public static final String STATUS_JOINING = "Joining";
  public static final String STATUS_LOST = "Lost";

  public static final String SNIPER_STATUS_NAME = "sniper status";
  private final JLabel sniperStatus = createLabel(STATUS_JOINING);

  public MainWindow() {
    super("Auction Sniper");
    setName(Main.MAIN_WINDOW_NAME);
    add(sniperStatus);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void showStatus(String status) {
    sniperStatus.setText(status);
  }

  private static JLabel createLabel(String initialText) {
    JLabel result = new JLabel(initialText);
    result.setName(SNIPER_STATUS_NAME);
    result.setBorder(new LineBorder(Color.BLACK));
    return result;
  }
}
