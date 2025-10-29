from openai import OpenAI
import sys

#print(sys.executable)  # για έλεγχο ποια Python τρέχει

client = OpenAI()



# Διαβάζουμε το πρόβλημα
problem_path = r"C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/BACK_UP_PROJECTS/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΟ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/problems.txt"
responses_path = r"C:/Users/Asimakis/Documents/ΠΑΝΕΠΙΣΤΗΜΙΟ/BACK_UP_PROJECTS/ΠΙΘΑΝΟΤΗΤΕΣ _ ΕΡΓΟ/ΔΕΣΜΕΥΜΕΝΗ ΠΙΘΑΝΟΤΗΤΑ/reservedprob/responses.txt"

try:
    with open(problem_path, "r", encoding="utf-8") as f:
        problem = f.read().strip()
except Exception as e:
    with open(responses_path, "w", encoding="utf-8") as f:
        f.write(f"Σφάλμα κατά την ανάγνωση του problems.txt: {e}")
    sys.exit(1)

if not problem:
    with open(responses_path, "w", encoding="utf-8") as f:
        f.write("")
    sys.exit(0)

try:
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[
            {"role": "user", "content": problem}
        ]
    )

    answer = response.choices[0].message.content

    with open(responses_path, "w", encoding="utf-8") as f:
        f.write(answer)

except Exception as e:
    with open(responses_path, "w", encoding="utf-8") as f:
        f.write(f"Σφάλμα κατά την εκτέλεση του ChatGPT API: {e}")
