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

- JUnit 5
- Mockito for mocking
- JaCoCo for coverage

## Build

- Maven based project
- `mvn clean install`
- `mvn test`
- `mvn jacoco:report`
