import socket
import tkinter as tk

HOST = "100.122.251.100"  # Raspberry Pi Tailscale IP
PORT = 65432

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Create a GUI window using Tkinter
root = tk.Tk()
root.title("Weight Sensor Data")

# Create a label in the GUI to display the weight
weight_label = tk.Label(root, text="Waiting for weight data...", font=("Helvetica", 24))
weight_label.pack(pady=50)


def update_weight_label(weight):
    weight_label.config(text=f"📦 Weight: {weight} grams")


try:
    print(f"🔄 Connecting to Raspberry Pi at {HOST}:{PORT}...")
    client_socket.connect((HOST, PORT))  # Connect to the Raspberry Pi

    print("✅ Connected! Receiving weight data...\n")

    while True:
        data = client_socket.recv(1024).decode('utf-8')  # Receive data from the Raspberry Pi
        if not data:
            break

        print(f"📦 Weight received: {data.strip()} grams")  # Debugging output
        update_weight_label(data.strip())  # Update the GUI with the received weight

        root.update()  # Update the Tkinter GUI

except Exception as e:
    print(f"❌ ERROR: {e}")
finally:
    client_socket.close()
    print("🔒 Connection closed.")
    root.quit()  # Close the Tkinter window when done
