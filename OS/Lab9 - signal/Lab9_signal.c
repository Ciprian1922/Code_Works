#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

volatile int sigusr1_count = 0;

void sigalrm_handler(int signum) {
    // Check if it's the parent process
    if (getpid() > 1) {
        write(STDERR_FILENO, "Received SIGALRM\n", sizeof("Received SIGALRM\n") - 1);

        // Send SIGUSR1 to the second subprocess
        kill(0, SIGUSR1);
    }
}

void sigusr1_handler(int signum) {
    sigusr1_count++;

    char buffer[64];
    int n = snprintf(buffer, sizeof(buffer), "Received SIGUSR1 %d times\n", sigusr1_count);
    write(STDERR_FILENO, buffer, n);

    if (sigusr1_count == 4) {
        write(STDERR_FILENO, "Subprocess2 is exiting\n", sizeof("Subprocess2 is exiting\n") - 1);
        exit(sigusr1_count);
    }
}

int main() {
    // Set up SIGALRM handler
    if (signal(SIGALRM, sigalrm_handler) == SIG_ERR) {
        perror("signal(SIGALRM) error");
        exit(EXIT_FAILURE);
    }

    // Set up SIGUSR1 handler
    if (signal(SIGUSR1, sigusr1_handler) == SIG_ERR) {
        perror("signal(SIGUSR1) error");
        exit(EXIT_FAILURE);
    }

    pid_t pid1, pid2;

    // Subprocess 1
    if ((pid1 = fork()) == 0) {
        write(STDERR_FILENO, "Subprocess1 started\n", sizeof("Subprocess1 started\n") - 1);

        // Set an alarm to send SIGALRM every 5 seconds
        while (1) {
            // Reset the alarm before waiting for the next one
            alarm(0);
            sleep(5);
            alarm(1);
        }
    }

    // Subprocess 2
    if ((pid2 = fork()) == 0) {
        write(STDERR_FILENO, "Subprocess2 started\n", sizeof("Subprocess2 started\n") - 1);

        // Wait for SIGUSR1 four times
        while (sigusr1_count < 4) {
            pause();  // Wait for a signal
        }

        write(STDERR_FILENO, "Subprocess2 is exiting\n", sizeof("Subprocess2 is exiting\n") - 1);
        exit(sigusr1_count);
    }

    // Parent process
    write(STDERR_FILENO, "Parent process started\n", sizeof("Parent process started\n") - 1);

    // Set an initial alarm to trigger the first SIGALRM
    alarm(5);

    // Wait for the second subprocess to finish
    waitpid(pid2, NULL, 0);

    // Send SIGTERM to the first subprocess
    kill(pid1, SIGTERM);

    write(STDERR_FILENO, "Parent process is exiting\n", sizeof("Parent process is exiting\n") - 1);

    return 0;
}
