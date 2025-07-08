# 🧠 LeetCode Java Solutions

A comprehensive Java framework for solving LeetCode problems. This project uses a modular approach to organize solutions, handle test automation, and manage interactive inputs.

---

## 📌 Key Features

- 🏗️ **Scalable Design**: Each problem is encapsulated in a self-contained Java class.
- 🧪 **Test Cases**: JSON-driven test cases for structured problem evaluation.
- 📜 **Metadata Parsing**: Dynamically loads problem descriptions, IDs, and related metadata.
- 🔄 **Interactive Support**: Supports user-driven custom inputs for testing solutions.

---

## 🛠️ Technology Stack

- **Java 24**: Main programming language used in the project.
- **Maven**: Build and dependency management tool.
- **Gson 2.11.0**: Handles JSON serialization/deserialization.
- **JetBrains Annotations**: Provides static analysis support.

---

## 🚀 Quick Start

### ✅ Prerequisites

Ensure you have the following tools installed:

- Java 24+
- Maven 3+

### 🔨 Building the Project

Run the following command to build the project:
```bash
mvn clean install
```
---

## ⚡ How to Run

You can execute solutions for LeetCode problems in two modes:

1. **Test Case Mode**: Evaluates test cases defined in JSON files.
2. **Interactive Mode**: Allows manual inputs for testing.

To run the project, execute `Main.java` and follow on-screen instructions.

---

## 🤝 Contributing

We welcome your contributions to make this framework more robust and feature-rich!

1. Fork this repository.
2. Implement a new problem as `LC<id>.java` in the `solutions/` package.
3. Add test cases in JSON format in the `resources/testcases/` directory.
4. Create a pull request with a brief description.

---

## ⚖️ License

This project is licensed under the **MIT License**. Feel free to use and modify it.

---

## 📬 Contact

For questions, suggestions, or issues, feel free to open a [GitHub Issue](https://github.com/your-username/LeetCode/issues). We'd love to hear from you!

> Built with ❤️ for clean code, scalability, and efficient problem-solving.