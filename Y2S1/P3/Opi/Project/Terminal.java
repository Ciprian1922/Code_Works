import java.io.Serializable;
import java.util.List;
public class Terminal implements Gate, Comparable<Terminal>,Serializable
{
    private String terminalName;
    private List<Gate> gates;
    private List<Facility> facilities;


    public Terminal(String terminalName, List<Gate> gates, List<Facility> facilities) 
    {
        this.terminalName = terminalName;
        this.gates = gates;
        this.facilities = facilities;
    }


    public String getTerminalName() 
    {
        return terminalName;
    }

    public List<Gate> getGates() 
    {
        return gates;
    }

    public List<Facility> getFacilities() 
    {
        return facilities;
    }


    public void setTerminalName(String terminalName) 
    {
        this.terminalName = terminalName;
    }


    public void setGates(List<Gate> gates) 
    {
        this.gates = gates;
    }


    public void setFacilities(List<Facility> facilities) 
    {
        this.facilities = facilities;
    }


    public String toString() 
    {
        return "Terminal [terminalName=" + terminalName + ", gates=" + gates + ", facilities=" + facilities + "]";
    }

    public String getGateNumber() 
    {
        return getTerminalName();
    }


    public void openGate() 
    {
        System.out.println("Terminal "+terminalName+" gate is open!");
    }

    public void closeGate() 
    {
        System.out.println("Terminal "+terminalName+" gate is closed!");
    }


    public int compareTo(Terminal terminal) 
    {
        int facilities_1=facilities.size();
        int facilities_2=terminal.facilities.size();

        if(facilities_1<facilities_2)
            return -1;
        if(facilities_1>facilities_2)
            return 1;
        return 0;
    }
}
