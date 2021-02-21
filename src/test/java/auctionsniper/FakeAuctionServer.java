package auctionsniper;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import org.assertj.core.api.Condition;
import org.assertj.core.api.HamcrestCondition;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class FakeAuctionServer {

  public static final String ITEM_ID_AS_LOGIN = "auction-%s";
  public static final String AUCTION_RESOURCE = "Auction";
  public static final String XMPP_HOSTNAME = "localhost";
  private static final String AUCTION_PASSWORD = "auction";

  private final String itemId;
  private final XMPPConnection connection;
  private Chat currentChat;

  private final SingleMessageListener messageListener = new SingleMessageListener();

  public FakeAuctionServer(String itemId) {
    this.itemId = itemId;
    this.connection = new XMPPConnection(XMPP_HOSTNAME);
  }

  public void startSellingItem() throws XMPPException {
    connection.connect();
    connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
    connection.getChatManager().addChatListener(
        (chat, createdLocally) -> {
          currentChat = chat;
          chat.addMessageListener(messageListener);
        }
    );
  }

  public void reportPrice(int price, int increment, String bidder) throws XMPPException {
    currentChat.sendMessage(
        String.format(
            "SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;",
            price, increment, bidder));
  }

  public void hasReceivedJoinRequestFrom(String sniperId) throws InterruptedException {
    receivesAMessageMatching(
        sniperId,
        new HamcrestCondition<>(equalTo(Main.JOIN_COMMAND_FORMAT)));
  }

  public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
    receivesAMessageMatching(
        sniperId,
        new HamcrestCondition<>(equalTo(String.format(Main.BID_COMMAND_FORMAT, bid))));
  }

  private void receivesAMessageMatching(String sniperId, Condition<? super String> condition)
      throws InterruptedException {
    messageListener.receivesAMessage(condition);
    assertThat(currentChat.getParticipant()).isEqualTo(sniperId);
  }

  public void announceClosed() throws XMPPException {
    currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;");
  }

  public void stop() {
    connection.disconnect();
  }

  public String getItemId() {
    return itemId;
  }
}
