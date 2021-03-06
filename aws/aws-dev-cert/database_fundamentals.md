# Database Fundamentals for AWS

## What is a Cloud Database?

RDS - Availability and Durability

- Automated backups
- Database snapshots
- Multi-AZ deployments
- Automatic host replacemnt
- No set up fee
- no hardware required
- pay only for what we use
- easy to scale up and down

Each region includes distinct facilities located in different areas with the region called Availability Zones (AZ).

AZ's are distinct geographical locations that are engineered to b insulated from failures in any other AZ.

Loctated on separate electrical grids, flood plains, risk profiles.

By having Amazon RDS instances in more than one AZ a database can be protected from failure at a single region.

### AWS Shared Responsibility Model

#### Customer Responsibility

Customer manage security in the cloud

- Customer Data
- Platform, Applications, Identity & Access Management
- Operating System, Network & Firewall Configuration
- Client Side Data Encryption & Data Integrity Authentication
- Server Side Encryption (File System and/or Data)
- Network Traffic Protection (Encryption/Integrity/Identity)

#### AWS Responsibility

AWS Manages security of the cloud

##### AWS Foundation Services

- Compute
- Storage
- Database
- Network

##### AWS Global Infrastructure

- Region
- Availability Zones
- Edge Locations

> Security has two aspects: Data at rest and data in transit. AWS provides encryption for data in transit. Data at rest can be encrypted through a number of AWS services

## Overview of the AWS Database Services

Split up into Relational and Non-relational Databases

### Relational Databases

Structured tables <- generally support -> Structured Query Language (SQL) operations

A database view is basically a saved select statement. Tables are linked together via joining on common fields and they generate a temporary view. This processing is done by the RDBMS script engine which means a relational database is generally quite complex and requires a lot of computing resources. This means generally the software footprint for relational databases are bigger than for non-relational.

### Non Relational Databases

- No table schema required
- Can support non structured data
- Focus on providing a fast, secure data store
- Generally lack a processing engine so are lighter in design
- Do not require a schema

#### Communication

##### Relational

Persisted Network Connection:
Computer -> SQL statement -> Relational Database
Relational Database -> Response from Database -> Computer

##### Non Relational

Computer -> Request via RESTful API call -> Amazon DynamoDB
Amazon DynamoDB -> HTTP response -> Computer

##### Summary of differences/use cases

###### Relational

- RDBMS/ACID Engine
- Supports complex relationships between tables
- Uses the Structured Query Language
- Generally accessed vai a persistent network connection (ODBC)
- Uses a schema to define tables
- Generally provides a processing engine within the database to manage procssing of select, create, replace, update, delete statements


###### Non Relational

- Simple document or key store
- Can store many different data types
- Generally accessed via RESTful HTTP
- No schema required
- Every table must have a primary key
- Scales fast
- Lighter by design

AWS Non Relational Databases are **DynamoDB** and **Amazon Elasticache**.

## Amazon Non Relational Databases In Depth

### DynamoDB

- A cloud native database for managing high volumes of records and transactions without the need for provisioning capacity up front.
- A fully managed service
- Supports both document and key store object

### Amazon Elasticache

Runs on 2 different database engines -

#### Amazon Elasticache for Redis

Use if

- You need complex data types, such as strings, hashes, lists, sets, sorted sets, and bitmaps
- You need persistence of your key store
- You need to encryptyour cache data (e.g. to maintain compliance)
- You need to replicate your cached data

#### Amazon Elasticache for Memcached

Use if

- You need the simplest model possible
- You need to run large nodes with multiple cores or threads
- You need the ability to scale out/in, adding and removing nodes as demand on your system increases and decreases

## AWS Relational Databases in Depth

What are the benefits of using Amazon RDS?

- The ability to scale components
- Automatic backups and patching
- High availability - can run in parallel over several availability zones
- Automatic failure detection and recovery

### Amazon RDS for MySQL

- Micro instances - often assocated with free tier so useful for non-prod or pilot projects
- General purpose - latest gen and current gen like the M series
- Memory optimized - the latest and current gen R series instances
- Burst support - latest and current T series instances
- General purpose storage provides cost-effective storage for small or medium sized workloads
