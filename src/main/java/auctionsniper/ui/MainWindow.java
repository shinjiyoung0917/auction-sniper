package auctionsniper.ui;

import javax.swing.*;

import auctionsniper.Main;

public class MainWindow extends JFrame {

  public MainWindow() {
    super("Auction Sniper");
    setName(Main.MAIN_WINDOW_NAME);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
}
