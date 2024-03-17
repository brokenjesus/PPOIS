import mysql.connector

from core.db_configs import DBConfigs


class DBHandler(DBConfigs):
    def __init__(self):
        self.conn = mysql.connector.connect(
            host=self.HOST,
            user=self.USER,
            password=self.PASSWORD,
            database=self.DATABASE
        )
        self.cursor = self.conn.cursor()
        self.create_database_if_not_exists()
        self.create_table_if_not_exists()

    def create_database_if_not_exists(self):
        database_creation_query = f"CREATE DATABASE IF NOT EXISTS {DBConfigs.DATABASE}"
        self.cursor.execute(database_creation_query)
        self.conn.commit()

    def create_table_if_not_exists(self):
        table_creation_query = """
        CREATE TABLE IF NOT EXISTS players (
            id INT AUTO_INCREMENT PRIMARY KEY,
            full_name VARCHAR(255),
            birth_date DATE,
            football_team VARCHAR(255),
            home_city VARCHAR(255),
            squad VARCHAR(255),
            position VARCHAR(255)
        )
        """
        self.cursor.execute(table_creation_query)
        self.conn.commit()

    def add_player(self, player):
        insert_query = """
        INSERT INTO players (full_name, birth_date, football_team, home_city, squad, position)
        VALUES (%s, %s, %s, %s, %s, %s)
        """
        player_data = player.get_player_data()
        self.cursor.execute(insert_query, player_data)
        self.conn.commit()

    def search_players(self, criteria, offset=None, limit=None):
        select_query = "SELECT * FROM players"

        conditions = []
        values = []
        for key, value in criteria.items():
            conditions.append(f"{key} = %s")
            values.append(value)

        if conditions:
            select_query += " WHERE " + " AND ".join(conditions)

        if limit is not None:
            select_query += f" LIMIT {limit}"
            if offset is not None:
                select_query += f" OFFSET {offset}"

        self.cursor.execute(select_query, tuple(values))
        result = self.cursor.fetchall()
        return result

    def get_players_count(self, criteria=None):
        count_query = "SELECT COUNT(*) FROM players"
        conditions = []
        values = []
        if criteria:
            for key, value in criteria.items():
                conditions.append(f"{key} = %s")
                values.append(value)

        if conditions:
            count_query += " WHERE " + " AND ".join(conditions)
        self.cursor.execute(count_query, tuple(values))
        result = self.cursor.fetchone()
        if result:
            return result[0]  # Assuming the count is the first column in the result
        else:
            return 0

    def delete_players(self, criteria):
        select_query = "DELETE FROM players"

        conditions = []
        values = []
        for key, value in criteria.items():
            conditions.append(f"{key} = %s")
            values.append(value)

        if conditions:
            select_query += " WHERE " + " AND ".join(conditions)

        self.cursor.execute(select_query, tuple(values))
        result = self.cursor.fetchall()
        return result

    def close_connection(self):
        self.cursor.close()
        self.conn.close()
