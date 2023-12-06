import java.io.Serializable;

public class Flight implements Serializable
{
    private String flightNumber;
    private Location departureLocation;
    private Location arrivalLocation;

    public Flight(String flightNumber, Location departureLocation, Location arrivalLocation) 
    {
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    public void setFlightNumber(String flightNumber) 
    {
        this.flightNumber = flightNumber;
    }

    public void setDepartureLocation(Location departureLocation) 
    {
        this.departureLocation = departureLocation;
    }

    public void setArrivalLocation(Location arrivalLocation) 
    {
        this.arrivalLocation = arrivalLocation;
    }

    public String toString() 
    {
        return "Flight [flightNumber=" + flightNumber + ", departureLocation=" + departureLocation+ ", arrivalLocation=" + arrivalLocation + "]";
    }
}
