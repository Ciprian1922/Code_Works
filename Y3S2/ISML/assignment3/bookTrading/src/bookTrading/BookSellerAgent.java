package bookTrading;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.Map;

public class BookSellerAgent extends Agent {
    private Map<String, Integer> bookCatalog = new HashMap<>();

    protected void setup() {
        System.out.println("Seller agent " + getAID().getName() + " is ready.");

        // Add books
        bookCatalog.put("Java Programming", 50);
        bookCatalog.put("Artificial Intelligence", 70);

        addBehaviour(new OfferRequestsServer());
    }

    private class OfferRequestsServer extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                String bookTitle = msg.getContent();
                ACLMessage reply = msg.createReply();

                if (msg.getPerformative() == ACLMessage.CFP) {
                    // Handle book request
                    if (bookCatalog.containsKey(bookTitle)) {
                        int price = bookCatalog.get(bookTitle);
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContent(String.valueOf(price));
                        System.out.println("Offering book: " + bookTitle + " for $" + price);
                    } else {
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("Not available");
                    }
                    send(reply);
                }
                else if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    // Handle book purchase
                    if (bookCatalog.containsKey(bookTitle)) {
                        bookCatalog.remove(bookTitle); // Remove from catalog since it's sold
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Sold");
                        System.out.println("Book " + bookTitle + " sold to " + msg.getSender().getLocalName());
                    } else {
                        reply.setPerformative(ACLMessage.FAILURE);
                        reply.setContent("Book already sold");
                    }
                    send(reply);
                }
            } else {
                block();
            }
        }
    }

    public void updateCatalogue(String title, int price) {
        bookCatalog.put(title, price);
        System.out.println("Added to catalogue: " + title + " - $" + price);
    }

}
