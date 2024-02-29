import java.io.Serializable;

public class Ticket implements Serializable
{
    private int ticketNumber;
    private Passenger passenger;
    private Flight flight;

    public Ticket(int ticketNumber, Passenger passenger, Flight flight) 
    {
        this.ticketNumber = ticketNumber;
        this.passenger = passenger;
        this.flight = flight;
    }

    public int getTicketNumber() 
    {
        return ticketNumber;
    }

    public Passenger getPassenger() 
    {
        return passenger;
    }

    public Flight getFlight() 
    {
        return flight;
    }


    public void setTicketNumber(int ticketNumber) 
    {
        this.ticketNumber = ticketNumber;
    }

    public void setPassenger(Passenger passenger)
    {
        this.passenger = passenger;
    }

    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }


    public String toString() 
    {
        return "Ticket [ticketNumber=" + ticketNumber + ", passenger=" + passenger + ", flight=" + flight + "]";
    }
}
