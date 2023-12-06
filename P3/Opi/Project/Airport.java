import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Airport implements Comparable<Airport>, Serializable
{
    private String airportName;
    private List<Terminal> terminals;
    private List<Plane> availablePlanes;

    public Airport(String airportName, List<Terminal> terminals, List<Plane> availablePlanes) 
    {
        this.airportName = airportName;
        this.terminals = terminals;
        this.availablePlanes = availablePlanes;
    }

    public String getAirportName() 
    {
        return airportName;
    }

    public List<Terminal> getTerminals() 
    {
        return terminals;
    }

    public List<Plane> getAvailablePlanes() 
    {
        return availablePlanes;
    }

    public void setAirportName(String airportName) 
    {
        this.airportName = airportName;
    }

    public void setTerminals(List<Terminal> terminals) 
    {
        this.terminals = terminals;
    }

    public void setAvailablePlanes(List<Plane> availablePlanes) 
    {
        this.availablePlanes = availablePlanes;
    }

    public String toString() 
    {
        return "Airport: " + airportName +"\n Listing Planes:\n"+ availablePlanes.toString() + "\n"+".........................";
    }

    public void sortTerminals()
    {
        if(terminals==null)
            throw new NullPointerException("Terminals list is null. {Airport: "+airportName+"}");
        Collections.sort(terminals);
    }

    public void sortAvailablePlanes()
    {
        if(availablePlanes==null)
            throw new NullPointerException("Available planes list is null. {Airport: "+airportName+"}");
        Collections.sort(availablePlanes);
    }

    public static float averageSpeed(Collection<Plane> planes)
    {
         float avg=0;
         for(Plane plane:planes)
             avg+=plane.getTopSpeed();
         avg/=planes.size();
         return avg;
    }

    public float getAverageSpeed()
    {
        return averageSpeed(availablePlanes);
    }

    public int compareTo(Airport airport)
    {
        float avg_speed_1=getAverageSpeed();
        float avg_speed_2=getAverageSpeed();

        if(avg_speed_1<avg_speed_2)
            return -1;
        if(avg_speed_1>avg_speed_2)
            return 1;
        return 0;
    }
}
