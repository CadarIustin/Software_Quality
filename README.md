# JabberPoint Project

## Branch Structure

- **main**: Production-ready code
- **development**: Integration branch
- **feature/xxx**: Feature branches
- **testing**: Automated testing
- **acceptance**: Final review branch
- **dev**: Development branch

## CI/CD Workflow

Our project uses a modern CI/CD pipeline with GitHub Actions to ensure code quality and automate testing:

1. Development on feature branches
2. Pull request to testing branch triggers automated tests and code quality checks
3. After successful tests, changes are automatically promoted from testing to acceptance
4. Final review in acceptance branch before merging to main

### CI/CD Components

- **Code Coverage (JaCoCo)**: Runs unit tests and generates coverage reports
- **Branch Promotion**: Automatically promotes code from testing to acceptance after successful tests
- **Java Linter**: Checks code style and suggests formatting improvements

## Testing

### Overview
The project uses a comprehensive testing approach with several complementary tools to ensure code quality and reliability.

- **JUnit 5**
  - Core testing framework for writing and running Java tests
  - Provides annotations like `@Test`, `@BeforeEach`, and `@AfterEach` for test lifecycle management
  - Supports parameterized tests and dynamic test generation
  - Used for unit tests of individual components (e.g., `PresentationTest`, `SlideTest`)
  
- **Mockito for mocking**
  - Allows creation of mock objects to simulate dependencies in unit tests
  - Enables testing of components in isolation without requiring actual implementations of dependencies
  - Used to verify interactions between components without executing their actual code
  - Helps test error conditions and edge cases that would be difficult to reproduce with real objects

- **JaCoCo for coverage**
  - Measures how much of your code is executed during tests
  - Generates detailed HTML reports showing which lines, branches, and methods are covered
  - Identifies untested or undertested parts of the codebase
  - Report is automatically generated during CI/CD pipeline execution
  - Coverage target: aim for minimum 50% line and branch coverage

### JaCoCo Reports

JaCoCo generates comprehensive code coverage reports that are uploaded as artifacts in GitHub Actions. To access these reports:

1. Go to the "Actions" tab in your GitHub repository
2. Select the "Code Coverage Jacoco - Java CI with Maven" workflow
3. Select the most recent workflow run
4. Scroll down to the "Artifacts" section
5. Download the "jacoco-report" artifact
6. Extract the ZIP file and open "index.html" in your browser

The report provides detailed coverage information including:
- Overall project coverage
- Coverage for changed files
- Per-file coverage metrics
- Line-by-line coverage visualization

### Running Tests Locally
- `mvn test`: Run all tests
- `mvn test -Dtest=ClassName`: Run tests in a specific class
- `mvn jacoco:report`: Generate coverage reports (available in `target/site/jacoco/`)

## XML and DTD

### Document Type Definition (DTD)

This project uses XML for presentation storage, with a Document Type Definition (DTD) to define the structure:

```dtd
<!ELEMENT presentation (showtitle, slide*)>
<!ELEMENT showtitle (#PCDATA)>
<!ELEMENT slide (title, item*)>
<!ELEMENT title (#PCDATA)>
<!ELEMENT item (#PCDATA)>
<!ATTLIST item kind CDATA #REQUIRED>
<!ATTLIST item level CDATA #REQUIRED>
```

The DTD defines:
- A presentation contains a showtitle and multiple slides
- Each slide has a title and multiple items
- Each item has required attributes: kind and level

The XMLAccessor class handles reading and writing presentations in this format, validating against the DTD to ensure data integrity.

## Style System

The project includes an enhanced Style class with:
- FontName enum with SERIF, SANS_SERIF, and MONOSPACED options
- Accessor methods for font properties
- Support for different themes via the ThemeStrategy interface
- DefaultTheme and DarkTheme implementations

This provides a consistent and type-safe approach to font management across the application.

## Build

- Maven based project
- `mvn clean install`
- `mvn test`
- `mvn jacoco:report`
