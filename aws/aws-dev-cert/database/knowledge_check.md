# Knowledge Check

**Which of the following statements about Amazon DynamoDB is not true?**
Reads and writes can slow down as the table gets large.

Explanation
DynamoDB tables are schemaless so you don't have to define the exact data model in advance. The data model can change automatically to fit your application's needs. DynamoDB is designed to be fast; reads and writes take just a few milliseconds to complete and DynamoDB will be fast no matter how large your table grows, unlike a relational database, which can slow down as the table gets large. DynamoDB is designed to be highly available and your data is automatically replicated across three different availability zones within a geographic region. Finally, although DynamoDB performance can scale up as your needs grow, your performance is limited to the amount of read and write throughput that you've provisioned for each table.

**When using Provisioned Capacity mode, how are you charged for Amazon DynamoDB?**
by the total amount of throughput that you configure for your tables plus the total amount of storage space used by your data

Explanation
You are charged for the total amount of throughput that you configure for your tables plus the total amount of storage space used by your data.

**In Amazon RDS, what is the purpose of Multi-AZ deployment?**
to create high availability and data redundancy

Explanation
If high availability and resiliency are of importance when it comes to your database, then you might want to consider a feature known as Multi-AZ, which stands for multi-availability zones. When Multi-AZ is configured, a secondary RDS instance is deployed within a different availability zone within the same region as the primary instance. The primary purpose of the second instance is to provide a failover option for your primary RDS instance. When we have a Multi-AZ deployment, it will create another standby instance in a different availability zone to create high availability and data redundancy.

**Which of the following database engines is supported by Amazon RDS?**
Amazon Aurora
MySQL
PostgreSQL

Explanation
Amazon RDS allows you to select from a range of different database engines. These currently include MySQL, MariaDB, PostgreSQL, Amazon Aurora, Oracle, and SQL Server. MySQL is considered the number-one open-source relational database management system. MariaDB is the community-developed fork of MySQL. The PostgreSQL database engine comes in a close second behind MySQL as the preferred open source database. Amazon Aurora is AWS's own fork of MySQL, which provides ultrafast processing and availability, as it has its own cloud-native database engine. The Oracle database is a common platform in corporate environments. SQL Server is a Microsoft database with a number of different licensing options.

**Which of the following statements about Amazon Neptune is not true?**
It supports SQL queries.

Explanation
Amazon Neptune uses its own graph database engine and supports two graph query frameworks. One of these is Apache Tinkerpop Gremlin, and this allows you to query your graph running on your Neptune database, using the Gremlin traversal language. And we have the Worldwide Web Consortium Sparql.

**With regard to database storage, what does IOPS stand for?**
input and output operations per second

Explanation
If we look at the Provisioned IOPS, we can define the allocated storage and then also the number of IOPS as well, which is the input and output operations per second.

****************An in-memory cache is generally used to improve**
read-only performance

Explanation
A common scenario is to have a web application that reads and writes data to persistent storage--for example, to a relational database such as RDS or a NoSQL database such as DynamoDB. This is where an in-memory cache is useful. It's generally used to improve read-only performance.

**Which of the following is not a common use case for ElastiCache?**
customer relationship management systems

Explanation
Before I finish this lecture covering ElastiCache, I want to point out some of the common use cases where you might use Amazon ElastiCache. Due to its incredibly fast performance and scaling abilities, this is commonly used in the online gaming industry, where it's vital that statistical information like a scoreboard is presented as quickly and as consistently as possible to all the players in the game. Another common use is for social networking sites, where they need a way to store temporary session information in session management. Real-time analytics is also a great use for ElastiCache, as it can be used in conjunction with other services such as Amazon Kinesis to ingest, process, and analyze data at scale.

**Each Neptune database cluster maintains a separate copy of the virtual database cluster volume in at least how many different availability zones?**
3

Explanation
To ensure high availability is factored into Neptune, each cluster maintains a separate copy of the shared volume in at least three different availability zones.

**What is caching?**
storing frequently accessed information in memory to allow faster access than when requesting the information from a persistent data store

Explanation
Additional memory enables our device to store frequently accessed information in memory instead of having to request the information from the hard drive, which is much slower than RAM. This process is known as caching.

**Amazon Neptune is a fast, reliable, secure, and fully managed (blank) service.**
graph database

Explanation
Amazon Neptune is a fast, reliable, secure, and fully managed graph database service.

**Which Amazon RDS template uses defaults for high availability and fast and consistent performance?**
Production

Explanation
Now, depending on what template we select, Amazon RDS will predefine a list of other configurable components. The Production template uses defaults for high availability and fast and consistent performance. The Dev/Test template is intended for development use outside of a production environment. And the Free Tier is simply to allow you to get hands-on experience with RDS and doesn't really use many of the features.

**Which of the following is a type of Amazon Neptune endpoint?**
A reader
A cluster
An instance
All of these are Neptune endpoints.

Explanation
There are three different types of Amazon Neptune endpoints: cluster endpoint, reader endpoint, and instance endpoint.

**What is an endpoint on Amazon Neptune?**
a URL address and a port that points to your components

Explanation
An endpoint is simply a URL address and a port that points to your components.

**Amazon ElastiCache allows you to retrieve information from where?**
in-memory data stores

Explanation
Amazon ElastiCache is a service that makes it easy to deploy, operate, and scale open-source, in-memory data stores in the cloud. This service improves performance through caching, where web applications allow you to retrieve information from fast, managed, in-memory data stores instead of relying entirely on slower disk-based solutions.
