import java.io.Serializable;

public class Location implements Serializable
{
    private String city;
    private String country;
    private Vector3 coordinates;

    public Location(String city, String country, Vector3 coordinates) 
    {
        this.city = city;
        this.country = country;
        this.coordinates = coordinates;
    }

    public String getCity() 
    {
        return city;
    }

    public String getCountry() 
    {
        return country;
    }

    public Vector3 getCoordinates() 
    {
        return coordinates;
    } 

    public void setCity(String city) 
    {
        this.city = city;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }
    public void setCoordinates(Vector3 coordinates) 
    {
        this.coordinates = coordinates;
    }

    public String toString()
    {
        return "Location [city=" + city + ", country=" + country + ", coordinates=" + coordinates + "]";
    }
}