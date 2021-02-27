package auctionsniper;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import auctionsniper.AuctionEventListener.PriceSource;

@ExtendWith(MockitoExtension.class)
class AuctionSniperTest {

  @Mock
  private SniperListener sniperListener;

  @Mock
  private Auction auction;

  @InjectMocks
  private AuctionSniper sniper;

  private SniperState sniperState = SniperState.IDLE;

  @Test
  void reports_lost_if_auction_closes_immediately() {
    sniper.auctionClosed();

    verify(sniperListener, times(1)).sniperLost();
  }

  @Test
  void reports_lost_if_auction_closes_when_bidding() {
    sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
    sniper.auctionClosed();

    ignoreStubs(auction);

    sniperListener.sniperBidding();
    sniperState = SniperState.BIDDING;
    verify(sniperListener, times(1)).sniperLost();

    //allowing(sniperListener).sniperBidding();
    //then(sniperState.is("bidding"));
    //verify(sniperListener, times(1)).sniperLost();
    //when(sniperState.is("bidding"));
  }

  @Test
  void bids_higher_and_reports_bidding_when_new_price_arrives() {
    final int price = 1001;
    final int increment = 25;

    sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);

    verify(auction, times(1)).bid(price + increment);
    verify(sniperListener, atLeastOnce()).sniperBidding();
  }
  
  @Test
  void reports_is_winning_when_current_price_comes_from_sniper() {
    sniper.currentPrice(123, 45, PriceSource.FromSniper);

    verify(sniperListener, atLeastOnce()).sniperWinning();
  }

  public enum SniperState {
    IDLE, BIDDING;
  }
}