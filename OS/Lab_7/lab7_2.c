#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <fcntl.h>

#define BUFFER_SIZE (1 << 12)

// Return 1 if 'c' is a vowel, 0 otherwise
int isVowel(char c) {
    if (c == 'a' || c == 'A' || c == 'e' || c == 'E' || c == 'i' || c == 'I' ||
        c == 'o' || c == 'O' || c == 'u' || c == 'U') {
        return 1;
    }
    return 0;
}

// Counts vowels in a text buffer of size "size" bytes
int countVowels(char *buffer, int size) {
    int count = 0;
    for (int i = 0; i < size; i++) {
        if (isVowel(buffer[i])) {
            count++;
        }
    }
    return count;
}

int main(int argc, char *argv[]) {
    int fd[2], res;
    pid_t p;
    char fileName[256];

    if (argc < 2) {
        printf("Usage: %s <input_text_file>\n", argv[0]);
        exit(1);
    }

    res = pipe(fd);
    if (res < 0) {
        printf("Could not create pipe!\n");
        exit(2);
    }

    p = fork();
    if (p < 0) {
        printf("Could not create the child process!\n");
        exit(3);
    }

    if (p > 0) { // parent
        char buffer[BUFFER_SIZE];
        int file, n;

        strcpy(fileName, argv[1]);
        file = open(fileName, O_RDONLY);
        close(fd[0]); // close reading edge/side

        while ((n = read(file, buffer, BUFFER_SIZE)) > 0) {
            write(fd[1], buffer, n); // write into the pipe
        }

        close(fd[1]); // close pipe entirely
        close(file);
        wait(NULL); // wait for child to finish
    } else { // child
        char buffer[BUFFER_SIZE];
        int n, vowels = 0;

        close(fd[1]); // close writing edge/side of the pipe

        while ((n = read(fd[0], buffer, BUFFER_SIZE)) > 0) {
            vowels += countVowels(buffer, n);
        }

        close(fd[0]); // close the pipe entirely
        printf("We have found %d vowels!\n", vowels);
        exit(0);
    }

    return 0;
}
