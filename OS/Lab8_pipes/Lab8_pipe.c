#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
    if (argc < 4) {
        fprintf(stderr, "Usage: %s command1 arg1 arg2 ... '|' command2 arg1 arg2 ...\n", argv[0]);
        exit(EXIT_FAILURE);
    }
    //find the position of the pipe symbol '|'
    int pipe_index = -1;
    for (int i = 1; i < argc; ++i) {
        if (argv[i][0] == '|' && argv[i][1] == '\0') {
            pipe_index = i;
            break;
        }
    }
    //check if the pipe symbol exists
    if (pipe_index == -1) {
        fprintf(stderr, "Error: '|' not found in the command-line arguments.\n");
        exit(EXIT_FAILURE);
    }
    //setting up the commands and arguments for the first and second processes
    char **cmd1 = &argv[1];
    char **cmd2 = &argv[pipe_index + 1];
    //replace '|' with NULL to terminate the arguments for the first command
    argv[pipe_index] = NULL;

    //creating a pipe
    int pipe_fd[2];
    if (pipe(pipe_fd) == -1) {
        perror("pipe");
        exit(EXIT_FAILURE);
    }
    //fork the first child process (for the command before '|')
    pid_t pid1 = fork();

    if (pid1 == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    } else if (pid1 == 0) {
        //child process 1, set up stdout to write to the pipe
        close(pipe_fd[0]);  //close unused read end
        dup2(pipe_fd[1], STDOUT_FILENO);
        close(pipe_fd[1]);  //close the write end as it is now duplicated

        //executing the command
        execvp(cmd1[0], cmd1);
        perror("execvp");
        exit(EXIT_FAILURE);
    }

    //fork the second child process (for the command after '|')
    pid_t pid2 = fork();

    if (pid2 == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    } else if (pid2 == 0) {
        //child process 2 (command2)
        //set up stdin to read from the pipe
        close(pipe_fd[1]);  //close unused write end
        dup2(pipe_fd[0], STDIN_FILENO);
        close(pipe_fd[0]);  //close the read end as it is now duplicated

        //execute the command
        execvp(cmd2[0], cmd2);
        perror("execvp");
        exit(EXIT_FAILURE);
    }

    //parent process
    close(pipe_fd[0]);  //close unused read end in the parent
    close(pipe_fd[1]);  //close unused write end in the parent

    //wait for both child processes to complete
    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);

    return 0;
}