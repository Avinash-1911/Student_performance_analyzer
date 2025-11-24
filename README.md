# Student Performance Analyzer

**Course:** Flipped Course — Java  
**Author:** Avinash Raj  
**Deadline:** November 25, 2025

## Project Summary
A Java-based application to record students' marks (dynamic number of subjects), compute total, percentage, grade and performance category, and save records to both text (`data/students.txt`) and CSV (`data/students.csv`) files.

## Features
- Add student records with dynamic subjects
- Compute total, percentage, grade and performance category
- Save records to both `.txt` and `.csv`
- Display all records (text or CSV)
- Search by roll number (CSV)
- Simple, modular OOP design (Student, GradeCalculator, FileManager, Main)

## Project Structure

StudentPerformanceAnalyzer/
├── src/
│ ├── Student.java
│ ├── GradeCalculator.java
│ ├── FileManager.java
│ └── Main.java


## How to Compile & Run (command-line)
1. Open terminal in project root.
2. Compile:
```bash
java -cp bin Main

