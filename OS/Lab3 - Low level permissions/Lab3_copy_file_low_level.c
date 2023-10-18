#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("Wrong usage, try %s <source_file> <destination_file>\n", argv[0]);
        return 1;
    }
    int source_fd, destination_fd;
    off_t length;

    source_fd = open(argv[1], O_RDONLY);
    if (source_fd == -1) {
        perror("Source file");
        return 1;
    }
    length = lseek(source_fd, 0, SEEK_END);
    lseek(source_fd, 0, SEEK_SET);

    destination_fd = open(argv[2], O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);
    if (destination_fd == -1) {
        perror("Destination file");
        close(source_fd);
        return 1;
    }
    char *buffer = (char *)malloc(length);
    if (buffer == NULL) {
        printf("Could not allocate memory\n");
        close(source_fd);
        close(destination_fd);
        return 3;
    }
    ssize_t n = read(source_fd, buffer, length);
    if (n < 0) {
        perror("Could not read from source file");
        close(source_fd);
        close(destination_fd);
        free(buffer);
        return 4;
    }
    ssize_t bytes_written = write(destination_fd, buffer, n);
    if (bytes_written != n) {
        perror("Could not write to destination file");
        close(source_fd);
        close(destination_fd);
        free(buffer);
        return 5;
    }
    printf("File copied with success! <3\n");
    close(source_fd);
    close(destination_fd);
    free(buffer);

    if (unlink(argv[1]) != 0) {
        perror("Error deleting source file");
        return 1;
    }
    return 0;
}
