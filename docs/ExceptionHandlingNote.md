# 📚 Guide: When to Extend `Exception` vs `RuntimeException` in Java

---

## 🧠 The Core Difference

| Type                    | Base Class         | Checked at Compile Time | Must Be Handled         |
| ----------------------- | ------------------ | ----------------------- | ----------------------- |
| **Checked Exception**   | `Exception`        | ✅ Yes                   | `try/catch` or `throws` |
| **Unchecked Exception** | `RuntimeException` | ❌ No                    | Optional                |

---

## ✅ When to Extend `Exception` (Checked Exception)

### ✔ Use this when:

* The caller **can realistically recover**
* The error is **expected** in normal flow
* The compiler should **force handling**
* The issue is **external or environmental**

### 📌 Typical Scenarios

* File not found
* Invalid input from user
* Empty data sets
* Business rule violations that must be handled

### 🔍 Examples from Your System

```java
public class EmptyProjectException extends Exception {
    public EmptyProjectException(String message) {
        super(message);
    }
}
```

**Why checked?**

* User can create a project
* Program can continue
* Caller must decide what to do

Another example:

```java
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
```

**Reason:**

* User input is unpredictable
* Menu flow should retry, not crash

---

## ❌ When NOT to Extend `Exception`

* If forcing `try/catch` everywhere hurts readability
* If the error indicates a **programming mistake**

---

## ✅ When to Extend `RuntimeException` (Unchecked)

### ✔ Use this when:

* Error is a **developer bug**
* System is in an **invalid state**
* Recovery is unlikely
* You want to **fail fast**

### 📌 Typical Scenarios

* Null values where they should never exist
* Invalid IDs caused by logic errors
* Broken invariants

### 🔍 Examples from Your System

```java
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
```

**Why runtime?**

* Task ID should already be validated
* Indicates a logic flaw
* Forcing `try/catch` everywhere adds noise

Another example:

```java
throw new IllegalStateException("Task list corrupted");
```

---

## 🧩 Rule of Thumb (Easy to Remember)

> **If the caller can fix it → Checked (`Exception`)**
> **If the developer must fix it → Unchecked (`RuntimeException`)**

---

## 🧪 How This Applies to Your Project

| Exception                     | Extend             | Why              |
| ----------------------------- | ------------------ | ---------------- |
| `InvalidInputException`       | `Exception`        | User can retry   |
| `EmptyProjectException`       | `Exception`        | Recoverable      |
| `TaskNotFoundException`       | `RuntimeException` | Logic error      |
| `FilePersistenceException`    | `Exception`        | External system  |
| `ConcurrentModificationIssue` | `RuntimeException` | Programmer fault |

---

## 🧠 Best Practices (Senior Level)

✔ Never use `Exception` as a catch-all
✔ Prefer **specific exceptions**
✔ Avoid checked exceptions in deep layers
✔ Document exceptions using **Javadoc `@throws`**
✔ Don’t mix checked and unchecked randomly

---

## 📝 Example Javadoc (Correct Style)

```java
/**
 * Retrieves a task by its ID.
 *
 * @param taskId the task identifier
 * @return the task
 * @throws TaskNotFoundException if the task does not exist
 */
public Task getTaskById(int taskId) {
    ...
}
```

---

## 🚀 Final Takeaway

| Question                  | Answer             |
| ------------------------- | ------------------ |
| Should user handle this?  | `Exception`        |
| Is this a bug?            | `RuntimeException` |
| Can program continue?     | Checked            |
| Should program fail fast? | Runtime            |
