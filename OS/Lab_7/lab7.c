#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

#define MSG_LENGTH 128
#define BUFFER_SIZE 8

int main() {
    pid_t p;
    int res, fd[2];
    char *msg;

    res = pipe(fd);
    if (res < 0) {
        printf("Could not create the pipe!\n");
        return 1;
    }

    p = fork();

    if (p < 0) {
        printf("Could not create the child process!\n");
        return 2;
    }

    if (p == 0) { // child
        msg = (char *)malloc(sizeof(char) * MSG_LENGTH);
        close(fd[0]); // we don't read from the pipe, we write into it

        printf("Enter a message for the child to display: ");
        fgets(msg, MSG_LENGTH, stdin);

        write(fd[1], msg, strlen(msg) + 1);
        close(fd[1]); // close the pipe
        wait(&res); // wait for the child to finish
        free(msg); // free memory
    } else { // parent
        char buffer[BUFFER_SIZE];
        int n, r = 0;
        close(fd[1]); // we are reading from the pipe, so no writing required

        while ((n = read(fd[0], buffer, BUFFER_SIZE - 1)) > 0) {
            if (!r) {
                printf("Parent received:\n");
                r = 1;
            }
            buffer[n] = '\0'; // Null-terminate the string
            printf("%s", buffer);
        }

        printf("\n");
        close(fd[0]);
    }

    return 0;
}
