#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

void sig_handler(int signo) {
    if (signo == SIGALRM) {
        printf("Received SIGALRM\n");
    } else if (signo == SIGTSTP) {
        printf("Received SIGTSTP\n");
    } else if (signo == SIGINT) {
        printf("Received SIGINT\n");
        for (int i = 0; i < 10; ++i) {
            sleep(1); // Wait for a total of 10 seconds
        }
    }
}

int main() {
    pid_t pid1, pid2, pid3;

    if ((pid1 = fork()) == 0) {
        // Subprocess1
        printf("Subprocess1 started\n");
        while (1) {
            sleep(1);
        }
    }

    if ((pid2 = fork()) == 0) {
        // Subprocess2
        printf("Subprocess2 started\n");
        signal(SIGTSTP, sig_handler);
        while (1) {
            sleep(1);
        }
    }

    if ((pid3 = fork()) == 0) {
        // Subprocess3
        printf("Subprocess3 started\n");
        signal(SIGINT, sig_handler);
        while (1) {
            sleep(1);
        }
    }

    // Main process
    signal(SIGALRM, sig_handler);

    alarm(10); // Set the initial alarm

    sleep(10); // Wait for 10 seconds
    printf("Main process sending SIGTSTP to Subprocess2\n");
    kill(pid2, SIGTSTP);

    sleep(10); // Wait for another 10 seconds
    printf("Main process sending SIGINT to Subprocess3\n");
    kill(pid3, SIGINT);

    // Wait for subprocesses to finish
    waitpid(pid2, NULL, 0);
    waitpid(pid3, NULL, 0);

    // Send SIGKILL to Subprocess1
    printf("Main process sending SIGKILL to Subprocess1\n");
    kill(pid1, SIGKILL);

    // Wait for Subprocess1 to finish
    waitpid(pid1, NULL, 0);

    return 0;
}
