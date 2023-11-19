public class Brain {
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    public Brain(InputDevice inputDevice, OutputDevice outputDevice){
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    public void action(boolean isDev){
        for(int i = 0; i < 5; i++)
            outputDevice.write(String.valueOf(i));
        outputDevice.write(String.valueOf(inputDevice.ceva));

        //developer option
        if(isDev){
            outputDevice.write(String.valueOf(inputDevice.altcevaceva));
        }
    }
    public void run(boolean isDev) {
        action(isDev);
    }
}
