public class OutputDevice {
    public void writeMessage(String mess){
        System.out.println(mess);
    }
    public void writeMessage(int mess){
        String number = Integer.toString(mess);
        System.out.println(number);
    }
}