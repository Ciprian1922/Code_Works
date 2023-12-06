public class Passenger extends Person
{
    private String passportNumber;

    public Passenger(String name, int age, String contactNumber, String passportNumber)
    {
        super(name, age, contactNumber);
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() 
    {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) 
    {
        this.passportNumber = passportNumber;
    }

    public String toString() 
    {
        return "Passenger [passportNumber=" + passportNumber +"]";
    }
}
