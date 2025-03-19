package bookTrading;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class BookTradingMain {
    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        AgentContainer mainContainer = rt.createMainContainer(p);

        try {
            AgentController seller = mainContainer.createNewAgent("seller", "bookTrading.BookSellerAgent", null);
            seller.start();

            Object[] buyerArgs = new Object[]{"Java Programming"};
            AgentController buyer = mainContainer.createNewAgent("buyer", "bookTrading.BookBuyerAgent", buyerArgs);
            buyer.start();

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
