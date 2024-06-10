
# Architecture Decision Record (Multi-Language Support Strategy)

## Multi-Language Support Strategy

### Context

Our application needs to support multiple languages to cater to a global audience. We need a strategy to manage translations and ensure that the user interface can adapt to different languages without requiring significant changes to the codebase.

### Decision

We will use an external localization service along with JSON-based resource files for managing translations. The application will load the appropriate language file based on the user's preference or browser settings.
In this example the User will send his language as a RequestParam in the URL and the response will ensure his language. As default if it doesn't exist it will be English.

### Consequences

- **Positive:**
    - Simplifies the process of adding new languages.
    - Translations can be managed separately from the codebase.
    - Allows for dynamic language switching without redeploying the application.

- **Negative:**
    - Requires integration with the external localization service.
    - Potential performance overhead due to loading external resources.
    - Dependence on the availability and reliability of the external service.