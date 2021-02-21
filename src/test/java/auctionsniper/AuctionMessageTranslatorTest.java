package auctionsniper;

import static org.mockito.Mockito.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuctionMessageTranslatorTest {

  public static final Chat UNUSED_CHAT = null;

  @Mock
  private AuctionEventListener lister;

  @InjectMocks
  private AuctionMessageTranslator translator;

  @Test
  void notifiesAuctionClosedWhenCloseMessageReceived() {
    Message message = new Message();
    message.setBody("SOLVersion: 1.1; Event: CLOSE;");

    translator.processMessage(UNUSED_CHAT, message);

    verify(lister, times(1)).auctionClosed();
  }
}
