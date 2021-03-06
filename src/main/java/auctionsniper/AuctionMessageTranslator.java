package auctionsniper;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.AuctionEventListener.PriceSource;

public class AuctionMessageTranslator implements MessageListener {

  private final AuctionEventListener listener;
  private final String sniperId;

  public AuctionMessageTranslator(AuctionEventListener listener, String sniperId) {
    this.listener = listener;
    this.sniperId = sniperId;
  }

  @Override
  public void processMessage(Chat chat, Message message) {
    AuctionEvent event = AuctionEvent.from(message.getBody());

    String type = event.type();
    if ("CLOSE".equals(type)) {
      listener.auctionClosed();
    } else if ("PRICE".equals(type)) {
      listener.currentPrice(
          event.currentPrice(),
          event.increment(),
          event.isFrom(sniperId));
    }
  }

  private static class AuctionEvent {

    private final Map<String, String> fields = new HashMap<>();

    public String type() {
      return get("Event");
    }

    public int currentPrice() {
      return getInt("CurrentPrice");
    }

    public int increment() {
      return getInt("Increment");
    }

    private int getInt(String fieldName) {
      return Integer.parseInt(get(fieldName));
    }

    private String get(String fieldName) {
      return fields.get(fieldName);
    }

    private void addField(String field) {
      String[] pair = field.split(":");
      fields.put(pair[0].trim(), pair[1].trim());
    }

    static AuctionEvent from(String messageBody) {
      AuctionEvent event = new AuctionEvent();

      for (String field : fieldsIn(messageBody)) {
        event.addField(field);
      }
      return event;
    }

    static String[] fieldsIn(String messageBody) {
      return messageBody.split(";");
    }

    public PriceSource isFrom(String sniperId) {
      return sniperId.equals(bidder()) ? PriceSource.FromSniper : PriceSource.FromOtherBidder;
    }

    private String bidder() {
      return get("Bidder");
    }
  }
}
