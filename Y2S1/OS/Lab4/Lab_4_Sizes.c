#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <dirent.h>

// Compute size of a simple file
off_t get_file_size(const char *path) {
    struct stat st;
    if (stat(path, &st) == 0) {
        if (S_ISREG(st.st_mode)) {
            return st.st_size;
        }
    }
    return -1;
}

// Recursively traversing the directory function
void traverse_directory(const char *dirname) {
    DIR *dir;
    struct dirent *entry;

    if (!(dir = opendir(dirname))) {
        perror("opendir");
        return;
    }

    while ((entry = readdir(dir)) != NULL) {
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0) {
            continue;
        }

        char path[512];
        snprintf(path, sizeof(path), "%s/%s", dirname, entry->d_name);

        off_t file_size = get_file_size(path);
        if (file_size >= 0) {
            printf("%s: %lld bytes\n", path, (long long)file_size);
        }

        if (entry->d_type == DT_DIR) {  // Check if it's a directory
            traverse_directory(path);  // Recursively explore the subdirectory
        }
    }

    closedir(dir);
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Try this usage: %s <directory>\n", argv[0]);
        return 1;
    }

    traverse_directory(argv[1]);

    return 0;
}
