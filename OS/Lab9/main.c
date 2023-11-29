#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>

void sig_handler(int signo) {
    if (signo == SIGALRM) {
        printf("Received SIGALRM\n");

        // Send SIGTSTP to subprocess2
        kill(getpid() + 2, SIGTSTP);

        // Wait for a while
        sleep(10);

        // Send SIGINT to subprocess3
        kill(getpid() + 3, SIGINT);
    } else if (signo == SIGTSTP) {
        printf("Subprocess2 received SIGTSTP\n");
        // Handle SIGTSTP-specific logic here
    } else if (signo == SIGINT) {
        printf("Subprocess3 received SIGINT\n");
        // Handle SIGINT-specific logic here
    }
}

void subprocess_logic(int subprocess_number) {
    printf("Subprocess%d started\n", subprocess_number);

    // Handle specific logic for each subprocess
    int count = 0;
    while (count < 10) {  // Adjust the termination condition as needed
        // Subprocess-specific logic here
        sleep(1);
        count++;
    }

    printf("Subprocess%d is exiting\n", subprocess_number);
    exit(0);
}


int main() {
    pid_t pid1, pid2, pid3;

    // Set up signal handler
    signal(SIGALRM, sig_handler);
    signal(SIGTSTP, sig_handler);
    signal(SIGINT, sig_handler);

    // Fork subprocess1
    pid1 = fork();

    if (pid1 == 0) {
        subprocess_logic(1);
        exit(0);
    }

    // Fork subprocess2
    pid2 = fork();

    if (pid2 == 0) {
        subprocess_logic(2);
        exit(0);
    }

    // Fork subprocess3
    pid3 = fork();

    if (pid3 == 0) {
        subprocess_logic(3);
        exit(0);
    }

    // Set the alarm to be triggered every 10 seconds
    alarm(10);

    // Wait for subprocess2 and subprocess3 to finish
    waitpid(pid2, NULL, 0);
    waitpid(pid3, NULL, 0);

    // Send SIGKILL to subprocess1
    kill(pid1, SIGKILL);

    return 0;
}
