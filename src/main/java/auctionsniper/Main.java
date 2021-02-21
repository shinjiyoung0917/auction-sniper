package auctionsniper;

import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.ui.MainWindow;

public class Main {

  private static final int ARG_HOSTNAME = 0;
  private static final int ARG_USERNAME = 1;
  private static final int ARG_PASSWORD = 2;
  private static final int ARG_ITEM_ID = 3;

  public static final String AUCTION_RESOURCE = "Auction";
  public static final String ITEM_ID_AS_LOGIN = "auction-%s";
  public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

  public static final String MAIN_WINDOW_NAME = "Auction Sniper";

  private MainWindow ui;

  public Main() throws InvocationTargetException, InterruptedException {
    startUserInterface();
  }

  public static void main(String... args) throws InvocationTargetException, InterruptedException, XMPPException {
    Main main = new Main();
    XMPPConnection connection = connectTo(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
    Chat chat = connection.getChatManager().createChat(
        auctionId(args[ARG_ITEM_ID], connection),
        (aChat, message) -> {
          // 아직 아무것도 없다.
        }
    );
    chat.sendMessage(new Message());
  }

  private static XMPPConnection connectTo(String hostname, String username, String password) throws XMPPException {
    XMPPConnection connection = new XMPPConnection(hostname);
    connection.connect();
    connection.login(username, password, AUCTION_RESOURCE);

    return connection;
  }

  private static String auctionId(String itemId, XMPPConnection connection) {
    return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
  }

  private void startUserInterface() throws InvocationTargetException, InterruptedException {
    SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
  }
}
