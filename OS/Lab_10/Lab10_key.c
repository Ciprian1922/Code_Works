#include <stdlib.h>
#include <signal.h>
#include <stdio.h>
#include <unistd.h>

//handle alarm signal
void handler(){
    printf("No key has been pressed =)).\n");
    exit(EXIT_SUCCESS);
}

int main() {
    //set up the alarm signal handler
    signal(SIGALRM, handler);

    while(True) {
        printf("Waiting for a key to be pressed...\n");
        //setting up the alarm for 10 seconds
        alarm(3);
        //waiting for input, you have to press enter
        char buffer[2];
        ssize_t theBytesRead = read(STDIN_FILENO, buffer, sizeof(buffer) - 1);
        //killing the alarm
        alarm(0);
        if (btheBytesRead > 0) {
            //printing the pressed key
            buffer[theBytesRead] = '\0';
            printf("The key that has been pressed: %s\n", buffer);
        } else {
            printf("No key pressed =((\n");
        }
    }
}