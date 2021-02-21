package auctionsniper;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Condition;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class SingleMessageListener implements MessageListener {

  private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

  @Override
  public void processMessage(Chat chat, Message message) {
    messages.add(message);
  }

  public void receivesAMessage(Condition<? super String> cond) throws InterruptedException {
    final Message message = messages.poll(5, TimeUnit.SECONDS);
    assertThat(message).isNotNull();
    assertThat(message.getBody()).is(cond);
  }
}
