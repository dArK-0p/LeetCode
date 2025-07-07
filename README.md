# 🧠 LeetCode Java Solutions

A structured, extensible Java framework for solving and testing LeetCode problems with rich metadata, test automation, and interactive input.

---

## 📌 Project Highlights

- 📂 Modular architecture with each problem as a self-contained class
- 🧪 Built-in support for **visible** and **hidden** test cases (JSON-driven)
- ⚙️ Dynamic metadata parsing (title, description, problem ID, test files)
- 🧵 Interactive mode for user-driven inputs and custom test evaluation
- 🔧 Designed to scale across hundreds of problems cleanly

---

## 🛠️ Tech Stack

- **Java 24**
- **Maven**
- **Gson 2.11.0** – for JSON serialization/deserialization
- **JetBrains Annotations** – static analysis support

---

## 🧱 Project Structure

```
LeetCode/
├── src/main/java/org/leetcode/
│   ├── LC268.java           # Problem-specific implementation
│   ├── Problem.java         # Core problem interface
│   ├── MetadataSupport.java # Metadata and description access
│   ├── ProblemMetadataLoader.java
│   ├── TestCaseSupport.java
│   ├── Main.java            # Launch entry point
├── src/main/resources/
│   ├── descriptions/        # Problem descriptions
│   ├── testcases/           # JSON-formatted test cases
│   ├── availableProblems.json
```

> 💡 **Note:** All problem files are prefixed with `LC<id>.java`, and metadata is stored under `resources/`.

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 24+
- Maven 3+

### 🔨 Build the Project

```bash
mvn clean install
```

---

## ⚙️ Running a Problem

Solutions support two modes:

| Mode               | Command                       | Behavior                                |
| ------------------ | ----------------------------- | --------------------------------------- |
| 🧪 Test Case Mode   | `Main.java` (uses LC268 etc.) | Loads and evaluates all JSON test cases |
| ✍️ Interactive Mode | via `processUserInput()`      | Accepts custom input from user          |

---

## 📚 Implemented Problems

| ID                                                   | Title                          | Time   | Space  |
|------------------------------------------------------|--------------------------------|--------|--------|
| [268](https://leetcode.com/problems/missing-number/) | Missing Number                 | `O(n)` | `O(1)` |
| 7                                                    | Reverse Integer                | –      | –      |
| 1688                                                 | Count of Matches in Tournament | –      | –      |
| 2016                                                 | Maximum Difference Between...  | –      | –      |
| 3409                                                 | Longest Subsequence...         | –      | –      |

> ✅ `LC268` is fully implemented and serves as the reference structure.

---

## 🤝 Contributing

Want to add your own solution?

1. Fork the repository
2. Create a new class `LC<id>.java` implementing `Problem<I, O>`
3. Add JSON test cases in `resources/testcases/`
4. Submit a Pull Request!

---

## ⚖️ License

This project is licensed under the **MIT License**.

---

## 📬 Contact

For issues, ideas, or contributions, feel free to open an [Issue](https://github.com/your-username/LeetCode/issues).

---

> Built with ❤️ for structured problem solving and clean architecture.