from core.db_handler import DBHandler
from core.player import Player


class PlayersList:
    def __init__(self):
        self.players = []
        self.db_handler = DBHandler()

    def get_players_count(self, criteria=None):
        players_count = self.db_handler.get_players_count(criteria=criteria)
        return players_count

    def fetch_players_from_db(self, limit, offset, search_criteria=None):
        if search_criteria is None:
            search_criteria = {}
        players = self.db_handler.search_players(search_criteria, limit=limit, offset=offset)

        if players:
            self.players = players
            print("Players successfully loaded.")
            return players
        else:
            self.players = None
            print("The database does not contain players with this criteria.")

    def delete_player(self, search_criteria=None):
        if search_criteria is None:
            search_criteria = {}
        self.db_handler.delete_players(search_criteria)

    def add_player(self, full_name, birth_date, football_team, home_city, squad, position):
        player = Player(full_name, birth_date, football_team, home_city, squad, position)
        self.db_handler.add_player(player)
