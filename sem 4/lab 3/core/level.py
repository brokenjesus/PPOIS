import random
import pygame
import xml.etree.ElementTree as ET  # Import the XML module

from brick import Brick
from core.wall import Wall
from game_consts import *

# Define the file paths for the XML file
XML_FILE_PATH = "../static/levels.xml"

# Function to read levels from XML file
def read_levels_from_xml(file_path):
    levels = []
    tree = ET.parse(file_path)
    root = tree.getroot()
    for level in root.findall('level'):
        rows = [row.text for row in level.findall('row')]
        levels.append(rows)
    return levels

def create_level(level=None):
    if level is None:
        level = random.choice(read_levels_from_xml(XML_FILE_PATH))  # Read level from XML file
    bricks = pygame.sprite.Group()
    walls = pygame.sprite.Group()
    y = 0
    for row in level:
        x = 0
        for brick in row:
            if brick == "B":
                new_brick = Brick()
                new_brick.rect.x = x * BRICK_WIDTH
                new_brick.rect.y = y * BRICK_HEIGHT
                bricks.add(new_brick)
            elif brick == "W":
                new_wall = Wall(BRICK_WIDTH, BRICK_HEIGHT)
                new_wall.rect.x = x * BRICK_WIDTH
                new_wall.rect.y = y * BRICK_HEIGHT
                walls.add(new_wall)
            x += 1
        y += 1
    return bricks, walls
