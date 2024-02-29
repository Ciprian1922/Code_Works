#include <stdio.h>
#include <string.h>
#include <sys/stat.h>          // Include the sys/stat library for file status functions
#include <dirent.h>            // Include the dirent library for directory entry functions

// Function to compute the size of a simple file
off_t get_file_size(const char *path) {
    struct stat st;             // Declare a structure to store file information
    if (stat(path, &st) == 0) {  // Use the stat function to get file information, if successful
        if (S_ISREG(st.st_mode) && S_ISDIR(st.st_mode)) {  // Check if it's a regular file
            return st.st_size;      // Return the file size in bytes
        }
    }
    return -1;  // Not a regular file, return -1 to indicate an error
}

// Function to recursively traverse a directory
void traverse_directory(const char *dirname) {
    DIR *dir;                  // Declare a directory pointer
    struct dirent *entry;      // Declare a directory entry structure

    if (!(dir = opendir(dirname))) {
        perror("opendir");       // Open the specified directory; if it fails, print an error message
        return;
    }

    while ((entry = readdir(dir)) != NULL) {  // Loop through directory entries
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0) {
            continue;  // Skip the current directory (.) and parent directory (..) entries
        }

        char path[512];                        // Declare a character array to store the full path
        snprintf(path, sizeof(path), "%s/%s", dirname, entry->d_name);  // Construct the full path

        off_t file_size = get_file_size(path);   // Get the size of the current file
        if (file_size >= 0) {                   // If it's a valid file (not a directory)
            printf("%s: %lld bytes\n", path, (long long)file_size);  // Print the file path and size
        }

        if (S_ISDIR(entry->d_type)) {  // Check if the current entry is a directory
            traverse_directory(path);  // Recursively traverse the directory
        }
    }

    closedir(dir);  // Close the directory when all entries have been processed
}

int main(int argc, char *argv[]) {
    if (argc != 2) {  // Check if exactly one command-line argument is provided
        fprintf(stderr, "Try this usage: %s <directory>\n", argv[0]);  // Print a usage message to stderr
        return 1;  // Return an error code to indicate an issue
    }

    traverse_directory(argv[1]);  // Call the function to start directory traversal

    return 0;  // Return 0 to indicate successful execution
}
