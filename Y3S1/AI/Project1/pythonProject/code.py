import tkinter as tk  #tkinter for GUI elements
from collections import deque  # import deque for efficient queue operations
from tkinter import messagebox  # Import messagebox for displaying alerts


# Function to check if placing a queen at (row, col) is valid
def is_valid(state, row, col):
    """
    Check if it's valid to place a queen at (row, col) based on the current state of the board.

    :param state: List of column indices for queens already placed
    :param row: The row index for the new queen
    :param col: The column index for the new queen
    :return: True if the placement is valid, False otherwise
    """
    for r, c in enumerate(state):
        #check for column conflicts and diagonal conflicts
        if c == col or abs(c - col) == abs(r - row):
            return False
    return True  #return True if no conflicts are found


#BFS function to solve the N-Queens problem
def bfs_n_queens(n):
    """
    Use Breadth-First Search (BFS) to find all solutions for the N-Queens problem.

    :param n: The number of queens (and the size of the board)
    :return: A list of valid placements for queens
    """
    queue = deque([[]])  #initialize the queue with an empty state
    solutions = []  #list to store valid solutions

    while queue:
        state = queue.popleft()  #remove the front state from the queue

        if len(state) == n:  #check if we have placed n queens
            solutions.append(state)  #add this solution to the list
            continue  #skip to the next iteration

        row = len(state)  #the current row to place a queen in
        for col in range(n):  #try placing a queen in each column
            if is_valid(state, row, col):  #check if the placement is valid
                queue.append(state + [col]) #add the new state to the queue with the new queen placed

    return solutions  #return all found solutions


