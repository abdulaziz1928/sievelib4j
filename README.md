# sievelib4j

**sievelib4j** is a lightweight Java library for building [Sieve email filtering scripts](https://datatracker.ietf.org/doc/html/rfc5228) programmatically. It provides a fluent API to construct syntactically correct and properly escaped Sieve scripts with minimal effort. This library is particularly useful for mail clients or backend systems that need to generate or manage user-defined Sieve rules.

---

## Features

- Fully RFC-compliant Sieve script generation
- Built-in escaping and sanitization
- Modular structure: conditions, actions, control blocks
- Integration tests using Dovecot + ManageSieve + Testcontainers

---

## Installation

**Maven**:

```xml
<dependency>
  <groupId>io.github.abdulaziz1928</groupId>
  <artifactId>sievelib4j</artifactId>
  <version>1.1.0</version>
</dependency>
```

**Gradle**:

```
implementation 'io.github.abdulaziz1928:sievelib4j:1.1.0'
```

---


## Quick Example

```java
SieveFilterSet script = new SieveFilterSet();

script.appendFilter(SieveBuilder.builder()
        .id(UUID.randomUUID())
        .ifStatement(
                ControlIf.builder()
                        .condition(new EnvelopeCondition(
                                AddressPart.LOCAL_PART,
                                Match.contains(),
                                List.of("from"),
                                List.of("john")
                        ))
                        .actions(List.of(new FileIntoAction("Friends")))
                        .build())
        .elseStatement(new ControlElse(List.of(new DiscardAction())))
        .build());

System.out.println(script.generateScript());
```

This will generate:

```sieve
require ["envelope", "fileinto"];

# Filter: 2ec5f8eb-8600-48d7-adc2-88f736e15afc
if envelope :localpart :contains "from" "john" {
  fileinto "Friends";
} else {
  discard;
}

```

---

## Components
- ```SieveFilterSet```: Holds one or more filters

- ```SieveBuilder```: Fluent builder for single filter blocks

- ```SieveCondition```: Logical conditions (e.g., AddressCondition, SizeCondition)

- ```SieveAction```: Script actions (e.g., FileIntoAction, VacationAction, SetFlagAction)

- ```ControlIf, ControlElse, ControlElseIf```: Control structures

---

## Author
**Abdulaziz Aldhalaan** – [@abdulaziz1928](https://github.com/abdulaziz1928)

---

### Acknowledgements

`sievelib4j` was inspired by concepts from **[Jakarta Mail](https://jakarta.ee/specifications/mail)** and the Python library **[sievelib](https://github.com/tonioo/sievelib)**, which helped shape its structure and design.

---

## License

This project is licensed under the [MIT License](LICENSE).
