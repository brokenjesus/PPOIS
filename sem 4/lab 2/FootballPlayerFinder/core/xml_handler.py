import xml.etree.ElementTree as ET

from core.db_interface import DBInterface
from core.player import Player


class XMLHandler(DBInterface):
    def __init__(self, path):
        self.path = path

    def add_player(self, player):
        try:
            tree = ET.parse(self.path)
        except FileNotFoundError:
            root = ET.Element("players")
            tree = ET.ElementTree(root)
        else:
            root = tree.getroot()

        player_elem = ET.SubElement(root, "player")

        full_name_elem = ET.SubElement(player_elem, "full_name")
        full_name_elem.text = player.full_name

        birth_date_elem = ET.SubElement(player_elem, "birth_date")
        birth_date_elem.text = player.birth_date.strftime("%Y-%m-%d")

        football_team_elem = ET.SubElement(player_elem, "football_team")
        football_team_elem.text = player.football_team

        home_city_elem = ET.SubElement(player_elem, "home_city")
        home_city_elem.text = player.home_city

        squad_elem = ET.SubElement(player_elem, "squad")
        squad_elem.text = player.squad

        position_elem = ET.SubElement(player_elem, "position")
        position_elem.text = player.position

        tree.write(self.path)

        print(f"Player {player.full_name} has been added to {self.path}.")

    def delete_players(self, criteria):
        try:
            tree = ET.parse(self.path)
        except FileNotFoundError:
            print(f"File '{self.path}' not found.")
            return

        root = tree.getroot()
        deleted_count = 0

        for player_elem in reversed(root.findall("player")):
            full_name = player_elem.find("full_name").text
            birth_date = player_elem.find("birth_date").text
            football_team = player_elem.find("football_team").text
            home_city = player_elem.find("home_city").text
            squad = player_elem.find("squad").text
            position = player_elem.find("position").text

            match = all(criteria.get(key) == value for key, value in {
                "full_name": full_name,
                "birth_date": birth_date,
                "football_team": football_team,
                "home_city": home_city,
                "squad": squad,
                "position": position
            }.items() if criteria.get(key) is not None)

            if match:
                root.remove(player_elem)
                deleted_count += 1

        tree.write(self.path)
        return deleted_count

    def search_players(self, criteria, offset=0, limit=None):
        try:
            tree = ET.parse(self.path)
        except FileNotFoundError:
            return None

        root = tree.getroot()
        players = []

        count = 0

        for player_elem in root.findall("player"):
            full_name = player_elem.find("full_name").text
            birth_date = player_elem.find("birth_date").text
            football_team = player_elem.find("football_team").text
            home_city = player_elem.find("home_city").text
            squad = player_elem.find("squad").text
            position = player_elem.find("position").text

            match = all(criteria.get(key) == value for key, value in {
                "full_name": full_name,
                "birth_date": birth_date,
                "football_team": football_team,
                "home_city": home_city,
                "squad": squad,
                "position": position
            }.items() if criteria.get(key) is not None)

            if match:
                count += 1

                if count > offset:
                    players.append(Player(full_name=full_name, birth_date=birth_date,
                                          football_team=football_team, home_city=home_city, squad=squad,
                                          position=position))

                    if limit is not None and len(players) == limit:
                        break

        return players

    def get_players_count(self, criteria=None):
        try:
            tree = ET.parse(self.path)
        except FileNotFoundError:
            return 0

        root = tree.getroot()
        count = 0

        for player_elem in root.findall("player"):
            full_name = player_elem.find("full_name").text
            birth_date = player_elem.find("birth_date").text
            football_team = player_elem.find("football_team").text
            home_city = player_elem.find("home_city").text
            squad = player_elem.find("squad").text
            position = player_elem.find("position").text

            if criteria is None:
                count += 1
            else:
                match = all(criteria.get(key) == value for key, value in {
                    "full_name": full_name,
                    "birth_date": birth_date,
                    "football_team": football_team,
                    "home_city": home_city,
                    "squad": squad,
                    "position": position
                }.items() if criteria.get(key) is not None)

                if match:
                    count += 1

        return count


if __name__ == "__main__":
    # new_player = Player("John Doe", "1966-05-15", "Team A", "City X", "A", "Forward")

    xml_handler: DBInterface = XMLHandler("players.xml")

    # xml_handler.add_player(new_player)

    criteria = {"full_name": "John Doe", "football_team": "Team A"}

    found_players = xml_handler.search_players({}, 0, 10)

    # xml_handler.delete_players(criteria)

    if found_players:
        print("Found players:")
        for player in found_players:
            print(player.get_player_data())
    else:
        print("No players found.")
