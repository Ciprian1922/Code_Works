#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    int pipe_fd[2];
    pid_t pid;

    // Create a pipe
    if (pipe(pipe_fd) == -1) {
        perror("Pipe creation failed");
        exit(EXIT_FAILURE);
    }

    // Fork a child process
    pid = fork();

    if (pid == -1) {
        perror("Fork failed");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) {
        // Child process (left side of the pipe)
        close(pipe_fd[0]);  // Close the read end of the pipe

        // Redirect stdout to the write end of the pipe
        dup2(pipe_fd[1], STDOUT_FILENO);
        close(pipe_fd[1]);  // Close the duplicated file descriptor

        // Execute the first command
        execlp("echo", "echo", "Hello, World!", NULL);
        perror("Execution of echo failed");
        exit(EXIT_FAILURE);
    } else {
        // Parent process
        close(pipe_fd[1]);  // Close the write end of the pipe

        // Redirect stdin to the read end of the pipe
        dup2(pipe_fd[0], STDIN_FILENO);
        close(pipe_fd[0]);  // Close the duplicated file descriptor

        // Execute the second command
        execlp("wc", "wc", "-w", NULL);
        perror("Execution of wc failed");
        exit(EXIT_FAILURE);
    }

    return 0;
}

