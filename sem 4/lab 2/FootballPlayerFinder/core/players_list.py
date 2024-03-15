from core.db_handler import DBHandler
from core.player import Player

class PlayersList:
    def __init__(self):
        self.players = []

    def get_total_players_count(self):
        db_handler = DBHandler()
        players_count = db_handler.get_total_players_count()
        db_handler.close_connection()
        return players_count

    def fetch_all_players_from_db(self):
        db_handler = DBHandler()
        all_players = db_handler.search_players({})
        db_handler.close_connection()

        if all_players:
            self.players = all_players
            print("Players successfully loaded.")
        else:
            print("The database does not contain any players.")

    def fetch_players_from_db(self, limit, offset, search_criteria={}):
        db_handler = DBHandler()
        players = db_handler.search_players(search_criteria, limit=limit, offset=offset)
        db_handler.close_connection()

        if players:
            self.players = players
            print("Players successfully loaded.")
            return players
        else:
            print("The database does not contain any players.")


    def delete_player(self, player_id):
        db_handler = DBHandler()
        db_handler.delete_player(player_id)
        db_handler.close_connection()

    def add_player(self, full_name, birth_date, football_team, home_city, squad, position):
        db_handler = DBHandler()
        player = Player(full_name, birth_date, football_team, home_city, squad, position)
        db_handler.add_player(player)
        db_handler.close_connection()