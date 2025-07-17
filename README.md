# 🧠 LeetCode Java Framework

A robust and extensible Java framework tailored for solving LeetCode problems in a structured, scalable, and testable way. This project abstracts core problem-solving logic from metadata and test case handling, offering a seamless development and testing experience.

---

## 📀 Architecture Overview

This project follows a modular architecture comprising the following layers:

* **Core Framework (`core/`)**: Provides abstract classes and interfaces for problem-solving, metadata loading, test case management, and user input handling.
* **Solutions Layer (`solutions/`)**: Contains individual problem implementations extending the generic `LC<I, T, O>` base class.
* **Resources (`resources/`)**: Stores structured metadata, test cases, and problem descriptions.
* **CLI Interface (`Main.java`)**: Offers a command-line interface to run problems, view metadata, and test interactively.

---

## 📌 Key Features

* 🔁 **Unified Problem Contract**: All problems implement the `Problem<I, T, O>` interface for consistency.
* 🧪 **Automated Test Execution**: JSON-driven test cases support visible, hidden, and user-defined inputs.
* 🔍 **Dynamic Metadata Resolution**: Problem IDs, titles, and descriptions are parsed automatically.
* 👤 **Interactive Testing**: Accepts custom user input with input validation and formatting support.
* 🧱 **Singleton-Based Execution**: All problem classes follow a singleton pattern for consistent lifecycle management.

---

## 💪 Technology Stack

* **Java 24** – Primary development language
* **Maven** – Build and dependency management
* **Gson 2.11.0** – JSON parsing and serialization
* **JetBrains Annotations** – Static analysis and nullability checks

---

## 🚀 Getting Started

### ✅ Prerequisites

Ensure the following tools are installed on your system:

* Java 24+
* Maven 3.8+

### 🔧 Build Instructions

```bash
mvn clean install
```

### ▶️ Run the Application

```bash
# Inside your IDE
Run `Main.java`

# Or from terminal (if packaged as a jar in future)
java -cp target/LeetCode.jar org.leetcode.Main
```

---

## 🧪 Test Case Modes

This framework supports two execution modes:

1. **Test Case Mode**

    * Runs test cases from structured JSON files in `resources/testcases/`
    * Example: `TC268.json` for problem `LC268`

2. **Interactive Mode**

    * Prompts user for input/output via CLI
    * Validates and formats input before invoking the solver

---

## 📁 File Structure

```
src/
├── main/java/org/leetcode/
│   ├── core/         → Framework core (abstract classes & interfaces)
│   ├── solutions/    → Individual problems (e.g., LC7.java, LC268.java)
│   ├── Main.java     → CLI runner
├── resources/
│   ├── availableProblems.json → Metadata index
│   ├── descriptions/          → Problem descriptions (e.g., DES268.txt)
│   └── testcases/             → Test cases (e.g., TC268.json)
```

---

## ➕ Adding New Problems

1. Implement the solution in `solutions/` as `LC<id>.java`.
2. Add metadata in `resources/availableProblems.json`.
3. Add description in `resources/descriptions/DES<id>.txt`.
4. Add test cases in `resources/testcases/TC<id>.json`.

Use `LC268` and `LC7` as reference implementations.

---

## 🧠 Planned Enhancements

* 📆 Introduce a dedicated `IOFormatter` class/interface to modularize input/output conversion and formatted printing across problems.
* 🎨 Develop an interactive command-line interface under a separate `org.leetcode.ui.cli` package to improve usability and problem selection.
* 🧪 Expand and improve test case coverage for all problems, including edge cases and stress tests.

---

## 🤝 Contributing

We welcome contributions to expand the problem set or improve the framework:

1. Fork this repository
2. Create a new feature or fix branch
3. Follow the solution structure convention
4. Submit a pull request with relevant test data

---

## ⚖️ License

This project is licensed under the **MIT License**. Feel free to use, extend, and distribute it.

---

## 📬 Contact

Have questions or ideas? Open an [issue](https://github.com/your-username/LeetCode/issues) or start a discussion.

> Engineered for clarity, consistency, and real-world problem-solving proficiency.
