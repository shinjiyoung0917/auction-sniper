package auctionsniper;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class SingleMessageListener implements MessageListener {

  private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

  @Override
  public void processMessage(Chat chat, Message message) {
    messages.add(message);
  }

  public void receivesAMessage() throws InterruptedException {
    assertThat(messages.poll(5, TimeUnit.SECONDS))
        .isNotNull();
  }
}
