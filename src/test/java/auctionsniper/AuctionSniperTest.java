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

  @Mock
  private Auction auction;

  @InjectMocks
  private AuctionSniper sniper;

  @Test
  void reports_lost_when_auction_closes() {
    sniper.auctionClosed();

    verify(sniperListener, times(1)).sniperLost();
  }

  @Test
  void bids_higher_and_reports_bidding_when_new_price_arrives() {
    final int price = 1001;
    final int increment = 25;

    sniper.currentPrice(price, increment);

    verify(auction, times(1)).bid(price + increment);
    verify(sniperListener, atLeastOnce()).sniperBidding();
  }
}