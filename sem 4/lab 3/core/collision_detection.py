import random
import time

import pygame.mixer

from core.ball import Ball
from core.bullet import Bullet
from core.double_ball import DoubleBall
from core.meteor import Meteor
from game_consts import *

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

    def check_extra_ball_collision(self, balls, paddle, all_sprites):
        extra_balls = [sprite for sprite in all_sprites if isinstance(sprite, DoubleBall)]
        for extra_ball in extra_balls:
            if pygame.sprite.collide_rect(extra_ball, paddle):
                current_ball_count = len(balls)
                new_ball_count = current_ball_count * 2
                for _ in range(new_ball_count):
                    new_ball = Ball()
                    new_ball.rect.center = (random.randint(0, SCREEN_WIDTH), SCREEN_HEIGHT // 2)
                    all_sprites.add(new_ball)
                    balls.append(new_ball)

                extra_ball.kill()

    def check_meteor_collision(self, paddle, all_sprites, running):
        meteors = [sprite for sprite in all_sprites if isinstance(sprite, Meteor)]
        for meteor in meteors:
            if pygame.sprite.collide_rect(meteor, paddle):
                running = False

    def check_bullet_collision(self, walls, bricks, all_sprites):
        bullets = [sprite for sprite in all_sprites if isinstance(sprite, Bullet)]
        for bullet in bullets:
            for brick in bricks:
                if pygame.sprite.collide_rect(bullet, brick):
                    brick.kill()
            for wall in walls:
                if pygame.sprite.collide_rect(bullet, wall):
                    if wall.can_brake:
                        wall.kill()
                    else:
                        wall.set_break_true()
                        wall.image.fill(RED)
                        bullet.kill()

    def check_collision(self, balls, bricks, walls, paddle, all_sprites, running):

        self.check_extra_ball_collision(balls, paddle, all_sprites)
        self.check_meteor_collision(paddle, all_sprites, running)
        self.check_bullet_collision(walls, bricks, all_sprites)

        current_time = time.time()
        for ball in balls:
            if ball.rect.left <= 1 or ball.rect.right >= SCREEN_WIDTH - 1:
                ball.speed_x *= -1
            if ball.rect.top <= 0:
                ball.speed_y *= -1

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

                if ball.rect.bottom >= brick_top_edge - BALL_SPEED or ball.rect.top <= brick_bottom_edge + BALL_SPEED:
                    ball.speed_y *= -1
                elif ball.rect.right >= brick_left_edge - BALL_SPEED and ball.rect.left <= brick_right_edge + BALL_SPEED:
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
                    distance_from_center = ball.rect.centerx - paddle.rect.centerx
                    h = abs(paddle.rect.left - paddle.rect.centerx)

                    ball.speed_x = BALL_SPEED * distance_from_center / h
                elif (
                        ball.rect.right >= wall_left_edge - BALL_SPEED and ball.rect.left <= wall_right_edge + BALL_SPEED):
                    ball.speed_y *= -1
                    # ball.speed_x *= -1
                    if ball.speed_x > 0:
                        ball.speed_x = -1 * (PADDLE_SPEED + 5)
                    else:
                        ball.speed_x = (PADDLE_SPEED + 5)


            if pygame.sprite.spritecollide(ball, walls, False):
                # pygame.mixer.stop()  # Остановка всех звуков

                # sad_sound.play()
                wall_hit = pygame.sprite.spritecollide(ball, walls, False)[0]
                if wall_hit.can_brake:
                    wall_hit.kill()
                wall_left_edge = wall_hit.rect.left
                wall_right_edge = wall_hit.rect.right
                wall_top_edge = wall_hit.rect.top
                wall_bottom_edge = wall_hit.rect.bottom

                if ball.rect.bottom <= wall_top_edge + BALL_SPEED or ball.rect.top >= wall_bottom_edge - BALL_SPEED:
                    ball.speed_y *= -1
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

            if ball.rect.top > SCREEN_HEIGHT:  # Если мяч вышел за нижнюю границу экрана
                balls.remove(ball)
                # sad_sound.play()
            if current_time - self.last_brick_destroyed_time > 3 and not self.sad_sound_played:
                pygame.mixer.stop()
                sad_sound.play()
                self.sad_sound_played = True
