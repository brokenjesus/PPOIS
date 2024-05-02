import random

from collision_detection import *
from core.records_manager import RecordsManager
from game_screens import *
from level import create_level
from paddle import Paddle


import random
import time

def run_game(records_manager):
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
    pygame.display.set_caption("Arkanoid")
    clock = pygame.time.Clock()
    all_sprites = pygame.sprite.Group()
    bricks, walls = create_level()
    all_sprites.add(bricks)
    all_sprites.add(walls)
    paddle = Paddle()
    ball = Ball(walls)
    all_sprites.add(paddle)
    all_sprites.add(ball)

    balls = [ball]

    collision = Collision()
    start_time = time.time()  # начальное время уровня
    current_level = 1  # текущий уровень
    level_completed = False  # флаг для отслеживания завершения уровня

    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_LEFT:
                    paddle.speed = -PADDLE_SPEED
                elif event.key == pygame.K_RIGHT:
                    paddle.speed = PADDLE_SPEED
            elif event.type == pygame.KEYUP:
                if event.key == pygame.K_LEFT and paddle.speed < 0:
                    paddle.speed = 0
                elif event.key == pygame.K_RIGHT and paddle.speed > 0:
                    paddle.speed = 0

        collision.check_collision(balls, bricks, walls, paddle)

        if collision.bricks_destroyed >= 10:
            new_ball = Ball(walls)
            new_ball.rect.center = (randint(0, SCREEN_WIDTH), SCREEN_HEIGHT // 2)
            all_sprites.add(new_ball)
            balls.append(new_ball)  # Добавляем новый мяч в список мячей
            collision.bricks_destroyed = 0

        if len(bricks) == 0:
            level_completed = True
            print("won")

        if len(balls) == 0:
            # если мячи закончились, игра заканчивается
            game_over_text()
            running = False

        all_sprites.update()

        screen.fill(BLACK)
        all_sprites.draw(screen)
        pygame.display.flip()
        clock.tick(60)

        if level_completed:
            end_time = time.time()  # время окончания уровня
            level_time = (end_time - start_time).__round__(3)  # время прохождения уровня
            start_time = end_time  # обновляем начальное время для следующего уровня

            # Проверяем, побит ли предыдущий рекорд
            if current_level in records_manager.records:
                previous_record_time = records_manager.records[current_level]['time']
                if level_time < previous_record_time:
                    # Если текущее время лучше предыдущего рекорда, предлагаем ввести никнейм
                    # nickname = input("Congratulations! You've beaten the previous record. Enter your nickname: ")
                    # records_manager.add_record(current_level, nickname, level_time)
                    show_save_record_screen(level=current_level, time=level_time)
            else:
                # Если уровень новый, автоматически добавляем рекорд
                records_manager.add_record(current_level, "Player", level_time)
            level_completed_screen(level=current_level, time=level_time)
            # Обновляем флаги и данные для следующего уровня


def show_records(records_manager):
    print(records_manager.get_records_table())

if __name__ == "__main__":
    pygame.init()
    records_manager = RecordsManager()

    while True:
        choice = show_menu()
        if choice == 0:
            run_game(records_manager)
        elif choice == 1:
            show_records_screen(records_manager)
        elif choice == 2:
            # Код для отображения справки
            pass
        elif choice == 3:
            pygame.quit()
            sys.exit()
