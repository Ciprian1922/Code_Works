import RPi.GPIO as GPIO
import time

# GPIO Setup
RELAY_PIN = 18
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(RELAY_PIN, GPIO.OUT)

# Constants
FILL_TIME_FOR_50ML = 2.35  # Time in seconds to fill 50 ml
FILL_RATE = 50 / FILL_TIME_FOR_50ML  # ml per second
SECONDS_IN_A_DAY = 86400  # Total seconds in 24 hours

def fill_water(amount):
    """Turn on the relay for the time needed to fill the specified amount of water (in ml)."""
    fill_time = amount / FILL_RATE  # Calculate time needed to fill the amount
    GPIO.output(RELAY_PIN, True)  # Turn relay ON
    print(f"Relay is ON for {fill_time:.2f} seconds to fill {amount} ml of water.")
    time.sleep(fill_time)  # Keep the relay ON for calculated time
    GPIO.output(RELAY_PIN, False)  # Turn relay OFF
    print("Relay is OFF")

def water_plant_forever(amount, times_a_day):
    """Continuously water the plant the specified number of times a day."""
    interval = SECONDS_IN_A_DAY / times_a_day  # Time interval between waterings
    print(f"Plant will be watered {times_a_day} times a day, every {interval:.2f} seconds.")

    while True:
        fill_water(amount)  # Water the plant
        time.sleep(interval)  # Wait until the next watering

def main():
    # Set the amount of water (in ml) and times per day
    amount = 100  # Amount of water per watering (in ml)
    times_a_day = 4  # Number of waterings per day
    print(f"Starting plant watering system with {amount} ml per watering, {times_a_day} times a day.")
    
    water_plant_forever(amount, times_a_day)

if __name__ == "__main__":
    try:
        main()
    finally:
        GPIO.cleanup()  #cleanup GPIO settings
