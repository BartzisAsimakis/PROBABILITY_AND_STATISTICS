import tkinter as tk
from tkinter import messagebox
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

# ------------------ ΒΟΗΘΗΤΙΚΗ ΣΥΝΑΡΤΗΣΗ ------------------

def center_window(win, width, height):
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

    fig = plt.Figure(figsize=(6.4,3.9))  # μικρότερο γράφημα
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
             font=("Lucida", 12, "bold"), bg="lightblue").pack(pady=5)
    entry_n_exp = tk.Entry(win, width=10)
    entry_n_exp.pack(pady=5)

    entries_frame = tk.Frame(win, bg="lightblue")
    entries_frame.pack(pady=5)

    result_label = tk.Label(win, text="", font=("Lucida", 12), bg="lightblue")
    result_label.pack(pady=10)

    date_list = []
    current_index = [0]
    total_n = [0]

    def create_date_fields(event=None):
        for widget in entries_frame.winfo_children():
            widget.destroy()
        date_list.clear()
        current_index[0] = 0

        try:
            n = int(entry_n_exp.get())
            if n < 1 or n > 365:
                raise ValueError
            total_n[0] = n
        except:
            messagebox.showerror("Σφάλμα", "Δώσε έγκυρο αριθμό n (1–365)")
            return

        tk.Label(entries_frame, text=f"Εισάγετε {n} ημερομηνίες (π.χ. 12/4):",
                 font=("Lucida", 12, "bold"), bg="lightblue").pack(pady=5)

        show_next_entry()
        b1.pack_forget()

    def show_next_entry():
        for widget in entries_frame.winfo_children():
            if isinstance(widget, tk.Frame):
                widget.destroy()

        if current_index[0] < total_n[0]:
            frame = tk.Frame(entries_frame, bg="lightblue")
            frame.pack(pady=2)
            tk.Label(frame, text=f"Άτομο {current_index[0]+1}:", bg="lightblue").pack(side=tk.LEFT)
            e = tk.Entry(frame, width=10)
            e.pack(side=tk.LEFT)
            e.focus_set()

            def save_date(event=None):
                text = e.get().strip()
                if text:
                    date_list.append(text)
                    current_index[0] += 1
                    if current_index[0] < total_n[0]:
                        show_next_entry()
                    else:
                        # Μόλις εισαχθεί η τελευταία ημερομηνία → εκτέλεση πειράματος
                        run_experiment()
                else:
                    e.focus_set()

            btn_save = tk.Button(frame, text="Υποβολή", command=save_date)
            btn_save.pack(side=tk.LEFT, padx=5)
            btn_save.bind("<Return>", save_date)
            e.bind("<Return>", save_date)

    def run_experiment(event=None):
        try:
            dates = []
            for text in date_list:
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

    b1 = tk.Button(win, text="Υποβολή", command=create_date_fields)
    b1.pack(pady=5)
    b1.bind("<Return>", create_date_fields)
    entry_n_exp.bind("<Return>", create_date_fields)

# ------------------ ΥΠΟΛΟΓΙΣΜΟΣ ------------------

def calculate(event=None):
    try:
        n = int(entry_n.get())
        p_diff = probability_all_different(n)
        p_same = 1 - p_diff

        explanation = (
            "🧠 Συλλογισμός:\n"
            "Για να υπολογίσουμε την πιθανότητα όλα τα γενέθλια να είναι διαφορετικά:\n"
            "1️ Ανεξαρτησία γεγονότων\n"
            "2️ Πιθανότητα συνδυασμών\n"
            "3️ Συμπληρωματικό γεγονός: P(τουλάχιστον μία σύμπτωση) = 1 - P(όλα διαφορετικά)\n\n"
            f"Για n={n} → P(όλα διαφορετικά) = {p_diff:.6f}\n"
            f"P(τουλάχιστον μία σύμπτωση) = {p_same:.6f}"
        )

        label_result.config(font=("Comic Sans MS", 12), text=explanation,
                            bg="orange", relief="sunken")
    except:
        messagebox.showerror("Σφάλμα", "Δώσε έγκυρο αριθμό n")

# ------------------ GUI ------------------

root = tk.Tk()
root.title("Κεφάλαιο 1ο - Πρόβλημα Γενεθλίων")
center_window(root, 900, 750)
root.configure(bg="lightblue")

tk.Label(root, text="🎉 Το Πρόβλημα των Γενεθλίων 🎉",
         font=("Lucida", 18, "bold"), bg="lightblue").pack(pady=10)

problem_text = (
    "Δίνονται n άτομα σε ένα πάρτυ.\n"
    "Ποια είναι η πιθανότητα όλα τα άτομα να έχουν διαφορετικά γενέθλια;\n"
    "Υποθέτουμε 365 ισοπίθανες ημέρες."
)
tk.Label(root, text=problem_text, font=("Lucida", 12), bg="lightblue").pack(pady=10)

frame_input = tk.Frame(root, bg="lightblue")
frame_input.pack(pady=10)
tk.Label(frame_input, text="Αριθμός ατόμων n:", bg="lightblue").pack(side=tk.LEFT)
entry_n = tk.Entry(frame_input, width=10)
entry_n.pack(side=tk.LEFT, padx=5)

btn_calc = tk.Button(root, text="Υπολογισμός 📐", command=calculate)
btn_calc.pack(pady=5)
btn_calc.bind("<Return>", calculate)
entry_n.bind("<Return>", calculate)

btn_exp = tk.Button(root, text="Πείραμα 🎲", command=experiment_window)
btn_exp.pack(pady=5)
btn_exp.bind("<Return>", experiment_window)

label_result = tk.Label(root, text="", font=("Comic Sans MS", 12),
                        bg="orange", relief="sunken", justify="left")
label_result.pack(pady=15)

embed_graph()

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
