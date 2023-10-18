#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    umask(0); // No problems with different users
    if (argc != 3) {
        printf("Wrong usage, try %s <source_file> <destination_file>\n", argv[0]);
        return 1;
    }

    FILE *source, *destination;
    char ch;
    source = fopen(argv[1], "r");
    if (source == NULL) {
        perror("Source file");
        return 1;
    }
    destination = fopen(argv[2], "w");
    if (destination == NULL) {
        perror("Destination file");
        fclose(source);
        return 1;
    }

    // Reading the file until reaching EOF
    while ((ch = fgetc(source)) != EOF) {
        fputc(ch, destination);
    }

    printf("File copied with success! <3\n");

    // Closing the files
    fclose(source);
    fclose(destination);

    // Delete the source file
    if (unlink(argv[1]) != 0) {
        perror("Error deleting source file");
        return 1;
    }

    return 0;
}
