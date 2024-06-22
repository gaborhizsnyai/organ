# Code exercise 106 - Analyze organization structure

## Problem description
BIG COMPANY is employing a lot of employees. Company would like to analyze its organizational
structure and identify potential improvements. Board wants to make sure that every manager earns
at least 20% more than the average salary of its direct subordinates, but no more than 50% more
than that average. Company wants to avoid too long reporting lines, therefore we would like to
identify all employees which have more than 4 managers between them and the CEO.

We are given a CSV file which contains information about all the employees. File structure looks like
this:

```
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
```
Each line represents an employee (CEO included). CEO has no manager specified. Number of rows
can be up to 1000.

Write a simple program which will read the file and report:
- which managers earn less than they should, and by how much
- which managers earn more than they should, and by how much
- which employees have a reporting line which is too long, and by how much

Key points:
- use only Java SE (any version), and Junit (any version) for tests.
- use maven for project structure and build
- the application should read data from a file and print out output to console. No GUIs needed.

## Assumptions and design decisions

### CSV file content
Though it's not strictly specified in the description, but according to the example provided,
I've made the following assumptions for the sake of simplicity:
- The first line of the CSV file is the header, containing the column names.
- The columns don't contain escape characters, only plain text values.
- Each record could have a reference to a parent record, except the root record (CTO).
  The records are ordered by the parent record, so the parent record is always before the child record.
  This way, we can build the organization structure from the root record to the leafs without post-processing.
  In a real-world scenario, we should handle the records in any order.

### Handling money type
In financial applications, it's common to use a specific type for money values to avoid rounding errors.
For this exercise, instead of using complex types like `BigDecimal`, I've used `Integer` and `Double` for simplicity.
In a real-world application, when precision is a must, we should use a more appropriate type for money values.

### Data storage
I've used a simple in-memory data storage to store the incoming records in a tree-like structure and in an indexed structure.
To analyze the data, we need to traverse the tree, or search for specific records by criteria.

According to the specified maximum amount of records (1000), the in-memory storage should be sufficient.

### Reporting
Since the solution was meant to be simple, I've optimized the reporting to be as simple as possible
for console output. In a real-world application, we should use a more sophisticated reporting mechanism,
including flexible formatting and perhaps messaging.

### Code structure
When creating a new application, the biggest challenge is to create a good structure for the code,
balancing between simplicity and flexibility. For this exercise, I've chosen the ports-and-adapters architecture
to separate the business logic from the input/output logic. It adds some complexity to the code,
but it makes the code more flexible and easier to test and maintain.

Also, I've created an externalized configuration for application components as a 'CSV File to Console' reporting context.

The code currently uses file access through `java.nio.Files`, which is a simple and efficient way to read files for the first,
however, to make the app 12-factor-ish, we should use pipes to read file content from the standard input.
Let's stick with the file access for now. 

### Testing
To verify the correctness of the solution, I've created unit tests for the business logic. Some of the tests
required mocking the input/output components. I've used custom components for that, which are simple and easy to use
in this code structure, however, in a real-world application, we should use a more sophisticated mocking framework.

Due to missing mocking tools, the interaction with the file system is not covered by tests. The test suite
currently contains only unit tests, integration tests and end-to-end tests are missing.

### Error handling
The error handling is very simple in this solution. To keep the code simple, I've used basic exceptions only.
Also, I assumed that the input file is mostly correct, and the data is consistent.

In a real-world application, we need a more sophisticated error handling strategy, like
filtering rows causing errors, to avoid breaking the whole process.
