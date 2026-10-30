# MiniBank - Java OOP Semester Project

A beginner-friendly console banking application demonstrating core and advanced Java OOP concepts.

## Requirements
- JDK 17 or later
- VS Code with Java Extension Pack (recommended)

## Run in VS Code terminal (Windows PowerShell)

From the `MiniBank_Project` folder:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out minibank.MiniBank
```

## Main menu
1. Open New Account
2. View Account Details
3. Deposit Money
4. Withdraw Money
5. Transfer Money
6. List All Accounts
7. View Transaction History
8. Thread Safety Demo
9. Save and Exit

## Concepts demonstrated
- Classes, objects, constructors, encapsulation
- Abstract class and inheritance
- Method overriding and runtime polymorphism
- Exception handling and custom checked exceptions
- Collections, generics, Comparable and Comparator-style sorting
- Custom annotations and reflection
- Synchronization, Runnable and ExecutorService
- Serialization, NIO Files and try-with-resources
- ConcurrentHashMap-based repository
- Switch expressions and lambda expressions

## Data files
The application creates `data/accounts.dat` and `data/transactions.txt` when saving.

## Notes
This is an educational console project, not a real banking system. It does not connect to real bank services.
