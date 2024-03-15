from core.db_handler import DBHandler
from core.players_list import PlayersList
from datetime import datetime
from view import View

class Controller:
    PLAYERS_LIMIT = 10
    offset = 0

    def __init__(self, root):
        self.db_handler = DBHandler()
        self.players_list = PlayersList()
        self.view = View(root, self)

        self.load_players()


    def delete_player(self, player_id):
        search_criteria = {"id": player_id}
        self.db_handler.delete_player(search_criteria)
        self.load_players()

    def load_players(self):
        self.players_list.fetch_players_from_db(limit=self.PLAYERS_LIMIT, offset=self.offset, search_criteria={})
        self.view.update_player_window(self.players_list.players)

    def search_players(self):
        search_term = self.view.search_entry.get()
        criteria = self.view.criteria_var.get()

        if criteria == 'Birth Date':
            try:
                datetime.strptime(search_term, "%Y-%m-%d")
            except ValueError:
                messagebox.showerror("Error", "Invalid date format. Please enter a date in the format DD-MM-YYYY.")
                return

        if search_term and criteria:
            search_criteria = self.configure_search_criteria(criteria, search_term)
            self.players_list.fetch_players_from_db(limit=self.PLAYERS_LIMIT, offset=0,
                                                                    search_criteria=search_criteria)
            self.view.update_search_results(self.players_list.players)

    def add_player(self):
        full_name = self.view.full_name_entry.get()
        birth_date = self.view.birth_date_entry.get()
        football_team = self.view.football_team_entry.get()
        home_city = self.view.home_city_entry.get()
        squad = self.view.squad_entry.get()
        position = self.view.position_entry.get()

        self.players_list.add_player(full_name, birth_date, football_team, home_city, squad, position)

    def first_page(self):
        if self.offset > 0:
            self.offset = 0
            self.load_players()

    def last_page(self):
        if self.offset < self.players_list.get_total_players_count()-self.PLAYERS_LIMIT:
            self.offset = self.players_list.get_total_players_count()-self.PLAYERS_LIMIT
            self.load_players()

    def prev_page(self):
        if self.offset > self.PLAYERS_LIMIT:
            self.offset -= self.PLAYERS_LIMIT
            self.load_players()
        else:
            self.first_page()

    def next_page(self):
        if self.offset < self.players_list.get_total_players_count()-self.PLAYERS_LIMIT:
            self.offset += self.PLAYERS_LIMIT
            self.load_players()

    def configure_search_criteria(self, criteria, search_term):
        match criteria:
            case "Full Name":
                return {"full_name": search_term}
            case "Birth Date":
                return {"birth_date": search_term}
            case "Position":
                return {"position": search_term}
            case "Squad":
                return {"squad": search_term}
            case "Football Team":
                return {"football_team": search_term}
            case "Home City":
                return {"home_city": search_term}
            case _:
                return None

    def show_context_menu(self, event):
        item = self.view.player_tree.item(self.view.player_tree.focus())
        if item and item["values"]:
            player_id = item["values"][0]  # Assuming ID is the first column
            self.confirm_delete(player_id)

    def confirm_delete(self, player_id):
        result = messagebox.askokcancel("Confirm Deletion", "Are you sure you want to delete this player?")
        if result:
            self.delete_player(player_id)