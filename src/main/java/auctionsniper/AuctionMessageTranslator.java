package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {

  private final AuctionEventListener listener;

  public AuctionMessageTranslator(AuctionEventListener lister) {
    this.listener = lister;
  }

  @Override
  public void processMessage(Chat chat, Message message) {
    listener.auctionClosed();
  }
}
