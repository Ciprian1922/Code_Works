#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>

int main(int argc, char *argv[]) {
    if (argc < 4 || strcmp(argv[2], "|") != 0) {
        fprintf(stderr, "Usage: %s <command1> | <command2>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    // Create a pipe
    int pipe_fd[2];
    if (pipe(pipe_fd) == -1) {
        perror("Pipe creation failed");
        exit(EXIT_FAILURE);
    }

    // Fork the first process
    pid_t pid1 = fork();

    if (pid1 == -1) {
        perror("Fork failed");
        exit(EXIT_FAILURE);
    }

    if (pid1 == 0) {
        // Child process 1 (left side of the pipe)
        close(pipe_fd[0]); // Close the read end of the pipe

        // Redirect stdout to the write end of the pipe
        dup2(pipe_fd[1], STDOUT_FILENO);
        close(pipe_fd[1]); // Close the duplicated file descriptor

        // Execute the first command
        if (execvp(argv[1], &argv[1]) == -1) {
            perror("Execution of command1 failed");
            exit(EXIT_FAILURE);
        }
    } else {
        // Parent process
        // Fork the second process
        pid_t pid2 = fork();

        if (pid2 == -1) {
            perror("Fork failed");
            exit(EXIT_FAILURE);
        }

        if (pid2 == 0) {
            // Child process 2 (right side of the pipe)
            close(pipe_fd[1]); // Close the write end of the pipe

            // Redirect stdin to the read end of the pipe
            dup2(pipe_fd[0], STDIN_FILENO);
            close(pipe_fd[0]); // Close the duplicated file descriptor

            // Execute the second command
            if (execvp(argv[3], &argv[3]) == -1) {
                perror("Execution of command2 failed");
                exit(EXIT_FAILURE);
            }
        } else {
            // Parent process
            close(pipe_fd[0]); // Close unused read end of the pipe
            close(pipe_fd[1]); // Close unused write end of the pipe

            // Wait for both child processes to finish
            waitpid(pid1, NULL, 0);
            waitpid(pid2, NULL, 0);
        }
    }

    return 0;
}
