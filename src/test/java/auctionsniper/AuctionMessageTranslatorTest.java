package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.jupiter.api.Test;

public class AuctionMessageTranslatorTest {

  public static final Chat UNUSED_CHAT = null;
  private final AuctionMessageTranslator translator = new AuctionMessageTranslator();

  @Test
  void notifiesAuctionClosedWhenCloseMessageReceived() {
    Message message = new Message();
    message.setBody("SOLVersion: 1.1; Event: CLOSE;");

    translator.processMessage(UNUSED_CHAT, message);
  }
}
