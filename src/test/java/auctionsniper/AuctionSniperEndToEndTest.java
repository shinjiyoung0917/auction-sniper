package auctionsniper;

import org.jivesoftware.smack.XMPPException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class AuctionSniperEndToEndTest {

  private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
  private final ApplicationRunner application = new ApplicationRunner();

  @Test
  @Order(1)
  void sniperJoinsAuctionUntilAuctionCloses() throws XMPPException, InterruptedException {
    /*
    1. 경매에서 품목을 판매하고
    2. 경매 스나이터가 해당 경매에서 입찰을 시작하면
    3. 경매에서는 경매 스나이퍼로부터 Join 요청을 받을 것임
    4. 경매가 Close 됐다고 선언되면
    5. 경매 스나이퍼는 경매에서 낙찰에 실패했음을 보여줄 것임
     */
    auction.startSellingItem();
    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }

  @Test
  @Order(2)
  void sniperMakesAHigherBidButLoses() throws XMPPException, InterruptedException {
    auction.startSellingItem();

    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);

    auction.reportPrice(1000, 98, "other bidder");
    application.hasShownSniperIsBidding();

    auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

    auction.announceClosed();
    application.showsSniperHasLostAuction();
  }

  @Test
  void sniperWinsAnAuctionByBiddingHigher() throws Exception {
    auction.startSellingItem();

    application.startBiddingIn(auction);
    auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);

    auction.reportPrice(1000, 98, "other bidder");
    application.hasShownSniperIsBidding();

    auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

    auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
    application.hasShownSniperIsWinning();

    auction.announceClosed();
    application.showsSniperHasWonAuction();
  }

  @AfterEach
  void stopAuctionAndApplication() {
    auction.stop();
    application.stop();
  }
}
