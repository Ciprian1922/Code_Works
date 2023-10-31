#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    int i;
    pid_t child_pid, wpid;
    int status;

    // Create 20 processes
    for (i = 0; i < 20; i++) {
        child_pid = fork();

        if (child_pid < 0) {
            perror("Fork failed");
            exit(1);
        }

        if (child_pid == 0) {
            // Child process
            printf("Child%d (PID: %d)\n", i, getpid());
            // Terminate the child process with a value (i+1)
            exit(i + 1);
        }
    }

    // The parent process waits for all the children
    while ((wpid = wait(&status)) > 0) {
        if (WIFEXITED(status)) {
            printf("Child with PID %d ended up with the value %d\n", wpid, WEXITSTATUS(status));
        }
    } 

    return 0;
}
