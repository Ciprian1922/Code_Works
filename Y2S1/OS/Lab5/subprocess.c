#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>

void createChildProcesses(int num_processes, int parent_fd) {
    for (int i = 1; i <= num_processes; i++) {
        pid_t child_pid = fork();

        if (child_pid == 0) {
            dup2(parent_fd, STDOUT_FILENO);
            printf(" -> Child %d, PID = %d\n", i, getpid());
            exit(i);

        } else if (child_pid > 0) {
            int status;
            waitpid(child_pid, &status, 0);

            if (WIFEXITED(status)) {
                printf("Parent received value %d from Child %d and printed it to the file with FD %d. \n", WEXITSTATUS(status), i, parent_fd);
            } else {
                printf("Error in child process %d\n", i);
            }
        } else {
            perror("The fork failed");
            exit(1);
        }
    }
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <processes number>\n", argv[0]);
        return 1;
    }

    int num_processes = atoi(argv[1]);
    if (num_processes <= 0) {
        fprintf(stderr, "Invalid number of processes.\n");
        return 1;
    }
    printf(" ***Creating %d processes*** \n", num_processes);

    int parent_fd = open("out.txt", O_WRONLY | O_CREAT | O_TRUNC, 0666);
    if (parent_fd == -1) {
        perror("Error opening the file");
        return 1;
    }
    createChildProcesses(num_processes, parent_fd);
    close(parent_fd);

    return 0;
}