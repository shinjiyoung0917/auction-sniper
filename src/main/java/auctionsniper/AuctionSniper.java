package auctionsniper;

public class AuctionSniper implements AuctionEventListener {

  private final Auction auction;
  private final SniperListener sniperListener;

  public AuctionSniper(Auction auction, SniperListener sniperListener) {
    this.auction = auction;
    this.sniperListener = sniperListener;
  }

  @Override
  public void currentPrice(int price, int increment) {
    // TODO: 자동 생성된 메서드 스텁.
  }

  @Override
  public void auctionClosed() {
    sniperListener.sniperLost();
  }
}
