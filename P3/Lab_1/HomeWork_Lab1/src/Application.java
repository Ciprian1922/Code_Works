import java.util.Random;

public class Application {
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    private Player playerH;
    private Player playerS;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        this.playerH = new Player();
        this.playerS = new Player();
    }

    public void playGame() {
        int p1 = 0;
        int p2 = 0;
        Random random = new Random();

        while (p1 < 5 && p2 < 5) {
            int H = random.nextInt(100) + 1;
            int S = random.nextInt(100) + 1;

            if (H == S) {
                p1 += 2;
                p2 += 2;
            } else if (S >= 10 && H % S == 0) {
                p2++;
            } else if (H >= 10 && S % H == 0) {
                p1++;
            }

            outputDevice.writeMessage("***********************");
            outputDevice.writeMessage("Player H:" + p1 + ", He picked: " + H);
            outputDevice.writeMessage("Player S:" + p2 + ", He picked: " + S);
        }

        if (p1 == p2) {
            outputDevice.writeMessage("Tie");
        } else if (p1 > p2) {
            outputDevice.writeMessage("Player H won!");
        } else {
            outputDevice.writeMessage("Player S won!");
        }
    }

    public void run() {
        outputDevice.writeMessage("Application started");
        outputDevice.writeMessage("Today’s lucky numbers are: ");
        playGame();
    }
}
