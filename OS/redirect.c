#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("Usage: %s <source_file> <destination_file>\n", argv[0]);
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

    while ((ch = fgetc(source)) != EOF) {
        fputc(ch, destination);
    }

    printf("File copied successfully!\n");

    fclose(source);
    fclose(destination);

    return 0;
}
