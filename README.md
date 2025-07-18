# 🧠 LeetCode Java CLI Framework

An extensible Java-based framework designed to solve LeetCode-style problems interactively from the command line. It provides a unified structure for managing problem metadata, test cases, input validation, and solution evaluation.

---

## 🏗️ Project Structure

```
src/
├── main/java/org/leetcode/
│   ├── core/         # Abstract framework (LC, interfaces, utils)
│   ├── solutions/    # Problem classes (e.g., LC7, LC268)
│   ├── ui/cli/       # CLI interface for interaction
│   └── Main.java     # Application entry point
├── resources/
│   ├── availableProblems.json     # Metadata
│   ├── descriptions/              # DES{id}.txt files
│   └── testcases/                 # TC{id}.json files
├── test/                          # (Reserved for future JUnit-style testing)
├── docs/                          # (Javadoc output location)
```

---

## ✨ Key Features

* ✅ **Structured Problem Lifecycle** using a generic abstract class `LC<I, T, O>` for all solutions.
* 🔍 **Metadata & TestCase Handling** via centralized JSON files.
* 🧪 **Test Automation** supporting visible, hidden, and user-defined cases.
* 🖥️ **Interactive CLI Interface** (via `CliRunner`) for choosing and running problems.
* ♻️ **Singleton-Based Problem Management** for efficient memory use and class reuse.
* 🌐 **Auto-Formatted Console Output** for test results.
* ✍️ **Support for Custom Input** with validation and normalization.

---

## 👩‍💼 Getting Started

### ✅ Prerequisites

* Java 24+
* Maven 3.8+

### 💪 Build the Project

```bash
mvn clean install
```

### ⏩ Run the CLI

```bash
java -cp target/LeetCode-1.0-SNAPSHOT.jar org.leetcode.ui.cli.Main
```

---

## 🎓 How to Use

1. Launch CLI and view available problems.
2. Enter the ID of a problem to run.
3. Choose from:

   * Run predefined test cases
   * Provide custom inputs
   * View details, metadata, or test case breakdown

Test cases and metadata are loaded from:

* `/resources/testcases/TC{id}.json`
* `/resources/descriptions/DES{id}.txt`
* `/resources/availableProblems.json`

---

## ⚙️ Planned Enhancements

* Extract I/O formatting into a separate `IOFormatter` interface.
* Improve test case visibility and debugging support.
* Add support for saving failed test cases for review.
* CLI enhancements for smoother navigation and search.

---

## 📄 License

This project is licensed under the [MIT License](./LICENSE).

---

## 📢 Feedback & Contribution

This project is in early development. Feedback, suggestions, and contributions are welcome!

* Suggest enhancements via GitHub Issues
* Fork and contribute new problems
* Discuss future use cases or integrations

> Built with purpose: to learn, explore, and build something useful.
