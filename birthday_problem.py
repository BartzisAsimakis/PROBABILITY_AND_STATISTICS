import tkinter as tk
from tkinter import messagebox
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

# ------------------ ΒΟΗΘΗΤΙΚΗ ΣΥΝΑΡΤΗΣΗ ------------------

def center_window(win, width, height):
    """Κεντράρει το παράθυρο στην οθόνη"""
    win.update_idletasks()
    x = (win.winfo_screenwidth() // 2) - (width // 2)
    y = (win.winfo_screenheight() // 2) - (height // 2)
    win.geometry(f"{width}x{height}+{x}+{y}")

# ------------------ ΠΙΘΑΝΟΤΗΤΕΣ ------------------

def probability_all_different(n):
    if n > 365:
        return 0
    prob = 1
    for i in range(n):
        prob *= (365 - i) / 365
    return prob

# ------------------ ΓΡΑΦΗΜΑ ------------------

def embed_graph():
    people = list(range(1, 61))
    probs = [1 - probability_all_different(n) for n in people]

    fig = plt.Figure(figsize=(6,3))
    ax = fig.add_subplot(111)
    ax.plot(people, probs, marker='o')
    ax.set_xlabel("Αριθμός ατόμων")
    ax.set_ylabel("Πιθανότητα σύμπτωσης")
    ax.set_title("Πρόβλημα Γενεθλίων 📈")
    ax.grid(True)

    canvas = FigureCanvasTkAgg(fig, master=root)
    canvas.draw()
    canvas.get_tk_widget().pack(pady=10)

# ------------------ ΠΕΙΡΑΜΑ ------------------

def experiment_window():
    win = tk.Toplevel(root)
    win.title("🎲 Πείραμα Γενεθλίων")
    center_window(win, 500, 550)
    win.configure(bg="lightblue")

    tk.Label(win, text="Δώσε τον αριθμό ατόμων n και πάτησε 'Υποβολή'",
             font=("Arial", 12, "bold"), bg="lightblue").pack(pady=5)
    entry_n_exp = tk.Entry(win, width=10)
    entry_n_exp.pack(pady=5)

    entries_frame = tk.Frame(win, bg="lightblue")
    entries_frame.pack(pady=5)

    result_label = tk.Label(win, text="", font=("Arial", 12), bg="lightblue")
    result_label.pack(pady=10)

    entry_widgets = []

    def create_date_fields():
        for widget in entries_frame.winfo_children():
            widget.destroy()
        entry_widgets.clear()

        try:
            n = int(entry_n_exp.get())
            if n < 1 or n > 365:
                raise ValueError
        except:
            messagebox.showerror("Σφάλμα", "Δώσε έγκυρο αριθμό n (1–365)")
            return

        tk.Label(entries_frame, text=f"Εισάγετε {n} ημερομηνίες (π.χ. 12/4):",
                 font=("Arial", 12, "bold"), bg="lightblue").pack(pady=5)
        frame = tk.Frame(entries_frame, bg="lightblue")
        e = tk.Entry(frame, width=10)
        e.pack(side=tk.LEFT)
        date_list = []
        for i in range(n):
            e.children.clear()
            #frame = tk.Frame(entries_frame, bg="lightblue")
            frame.pack(pady=2)
            tk.Label(frame, text=f"Άτομο {i+1}:", bg="lightblue").pack(side=tk.LEFT)
            #e = tk.Entry(frame, width=10)
            date_list.append(e.get())
            #entry_widgets.append(e)

        if len(date_list) == n:
            tk.Button(entries_frame, text="Εκτέλεση Πειράματος", command=run_experiment).pack(pady=10)

    def run_experiment():
        try:
            dates = []
            for e in entry_widgets:
                text = e.get().strip()
                day, month = map(int, text.split("/"))
                if not (1 <= day <= 31 and 1 <= month <= 12):
                    raise ValueError
                dates.append((day, month))

            duplicates = set([d for d in dates if dates.count(d) > 1])
            if duplicates:
                msg = "❗ Υπάρχει σύμπτωση!\n"
                for d in duplicates:
                    msg += f"{d[0]}/{d[1]}: {dates.count(d)} φορές\n"
            else:
                msg = "✔ Όλα τα γενέθλια είναι διαφορετικά 🎈"
            result_label.config(text=msg)
        except:
            messagebox.showerror("Σφάλμα", "Δώσε ημερομηνίες στη μορφή Ημέρα/Μήνας (π.χ. 12/4)")

    tk.Button(win, text="Υποβολή", command=create_date_fields).pack(pady=5)

# ------------------ ΥΠΟΛΟΓΙΣΜΟΣ ------------------

def calculate():
    try:
        n = int(entry_n.get())
        p_diff = probability_all_different(n)
        p_same = 1 - p_diff

        explanation = (
            "🧠 Συλλογισμός:\n"
            "Για να υπολογίσουμε την πιθανότητα όλα τα γενέθλια να είναι διαφορετικά:\n"
            "1️⃣ Ανεξαρτησία γεγονότων\n"
            "2️⃣ Πιθανότητα συνδυασμών\n"
            "3️⃣ Συμπληρωματικό γεγονός: P(τουλάχιστον μία σύμπτωση) = 1 - P(όλα διαφορετικά)\n\n"
            f"Για n={n} → P(όλα διαφορετικά) = {p_diff:.6f}\n"
            f"P(τουλάχιστον μία σύμπτωση) = {p_same:.6f}"
        )

        label_result.config(text=explanation)
    except:
        messagebox.showerror("Σφάλμα", "Δώσε έγκυρο αριθμό n")

# ------------------ GUI ------------------

root = tk.Tk()
root.title("Κεφάλαιο 1ο - Πρόβλημα Γενεθλίων")
center_window(root, 900, 750)
root.configure(bg="lightblue")

tk.Label(root, text="🎉 Το Πρόβλημα των Γενεθλίων 🎉", font=("Arial", 18, "bold"), bg="lightblue").pack(pady=10)

problem_text = (
    "Δίνονται n άτομα σε ένα πάρτυ.\n"
    "Ποια είναι η πιθανότητα όλα τα άτομα να έχουν διαφορετικά γενέθλια;\n"
    "Υποθέτουμε 365 ισοπίθανες ημέρες."
)
tk.Label(root, text=problem_text, font=("Arial", 12), bg="lightblue").pack(pady=10)

frame_input = tk.Frame(root, bg="lightblue")
frame_input.pack(pady=10)
tk.Label(frame_input, text="Αριθμός ατόμων n:", bg="lightblue").pack(side=tk.LEFT)
entry_n = tk.Entry(frame_input, width=10)
entry_n.pack(side=tk.LEFT, padx=5)
tk.Button(root, text="Υπολογισμός 📐", command=calculate).pack(pady=5)
tk.Button(root, text="Πείραμα 🎲", command=experiment_window).pack(pady=5)

label_result = tk.Label(root, text="", font=("Arial", 12), bg="lightblue", justify="left")
label_result.pack(pady=15)

embed_graph()

root.mainloop()
