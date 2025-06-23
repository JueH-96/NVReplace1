A simple Java tool that replaces words in a given text based on specified JSON format replacement rules. It supports replacing both nouns and verbs with alternative terms.

🛠️ Environment Requirements
Java Development Kit (JDK) 1.8
Gradle 8.5
# How to Build and Run


1. Build the Project
Run the following command in the project root directory:


```bash
./gradlew build
```
This will generate a JAR file in the build/libs/ directory.

2. Running the Program
There are two ways to run the program:

Option 1: Direct Execution
You can directly execute the source file ReplaceText.java.

Option 2: Using the Built JAR File
Run the compiled JAR file using the following command:


```bash
java -jar build/libs/ReplaceText-1.0-all.jar
```
✍️ Usage Instructions
Input the original text you want to replace.
After finishing, press Enter twice to submit.
Then input your JSON-formatted replacement rules .
End the JSON input by typing END followed by pressing Enter .
The program will output the replaced text.
💡 Tip: Make sure the JSON is correctly formatted and ends with the word END. 

📝 Example
Step 1: Enter Original Text
(Press Enter twice after entering the text)



The programmer writes code every day, debugs the program, and solves various problems.
Step 2: Enter Replacement Rules
(End with END + Enter)
```bash
json
{
  "nouns": {
    "programmer": ["developer", "engineer"],
    "code": ["software", "script"],
    "program": ["application", "system"]
  },
  "verbs": {
    "writes": ["codes", "develops"],
    "debugs": ["troubleshoots", "tests"],
    "solves": ["resolves", "addresses"]
  }
}
END
```
Output Result:
```bash
Original text: The programmer writes code every day, debugs the program, and solves various problems.
Nouns to replace: {code=["software","script"], programmer=["developer","engineer"], program=["application","system"]}
Verbs to replace: {debugs=["troubleshoots","tests"], solves=["resolves","addresses"], writes=["codes","develops"]}

Replaced Text:
The "developer","engineer" "codes","develops" "software","script" every day, "troubleshoots","tests" the "application","system", and "resolves","addresses" various problems.
```
