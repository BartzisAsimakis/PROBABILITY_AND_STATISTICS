import sys
from openai import OpenAI

def main():
    # Έλεγχος ότι υπάρχει παράμετρος (ο αριθμός προβλήματος)
    if len(sys.argv) < 2:
        print("❌ Δώσε τον αριθμό προβλήματος ως όρισμα.")
        sys.exit(1)

    problem_number = int(sys.argv[1])
    client = OpenAI()

    # Διαβάζουμε το αρχείο με όλα τα προβλήματα
    with open("problems.txt", "r", encoding="utf-8") as f:
        lines = f.readlines()

    # Εντοπισμός προβλήματος
    problem = None
    for line in lines:
        if line.strip().startswith(f"{problem_number}."):
            problem = line.split(".", 1)[1].strip()
            break

    if not problem:
        print(f"❌ Δεν βρέθηκε πρόβλημα με αριθμό {problem_number}.")
        sys.exit(1)

    #print(f"🔍 Επιλέχθηκε πρόβλημα #{problem_number}: {problem}")

    # Αποστολή στο μοντέλο
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": problem}],
    )

    answer = response.choices[0].message.content.strip()

    # Αποθήκευση της απάντησης
    with open("responses.txt", "a", encoding="utf-8") as f:f.write(f"{problem_number}: {answer}\n")


    print("✅ Η λύση αποθηκεύτηκε στο responses.txt")

if __name__ == "__main__":
    main()
