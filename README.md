# Code exercise 106 - Analyze organization structure

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