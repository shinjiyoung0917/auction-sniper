package auctionsniper;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuctionSniperTest {

  @Mock
  private SniperListener sniperListener;

  @InjectMocks
  private AuctionSniper sniper;

  @Test
  void reports_lost_when_auction_closes() {
    sniper.auctionClosed();

    verify(sniperListener, times(1)).sniperLost();
  }
}