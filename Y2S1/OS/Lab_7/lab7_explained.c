#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

#define MSG_LENGTH 128   // Maximum length for the message to be sent through the pipe.
#define BUFFER_SIZE 2   // Size of the buffer used for reading from the pipe.

int main() {
    pid_t p;        // Variable to store the process ID of the forked process.
    int res, fd[2]; // 'res' for storing results (return codes) and 'fd' for file descriptors of the pipe.
    char *msg;      // Pointer to char, used to store the message to be sent through the pipe.

    res = pipe(fd);//This line creates a pipe, which is a unidirectional communication channel.
                  //fd is an array of file descriptors, where fd[0] is for reading and fd[1] is for writing.
                  
    // Check if the creation of the pipe was successful.
    if (res < 0) {
        printf("Could not create the pipe!\n");  // Print an error message if pipe creation fails.
        return 1;  // Return 1 to indicate an error condition to the calling process.
    }

    p = fork();//This line forks the process into a parent and a child. After this line, both
              //the parent and child will continue executing from this point, but they will have different pid values.

    if (p < 0) {
        printf("Could not create the child process!\n");
        return 2;
    }

    if (p == 0) { // This block is executed by the child process.
        msg = (char *)malloc(sizeof(char) * MSG_LENGTH); // Allocates memory to store the
        //message that the child will send to the parent. MSG_LENGTH defines the maximum
        //length of the message.
        close(fd[0]); // The child closes the reading end of the pipe because it will only
        //write into it.

        printf("Enter a message for the child to display: ");
        fgets(msg, MSG_LENGTH, stdin); // Read message from the user

        write(fd[1], msg, strlen(msg) + 1); // Write the message to the pipe,The +1 accounts 
        //for the null terminator of the string.
        close(fd[1]); //Closes the writing end of the pipe to indicate that the child has 
        //finished writing.
        wait(&res); //The parent will wait for the child to finish. The exit status of 
        //the child process will be stored in res.
        free(msg); // Free allocated memory for the message.
    } else { // This block is executed by the parent process.
        char buffer[BUFFER_SIZE]; //Defines a buffer to store the received message.
        int n, r = 0; //: n will store the number of bytes read from the pipe, 
                     //and r is a flag to print the header only once.
        close(fd[1]); // The parent closes the writing end of the pipe because it will
                     //only read from it.

        while ((n = read(fd[0], buffer, BUFFER_SIZE - 1)) > 0) { //: Reads from the pipe until no
               //more data is available. The BUFFER_SIZE - 1 is used to leave space for a null terminator.
        // Check if 'r' is false (0) to print the header only once when the parent starts receiving the message.
        if (!r) {
            printf("Parent received:\n");  // Print a header indicating the start of the received message.
            r = 1;  // Set 'r' to true (1) to indicate that the header has been printed.
        }

            buffer[n] = '\0'; //Null-terminates the string to ensure it's properly formatted.
            printf("%s", buffer); // Print the received message
        }

        printf("\n");
        close(fd[0]); //Closes the reading end of the pipe to indicate that the parent has finished reading.
    }

    return 0;
}
// This program demonstrates inter-process communication using a pipe, where the child sends a message
// to the parent. The parent then prints the received message. The use of a pipe ensures synchronization
// and communication between the two processes.
