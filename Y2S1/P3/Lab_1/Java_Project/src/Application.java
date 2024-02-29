public class Application{
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    public Application(InputDevice inp, OutputDevice out){
        this.inputDevice = inp;
        this.outputDevice = out;
    }
    public void run(){
        outputDevice.writeMessage("Application started");
        outputDevice.writeMessage("Today’s lucky numbers are: ");
        for(int i = 0 ; i < 2 ; i++) {
            outputDevice.writeMessage(inputDevice.nextInt());
        }
    }
}
