# JabberPoint - Software Quality Project

This repository contains the JabberPoint presentation tool project with an implemented DTaB (Develop, Test, and Build) strategy.

## Project Overview

JabberPoint is a simple presentation tool written in Java, similar to PowerPoint but with more basic functionality. This project applies software quality principles and automated testing.

## Branch Structure

The project follows a specific branching strategy to ensure code quality:

- **main**: Production-ready code that has passed all tests and reviews
- **development**: Main development branch where features are integrated
- **feature/xxx**: Feature-specific branches created from development
- **testing**: Branch where automated tests are run on merged features
- **acceptance**: Intermediate branch between testing and main

## DTaB Workflow

1. Development happens on feature branches (`feature/xxx`)
2. Once a feature is complete, it's merged into the `development` branch
3. Features from `development` are merged into the `testing` branch
4. Automated tests run on the `testing` branch
5. If tests pass, code is merged to the `acceptance` branch
6. After final review, `acceptance` is merged to `main`

## Testing Strategy

The project uses:
- JUnit 5 for unit testing
- Mockito for mocking in tests
- JaCoCo for code coverage analysis

Tests automatically run via GitHub Actions when code is pushed to the `testing` branch.

## Getting Started

1. Clone this repository
2. Use Maven to build the project: `mvn clean install`
3. Run the tests: `mvn test`
4. View the JaCoCo code coverage report: `mvn jacoco:report`
