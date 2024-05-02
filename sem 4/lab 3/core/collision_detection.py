import time
from random import randint

from core.ball import Ball
from game_consts import *
import pygame.mixer

# Инициализация Pygame
pygame.mixer.init()

# Загрузка звукового файла
barabara_sound = pygame.mixer.Sound('../static/sounds/barabara.mp3')
sad_sound = pygame.mixer.Sound('../static/sounds/sad.mp3')

class Collision:
    def __init__(self):
        self.last_brick_destroyed_time = time.time()  # Инициализация времени последнего разрушения кирпича
        self.bricks_destroyed = 0
        self.sad_sound_played = False

    def check_collision(self, balls, bricks, walls, paddle):
        current_time = time.time()
        for ball in balls:
            if pygame.sprite.spritecollide(ball, bricks, False):
                pygame.mixer.stop()  # Остановка всех звуков
                self.sad_sound_played = False
                self.last_brick_destroyed_time = time.time()


                brick_hit = pygame.sprite.spritecollide(ball, bricks, True)[0]
                barabara_sound.play()
                self.bricks_destroyed += 1
                brick_left_edge = brick_hit.rect.left
                brick_right_edge = brick_hit.rect.right
                brick_top_edge = brick_hit.rect.top
                brick_bottom_edge = brick_hit.rect.bottom

                if ball.rect.bottom <= brick_top_edge - BALL_RADIUS or ball.rect.top >= brick_bottom_edge + BALL_RADIUS:
                    ball.speed_y *= -1
                    print("top/bottom")
                elif ball.rect.right >= brick_left_edge + BALL_RADIUS and ball.rect.left <= brick_right_edge - BALL_RADIUS:
                    if ball.speed_x > 0:
                        ball.speed_x = BALL_SPEED * -1
                    else:
                        ball.speed_x = BALL_SPEED

            if pygame.sprite.collide_rect(ball, paddle):
                # pygame.mixer.stop()  # Остановка всех звуков

                # sad_sound.play()
                wall_left_edge = paddle.rect.left
                wall_right_edge = paddle.rect.right
                wall_top_edge = paddle.rect.top
                wall_bottom_edge = paddle.rect.bottom

                if ball.rect.bottom <= wall_top_edge + BALL_SPEED or ball.rect.top >= wall_bottom_edge - BALL_SPEED:
                    ball.speed_y *= -1
                    awd = abs(ball.rect.centerx - paddle.rect.centerx)
                    distance_from_center = abs(paddle.rect.left - paddle.rect.centerx)

                    if ball.speed_x > 0:
                        ball.speed_x = BALL_SPEED * awd / distance_from_center
                    else:
                        ball.speed_x = -1 * BALL_SPEED * awd / distance_from_center
                elif (ball.rect.right >= wall_left_edge - BALL_SPEED and ball.rect.left <= wall_right_edge + BALL_SPEED):
                    ball.speed_y *= -1
                    ball.speed_x *= -1
                    # if ball.speed_x > 0:
                    #     ball.speed_x = -1*(PADDLE_SPEED+1)
                    # else:
                    #     ball.speed_x = (PADDLE_SPEED + 1)

                    print("side collision")

            if pygame.sprite.spritecollide(ball, walls, False):
                # pygame.mixer.stop()  # Остановка всех звуков

                # sad_sound.play()
                wall_hit = pygame.sprite.spritecollide(ball, walls, False)[0]
                wall_left_edge = wall_hit.rect.left
                wall_right_edge = wall_hit.rect.right
                wall_top_edge = wall_hit.rect.top
                wall_bottom_edge = wall_hit.rect.bottom

                if ball.rect.bottom <= wall_top_edge + BALL_SPEED or ball.rect.top >= wall_bottom_edge - BALL_SPEED:
                    ball.speed_y *= -1
                    print("top/bottom")
                # Проверяем столкновение с боковой стеной
                elif (
                        ball.rect.right >= wall_left_edge - BALL_SPEED and ball.rect.left <= wall_right_edge + BALL_SPEED):
                    # Вычисляем угол отражения в зависимости от точки столкновения мяча с стеной
                    # Для простоты будем менять только угол отражения по оси X
                    ball_center_x = ball.rect.centerx
                    wall_center_x = wall_hit.rect.centerx

                    if ball.speed_x > 0:
                        ball.speed_x = BALL_SPEED * -1
                    else:
                        ball.speed_x = BALL_SPEED
                    print("side collision")

            if ball.rect.top > SCREEN_HEIGHT:  # Если мяч вышел за нижнюю границу экрана
                balls.remove(ball)
                # sad_sound.play()
            if current_time - self.last_brick_destroyed_time > 3 and not self.sad_sound_played:
                pygame.mixer.stop()
                sad_sound.play()
                self.sad_sound_played = True