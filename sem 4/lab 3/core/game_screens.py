import sys
import pygame

from core.records_manager import RecordsManager
from game_consts import *

# Initialize Pygame
pygame.init()

def level_completed_screen(level, time):
    screen.fill(BLACK)

    font = pygame.font.Font('../static/font.ttf', 36)
    title_text = font.render("Level Completed!", True, WHITE)
    title_rect = title_text.get_rect(center=(SCREEN_WIDTH // 2, 100))
    screen.blit(title_text, title_rect)

    time_text = font.render(f"Your time: {time} seconds", True, WHITE)
    time_rect = time_text.get_rect(center=(SCREEN_WIDTH // 2, 200))
    screen.blit(time_text, time_rect)

    continue_text = font.render("Press any key to continue", True, WHITE)
    continue_rect = continue_text.get_rect(center=(SCREEN_WIDTH // 2, 300))
    screen.blit(continue_text, continue_rect)

    pygame.display.flip()

    # Ожидание нажатия любой клавиши
    waiting = True
    while waiting:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == pygame.KEYDOWN:
                waiting = False
                show_menu()


def show_save_record_screen(level, time):
    screen.fill(BLACK)

    font = pygame.font.Font('../static/font.ttf', 36)
    title_text = font.render("New High Score!", True, WHITE)
    title_rect = title_text.get_rect(center=(SCREEN_WIDTH // 2, 100))
    screen.blit(title_text, title_rect)

    time_text = font.render(f"Your time: {time} seconds", True, WHITE)
    time_rect = time_text.get_rect(center=(SCREEN_WIDTH // 2, 200))
    screen.blit(time_text, time_rect)

    nickname_text = font.render("Enter your nickname:", True, WHITE)
    nickname_rect = nickname_text.get_rect(center=(SCREEN_WIDTH // 2, 300))
    screen.blit(nickname_text, nickname_rect)

    input_box = pygame.Rect(SCREEN_WIDTH // 2 - 200, 350, 400, 50)
    input_text = ""
    active = True
    records = RecordsManager()
    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    return  # Возврат к предыдущему экрану
                elif active:
                    if event.key == pygame.K_RETURN:
                        # Сохранить рекорд и вернуться к предыдущему экрану
                        records.add_record(level, input_text, time)
                        return
                    elif event.key == pygame.K_BACKSPACE:
                        input_text = input_text[:-1]
                    else:
                        input_text += event.unicode

        # Отрисовка поля ввода
        pygame.draw.rect(screen, WHITE, input_box, 2)
        font_input = pygame.font.Font(None, 36)
        input_surface = font_input.render(input_text, True, WHITE)
        screen.blit(input_surface, (input_box.x + 5, input_box.y + 5))

        pygame.display.flip()

def show_records_screen(records_manager):
    screen.fill(BLACK)

    font = pygame.font.Font('../static/font.ttf', 36)
    title_text = font.render("High Score Table", True, WHITE)
    title_rect = title_text.get_rect(center=(SCREEN_WIDTH // 2, 100))
    screen.blit(title_text, title_rect)

    records_text = records_manager.get_records_table()
    records_lines = records_text.split('\n')
    for i, line in enumerate(records_lines):
        text = font.render(line, True, WHITE)
        text_rect = text.get_rect(center=(SCREEN_WIDTH // 2, 200 + i * 40))
        screen.blit(text, text_rect)

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    return  # Возврат к предыдущему экрану

        pygame.display.flip()


def show_menu():
    screen.fill(BLACK)  # Очистка экрана
    font = pygame.font.Font('../static/font.ttf', 36)

    # Отображение каждого пункта меню
    menu_items = ["1. Start game", "2. High score table", "3. Help", "0. Exit"]
    item_positions = [(SCREEN_WIDTH // 2, 200 + i * 50) for i in range(len(menu_items))]

    rendered_items = [font.render(item, True, WHITE) for item in menu_items]

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == pygame.MOUSEBUTTONDOWN:
                mouse_pos = pygame.mouse.get_pos()
                for i, pos in enumerate(item_positions):
                    if rendered_items[i].get_rect(center=pos).collidepoint(mouse_pos):
                        return i  # Возвращает индекс выбранного пункта меню

        for i, item in enumerate(rendered_items):
            screen.blit(item, item_positions[i])  # Отображение пунктов меню на экране

        pygame.display.flip()

def game_over_text():
    font = pygame.font.Font(None, 36)
    text = font.render("Game Over :(", True, RED)
    text_rect = text.get_rect(center=(SCREEN_WIDTH // 2, SCREEN_HEIGHT // 2))
    screen.blit(text, text_rect)
    pygame.display.flip()
    pygame.time.wait(2000)  # Подождать 2 секунды перед выходом
    pygame.quit()
