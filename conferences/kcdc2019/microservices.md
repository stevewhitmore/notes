# Microservices - The Easy Way Is the Wrong Way

Definition: Mark Feller Quote

- Should always be treated as independent application
- Communicates over network
- Single Responsibility -> only one reason to change
- Owned by one team
- Individually deployable

> Monoliths are not inherently bad: only when you can't isolate services.

Microservices ventures into Distributed Computing (this has been around for 50+ years - think SmallTalk)

## Why Use Microservices?

Deliver business functionality:

- Quicker
- Safer
- More Stable
- Team Autonomy
  - Best language for the job
  - Focused development
  - Agile works well
- Service Autonomy
  - Single Responsiblity
  - Deployable with other services
- Scalability
  - Scale independently of others
  - Choice of servers
- Fault Isolation
  - Your problems arn't my problems "Stay in your lane bro"

## Why NOT To Use Microservices?

- Monolith is too hard
- Too much coupling - need refactoring first
- Team unwilling or not ready
- No support structure
- High cost of time and money

**Network layer 4 and 7 -> OSI Model**

- 1 is the hardware
- 4 is the IP calls access control/firewall
- 7 is the http call

> Beware of shared libraries!

**Encrypt all service communication even if it's behind a firewall**

**Use single repo for each microservice**

## Decentralize Data

- Single relational DB is a coupling point

How to deal with it?

- Duplicate parts that need to be shared

## Decomposing Monolith to Microservice

- Monolith needs to have clear boundaries, DI, and actually needs refactoring before split up
- Start small

## Good Resources To Consider

**Eric Evans - DDD (Domain Driven Design)**

- Start in the middle

**Vahn Vernon - Implementing Domain Driven Design**

**Chris Richardson - Microservices Patterns**

## Identify

- Figure out Bounded Contexts (dictated by business needs)
- Identify possiblej services. Bounded Contexts must not span multiple microservices

> Web and API should be loosely coupled - Web would be "wiki" while API not backend mirror of that necessarily.
> Could do menu, content, login microservices on the backend.
> Think smaller for APIs
