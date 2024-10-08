import tkinter as tk
from collections import deque
from tkinter import messagebox


#Function to check if placing a queen at (row, col) is valid
def is_valid(state, row, col):
    for r, c in enumerate(state):
        if c == col or abs(c - col) == abs(r - row):
            return False
    return True


#BFS function to solve n-queens problem
def bfs_n_queens(n):
    queue = deque([[]])
    solutions = []

    while queue:
        state = queue.popleft()

        if len(state) == n:
            solutions.append(state)
            continue

        row = len(state)
        for col in range(n):
            if is_valid(state, row, col):
                queue.append(state + [col])

    return solutions


#GUI Class
class NQueensGUI:
    def __init__(self, master):
        self.master = master
        self.board = []
        self.queens = []  # to track placed queens
        self.solutions = []
        self.solution_index = 0
        self.n = 8  # Default number of queens (can be 1-10)
        self.board_size = 8  # Default board size

        # Create canvas for chessboard
        self.canvas = tk.Canvas(self.master, width=600, height=600)
        self.canvas.grid(row=0, column=0, columnspan=4)

        # Input field for specifying number of queens
        self.n_label = tk.Label(self.master, text="Number of Queens (1-10):")
        self.n_label.grid(row=1, column=0)

        self.n_entry = tk.Entry(self.master, width=5)
        self.n_entry.grid(row=1, column=1)
        self.n_entry.insert(0, '8')  # Default value is 8

        # Buttons to control the process
        self.solve_button = tk.Button(self.master, text="Solve", command=self.solve)
        self.solve_button.grid(row=2, column=0)

        self.next_solution_button = tk.Button(self.master, text="Next Solution", command=self.show_next_solution)
        self.next_solution_button.grid(row=2, column=1)

        self.reset_button = tk.Button(self.master, text="Reset", command=self.reset)
        self.reset_button.grid(row=2, column=2)

        # Draw the initial chessboard grid (default 8x8)
        self.draw_board(8)

    #Function to draw the chessboard
    def draw_board(self, size):
        """ Draw the chessboard of a given size (8x8, 9x9, or 10x10)."""
        self.cell_size = 600 // size
        colors = ["white", "gray"]
        self.board = []
        for row in range(size):
            board_row = []
            for col in range(size):
                color = colors[(row + col) % 2]
                x1 = col * self.cell_size
                y1 = row * self.cell_size
                x2 = x1 + self.cell_size
                y2 = y1 + self.cell_size
                board_row.append(self.canvas.create_rectangle(x1, y1, x2, y2, fill=color))
            self.board.append(board_row)

    #Function to place a queen on the board
    def place_queen(self, row, col):
        """ Place a queen on the board at (row, col). """
        x1 = col * self.cell_size
        y1 = row * self.cell_size
        x2 = x1 + self.cell_size
        y2 = y1 + self.cell_size
        self.queens.append(self.canvas.create_oval(x1 + 5, y1 + 5, x2 - 5, y2 - 5, fill="red"))

    #Function to remove all queens from the board
    def clear_queens(self):
        """ Clear all queens from the board. """
        for queen in self.queens:
            self.canvas.delete(queen)
        self.queens = []

    #Solve the N-Queens problem
    def solve(self):
        try:
            self.n = int(self.n_entry.get())
            if self.n < 1 or self.n > 10:
                raise ValueError

            # Update the board size: always 8x8, unless n > 8
            self.board_size = max(8, self.n)

            # Solve the N-Queens problem for the specified number of queens
            self.solutions = bfs_n_queens(self.n)
            self.solution_index = 0

            if self.solutions:
                self.show_solution(self.solutions[0])
            else:
                messagebox.showinfo("No Solution", "No solutions available for this number of queens.")

            # Redraw the board based on the current board size (8x8, 9x9, or 10x10)
            self.canvas.delete("all")
            self.draw_board(self.board_size)

        except ValueError:
            messagebox.showerror("Invalid Input", "Please enter a number between 1 and 10.")

    #Show the next solution
    def show_next_solution(self):
        """Show the next solution in the list."""
        if not self.solutions:
            return
        self.solution_index = (self.solution_index + 1) % len(self.solutions)
        self.show_solution(self.solutions[self.solution_index])

    #Show a specific solution
    def show_solution(self, solution):
        """ Display the given solution by placing queens on the board. """
        self.clear_queens()
        for row, col in enumerate(solution):
            self.place_queen(row, col)

    #Reset the board
    def reset(self):
        """ Reset the chessboard and input field to default values. """
        self.clear_queens()
        self.canvas.delete("all")
        self.n_entry.delete(0, tk.END)
        self.n_entry.insert(0, '8')  # Reset to default n=8
        self.board_size = 8
        self.draw_board(8)


#Main function to run the GUI
def main():
    root = tk.Tk()
    root.title("N-Queens Problem")
    app = NQueensGUI(root)
    root.mainloop()


if __name__ == "__main__":
    main()
