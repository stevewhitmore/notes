# Jave EE: The Big Picture

## Four platforms

### SE (Standard)

- Core platform
- Core libraries and APIs
- Basic types and bojects to high-level classes
- JVM (JDK)
- Dev tools (JDK)
- Deployment and monitorying (JDK)

### ME (Micro)

- Subset of SE
- For mobile devices
- Small footprint JVM
- Small devices like sensors, printers, etc
- IoT

### FX

- Rich internet apps
- UI API
- Hardware-accelerated graphics
- High-performance clients
- Modern look and feel
- Connect to remote services

### EE (Enterprise)

- Extends SE
- Large scale
- Distributed system
- The other 3 are often clients of this platform

## Enterprise Applications

Designed for:

- Multi-tiered
- Scalable
- Releiable
- Secure
- Solve the problems of large enterprises

"""""""""""""""""""""""""""""""""""""""""""""

### POJO vs Java Bean

A JavaBean is a POJO that is serializable, has a no-argument constructor, and allows access to properties using getter and setter methods

#### POJO

- It doesn’t have special restrictions other than those forced by Java language
- It doesn’t provide much control on members.
- It can implement Serializable interface.
- Fields can be accessed by their names.
- Fields can have any visiblity.
- There can be a no-arg constructor.
- It is used when you don’t want to give restriction on your members and give user complete access of your entity

#### Java Bean

- It is a special POJO which have some restrictions.
- It provides complete control on members.
- It should implement serializable interface.
- Fields are accessed only by getters and setters.
- Fields have only private visiblity.
- It must have a no-arg constructor.
- It is used when you want to provide user your entity but only some part of your entity.

### Hibernate vs Java EE

The relationship between Hibernate and Java EE is that Hibernate is a certified JPA provider, and Java EE is a platform that, among many other specifications, it contains the JPA specification. Hibernate is an implementation of the JPA specification.

"""""""""""""""""""""""""""""""""""""""""""""

## Java EE Architecture

- Container
- Components
- Services
- APIs
- Packaging
- Deployment
- Protocols
- Java SE APIs

### Container

- Runtime environment
- hide technical complexity
- enhance portability
- host apps
- handle complex low-level details
- admin applications

### Components

- Static or dynamic web pages
- server-side classes
- handle business code
- process data
- access legacy systems

### Services

Provides services to containers such as:

- security
- transaction management
- naming
- remote connectivity
and more

Services are configurable
Configuration is isolated

- Uses metadata

### Containers

Open Source exmaples: JBoss, Glassfish, Tommy
Propriatery: IBM Websphere, Oracle Weblogic Server
You can deploy several applications within a single container. It provides
isolation (each app has its own resources, components, and class-loader) and provides administration for each app.

### Protocols

For interacting with the apps.

- HTTP/S is the big one that's mostly used
- RMI/IIOP (Remote Method Invocation/Internet Inter-ORB Protocol)
  - Allows user to integrate with CORBA objects which can be written in any language (Ada, C, C++, Java...)

### Deployment

Distributed applications are several instances of an application deployed to several containers.
Allows for:

- Load balancing
- Fail over
- Scalability
- Availability

## Java EE Services

Tiers:

- Business
- Web
- Interoperability
- Services common to most tiers

### Business Tier

handles business logic and handles/processes data

- *Transaction management services*
  - very powerful. deals with relational dbs and messaging services
  
- *Persistence services*
  - maps between objects and relational databases
  - provides high-level query language
  
- *Validation services*

- *Batch services*
  - Allows user to handle bulk operations running on long-running jobs
  
### Web Tier

Handles interaction between client and business tier

- *Web pages*
  - HTML5 static content

- *Servlets*
  - handles http/s communication

- *Expression language*
  - Handles binding between graphical components and backend components
  - In other words, JSF, which is the bane of every developer's existence
  
- *Web sockets*
  - allow bi-direction conversation between web clients and servers
  
### Interoperability Tier

Allows for interaction with other applications. Is considered one of the biggest advantages of Java EE.

- *SOAP Services**
- *REST Services*
- *JSON*
- *Messaging*
- *Emails*
  - Java mail API
- *Connectors*
  - Lets you connect to any db type
  
### Common Services for most tiers

- *DI (Dependency Injection)
  - Any resource/component can be injected into any other one
  - Most commonly used one
  
- *Interception*
- *Security*
- *Concurrency*
  - Important to handle loads easily
  
## Java SE Services

Java EE is a superset of SE. Some essential SE services include

- DB Access
- Naming and directory interface used for injection
- XML
- Remote (managing low level communication between components)

## Business Concerns

- Standard
- Not good for startups that need to be bleeding edge. It's very concerned with backwards compatibility
- Open(ish) see SO answer:
"The problem is that in order to call something "Java" you need to get it certified as compliant to the Java spec.

One of the pre-requisites of getting this certification is running you JVM through a test suite - Java Technology Compatibility Kit (TCK). This test suite is NOT open sourced.

So you can build a JVM that behaves in a very Java like way and be completely open source, but, if you want to call it a "Java JVM" you need to buy the certification suite under a non open source license. To many open source advocates this is a complete non starter."

- Has paid support
- Easy to hire for. It's got millions of developers using it

## Technical Concerns

- Portable (write once, run everywhere)
- Can integrate with basically anything
- Not great for everything (like IoT or client-side dev)
- Scalable
- Security
- Not great for big data. It was designed to work with relation dbs and doesn't have any solid APIs for NoSQL database types
- Extensible and modular so is very adaptable for many uses
