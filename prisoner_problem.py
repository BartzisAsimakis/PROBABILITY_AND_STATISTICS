# -*- coding: utf-8 -*-
"""
Κεφάλαιο 1ο - Προβλήματα - Πρόβλημα 24: Το δίλημμα του φυλακισμένου
ΓΕΝΙΚΕΥΣΗ: K ελεύθεροι από N φυλακισμένους
Διαδραστική εφαρμογή με Tkinter + matplotlib
"""

import tkinter as tk
from tkinter import messagebox
import random

from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure

# ==================== ΒΟΗΘΗΤΙΚΕΣ ====================

def center_window(win, width=950, height=920):
    screen_w = win.winfo_screenwidth()
    screen_h = win.winfo_screenheight()
    x = (screen_w // 2) - (width // 2)
    y = (screen_h // 2) - (height // 2)
    win.geometry(f"{width}x{height}+{x}+{y}")


# ==================== MONTE CARLO ====================

def monte_carlo_probability(n, k, trials):
    """
    Monte Carlo προσομοίωση:
    n = συνολικοί φυλακισμένοι
    k = πόσοι ελευθερώνονται
    Επιστρέφει την πιθανότητα να ελευθερωθεί ο φυλακισμένος 1
    """
    win = 0
    for _ in range(trials):
        prisoners = list(range(1, n + 1))
        free = set(random.sample(prisoners, k))
        if 1 in free:
            win += 1
    return win / trials


# ==================== ΓΡΑΦΗΜΑ ====================

def update_graph():
    global graph_canvas

    try:
        n = int(entry_n.get())
        k = int(entry_k.get())
        if n < 3 or k < 1 or k >= n:
            raise ValueError
    except ValueError:
        return

    steps = list(range(500, 20001, 500))
    results = [monte_carlo_probability(n, k, t) for t in steps]

    fig = Figure(figsize=(6.8, 4.8))
    ax = fig.add_subplot(111)

    ax.plot(steps, results, marker='o', label="Monte Carlo")
    ax.axhline(k / n, linestyle='--', label="Θεωρητική πιθανότητα K/N")

    ax.set_title("Γενίκευση Διλήμματος Φυλακισμένου (K από N)")
    ax.set_xlabel("Αριθμός επαναλήψεων")
    ax.set_ylabel("Πιθανότητα να ελευθερωθείς")
    ax.legend()

    try:
        graph_canvas.get_tk_widget().destroy()
    except:
        pass

    graph_canvas = FigureCanvasTkAgg(fig, master=root)
    graph_canvas.draw()
    graph_canvas.get_tk_widget().pack(pady=10)


# ==================== ΠΡΟΣΟΜΟΙΩΣΗ ====================

def simulate():
    try:
        n = int(entry_n.get())
        k = int(entry_k.get())
        if n < 3 or k < 1 or k >= n:
            raise ValueError
    except ValueError:
        messagebox.showerror("Σφάλμα ❌", "Ισχύει: N ≥ 3 και 1 ≤ K < N")
        return

    prisoners = list(range(1, n + 1))
    free = set(random.sample(prisoners, k))
    me = 1

    possible = list(free - {me})

    if possible:
        said = random.choice(possible)
        guard_msg = f"Ο φύλακας λέει: Ο φυλακισμένος {said} θα ελευθερωθεί."
    else:
        guard_msg = "Ο φύλακας δεν μπορεί να αποκαλύψει κανέναν άλλον."

    result_text = (
        f"🎲 ΤΥΧΑΙΑ ΠΡΟΣΟΜΟΙΩΣΗ\n"
        f"Ελεύθεροι φυλακισμένοι: {sorted(free)}\n\n"
        f"🗣️ {guard_msg}\n\n"
        f"📌 Αρχική πιθανότητα: K/N = {k}/{n}"\
        f"\n📌 Η πληροφορία ΔΕΝ αλλάζει αυτήν την πιθανότητα."
    )

    text_result.config(state="normal")
    text_result.delete("1.0", tk.END)
    text_result.insert(tk.END, result_text)
    text_result.config(state="disabled")

    update_graph()


# ==================== ΠΑΡΑΘΥΡΟ ====================

root = tk.Tk()
root.title("Κεφάλαιο 1ο - Προβλήματα - Πρόβλημα 24: Το δίλημμα του φυλακισμένου")
center_window(root)
root.configure(bg="#ADD8E6")

# ==================== ΤΙΤΛΟΣ ====================

title = tk.Label(root, text="🔒 Το δίλημμα του φυλακισμένου (K από N)",
                 font=("Helvetica", 20, "bold"), bg="#ADD8E6")
title.pack(pady=10)

# ==================== ΕΚΦΩΝΗΣΗ ====================

statement = (
    "Από N φυλακισμένους, ακριβώς K πρόκειται να αφεθούν ελεύθεροι.\n"
    "Ένας φυλακισμένος ρωτά τον φύλακα να του αποκαλύψει\n"
    "έναν ΑΛΛΟΝ που σίγουρα θα ελευθερωθεί.\n\n"
    "Ερώτημα: Αλλάζει η πιθανότητα K/N;"
)

lbl_statement = tk.Label(root, text=statement, font=("Helvetica", 12),
                         bg="#ADD8E6", justify="center")
lbl_statement.pack(pady=10)

# ==================== ΘΕΩΡΙΑ ====================

theory = (
    "🧠 ΓΕΝΙΚΟ ΣΥΜΠΕΡΑΣΜΑ\n"
    "Η αποκάλυψη του φύλακα ΔΕΝ είναι τυχαία.\n"
    "Άρα η πληροφορία είναι υπό συνθήκη.\n\n"
    "👉 Η πιθανότητα παραμένει K/N (Monty Hall γενίκευση 🚗🐐)."
)

lbl_theory = tk.Label(root, text=theory, font=("Helvetica", 12, "bold"),
                      bg="#FFA500", justify="center")
lbl_theory.pack(pady=10, ipadx=10, ipady=10)

# ==================== CONTROLS ====================

frame = tk.Frame(root, bg="#ADD8E6")
frame.pack(pady=10)

lbl_n = tk.Label(frame, text="👥 Συνολικοί φυλακισμένοι (N):", bg="#ADD8E6")
lbl_n.grid(row=0, column=0, padx=5)

entry_n = tk.Entry(frame, width=5)
entry_n.insert(0, "5")
entry_n.grid(row=0, column=1)

lbl_k = tk.Label(frame, text="🔓 Ελεύθεροι (K):", bg="#ADD8E6")
lbl_k.grid(row=0, column=2, padx=5)

entry_k = tk.Entry(frame, width=5)
entry_k.insert(0, "2")
entry_k.grid(row=0, column=3)

btn_sim = tk.Button(frame, text="▶ Προσομοίωση", command=simulate)
btn_sim.grid(row=0, column=4, padx=10)

# ==================== ΑΠΟΤΕΛΕΣΜΑΤΑ ====================

text_result = tk.Text(root, height=7, width=110, state="disabled")
text_result.pack(pady=10)

# ==================== ΕΚΚΙΝΗΣΗ ====================

update_graph()

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
add_footer(root)

root.mainloop()
