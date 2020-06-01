# Refactoring JS 
Presenter: Joe Morgan

> Do it little by little, piece by piece

**2 types of refactoring**
- Cleanup
- Abstractions (Avoid Hasty Abstractions coding style)

## Start peeling out pieces
1. Isolate
2. Purify
3. Combine

**Many/robust tests will minimize risk of introducting new bugs**
- Mostly shallow integration tests
- isolateed tests can change since code is changing
- shallow int tests should not change to ensure UX isn't broken

Layer functionality based on display vs data vs utilities

> See "Age" slide for example on how to cvreate objects conditionally

**Fail Early Fail Often**

Look at using spread operators in fn arguments for variable number of arguments
- See slide `limits.every(limit => {...})`

**Keep things short and flat and isolated**
- Keep it SOLID w/emphasis on Single Responsibility

