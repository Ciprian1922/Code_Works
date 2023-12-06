import java.util.Collection;
import java.util.List;;
public class Plane implements Comparable<Plane>
{
    private String planeId;
    private String model;
    private int capacity;
    private float topSpeed;
    private List<Passenger> passangers;


    public Plane(String planeId, String model, int capacity,float topSpeed, List<Passenger> passangers) 
    {
        this(planeId,model,capacity,topSpeed);
        this.passangers = passangers;
    }


    public Plane(String planeId, String model, int capacity,float topSpeed) 
    {
        this.planeId = planeId;
        this.model = model;
        this.capacity = capacity;
        this.topSpeed=topSpeed;
    }

    void addPassenger(Passenger passenger)
    {
        if(passenger==null)
            throw new NullPointerException("Passengers list is null. {Plane: "+planeId+"}");
        passangers.add(passenger);
    }

    void addPaggangers(Collection<Passenger> passangers)
    {
        for(Passenger p:passangers)
            this.passangers.add(p);
    }

    public String getPlaneId() 
    {
        return planeId;
    }


    public String getModel()
    {
        return model;
    }


    public int getCapacity() 
    {
        return capacity;
    }

    
    public float getTopSpeed() 
    {
        return topSpeed;
    }


    public List<Passenger> getPassangers() 
    {
        return passangers;
    }


    public void setPlaneId(String planeId) 
    {
        this.planeId = planeId;
    }


    public void setModel(String model) 
    {
        this.model = model;
    }


    public void setCapacity(int capacity) 
    {
        this.capacity = capacity;
    }

    public void setTopSpeed(float topSpeed) 
    {
        this.topSpeed = topSpeed;
    }



    public void setPassangers(List<Passenger> passangers) 
    {
        this.passangers = passangers;
    }


    public String toString() 
    {
        return "Plane: " + planeId + ", model=" + model + "\n Listing passangers...\n"+ passangers.toString() + "\n......................";
    }

    public int compareTo(Plane plane)
    {
        if(topSpeed<plane.topSpeed)
            return -1;
        if(topSpeed>plane.topSpeed)
            return 1;
        return 0;
    }
}
