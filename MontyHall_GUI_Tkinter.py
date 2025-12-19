#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Monty Hall - Premium Interactive Demo (Tkinter)

Αποθήκευση: MontyHall_GUI_Tkinter_premium.py
Τρέξιμο: python MontyHall_GUI_Tkinter_premium.py

Premium features:
- Κεντράρισμα παραθύρου
- Μεγαλύτερες γραμματοσειρές και καθαρή διάταξη
- Απαλές 'αναλαμπές' όταν ο host ανοίγει πόρτες (animation)
- Δυναμική ανάλυση πιθανοτήτων σε κάθε βήμα με εξήγηση βάσει κανόνα πολλαπλασιασμού
- Buttons: New Game, Stay, Switch, Simulate (1000 / 10000)
- Στατιστικά για switch vs stay
"""

import tkinter as tk
from tkinter import ttk, messagebox, font
import random
import threading
import time
from openai import OpenAI
import sys


#print(sys.executable)  # για έλεγχο ποια Python τρέχει


# client = OpenAI()

# response = client.responses.create(
#     model="gpt-4o-mini",
#     input="Μία ιστορική πληροφορία για το παράδοξο του Monty Hall μέχρι 80 χαρακτήρες"
# )

# answer = response.output_text
# print(answer)



# except Exception as e:
#     with open(responses_path, "w", encoding="utf-8") as f:
#         f.write(f"Σφάλμα κατά την εκτέλεση του ChatGPT API: {e}")

# ---------- Helpers ----------
def center_window(root, width, height):
    root.update_idletasks()
    x = (root.winfo_screenwidth() // 2) - (width // 2)
    y = (root.winfo_screenheight() // 2) - (height // 2)
    root.geometry(f"{width}x{height}+{x}+{y}")

# ---------- Main GUI ----------
class MontyHallPremium:
    def __init__(self, root):
        style = ttk.Style()
        style.configure(".", background="lightblue")  # Το "." εφαρμόζεται σε όλα τα ttk widgets

        self.root = root
        root.title("Το παράδοξο του Monty Hall")
        root.configure(bg="orange")  # ή root["bg"] = "lightblue"
        # styling & fonts
        self.base_font = font.nametofont("TkDefaultFont")
        self.base_font.configure(size=11)
        self.title_font = font.Font(size=14, weight="bold")
        self.large_font = font.Font(size=12)
        self.small_font = font.Font(size=10)


        # initial window size and center
        self.win_width = 800
        self.win_height = 920
        center_window(root, self.win_width, self.win_height)

        # state
        self.num_doors = tk.IntVar(value=3)
        self.leave_one_other = tk.BooleanVar(value=True)
        self.game_in_progress = False
        self.player_choice = None
        self.car_door = None
        self.opened_doors = set()
        self.door_buttons = []
        self.switch_stats = {'switch_wins': 0, 'switch_losses': 0}
        self.stay_stats = {'stay_wins': 0, 'stay_losses': 0}

        # top frame: title + controls
        top = ttk.Frame(root, padding=10)
        top.pack(side=tk.TOP, fill=tk.X)

        title_lbl = ttk.Label(top, text="Monty Hall — Παράδειγμα\n", font=self.title_font)
        title_lbl.pack(padx=(4,20))

        controls = ttk.Frame(top)
        controls.pack(side=tk.TOP)

        ttk.Label(controls, text="Αριθμός πόρτων:", font=self.small_font).grid(row=0, column=0, sticky="e")
        self.spin = ttk.Spinbox(controls, from_=3, to=50, textvariable=self.num_doors,
                                width=7, command=self.reset_board, font=self.small_font)
        self.spin.grid(row=0, column=1, padx=6)

        ttk.Button(controls, text="Νέο Παιχνίδι", command=self.reset_board).grid(row=0, column=2, padx=6)
        ttk.Button(controls, text="Προσομοίωση (1000)", command=lambda: self.run_simulation(1000)).grid(row=0, column=3, padx=6)
        ttk.Button(controls, text="Προσομοίωση (10000)", command=lambda: self.run_simulation(10000)).grid(row=0, column=4, padx=6)

        ttk.Checkbutton(controls, text='Host αφήνει μία άλλη κλειστή πόρτα (κλασικό)', variable=self.leave_one_other).grid(row=1, column=0, columnspan=5, pady=(6,0))

        # main content: left = board, right = info
        content = ttk.Frame(root, padding=10)
        content.pack(fill=tk.BOTH, expand=True)

        # ---- left: board ----
        board_panel = ttk.Frame(content)
        board_panel.pack(side=tk.TOP, fill=tk.BOTH, expand=True)

        board_label = ttk.Label(board_panel, font=self.large_font)
        board_label.pack(anchor='nw')

        self.board_frame = ttk.Frame(board_panel, padding=8, relief=tk.FLAT)
        self.board_frame.pack(fill=tk.BOTH, expand=True)

        # ---- right: info & actions ----
        right_panel = ttk.Frame(content, width=520)
        right_panel.pack(side=tk.RIGHT, fill=tk.Y)

        # status
        self.status_label = ttk.Label(right_panel, text='Πατήστε "Νέο Παιχνίδι" ή επιλέξτε πόρτα.', wraplength=300, font=self.small_font)
        self.status_label.pack(pady=(0,8), anchor='nw')

        # probabilities box (premium look)
        prob_frame = ttk.LabelFrame(right_panel, text="Ανάλυση Πιθανοτήτων", padding=8)
        prob_frame.config(width=500)   # ή όποιο πλάτος θες
        prob_frame.pack(pady=(0,8), anchor='center')


        self.prob_text = tk.Text(prob_frame, height=10, width=118, font=self.small_font, state='disabled', bg="#f7f7f7")
        self.prob_text.pack(fill=tk.X)

        # rule explanation (multiplication rule)
        rule_frame = ttk.LabelFrame(right_panel, text="Κανόνας Πολλαπλασιασμού — Ερμηνεία", padding=8)
        rule_frame.pack(fill=tk.X, pady=(0,8))
        self.rule_lbl = ttk.Label(rule_frame, text="Ο κανόνας πολλαπλασιασμού: P(A∩B) = P(A)·P(B|A)\nΕδώ τον χρησιμοποιούμε για να εξηγήσουμε γιατί το switch έχει μεγαλύτερη πιθανότητα.", wraplength=600, font=self.small_font)
        self.rule_lbl.pack()

        # actions
        actions = ttk.Frame(right_panel)
        actions.pack(fill=tk.X, pady=(6,0))

        self.stay_button = ttk.Button(actions, text='ΜΕΝΩ (Stay)', command=self.resolve_stay, state=tk.DISABLED)
        self.stay_button.pack(side=tk.LEFT, expand=True, fill=tk.X, padx=4)
        self.switch_button = ttk.Button(actions, text='ΑΛΛΑΖΩ (Switch)', command=self.resolve_switch, state=tk.DISABLED)
        self.switch_button.pack(side=tk.LEFT, expand=True, fill=tk.X, padx=4)

        # stats
        # stats_frame_style = ttk.Style()
        # stats_frame_style.configure("Stats.TLabelframe", background="lightblue")

        stats_frame = ttk.LabelFrame(right_panel, text="Στατιστικά", padding=8)
        stats_frame.pack(fill=tk.X, pady=(8,0))


        self.switch_label = ttk.Label(stats_frame, text='Switch: 0 νίκες / 0 συνολικά (0.00%)', font=self.small_font)
        self.switch_label.pack(anchor='w')
        self.stay_label = ttk.Label(stats_frame, text='Stay: 0 νίκες / 0 συνολικά (0.00%)', font=self.small_font)
        self.stay_label.pack(anchor='w')

        # footer hint
        style = ttk.Style()
        style.configure("Footer.TLabel", background="orange")

        footer = ttk.Label(root, text="", font=self.small_font, style="Footer.TLabel")
        footer.pack(side=tk.BOTTOM, pady=(6,6))


        # initialize
        self.reset_board()

    # ---------- board lifecycle ----------
    def reset_board(self):
        # reset state
        self.game_in_progress = True
        self.player_choice = None
        self.opened_doors = set()
        self.door_buttons = []

        n = max(3, int(self.num_doors.get()))
        self.car_door = random.randrange(n)

        # clear board_frame
        for w in self.board_frame.winfo_children():
            w.destroy()

        # create grid of doors (stylish buttons)
        cols = min(6, n)
        rows = (n + cols - 1) // cols
        idx = 0
        for r in range(rows):
            row = ttk.Frame(self.board_frame)
            row.pack(pady=6)
            for c in range(cols):
                if idx >= n: break
                btn = tk.Button(row, text=f'Πόρτα\n{idx+1}', width=10, height=4,
                                font=self.large_font, relief='raised',
                                command=lambda i=idx: self.player_select(i))
                btn.grid(row=0, column=c, padx=6, pady=2)
                # store metadata on button
                btn.door_index = idx
                btn.is_revealed = False
                self.door_buttons.append(btn)
                idx += 1

        # reset UI controls
        self.stay_button.config(state=tk.DISABLED)
        self.switch_button.config(state=tk.DISABLED)

        self.status_label.config(text=f'Επιλέξτε μία πόρτα (1..{n}).')
        self.update_probabilities()
        self.update_stats_labels()

    def player_select(self, i):
        if not self.game_in_progress:
            return
        if self.player_choice is not None:
            messagebox.showinfo('Προσοχή', 'Έχετε ήδη επιλέξει πόρτα.')
            return
        self.player_choice = i
        # visual selection
        for b in self.door_buttons:
            b.config(relief='raised')
        self.door_buttons[i].config(relief='sunken')
        self.status_label.config(text=f'Επιλέξατε πόρτα {i+1}. Ο host ανοίγει πόρτες με κατσίκες...')
        self.update_probabilities()
        # host opens doors after short delay (for animation)
        self.root.after(10000, self.host_open_doors)

    def host_open_doors(self):
        n = len(self.door_buttons)
        can_open = [d for d in range(n) if d != self.car_door and d != self.player_choice]

        # decide which to open
        if self.leave_one_other.get():
    # ΚΛΑΣΙΚΟ ΜΟΝΤΥ ΧΩΡΙΣ ΛΑΘΟΣ
            if self.player_choice != self.car_door:
                # Αν ο παίκτης διάλεξε λάθος, η ΜΟΝΗ πόρτα που μένει είναι αυτή με το αυτοκίνητο
                keep_closed = self.car_door
                to_open = [d for d in can_open if d != keep_closed]
            else:
                # Αν ο παίκτης διάλεξε το αυτοκίνητο, τότε μένει τυχαία μία λάθος πόρτα
                keep_closed = random.choice(can_open)
                to_open = [d for d in can_open if d != keep_closed]
        else:
            open_count = max(1, len(can_open)//2)
            to_open = random.sample(can_open, open_count)

        # animate opening one-by-one
        def reveal_sequence(seq, i=0):
            if i >= len(seq):
                # finished opening
                self.opened_doors.update(seq)
                self.stay_button.config(state=tk.NORMAL)
                self.switch_button.config(state=tk.NORMAL)
                remaining = [j for j in range(n) if j not in self.opened_doors]
                self.status_label.config(text=f'Host άνοιξε {len(seq)} πόρτες. Απομένουν {len(remaining)} κλειστές. Επιλέξτε Stay ή Switch.')
                self.update_probabilities()
                return
            d = seq[i]
            self.animate_reveal_goat(d, lambda: reveal_sequence(seq, i+1))

        reveal_sequence(to_open)

    def animate_reveal_goat(self, d, callback=None):
        btn = self.door_buttons[d]
        # small blink then reveal goat
        def step1():
            btn.config(text='...', state='disabled')
            self.root.after(180, step2)
        def step2():
            btn.config(text=f'Πόρτα {d+1}\n🐐', bg="#f8d7da", activebackground="#f5c6cb")
            btn.is_revealed = True
            if callback:
                # tiny pause for effect
                self.root.after(160, callback)
        step1()

    def animate_reveal_car(self, d, callback=None):
        btn = self.door_buttons[d]
        btn.config(text=f'Πόρτα {d+1}\n🚗', bg="#d4edda", activebackground="#c3e6cb")
        btn.is_revealed = True
        if callback:
            self.root.after(160, callback)

    def resolve_stay(self):
        if self.player_choice is None:
            return
        self.finish_game(switch=False)

    def resolve_switch(self):
        if self.player_choice is None:
            return
        n = len(self.door_buttons)
        remaining_closed = [i for i in range(n) if i not in self.opened_doors]
        other_choices = [i for i in remaining_closed if i != self.player_choice]
        if not other_choices:
            messagebox.showinfo('Πληροφορία', 'Δεν υπάρχει άλλη κλειστή πόρτα για να αλλάξετε — αντιμετωπίζεται σαν Stay.')
            self.finish_game(switch=False)
            return
        # choose new (simulate user's switch): choose the best logical (if only one) or random
        new = random.choice(other_choices)
        # update UI selection
        for b in self.door_buttons:
            b.config(relief='raised')
        self.door_buttons[new].config(relief='sunken')
        self.player_choice = new
        self.finish_game(switch=True)

    def finish_game(self, switch: bool):
        n = len(self.door_buttons)
        # reveal all (animate)
        to_reveal = [i for i in range(n) if not self.door_buttons[i].is_revealed]
        # reveal car last for dramatic effect
        def seq_reveal(seq, i=0):
            if i >= len(seq):
                # update stats & UI
                won = (self.player_choice == self.car_door)
                if switch:
                    if won:
                        self.switch_stats['switch_wins'] += 1
                    else:
                        self.switch_stats['switch_losses'] += 1
                else:
                    if won:
                        self.stay_stats['stay_wins'] += 1
                    else:
                        self.stay_stats['stay_losses'] += 1
                self.update_stats_labels()
                if won:
                    self.status_label.config(text=f'ΝΙΚΗ! Η πόρτα {self.player_choice+1} είχε το αυτοκίνητο.')
                else:
                    self.status_label.config(text=f'ΗΤΤΑ. Η πόρτα {self.player_choice+1} δεν είχε αυτοκίνητο. (Αυτή ήταν η πόρτα {self.car_door+1})')
                self.stay_button.config(state=tk.DISABLED)
                self.switch_button.config(state=tk.DISABLED)
                self.game_in_progress = False
                self.update_probabilities()
                return
            d = seq[i]
            if d == self.car_door:
                # reveal car with highlight
                self.animate_reveal_car(d, lambda: self.root.after(220, lambda: seq_reveal(seq, i+1)))
            else:
                self.animate_reveal_goat(d, lambda: self.root.after(120, lambda: seq_reveal(seq, i+1)))
        seq_reveal(to_reveal)

    # ---------- probabilities & explanation ----------
    def update_probabilities(self):
        n = len(self.door_buttons)
        # prepare text area writable
        self.prob_text.config(state='normal')
        self.prob_text.delete('1.0', tk.END)

        if self.player_choice is None:
            txt = (f"Αρχική κατάσταση (πριν την επιλογή):\n"
                   f"- Κάθε πόρτα έχει P = 1/{n} = {1/n:.4f}\n"
                   f"- Συνολική P(όχι η επιλεγμένη) = {(n-1)}/{n} = {(n-1)/n:.4f}\n\n"
                   f"Εξηγούμε με τον Κανόνα Πολλαπλασιασμού όταν ο host ανοίξει πόρτες.\n")

            self.prob_text.insert(tk.END, txt)
            self.prob_text.config(state='disabled')
            return

        # μετά την επιλογή αλλά προτού ανοίξει ο host
        if len(self.opened_doors) == 0:
            txt = (f"Μετά την επιλογή πόρτας (αλλά πριν ο host ανοίξει):\n"
                f"- P(η αρχική πόρτα έχει αυτοκίνητο) = 1/{n} = {1/n:.4f}\n"
                f"- P(το αυτοκίνητο βρίσκεται σε κάποια από τις υπόλοιπες πόρτες) = {(n-1)}/{n} = {(n-1)/n:.4f}\n\n"
                f"Αφού ο host ανοίξει πόρτες με σίγουρα κατσίκες, οι πιθανότητες αναδιανέμονται —\n"
                f"ο Κανόνας Πολλαπλασιασμού εξηγεί το γιατί το 'switch' κερδίζει περισσότερο.\n")

            self.prob_text.config(state='normal')
            self.prob_text.delete('1.0', tk.END)
            self.prob_text.insert(tk.END, txt)
            self.prob_text.config(state='disabled')

            # ΧΡΗΣΙΜΟΠΟΙΕΙ ΟΠΟΙΟ WIDGET ΥΠΑΡΧΕΙ
            self.prob_text.after(10000, lambda: None)

            return



        # μετά το άνοιγμα από τον host
        remaining_closed = [i for i in range(n) if i not in self.opened_doors]
        remaining_count = len(remaining_closed)
        # P(initial choice correct) remains 1/n
        p_initial = 1 / n
        # P(car not in initial) = (n-1)/n
        p_not_initial = (n-1) / n

        # If host leaves exactly one other closed (classic), then switching gives full p_not_initial to that single door.
        if self.leave_one_other.get() and remaining_count == 3:
            # find the other closed door index
            other = [i for i in remaining_closed if i != self.player_choice][0]
            txt = (f"Μετά το άνοιγμα θυρών (κλασικό σενάριο - έμεινε 1 άλλη κλειστή):\n"
                   f"- P(αρχική πόρτα έχει αυτοκίνητο) = 1/{n} = {p_initial:.4f}\n"
                   f"- P(η άλλη κλειστή πόρτα έχει αυτοκίνητο) = {(n-1)}/{n} = {p_not_initial:.4f}\n\n"
                   f"Κανόνας Πολλαπλασιασμού (σύντομη ερμηνεία):\n"
                   f"Αν θεωρήσουμε το γεγονός A='αρχικά επέλεξα λάθος' και B='ο host ανοίγει αυτές τις πόρτες',\n"
                   f"τότε P(A∩B)=P(A)·P(B|A). Επειδή ο host επιλέγει πάντα πόρτες με κατσίκες, P(B|A)≈1 για τις συγκεκριμένες ανοίγεις,\n"
                   f"οπότε η πιθανότητα που \"μεταφέρεται\" στην άλλη κλειστή πόρτα είναι περίπου P(A) = {(n-1)}/{n}.\n")
            self.prob_text.insert(tk.END, txt)
            self.prob_text.config(state='disabled')
            return

        # γενική περίπτωση: υποθέτουμε ότι το p_not_initial κατανέμεται αναλογικά στις άλλες κλειστές πόρτες
        per_other = p_not_initial / (remaining_count - 1) if (remaining_count - 1) > 0 else 0.0
        txt = (f"Μετά το άνοιγμα θυρών (γενική περίπτωση):\n"
               f"- Κλειστές πόρτες: {remaining_count}\n"
               f"- P(αρχική πόρτα) = 1/{n} = {p_initial:.4f}\n"
               f"- Συνολική P(όχι αρχική) = {(n-1)}/{n} = {p_not_initial:.4f}\n"
               f"- Αν οι υπόλοιπες παίζουν ρόλο, αυτή η συνολική πιθανότητα κατανέμεται στις άλλες κλειστές πόρτες.\n"
               f"- Εκτίμηση P(κάθε άλλη κλειστή πόρτα) ≈ {per_other:.4f}\n\n"
               f"Σημείωση: Ο ακριβής υπολογισμός χρησιμοποιεί τον κανόνα πολλαπλασιασμού για P(A∩B) και υπόθεση για το πώς επιλέγει ο host.\n")
        self.prob_text.insert(tk.END, txt)
        self.prob_text.config(state='disabled')

    # ---------- stats ----------
    def update_stats_labels(self):
        s_w = self.switch_stats['switch_wins']
        s_l = self.switch_stats['switch_losses']
        st_w = self.stay_stats['stay_wins']
        st_l = self.stay_stats['stay_losses']
        s_total = s_w + s_l
        st_total = st_w + st_l
        s_pct = (s_w / s_total * 100) if s_total else 0.0
        st_pct = (st_w / st_total * 100) if st_total else 0.0
        self.switch_label.config(text=f'Switch: {s_w} νίκες / {s_total} συνολικά ({s_pct:.2f}%)')
        self.stay_label.config(text=f'Stay: {st_w} νίκες / {st_total} συνολικά ({st_pct:.2f}%)')

    # ---------- simulation ----------
    def run_simulation(self, trials: int):
        def worker():
            # disable interactive controls
            self.spin.config(state='disabled')
            self.stay_button.config(state='disabled')
            self.switch_button.config(state='disabled')
            self.status_label.config(text=f'Running simulation of {trials} games...')

            leave_one_other = self.leave_one_other.get()
            n = max(3, int(self.num_doors.get()))

            local_switch_wins = 0
            local_switch_losses = 0
            local_stay_wins = 0
            local_stay_losses = 0

            for _ in range(trials):
                car = random.randrange(n)
                player = random.randrange(n)
                can_open = [d for d in range(n) if d != car and d != player]
                if leave_one_other:
                    if len(can_open) > 1:
                        keep_closed = random.choice(can_open)
                        to_open = [d for d in can_open if d != keep_closed]
                    else:
                        to_open = can_open
                else:
                    open_count = max(1, len(can_open)//2)
                    to_open = random.sample(can_open, open_count)
                opened = set(to_open)
                remaining_closed = [i for i in range(n) if i not in opened]
                other_choices = [i for i in remaining_closed if i != player]

                # stay
                if player == car:
                    local_stay_wins += 1
                else:
                    local_stay_losses += 1

                # switch (pick random other closed)
                if other_choices:
                    new_choice = random.choice(other_choices)
                    if new_choice == car:
                        local_switch_wins += 1
                    else:
                        local_switch_losses += 1
                else:
                    # no other choice -> treat as stay
                    if player == car:
                        local_switch_wins += 1
                    else:
                        local_switch_losses += 1

            # merge
            self.switch_stats['switch_wins'] += local_switch_wins
            self.switch_stats['switch_losses'] += local_switch_losses
            self.stay_stats['stay_wins'] += local_stay_wins
            self.stay_stats['stay_losses'] += local_stay_losses

            # update UI in main thread
            self.root.after(0, lambda: self.on_sim_done(trials))

        threading.Thread(target=worker, daemon=True).start()

    def on_sim_done(self, trials):
        self.spin.config(state='normal')
        self.update_stats_labels()
        self.status_label.config(text=f'Προσομοίωση {trials} ολοκληρώθηκε. Δείτε τα στατιστικά.')
def add_footer(root, text="Created by Bartzis Asimakis", bg_color="orange", height=30):
    """
    Προσθέτει ένα footer στο κάτω μέρος του παραθύρου, σταθερού ύψους,
    ορατό ανεξάρτητα από το μέγεθος του παραθύρου.

    Parameters:
    - root: Το κύριο Tkinter παράθυρο (Tk ή Toplevel)
    - text: Το κείμενο που θα εμφανίζεται
    - bg_color: Χρώμα φόντου του footer
    - height: Ύψος footer σε pixels
    """
    # Δημιουργία Label
    footer = tk.Label(root, text=text, bg=bg_color, fg="white", font=("Arial", 10))

    # Σταθερή θέση στο κάτω μέρος
    footer.place(relx=0, rely=1, anchor='sw', relwidth=1, height=height)

    return footer


# ---------- run ----------
if __name__ == "__main__":
    root = tk.Tk()
    app = MontyHallPremium(root)

    add_footer(root)
    root.mainloop()
