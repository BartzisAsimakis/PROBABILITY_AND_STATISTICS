import tkinter as tk
from tkinter import ttk
from PIL import Image, ImageTk
import os

# ======================
# ΣΩΣΤΟΙ ΥΠΟΛΟΓΙΣΜΟΙ (ΘΕΩΡΙΑ ΑΞΙΟΠΙΣΤΙΑΣ)
# ======================

def calculate_probabilities():
    # Πιθανότητες συνδέσμων (ανεξάρτητοι)
    A_C = 0.9
    A_D = 0.75
    C_E = 0.8
    C_F = 0.95
    E_B = 0.9
    F_B = 0.85
    D_B = 0.95

    # --- Υποδίκτυο C → B ---
    CEB = C_E * E_B          # C → E → B
    CFB = C_F * F_B          # C → F → B

    P_C_to_B = 1 - (1 - CEB) * (1 - CFB)

    # --- Διαδρομή A → C → B ---
    P_ACB = A_C * P_C_to_B

    # --- Διαδρομή A → D → B ---
    P_ADB = A_D * D_B

    # --- Τελική παράλληλη σύνδεση ---
    P_total = 1 - (1 - P_ACB) * (1 - P_ADB)

    return CEB, CFB, P_C_to_B, P_ACB, P_ADB, P_total


# ======================
# GUI
# ======================
WINDOW_WIDTH = 950
WINDOW_HEIGHT = 680
root = tk.Tk()
root.title("Κεφάλαιο 1ο - 1.5 ΑΝΕΞΑΡΤΗΣΙΑ - Παράδειγμα 1.24. Συνδεσιμότητα Δικτύων")
root.geometry("920x680")
root.configure(bg="lightblue")

# ======================
# ΚΕΝΤΡΑΡΙΣΜΑ ΠΑΡΑΘΥΡΟΥ
# ======================

screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()

x = (screen_width // 2) - (WINDOW_WIDTH // 2)
y = (screen_height // 2) - (WINDOW_HEIGHT // 2)

root.geometry(f"{WINDOW_WIDTH}x{WINDOW_HEIGHT}+{x}+{y}")


title = tk.Label(
    root,
    text="Πιθανότητα Ενεργού Μονοπατιού",
    font=("Arial", 18, "bold"),
    bg="lightblue"
)
title.pack(pady=10)

main_frame = tk.Frame(root, bg="lightblue")
main_frame.pack(fill="both", expand=True)

# ======================
# Εικόνα
# ======================

img_frame = tk.Frame(main_frame, bg="lightblue")
img_frame.pack(side="left", padx=20)

tk.Label(
    img_frame,
    text="Δίκτυο & Πιθανότητες Συνδέσμων",
    font=("Arial", 12, "bold"),
    bg="lightblue"
).pack(pady=5)

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
IMAGE_PATH = os.path.join(BASE_DIR, "1_24Foto.jpg")

image = Image.open(IMAGE_PATH)
image = image.resize((400, 260))
photo = ImageTk.PhotoImage(image)

img_label = tk.Label(img_frame, image=photo, bg="lightblue")
img_label.image = photo
img_label.pack()

# ======================
# Αποτελέσματα
# ======================

result_frame = tk.Frame(main_frame, bg="lightblue")
result_frame.pack(side="right", padx=10)

tk.Label(
    result_frame,
    text="Αναλυτικός Υπολογισμός",
    font=("Arial", 14, "bold"),
    bg="lightblue"
).pack(pady=5)

output = tk.Text(result_frame, width=80, height=20, font=("Courier", 10))
output.pack()

def show_solution():
    output.delete("1.0", tk.END)

    CEB, CFB, P_C_to_B, P_ACB, P_ADB, P_total = calculate_probabilities()

    output.insert(tk.END, "ΥΠΟΔΙΚΤΥΟ C → B (ΠΑΡΑΛΛΗΛΟ)\n")
    output.insert(tk.END, "-" * 60 + "\n")
    output.insert(tk.END, f"C → E → B = 0.8 × 0.9 = {CEB:.4f}\n")
    output.insert(tk.END, f"C → F → B = 0.95 × 0.85 = {CFB:.4f}\n")
    output.insert(tk.END, f"P(C → B) = 1 - (1 - 0.8 x 0.9) x (1 - 0.85 x 0.95) = {P_C_to_B:.4f}\n\n")

    output.insert(tk.END, "ΣΕΙΡΙΑΚΗ ΣΥΝΔΕΣΗ A → C → B\n")
    output.insert(tk.END, "-" * 60 + "\n")
    output.insert(tk.END, f"P(A → C → B) = 0.9 × {P_C_to_B:.4f} = {P_ACB:.4f}\n\n")

    output.insert(tk.END, "ΔΙΑΔΡΟΜΗ A → D → B\n")
    output.insert(tk.END, "-" * 60 + "\n")
    output.insert(tk.END, f"P(A → D → B) = 0.75 × 0.95 = {P_ADB:.4f}\n\n")

    output.insert(tk.END, "ΤΕΛΙΚΗ ΠΑΡΑΛΛΗΛΗ ΣΥΝΔΕΣΗ\n")
    output.insert(tk.END, "-" * 60 + "\n")
    output.insert(tk.END, f"👉 P(A → B) = 1 - (1 - 0.851) x (1 - 0.712) = {P_total:.4f}\n")
    button.config(state="disabled")
    button.state(["disabled"])

# button = ttk.Button(
#     result_frame,
#     text="Υπολόγισε Πιθανότητα",
#     command=show_solution
# ).pack(pady=10)

button = ttk.Button(
    result_frame,
    text="Υπολόγισε Πιθανότητα",
    command=show_solution
)
button.pack(pady=10)


# ======================
# Θεωρία
# ======================

theory_frame = tk.Frame(root, bg="orange", bd=3, relief="ridge")
theory_frame.pack(fill="x", padx=20, pady=10)

tk.Label(
    theory_frame,
    text="ΘΕΩΡΙΑ ΠΙΘΑΝΟΤΗΤΩΝ – ΑΝΕΞΑΡΤΗΣΙΑ",
    font=("Arial", 14, "bold"),
    bg="orange"
).pack(pady=5)

tk.Label(
    theory_frame,
    text=(
        "• Οι σύνδεσμοι είναι ανεξάρτητα γεγονότα.\n"
        "• Μονοπάτια που μοιράζονται σύνδεσμο ΔΕΝ είναι ανεξάρτητα.\n"
        "• Αρχικά υπολογίζονται οι ισοδύναμες πιθανότητες των επιμέρους υποδικτύων.\n"
        "• Έπειτα εφαρμόζουμε τους κανόνες συνδυασμού πιθανοτήτων για σειριακές και παράλληλες συνδέσεις.\n"
        "• Αυτή είναι η σωστή μέθοδος στη θεωρία αξιοπιστίας δικτύων."
    ),
    font=("Arial", 11),
    bg="orange",
    justify="left"
).pack(padx=10, pady=5)

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


#root.bind("<Return>", enter_pressed)
add_footer(root)

root.mainloop()