#GUI Class to create the N-Queens application
class NQueensGUI:
    def __init__(self, master):
        """
        Initialize the GUI components:
        :param master: The root window of the application
        """
        self.master = master  #save the reference to the main window
        self.board = []  #list to hold references to board squares
        self.queens = []  #list to track placed queens on the board
        self.solutions = []  #list to hold found solutions
        self.solution_index = 0  #to track which solution to show
        self.n = 8  #default number of queens (up to 10)
        self.board_size = 8  #default size of the board

        #create a canvas to draw the chessboard
        self.canvas = tk.Canvas(self.master, width=600, height=600)
        self.canvas.grid(row=0, column=0, columnspan=4)  #place canvas in the grid layout

        #input field for specifying the number of queens
        self.n_label = tk.Label(self.master, text="Number of Queens (1-10):")
        self.n_label.grid(row=1, column=0)  #place label in the grid

        self.n_entry = tk.Entry(self.master, width=5)  #create an entry widget
        self.n_entry.grid(row=1, column=1)  #place entry in the grid
        self.n_entry.insert(0, '8')  #set default value to 8

        #buttons for user interaction
        self.solve_button = tk.Button(self.master, text="Solve", command=self.solve)
        self.solve_button.grid(row=2, column=0)  #place solve button

        self.next_solution_button = tk.Button(self.master, text="Next Solution", command=self.show_next_solution)
        self.next_solution_button.grid(row=2, column=1)  #place next solution button

        self.reset_button = tk.Button(self.master, text="Reset", command=self.reset)
        self.reset_button.grid(row=2, column=2)  #place reset button

        #draw the initial chessboard grid (default 8x8)
        self.draw_board(8)

    #function to draw the chessboard
    def draw_board(self, size):
        """
        Draw the chessboard of a given size (8x8, 9x9, or 10x10).
        :param size: The size of the chessboard
        """
        self.cell_size = 600 // size  #calculate size of each cell based on canvas size
        colors = ["white", "gray"]  #define colors for the chessboard
        self.board = []  #reset the board list
        for row in range(size):  #loop through rows
            board_row = []  #list to hold row squares
            for col in range(size):  #loop through columns
                #determine color based on the sum of row and column indices
                color = colors[(row + col) % 2]
                x1 = col * self.cell_size  #calculate top-left corner x
                y1 = row * self.cell_size  #calculate top-left corner y
                x2 = x1 + self.cell_size  #calculate bottom-right corner x
                y2 = y1 + self.cell_size  #calculate bottom-right corner y
                #create the rectangle (square) for the board cell
                board_row.append(self.canvas.create_rectangle(x1, y1, x2, y2, fill=color))
            self.board.append(board_row)  #add the row to the board

    #function to place a queen on the board
    def place_queen(self, row, col):
        """
        Place a queen on the board at (row, col).
        :param row: The row index to place the queen
        :param col: The column index to place the queen
        """
        x1 = col * self.cell_size  #calculate the x position
        y1 = row * self.cell_size  #calculate the y position
        x2 = x1 + self.cell_size  #bottom-right corner x
        y2 = y1 + self.cell_size  #bottom-right corner y
        #create an oval (queen) in red color
        self.queens.append(self.canvas.create_oval(x1 + 5, y1 + 5, x2 - 5, y2 - 5, fill="red"))

    #function to remove all queens from the board
    def clear_queens(self):
        """
        Clear all queens from the board.
        """
        for queen in self.queens:  #loop through placed queens
            self.canvas.delete(queen)  #remove each queen from the canvas
        self.queens = []  #reset the list of queens

    #solve the N-Queens problem
    def solve(self):
        """
        Solve the N-Queens problem based on user input.
        """
        try:
            self.n = int(self.n_entry.get())  #get the number of queens from the entry field
            if self.n < 1 or self.n > 10:  #validate the number of queens
                raise ValueError  #raise an error if invalid

            #update the board size: always 8x8, unless n > 8
            self.board_size = max(8, self.n)  #set the board size based on the number of queens

            #solve the N-Queens problem for the specified number of queens
            self.solutions = bfs_n_queens(self.n)  #get all solutions using BFS
            self.solution_index = 0  #reset solution index

            if self.solutions:  #check if solutions were found
                self.show_solution(self.solutions[0])  #show the first solution
            else:
                messagebox.showinfo("No Solution", "No solutions available for this number of queens.")  #notify user

            #redraw the board based on the current board size (8x8, 9x9, or 10x10)
            self.canvas.delete("all")  #clear the canvas
            self.draw_board(self.board_size)  #draw the new board size

        except ValueError:
            messagebox.showerror("Invalid Input", "Please enter a number between 1 and 10.")  #show error message

    #show the next solution
    def show_next_solution(self):
        """
        Show the next solution in the list.
        """
        if not self.solutions:  #if no solutions exist, return
            return
        #increment solution index, wrapping around if needed
        self.solution_index = (self.solution_index + 1) % len(self.solutions)
        self.show_solution(self.solutions[self.solution_index])  #show the solution at the current index

    #show a specific solution
    def show_solution(self, solution):
        """
        Display the given solution by placing queens on the board.
        :param solution: A list of column indices representing queen placements
        """
        self.clear_queens()  #clear any existing queens from the board
        for row, col in enumerate(solution):  #loop through the solution
            self.place_queen(row, col)  #place each queen according to the solution

    #reset the board
    def reset(self):
        """
        Reset the chessboard and input field to default values.
        """
        self.clear_queens()  #remove queens from the board
        self.canvas.delete("all")  #clear the canvas
        self.n_entry.delete(0, tk.END)  #clear the entry field
        self.n_entry.insert(0, '8')  #reset to default n=8
        self.board_size = 8  #reset board size to 8
        self.draw_board(8)  #redraw the 8x8 board


#main function to run the GUI
def main():
    """
    Main function to create and run the N-Queens GUI application.
    """
    root = tk.Tk()  #create the main window
    root.title("N-Queens Problem")  #set the title of the window
    app = NQueensGUI(root)  #create an instance of the NQueensGUI class
    root.mainloop()  #start the Tkinter main event loop


main()  #execute the main function when the script is run
