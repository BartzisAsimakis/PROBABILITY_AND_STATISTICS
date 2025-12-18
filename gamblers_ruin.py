import tkinter as tk
from tkinter import messagebox
import random

# -------------------------------------------------
# Κεντράρισμα παραθύρου
# -------------------------------------------------
def center_window(win, width=650, height=770):
    screen_w = win.winfo_screenwidth()
    screen_h = win.winfo_screenheight()
    x = (screen_w - width) // 2
    y = (screen_h - height) // 2
    win.geometry(f"{width}x{height}+{x}+{y}")

# -------------------------------------------------
# Πιθανότητα Χρεοκοπίας Παίκτη (ΑΚΡΙΒΩΣ όπως στο βιβλίο)
# -------------------------------------------------
def gambler_ruin_probability(n, k, p):
    q = 1 - p

    # Δίκαιο παιχνίδι
    if abs(p - q) < 1e-12:
        return k / n

    r = q / p
    return (1 - r**k) / (1 - r**n)

# -------------------------------------------------
# Υπολογισμός
# -------------------------------------------------
def calculate():
    try:
        n = int(entry_n.get())
        k = int(entry_k.get())
        p = float(entry_p.get())

        if not (0 < p < 1):
            raise ValueError("Το p πρέπει να είναι μεταξύ 0 και 1")
        if not (0 <= k <= n):
            raise ValueError("Πρέπει να ισχύει 0 ≤ k ≤ n")

        prob = gambler_ruin_probability(n, k, p)
        percent = prob * 100

        result_label.config(
            text=(
                "🎉 ΑΠΟΤΕΛΕΣΜΑ 🎉\n\n"
                f"Πιθανότητα να φτάσει τα {n}€:\n\n"
                f"{prob:.6f}\n"
                f"({percent:.2f}%)"
            )
        )

        dice_label.config(text=random.choice(["🎰🎲", "🎲🃏", "🎰🃏"]))

    except Exception as e:
        messagebox.showerror("Σφάλμα", str(e))

def enter_pressed(event=None):
    if entry_n.get() and entry_k.get() and entry_p.get():
        calc_button.flash()
        calculate()


# -------------------------------------------------
# GUI
# -------------------------------------------------
root = tk.Tk()
root.title("🎲 Gambler's Ruin Simulator")
root.configure(bg="lightblue")
center_window(root)

# Τίτλος
title = tk.Label(
    root,
    text="🎰 Χρεοκοπία του Παίκτη 🎰",
    font=("Comic Sans MS", 24, "bold"),
    bg="lightblue"
)
title.pack(pady=10)

# Πλαίσιο Θεωρίας
theory_frame = tk.Frame(root, bg="orange", bd=6, relief="ridge")
theory_frame.pack(padx=20, pady=10, fill="x")

theory_text = (
    "ΘΕΩΡΙΑ ΠΙΘΑΝΟΤΗΤΩΝ\n\n"
    "wₖ = p·wₖ₊₁ + q·wₖ₋₁\n"
    "w₀ = 0 , wₙ = 1\n\n"
    "Αν p ≠ q:\n"
    "wₖ = (1 − rᵏ) / (1 − rⁿ),  r = q/p\n\n"
    "Αν p = q:\n"
    "wₖ = k / n"
)

theory_label = tk.Label(
    theory_frame,
    text=theory_text,
    font=("Comic Sans MS", 13),
    bg="orange",
    justify="left"
)
theory_label.pack(padx=10, pady=10)

# Πλαίσιο Εισόδου
input_frame = tk.Frame(root, bg="lightblue")
input_frame.pack(pady=10)

labels = ["n (στόχος €):", "k (αρχικά €):", "p (πιθανότητα):"]
entries = []

for i, text in enumerate(labels):
    tk.Label(
        input_frame,
        text=text,
        font=("Comic Sans MS", 12),
        bg="lightblue"
    ).grid(row=i, column=0, sticky="e", pady=3)

    entry = tk.Entry(input_frame, font=("Comic Sans MS", 12), width=10)
    entry.grid(row=i, column=1, padx=5)
    entries.append(entry)

entry_n, entry_k, entry_p = entries

# Κουμπί
calc_button = tk.Button(
    root,
    text="🎰 ΥΠΟΛΟΓΙΣΕ",
    font=("Comic Sans MS", 14, "bold"),
    command=calculate
)
calc_button.pack(pady=10)

# Αποτελέσματα
result_label = tk.Label(
    root,
    text="",
    font=("Comic Sans MS", 15, "bold"),
    bg="lightblue",
    height=6,
    wraplength=550,
    justify="center"
)
result_label.pack(pady=10)

# Γραφικά παιχνιδιού
dice_label = tk.Label(
    root,
    text="🎲🎰",
    font=("Arial", 36),
    bg="lightblue"
)
dice_label.pack(pady=5)

root.bind("<Return>", enter_pressed)


# Εκκίνηση
root.mainloop()
