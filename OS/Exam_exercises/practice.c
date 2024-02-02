#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

pid_t pid_parent1, pid_process2, pid_process3;//globally initializing the pid

int main(){
  pid_t pid1, pid2, pid3;
  
  pid_parent1 = getpid(); //getting the pid for the parent
  
  int pipefd[2];//check if the pipe was created corectly
  if(pipe(pipefd) == -1){
    perror("Error creating the pipe!");
    exit(EXIT_FAILURE);
  }
  pid1 = fork();
  
  if(pid1 == 0){ //we are in the first child process
    printf("The parent PID from the child1: %d\n", pid_parent1);
    
  }else if(pid1 > 0){ //we continue with the parent and create process two
    
    pid2 = fork(); pid_process2 = pid2;
    if(pid2 == 0){  // we go straight into the second process
      
      close(pipefd[1]); // closing the right end of the pipe here in process two
      read(pipefd[0], &pid3, sizeof(pid3)); // now we can read the pid through our pipe for third process
      close(pipefd[0]); // now we also close that reading end of the pipe
      // now that we have it, we can also print it
      printf("The PID of child 3 read from child 2 is: %d\n", pid3); // easy print
      
    }else if(pid2 > 0){ // we don's stop here, we continue to create the third process using parent 
        pid3 = pid_process3 = fork();
        
        if(pid3 == 0){
          printf("The PID of child 3 read from child 3 is: %d\n", getpid()); // easy print
        }else if(pid3 > 0) {
          // now we have to write the pid using the parent through the pipe so the child can read it
          close(pipefd[0]); // close read end of the pipe using the parent
          write(pipefd[1], &pid3, sizeof(pid3)); // writing into the pipe 
          close(pipefd[1]); // close write end of the pipe using the parent
        }
      }
  }
  
  //lastly we wait for the 3 processes to finish
  for(int i = 0; i<3; i++){
    wait(NULL);
  }
  
  return 0;
}
