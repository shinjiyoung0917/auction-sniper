package auctionsniper;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
    Map<String, String> event = unpackEventFrom(message);

    String type = event.get("Event");
    if ("CLOSE".equals(type)) {
      listener.auctionClosed();
    } else if ("PRICE".equals(type)) {
      listener.currentPrice(
          Integer.parseInt(event.get("CurrentPrice")),
          Integer.parseInt(event.get("Increment")));
    }
  }

  private Map<String, String> unpackEventFrom(Message message) {
    return Arrays.stream(message.getBody().split(";"))
        .map(element -> element.split(":"))
        .collect(Collectors.toMap(pair -> pair[0].trim(), pair -> pair[1].trim()));
  }
}
