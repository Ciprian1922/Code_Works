#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

pid_t parent_pid, child2_pid, child3_pid;

int main() {
    // Parent process
    pid_t pid1, pid2, pid3;

    parent_pid = getpid();  // Store parent PID

    int pipefd[2];
    if (pipe(pipefd) == -1) {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    pid1 = fork();

    if (pid1 == 0) {
        // First child process
        printf("Parent PID from process 1: %d\n", parent_pid);
    } else if (pid1 > 0) {
        // Parent continues
        child2_pid = pid2 = fork();

        if (pid2 == 0) {
            // Second child process
            close(pipefd[1]);  // Close write end of the pipe in the second child

            // Read the PID of the third process from the pipe
            read(pipefd[0], &pid3, sizeof(pid3));
            close(pipefd[0]);  // Close read end of the pipe in the second child

            printf("Third child PID from process two: %d\n", pid3);
        } else if (pid2 > 0) {
            // Parent continues
            child3_pid = pid3 = fork();

            if (pid3 == 0) {
                // Third child process
                printf("Third child PID(from the process 3): %d\n", getpid());
            } else {
                // Parent writes the PID of the third process to the pipe
                close(pipefd[0]);  // Close read end of the pipe in the parent
                write(pipefd[1], &pid3, sizeof(pid3));
                close(pipefd[1]);  // Close write end of the pipe in the parent
            }
        }
    }

    // Parent process waits for all children to finish
    for (int i = 0; i < 3; ++i) {
        wait(NULL);
    }

    return 0;
}

