# JabberPoint Project

## Branch Structure

- **main**: Production-ready code
- **development**: Integration branch
- **feature/xxx**: Feature branches
- **testing**: Automated testing
- **acceptance**: Final review branch

## Workflow

1. Development on feature branches
2. Merge to development
3. Merge to testing for automated tests
4. Merge to acceptance
5. Merge to main

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
  - Coverage target: aim for minimum 80% line coverage for critical components

### Continuous Integration
- GitHub Actions automatically runs tests on every push to the testing branch
- If all tests pass, changes are automatically merged from testing to acceptance branch
- Test reports are generated and archived as artifacts
- Failed tests prevent automatic promotion to preserve quality gates

### Running Tests Locally
- `mvn test`: Run all tests
- `mvn test -Dtest=ClassName`: Run tests in a specific class
- `mvn jacoco:report`: Generate coverage report (available at `target/site/jacoco/`)

## Build

- Maven based project
- `mvn clean install`
- `mvn test`
- `mvn jacoco:report`
