package auctionsniper;

import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import auctionsniper.ui.MainWindow;

public class Main {

  public static final String MAIN_WINDOW_NAME = "Auction Sniper";

  private MainWindow ui;

  public Main() throws InvocationTargetException, InterruptedException {
    startUserInterface();
  }

  public static void main(String... args) throws InvocationTargetException, InterruptedException {
    Main main = new Main();
  }

  private void startUserInterface() throws InvocationTargetException, InterruptedException {
    SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
  }
}
