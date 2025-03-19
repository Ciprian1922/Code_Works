package bookTrading;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import javax.swing.JOptionPane;

public class BookBuyerAgent extends Agent {
    private String targetBookTitle;
    private AID[] sellerAgents = {new AID("seller", AID.ISLOCALNAME)};
    private BuyerGui gui;

    protected void setup() {
        System.out.println("Buyer agent " + getAID().getName() + " is ready.");
        gui = new BuyerGui(this);
    }

    public void startBuyingProcess(String bookTitle) {
        this.targetBookTitle = bookTitle;
        addBehaviour(new TickerBehaviour(this, 5000) {
            protected void onTick() {
                System.out.println("Trying to buy: " + targetBookTitle);
                myAgent.addBehaviour(new RequestPerformer());
            }
        });
    }

    private class RequestPerformer extends Behaviour {
        private AID bestSeller;
        private int bestPrice = Integer.MAX_VALUE;  // Ensure it starts high
        private int repliesCnt = 0;
        private MessageTemplate mt;
        private int step = 0;

        public void action() {
            switch (step) {
                case 0:
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (AID seller : sellerAgents) {
                        cfp.addReceiver(seller);
                    }
                    cfp.setContent(targetBookTitle);
                    cfp.setConversationId("book-trade");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis());
                    myAgent.send(cfp);
                    System.out.println("Sent CFP for: " + targetBookTitle);

                    mt = MessageTemplate.and(
                            MessageTemplate.MatchConversationId("book-trade"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;

                case 1:
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            int price = Integer.parseInt(reply.getContent());
                            System.out.println("Received offer: $" + price + " from " + reply.getSender().getLocalName());

                            if (bestSeller == null || price < bestPrice) {
                                bestPrice = price;
                                bestSeller = reply.getSender();
                            }
                        }
                        repliesCnt++;
                        if (repliesCnt >= sellerAgents.length) {
                            step = 2;
                        }
                    } else {
                        block();
                    }
                    break;

                case 2:
                    if (bestSeller != null) {
                        ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                        order.addReceiver(bestSeller);
                        order.setContent(targetBookTitle);
                        order.setConversationId("book-trade");
                        order.setReplyWith("order" + System.currentTimeMillis());
                        myAgent.send(order);
                        System.out.println("Sent order to " + bestSeller.getLocalName() + " for $" + bestPrice);

                        mt = MessageTemplate.and(
                                MessageTemplate.MatchConversationId("book-trade"),
                                MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                        step = 3;
                    } else {
                        System.out.println("No valid offers received.");
                        step = 4;
                    }
                    break;

                case 3:
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println(targetBookTitle + " successfully purchased from agent " + reply.getSender().getLocalName());
                            System.out.println("Final Price = $" + bestPrice);
                            myAgent.doDelete();
                        } else {
                            System.out.println("Purchase failed: " + reply.getContent());
                        }
                        step = 4;
                    } else {
                        block();
                    }
                    break;
            }
        }

        public boolean done() {
            return (step == 2 && bestSeller == null) || step == 4;
        }
    }
}
