import random

from core.game_screens import game_over_text
from game_consts import *

class Ball(pygame.sprite.Sprite):
    def __init__(self, walls, color=RED):
        super().__init__()
        # Создаем поверхность для мяча с прозрачностью
        self.image = pygame.Surface([BALL_RADIUS * 2, BALL_RADIUS * 2], pygame.SRCALPHA)
        # Рисуем круглую форму
        pygame.draw.circle(self.image, color, (BALL_RADIUS, BALL_RADIUS), BALL_RADIUS)
        self.rect = self.image.get_rect()
        self.rect.x = SCREEN_WIDTH // 2
        self.rect.y = SCREEN_HEIGHT // 2
        self.speed_x = 0
        self.speed_y = BALL_SPEED
        self.walls = walls

    def update(self):
        self.rect.x += self.speed_x
        self.rect.y += self.speed_y

        if self.rect.left <= 0 or self.rect.right >= SCREEN_WIDTH:
            self.speed_x *= -1
        if self.rect.top <= 0:
            self.speed_y *= -1


        # if pygame.sprite.spritecollide(self, self.walls, False):
        #     wall_hit = pygame.sprite.spritecollide(self, self.walls, False)[0]
        #     wall_left_edge = wall_hit.rect.left
        #     wall_right_edge = wall_hit.rect.right
        #     wall_top_edge = wall_hit.rect.top
        #     wall_bottom_edge = wall_hit.rect.bottom
        #
        #     if self.rect.bottom <= wall_top_edge + BALL_SPEED or self.rect.top >= wall_bottom_edge - BALL_SPEED:
        #         self.speed_y *= -1
        #         print("top/bottom")
        #     # Проверяем столкновение с боковой стеной
        #     elif (self.rect.right >= wall_left_edge - BALL_SPEED and self.rect.left <= wall_right_edge + BALL_SPEED):
        #         # Вычисляем угол отражения в зависимости от точки столкновения мяча с стеной
        #         # Для простоты будем менять только угол отражения по оси X
        #         ball_center_x = self.rect.centerx
        #         wall_center_x = wall_hit.rect.centerx
        #         reflection_angle = (ball_center_x - wall_center_x) / (
        #                     PADDLE_WIDTH / 2)  # Нормализуем значение от -1 до 1
        #         self.speed_x *= -1
        #         print("side collision")



