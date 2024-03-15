import tkinter as tk
from typing import TYPE_CHECKING
from tkinter import ttk

if TYPE_CHECKING:
    from app.controller import Controller


class View:
    def __init__(self, root, controller):
        self.root = root
        self.root.title("Player List")
        self.controller:Controller = controller
        self.create_widgets()

    def create_widgets(self):
        self.player_tree = ttk.Treeview(self.root, columns=("ID", "Full Name", "Birth Date", "Football Team", "Home City", "Squad", "Position"), show="headings")
        self.player_tree.grid(row=0, column=0, padx=10, pady=10, columnspan=3)

        self.first_button = ttk.Button(self.root, text="First", command=self.controller.first_page)
        self.first_button.grid(row=1, column=2, padx=(150, 200), pady=5, sticky="w")

        self.prev_button = ttk.Button(self.root, text="Previous", command=self.controller.prev_page)
        self.prev_button.grid(row=1, column=1, padx=(0, 10), pady=5)

        self.next_button = ttk.Button(self.root, text="Next", command=self.controller.next_page)
        self.next_button.grid(row=1, column=2, padx=(100, 150), pady=5)

        self.last_button = ttk.Button(self.root, text="Last", command=self.controller.last_page)
        self.last_button.grid(row=1, column=1, padx=(0, 0), pady=5, sticky="e")



        headers = ["ID", "Full Name", "Birth Date", "Football Team", "Home City", "Squad", "Position"]
        for header in headers:
            self.player_tree.heading(header, text=header)

        # Виджеты для ввода критериев поиска
        header_menu = tk.Menu(self.root)
        self.root.config(menu=header_menu)

        search_menu = tk.Menu(header_menu, tearoff=0)
        header_menu.add_cascade(label="Options", menu=search_menu)

        search_menu.add_command(label="Add Player", command=self.show_add_player_window)
        search_menu.add_command(label="Search Players", command=self.show_search_window)

    def show_search_window(self):
        search_window = tk.Toplevel(self.root)
        search_window.title("Search Players")

        search_frame = tk.Frame(search_window)
        search_frame.pack(padx=10, pady=10)

        self.search_entry = ttk.Entry(search_frame, width=30)
        self.search_entry.grid(row=0, column=0, padx=10, pady=5)

        self.criteria_var = tk.StringVar()
        criteria_dropdown = ttk.Combobox(search_frame, textvariable=self.criteria_var,
                                         values=["Full Name", "Birth Date", "Position", "Squad", "Football Team",
                                                 "Home City"])
        criteria_dropdown.set("Select Criteria")
        criteria_dropdown.grid(row=0, column=1, padx=10, pady=5)

        # Treeview to display search results
        self.search_result_tree = ttk.Treeview(search_frame, columns=(
        "ID", "Full Name", "Birth Date", "Football Team", "Home City", "Squad", "Position"), show="headings")
        self.search_result_tree.grid(row=1, column=0, columnspan=3, padx=10, pady=10)

        headers = ["ID", "Full Name", "Birth Date", "Football Team", "Home City", "Squad", "Position"]
        for header in headers:
            self.search_result_tree.heading(header, text=header)

        search_button = ttk.Button(search_frame, text="Search", command=self.controller.search_players)
        search_button.grid(row=0, column=2, padx=10, pady=5)

        self.first_button = ttk.Button(search_frame, text="First", command=self.controller.first_page)
        self.first_button.grid(row=2, column=0, padx=(150, 200), pady=5, sticky="w")

        self.prev_button = ttk.Button(search_frame, text="Previous", command=self.controller.prev_page)
        self.prev_button.grid(row=2, column=0, padx=(0, 10), pady=5)

        self.next_button = ttk.Button(search_frame, text="Next", command=self.controller.next_page)
        self.next_button.grid(row=2, column=1, padx=(0, 10), pady=5)

        self.last_button = ttk.Button(search_frame, text="Last", command=self.controller.last_page)
        self.last_button.grid(row=2, column=1, padx=(0, 0), pady=5, sticky="e")

    def update_search_results(self, players):
        for item in self.search_result_tree.get_children():
            self.search_result_tree.delete(item)

        if players:
            for player in players:
                self.search_result_tree.insert("", tk.END, values=player)
        else:
            self.search_result_tree.insert("", tk.END, values=["No players found."])

    def show_add_player_window(self):
        add_player_window = tk.Toplevel(self.root)
        add_player_window.title("Add Player")

        add_frame = tk.Frame(add_player_window)
        add_frame.pack(padx=10, pady=10)

        full_name_label = tk.Label(add_frame, text="Full Name:")
        full_name_label.grid(row=0, column=0, padx=10, pady=5)
        self.full_name_entry = ttk.Entry(add_frame, width=30)
        self.full_name_entry.grid(row=0, column=1, padx=10, pady=5)

        birth_date_label = tk.Label(add_frame, text="Birth Date (YYYY-MM-DD):")
        birth_date_label.grid(row=1, column=0, padx=10, pady=5)
        self.birth_date_entry = ttk.Entry(add_frame, width=30)
        self.birth_date_entry.grid(row=1, column=1, padx=10, pady=5)

        football_team_label = tk.Label(add_frame, text="Football Team:")
        football_team_label.grid(row=2, column=0, padx=10, pady=5)
        self.football_team_entry = ttk.Entry(add_frame, width=30)
        self.football_team_entry.grid(row=2, column=1, padx=10, pady=5)

        home_city_label = tk.Label(add_frame, text="Home City:")
        home_city_label.grid(row=3, column=0, padx=10, pady=5)
        self.home_city_entry = ttk.Entry(add_frame, width=30)
        self.home_city_entry.grid(row=3, column=1, padx=10, pady=5)

        squad_label = tk.Label(add_frame, text="Squad:")
        squad_label.grid(row=4, column=0, padx=10, pady=5)
        self.squad_entry = ttk.Entry(add_frame, width=30)
        self.squad_entry.grid(row=4, column=1, padx=10, pady=5)

        position_label = tk.Label(add_frame, text="Position:")
        position_label.grid(row=5, column=0, padx=10, pady=5)
        self.position_entry = ttk.Entry(add_frame, width=30)
        self.position_entry.grid(row=5, column=1, padx=10, pady=5)

        add_button = ttk.Button(add_frame, text="Add Player", command=self.controller.add_player)
        add_button.grid(row=6, column=0, columnspan=2, padx=10, pady=5)  # corrected grid placement

    def update_player_window(self, players):
        for item in self.player_tree.get_children():
            self.player_tree.delete(item)

        # Обновляем таблицу данными
        if players:
            for player in players:
                self.player_tree.insert("", tk.END, values=player)
        else:
            self.player_tree.insert("", tk.END, values=["No players found."])