#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>

int main(int argc, char *argv[]) {
    // Check if the correct number of arguments is provided
    if (argc != 3) {
        printf("Wrong usage, try %s <source_file> <destination_file>\n", argv[0]);
        return 1;
    }

    int source_fd, destination_fd; // File descriptors for source and destination files
    off_t length; // Variable to store file length

    // Open the source file in read-only mode
    source_fd = open(argv[1], O_RDONLY);
    if (source_fd == -1) {
        perror("Source file"); // Print an error message if opening fails
        return 1;
    }

    // Get the length of the source file
    length = lseek(source_fd, 0, SEEK_END);
    lseek(source_fd, 0, SEEK_SET); // Set the file pointer back to the beginning

    // Open the destination file in write-only mode, create if it doesn't exist, truncate to 0, set permissions
    destination_fd = open(argv[2], O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);
    if (destination_fd == -1) {
        perror("Destination file"); // Print an error message if opening fails
        close(source_fd);
        return 1;
    }

    // Allocate memory for the buffer based on the length of the source file
    char *buffer = (char *)malloc(length);
    if (buffer == NULL) {
        printf("Could not allocate memory\n");
        close(source_fd);
        close(destination_fd);
        return 3;
    }

    // Read the contents of the source file into the buffer
    ssize_t n = read(source_fd, buffer, length);
    if (n < 0) {
        perror("Could not read from source file"); // Print an error message if reading fails
        close(source_fd);
        close(destination_fd);
        free(buffer);
        return 4;
    }

    // Write the buffer contents to the destination file
    ssize_t bytes_written = write(destination_fd, buffer, n);
    if (bytes_written != n) {
        perror("Could not write to destination file"); // Print an error message if writing fails
        close(source_fd);
        close(destination_fd);
        free(buffer);
        return 5;
    }

    // Print a success message
    printf("File copied with success! <3\n");

    // Close file descriptors and free allocated memory
    close(source_fd);
    close(destination_fd);
    free(buffer);

    // Delete the source file after successful copy
    if (unlink(argv[1]) != 0) {
        perror("Error deleting source file"); // Print an error message if deletion fails
        return 1;
    }

    return 0; // Return 0 to indicate success
}

