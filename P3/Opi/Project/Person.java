import java.io.Serializable;

public class Person implements Comparable<Person>, Serializable
{
    private String name;
    private int age;
    private String contactNumber;


    public Person(String name, int age, String contactNumber) 
    {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }


    public String getName() 
    {
        return name;
    }


    public int getAge() 
    {
        return age;
    }


    public String getContactNumber() 
    {
        return contactNumber;
    }


    public void setName(String name) 
    {
        this.name = name;
    }


    public void setAge(int age) 
    {
        this.age = age;
    }


    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public String toString() 
    {
        return "Person [name=" + name + ", age=" + age + ", contactNumber=" + contactNumber + "]";
    }

    public int compareTo(Person person)
    {
        if(age>person.age)
        return -1;
        if(age<person.age)
            return 1;
        return 0;
    }
}
