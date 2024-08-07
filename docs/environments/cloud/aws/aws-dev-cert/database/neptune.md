# Amazon Neptune

May not be as widely utilized as perhaps Amazon RDS or Amazon DynamoDB, simply due to what it is designed for. Amazon Neptune is a fast, reliable, secure, and fully managed graph database service.

Graph databases are for storing and navigating relationships between highly connected data which could contain billions of separate relationships. This can be very difficult with traditional RDBs.

Use case examples:

- Social networking
- Fraud detection (security should always be priority #1)
- Recommendation engines (targeted ads)

## Query Languages

**Apache Tinkerpop Gremlin**: Allows you to query your graph running on your Neptune DB using the Gremlin traversal language

**World Wide Web Consortium SparQL**: Designed to work with the internet and can be used to run queries against your Neptune DB graph

## Database Clusters, Instances, and Storage

A database cluster is comprised of one or more database instances across different AZs, in addition to a virtual database cluster volume which contains the data across all instances within the cluster. The single cluster volume consists of a number of Solid State Discs, SSDs. As your graph database grows, your shared volume will automatically scale and increase in size as required to a maximum of 64 terabytes.

### High Availability and Reliability

- Each cluster maintains a separate copy of the shared volume in at least 3 different AZs. 
- Also has Storage auto-repair to ensure data loss is minimized and need to restore data is minimized.
- Can also run replica instances that are read only while primary is r+w. 15 clusters per replica can exist which span multiple AZs
- If primary fails, a replica in a different AZ will be delegated as the primary and adopt r+w operations. This process takes usually about 30 seconds.

### Connecting to your Neptune Database

3 types of endpoints: Cluster, Reader, Instance

Cluster Endpoint
- Points directly to current primary DB instance of cluster
- Should be used by apps that require r+w access to db
- When primary DB instance fails, it will point to the new primary instance without any changes required by your apps accessing the DB

Reader Endpoint
- Used to connect to read replicas
- Allows apps to access db on read only bases for queries
- A single reader endpoint exists even if you have multiple read replicas
- Connections served by the read replicas will be performed on a round-robin basis
- The endpoint does not load balance your traffic in any way across the available replicas in your cluster

Instance Endpoint
- Every instance within your cluster will have a unique instance endpoint
- Allows you to direct certain traffic to specific instances within the cluster
- You might want to do this for load balancing reasons across your appications reading from yoru replicas

