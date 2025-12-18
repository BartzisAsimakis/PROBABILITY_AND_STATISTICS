import tkinter as tk
from tkinter import messagebox
from math import comb



# Συνάρτηση διωνυμικής πιθανότητας
def binomial_probability(k, n, p):
    return comb(n, k) * (p ** k) * ((1 - p) ** (n - k))

# Συνάρτηση υπολογισμού P(X > c)
def probability_more_than_c(c, n, p):
    prob = 0
    for k in range(c + 1, n + 1):
        prob += binomial_probability(k, n, p)
    return prob

# Συνάρτηση για ενημέρωση GUI
def calculate():
    try:
        c = int(c_var.get())
        n = int(n_var.get())
        p = float(p_var.get())
        if c < 0 or n <= 0 or not (0 <= p <= 1):
            raise ValueError
        if c > n:
            messagebox.showerror("Σφάλμα", "Ο αριθμός των modems δεν μπορεί να είναι μεγαλύτερος από τον αριθμό των πελατών!")
            return

        prob = probability_more_than_c(c, n, p)

        result_label.config(text=f"Πιθανότητα ότι οι πελάτες που χρειάζονται σύνδεση > {c} modems: {prob:.5f}")

        # ΘΕΩΡΗΤΙΚΗ εξήγηση στο πορτοκαλί πλαίσιο
        explanation_text = f"""
Χρήση της διωνυμικής πιθανότητας στο παράδειγμα των modems:

ΓΙΑΤΙ:
- Κάθε πελάτης είτε χρειάζεται σύνδεση είτε όχι (δύο δυνατά αποτελέσματα).
- Η πιθανότητα ότι ένας πελάτης χρειάζεται σύνδεση είναι p = {p}.
- Οι πελάτες ενεργούν ανεξάρτητα μεταξύ τους.

ΠΩΣ:
- Ορίζουμε X = αριθμός πελατών που χρειάζονται σύνδεση.
- Η μεταβλητή X ακολουθεί διωνυμική κατανομή με παραμέτρους n = {n} και p = {p}.
- Θέλουμε την πιθανότητα να χρειάζονται περισσότερες συνδέσεις από τα διαθέσιμα modems (c = {c}), δηλαδή P(X > c).
- Η διωνυμική κατανομή μας επιτρέπει να υπολογίσουμε θεωρητικά αυτή την πιθανότητα, αξιοποιώντας το γεγονός ότι κάθε πελάτης αποτελεί ανεξάρτητη “δοκιμή”.
"""
        explanation_label.config(text=explanation_text)

    except ValueError:
        messagebox.showerror("Σφάλμα", "Παρακαλώ εισάγετε έγκυρους αριθμούς!")

# Δημιουργία GUI
root = tk.Tk()
root.title("Κεφάλαιο 1ο - 1.5 Ανεξαρτησία - Παράδειγμα 1.25 - Ποιότητα Εξυπηρέτησης (Διωνυμική Πιθανότητα)")
root.configure(bg="lightblue")

# Κεντράρισμα του παραθύρου στην οθόνη
window_width = 800
window_height = 600
screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()
x_cordinate = int((screen_width/2) - (window_width/2))
y_cordinate = int((screen_height/2) - (window_height/2))
root.geometry(f"{window_width}x{window_height}+{x_cordinate}+{y_cordinate}")

# Τίτλος
title_label = tk.Label(root, text="Πιθανότητα Πελατών > Modems", font=("Arial", 18, "bold"), bg="lightblue")
title_label.pack(pady=10)

# Πλαίσιο εισαγωγής
input_frame = tk.Frame(root, bg="lightblue")
input_frame.pack(pady=10)

tk.Label(input_frame, text="c (modems):", bg="lightblue").grid(row=0, column=0, padx=5, pady=5, sticky="e")
tk.Label(input_frame, text="n (πελάτες):", bg="lightblue").grid(row=1, column=0, padx=5, pady=5, sticky="e")
tk.Label(input_frame, text="p (πιθανότητα):", bg="lightblue").grid(row=2, column=0, padx=5, pady=5, sticky="e")

c_var = tk.StringVar()
n_var = tk.StringVar()
p_var = tk.StringVar()

tk.Entry(input_frame, textvariable=c_var).grid(row=0, column=1, padx=5, pady=5)
tk.Entry(input_frame, textvariable=n_var).grid(row=1, column=1, padx=5, pady=5)
tk.Entry(input_frame, textvariable=p_var).grid(row=2, column=1, padx=5, pady=5)



calc_button = tk.Button(root, text="Υπολογισμός", command=calculate, bg="orange")
calc_button.pack(pady=10)

# Ετικέτες αποτελεσμάτων
result_label = tk.Label(root, text="", font=("Arial", 14, "bold"), bg="lightblue")
result_label.pack(pady=10)

# Πορτοκαλί πλαίσιο για θεωρητική εξήγηση με wrapping
explanation_label = tk.Label(
    root,
    text="",
    font=("Arial", 12),
    bg="orange",
    justify="left",
    anchor="nw",
    wraplength=750  # ενεργοποιεί το wrapping
)
explanation_label.pack(fill="both", padx=20, pady=10, expand=True)
def add_footer(root, text="Created by Bartzis Asimakis", bg_color="orange"):
    """
    Προσθέτει ένα footer στο κάτω μέρος του παραθύρου.

    Parameters:
    - root: Το κύριο Tkinter παράθυρο (Tk ή Toplevel)
    - text: Το κείμενο που θα εμφανίζεται
    - bg_color: Χρώμα φόντου του footer
    """
    footer = tk.Label(root, text=text, bg=bg_color, fg="white", font=("Arial", 10))
    footer.pack(side="bottom", fill="x")
    return footer

add_footer(root)

root.mainloop()
