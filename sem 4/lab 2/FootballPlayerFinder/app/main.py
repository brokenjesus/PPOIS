import tkinter as tk
import xml.etree.ElementTree as ET
from core.player import Player
from core.my_sql_handler import MySQLHandler

from app.controller import Controller

if __name__ == "__main__":
    root = tk.Tk()
    controller = Controller(root)
    root.mainloop()
    # # Парсинг XML-файла
    # tree = ET.parse(r'C:\Users\Broken\PycharmProjects\FootballPlayerFinder\core\players.xml')
    # root = tree.getroot()
    #
    # # Создание экземпляра класса MySQLHandler
    # db_handler = MySQLHandler()
    #
    # # Добавление игроков в базу данных
    # for player_xml in root.findall('player'):
    #     full_name = player_xml.find('full_name').text
    #     birth_date = player_xml.find('birth_date').text
    #     football_team = player_xml.find('football_team').text
    #     home_city = player_xml.find('home_city').text
    #     squad = player_xml.find('squad').text
    #     position = player_xml.find('position').text
    #
    #     # Создание экземпляра класса Player
    #     player = Player(full_name=full_name, birth_date=birth_date, football_team=football_team,
    #                     home_city=home_city, squad=squad, position=position)
    #
    #     # Добавление игрока в базу данных
    #     db_handler.add_player(player)
    #
    # # Закрытие соединения с базой данных
    # db_handler.close_connection()

