import pygame
import sys
import math

# Initialize Pygame
pygame.init()

# Constants
WIDTH, HEIGHT = 800, 600
PLAYER_X, PLAYER_Y = 200, 100
PLAYER_ANGLE = 0
PLAYER_SPEED = 5

# Colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)

# Initialize the screen
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("3D Perspective Simulation")

# Define the game map
wall_map = [
    ['X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'],
    ['X', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'],
    ['X', ' ', 'X', ' ', ' ', 'X', 'X', 'X', ' ', 'X'],
    ['X', ' ', 'X', ' ', ' ', 'X', ' ', 'X', ' ', 'X'],
    ['X', ' ', 'X', 'X', 'X', 'X', ' ', ' ', ' ', 'X'],
    ['X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X'],
    ['X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X'],
    ['X', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'],
    ['X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'],
    ['X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X']
]

def cast_ray(angle):
    for x in range(WIDTH):
        ray_angle = (PLAYER_ANGLE - FOV / 2) + (x / WIDTH) * FOV
        ray_x = PLAYER_X
        ray_y = PLAYER_Y
        distance_to_wall = 0

        hit_wall = False

        while not hit_wall and distance_to_wall < WIDTH:
            distance_to_wall += 1
            ray_x = PLAYER_X + math.cos(ray_angle) * distance_to_wall
            ray_y = PLAYER_Y + math.sin(ray_angle) * distance_to_wall

            if ray_x < 0 or ray_x >= WIDTH or ray_y < 0 or ray_y >= HEIGHT:
                hit_wall = True
            else:
                if wall_map[int(ray_x // 32)][int(ray_y // 32)] == 'X':
                    hit_wall = True

            if not hit_wall:
                # Draw the ray
                pygame.draw.line(screen, WHITE, (PLAYER_X, PLAYER_Y), (ray_x, ray_y), 2)
                pygame.display.flip()

def draw_map():
    for x in range(10):
        for y in range(10):
            if wall_map[x][y] == 'X':
                pygame.draw.rect(screen, WHITE, (x * 32, y * 32, 32, 32))

# Main game loop
FOV = math.pi / 2
clock = pygame.time.Clock()

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    keys = pygame.key.get_pressed()
    if keys[pygame.K_w]:
        PLAYER_X += PLAYER_SPEED * math.cos(PLAYER_ANGLE)
        PLAYER_Y += PLAYER_SPEED * math.sin(PLAYER_ANGLE)
    if keys[pygame.K_s]:
        PLAYER_X -= PLAYER_SPEED * math.cos(PLAYER_ANGLE)
        PLAYER_Y -= PLAYER_SPEED * math.sin(PLAYER_ANGLE)
    if keys[pygame.K_a]:
        PLAYER_ANGLE -= 0.02
    if keys[pygame.K_d]:
        PLAYER_ANGLE += 0.02

    screen.fill(BLACK)
    draw_map()
    cast_ray(PLAYER_ANGLE)
    pygame.display.flip()
    clock.tick(60)

pygame.quit()
sys.exit()
