#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <math.h>
#include <unistd.h>

//! use `gcc -o ex2 main.c -lm` to also link the math lib
void computation() {
    sigset_t mask, oldmask;
    sigfillset(&mask);

    if (sigprocmask(SIG_BLOCK, &mask, &oldmask) == -1) {
        perror("Error blocking signals");
        exit(EXIT_FAILURE);
    }

    int i;
    float array[100];

    for (i = 0; i < 100; i++)
        array[i] = 1000 * sin(cos((float)i) + tan((float)(100 - i)));

    // Restore the pending signals once the computation is finished
    if (sigprocmask(SIG_SETMASK, &oldmask, NULL) == -1) {
        perror("Error restoring signal mask");
        exit(EXIT_FAILURE);
    }

    _exit(EXIT_SUCCESS);
}

int main() {
    pid_t child_pid = fork();

    if (child_pid == -1) {
        perror("Error while forkng");
        exit(EXIT_FAILURE);
    }

    if (child_pid == 0) {
        // child
        computation();
    } else {
        // parent
        int status;

        if (waitpid(child_pid, &status, 0) == -1) {
            perror("Error waiting for child process");
            exit(EXIT_FAILURE);
        }

        if (WIFEXITED(status)) {
            printf("Computation completed successfully.\n");
        } else {
            fprintf(stderr, "Computation failed.\n");
            exit(EXIT_FAILURE);
        }
    }

    return EXIT_SUCCESS;
}
