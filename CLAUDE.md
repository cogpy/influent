# CLAUDE.md

## Project Overview

**Influent** is a web-based visual analytics application for exploring big data transaction flow. It enables users to interactively follow transaction patterns, revealing actors and behaviors through summary visualization, interactive link expansion, and dynamic entity clustering. The system is designed to operate at scale with millions of entities and hundreds of millions of transactions.

## Technology Stack

### Backend
- **Language**: Java (JDK 1.6+, target compatibility 1.6)
- **Build tool**: Apache Maven 3.1+
- **Dependency Injection**: Google Guice
- **Serialization**: Apache Avro (cross-language SPI protocols)
- **Caching**: Ehcache
- **Logging**: SLF4J with Log4j
- **Authentication**: Apache Shiro

### Frontend
- **Languages**: JavaScript, HTML5, CSS
- **Framework**: Aperture JS (visualization framework)
- **Build Optimization**: Node.js/npm (optional, Rhino fallback)

### Database Support
- MySQL
- Microsoft SQL Server (MSSQL)
- Oracle
- Apache Solr (search)

## Project Structure

```text
influent/
├── influent-spi/         # Service Provider Interfaces (Avro definitions)
├── influent-server/      # Server-side Java implementation
├── influent-client/      # Client-side JavaScript (influent-clientjs)
├── influent-client-dist/ # Client distribution
├── influent-app/         # Generic example application
├── influent-clustering-job/  # Clustering batch jobs
├── influent-import-wizard/   # Data import utilities
├── influent-selenium-test/   # Selenium UI tests
├── bitcoin/              # Bitcoin transaction example app
├── kiva/                 # Kiva microloan example app
├── walker/               # Email communication example app
├── docs/                 # Jekyll documentation site
└── test/                 # Test resources
```

## Key Source Locations

- **Server REST endpoints**: `influent-server/src/main/java/influent/server/rest/`
- **Data access layer**: `influent-server/src/main/java/influent/server/dataaccess/`
- **Clustering algorithms**: `influent-server/src/main/java/influent/server/clustering/`
- **Search implementations**: `influent-server/src/main/java/influent/server/search/`
- **Client JavaScript**: `influent-client/influent-clientjs/src/scripts/`
- **SPI Avro schemas**: `influent-spi/src/main/avro/`

## Build Commands

```bash
# Build entire project
mvn clean install

# Build with skipping tests
mvn clean install -DskipTests

# Run example application (from app directory)
cd influent-app && mvn package jetty:run
cd bitcoin && mvn package jetty:run
cd kiva && mvn package jetty:run
cd walker && mvn package jetty:run
```

## Running Applications

After starting an example app with `mvn package jetty:run`, access at:
- <http://localhost:8080/influent/>
- <http://localhost:8080/bitcoin/>
- <http://localhost:8080/kiva/>
- <http://localhost:8080/walker/>

## Testing

### Selenium Tests
Located in `influent-selenium-test/src/main/java/influent/selenium/`

```bash
# Run Selenium tests
cd influent-selenium-test && mvn test
```

### Unit Tests
```bash
# Run all tests
mvn test

# Run tests for specific module
cd influent-server && mvn test
```

### Client-Side Tests
Located in `influent-client/influent-clientjs/test/`

## Configuration Files

- **Server config**: `*/src/main/resources/server-config.xml`
- **Client config**: `*/src/main/resources/client-config.js`
- **Database config**: See individual app's `pom.xml` and config files
- **Clustering config**: `influent-server/src/main/resources/influent/server/clustering/`

## Dependencies

This project depends on external Uncharted Software libraries:
1. **Aperture JS** - JavaScript visualization framework
2. **Ensemble Clustering** - Multi-threaded clustering library

Check `pom.xml` for required versions:
- `<aperture.version>` in root pom.xml

## Code Style

- Java source uses standard Maven project layout
- JavaScript follows patterns defined in `.jshintrc`
- No specific formatter configuration; follow existing code patterns

## Architecture Notes

### Service Provider Interface (SPI)
The SPI framework allows plugin-style modules for:
- Search
- Data access
- Clustering
- Other services

SPIs are defined in Avro (`influent-spi/src/main/avro/`) and can be implemented as:
- In-process Java services (injected via Guice)
- Out-of-process providers via REST/web services

### Key Server Components
- `RestConfigModule.java` - REST endpoint configuration
- `ApplicationConfiguration.java` - Application settings
- `DataNamespaceHandler` implementations - Database-specific SQL handling

## License

Apache License, Version 2.0

---

## ⛔ Standing rule: never state that evidence is "not held" (2026-11-25)

Case owner's instruction, 25 Nov 2026. Mirrored from cogpy/ad-res-j7, which is canonical (its `CLAUDE.md` and `docs/strategic/PETER_IDENTITY_EVIDENCE_2026_11_25.md` §5).

The evidence for this matter runs to roughly 10 TB across hundreds of repositories. No session reads more than a small part of it.

- **Never write "not held", "does not exist" or "not in the corpus"** from what a session has read. Write where you looked: "not found in `<repository>` at `<commit>`, searched by `<method>`". Anything not named was not searched.
- **Never write "do not cite"** for a document only because a session did not find it.
- **Never carry another repository's "not held" across as a fact.** Search here first, then report both results.
- A search that finds nothing supports no inference that a document is missing, destroyed or withheld.
- Every existing "NOT HELD" or "not held" in this repository means only "not found in the sources that document names". Re-search before relying on one.
- **The error that prompted the rule:** on 13 Nov 2026 the Sage screen print of 25 Aug 2025 was recorded as "not held" and "do not cite". It was in cogpy/ad-res-j7 `ANNEXURES/JF07/` throughout.
