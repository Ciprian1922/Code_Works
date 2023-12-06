#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

volatile int sigusr1_count = 0;

void sigalrm_handler(int signum) {
    //check if it's the parent process
    if (getpid() > 1) {
        fprintf(stderr, "Received SIGALRM!\n");
        //Send SIGUSR1 to the second subprocess
        kill(0, SIGUSR1);
    }
}

void sigusr1_handler(int signum) {
    sigusr1_count++;
    fprintf(stderr, "Received SIGUSR1 %d times\n", sigusr1_count);
    if (sigusr1_count == 4) {
        fprintf(stderr, "Subprocess2 is exiting...\n");
        exit(sigusr1_count);
    }
}

int main() {
    //set up SIGALRM handler
    if (signal(SIGALRM, sigalrm_handler) == SIG_ERR) {
        perror("signal(SIGALRM) error");
        exit(EXIT_FAILURE);
    }

    //set up SIGUSR1 handler
    if (signal(SIGUSR1, sigusr1_handler) == SIG_ERR) {
        perror("signal(SIGUSR1) error");
        exit(EXIT_FAILURE);
    }
    pid_t pid1, pid2;
    //subprocess 1
    if ((pid1 = fork()) == 0) {
        fprintf(stderr, "Subprocess1 started!\n");
        //set an alarm to send SIGALRM every 5 seconds
        while (1) {
            sleep(5);
            alarm(5);
        }
    }
    //subprocess 2
    if ((pid2 = fork()) == 0) {
        fprintf(stderr, "Subprocess2 started!\n");
        //wait for SIGUSR1 four times
        while (sigusr1_count < 4) {
            sleep(1);
        }
        fprintf(stderr, "Subprocess2 is exiting...\n");
        exit(sigusr1_count);
    }


    //parent process
    fprintf(stderr, "Parent process started!\n");

    //set an initial alarm to trigger the first SIGALRM
    alarm(5);

    //wait for the second subprocess to finish
    waitpid(pid2, NULL, 0);

    //send SIGTERM to the first subprocess
    kill(pid1, SIGTERM);

    fprintf(stderr, "Parent process is exiting...\n");

    return 0;
}
