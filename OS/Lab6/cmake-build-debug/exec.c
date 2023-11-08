#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h> //file related constants
int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s <binary_file> <text_file>\n", argv[0]);
        exit(1);
    }
    char *binary_file = argv[1];
    char *text_file = argv[2];

    //create a subprocess to execute the binary file and redirect its output to a file
    int child_pid = fork();
    if (child_pid == 0) {
        // Child process
        // Redirect binary output to a file
        int fd = open("output_file", O_WRONLY | O_CREAT | O_TRUNC, 0644);
        if (fd == -1) {
            perror("open");
            exit(1);
        }
        dup2(fd, STDOUT_FILENO);//using this to duplicate it in the child process
        close(fd);

        // Execute the binary file
        execl(binary_file, binary_file, NULL);
        perror("execl");
        exit(1);
    } else if (child_pid == -1) {
        perror("fork");
        exit(1);
    }
    //second, wait for the child process to finish
    int status;
    waitpid(child_pid, &status, 0);

    if (WIFEXITED(status) && WEXITSTATUS(status) == 0) {
        //create another subprocess to compare the files
        int cmp_pid = fork();
        if (cmp_pid == 0) {
            //child process
            //compare the content of the output file and the text file
            execlp("diff", "diff", "output_file", text_file, NULL);
            perror("execlp");
            exit(1);
        } else if (cmp_pid == -1) {
            perror("fork");
            exit(1);
        }
        //wait for the comparison process to finish
        waitpid(cmp_pid, &status, 0);

        if (WIFEXITED(status) && WEXITSTATUS(status) == 0) {
            printf("The %s executable has the desired output <3\n", binary_file);
        } else {
            printf("ERROR: The %s executable does NOT have the desired output =/\n", binary_file);
        }
    } else {
        printf("ERROR: The %s executable failed to execute XO\n", binary_file);
    }
    return 0;
}
