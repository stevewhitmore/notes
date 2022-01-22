# Amazon Elasticache

A service that makes it easy to deploy, operate, and scale open-source, in-memory data stores in the cloud. 
This service improves the performance through caching, where web applications allow you to retrieve information from fast managed in-memory data stores instead of relying entirely on slower disk-based solutions.

Caching
- Additional memory enables our devide to store frequently accessed info in memory instead of having to request the information from teh harddrive.
- Memory accessed from RAM is way faster than hard drive.
- Typically good for read-only data since the majority of users are reading and not writing most of the time

Elasticache can be used for apps using RDBs or NoSQL databases. It can be used for any application that could use performance improvements from in-memory caching - not just web apps.

If an application gets growing traffic you can add caching instead of having to scale your web servers way up quickly. Instead of scaling vertically you scale horizontally which is less resource intensive and ccheaper.

Engines
Memcached
A high performance, submillisecond latency in-memory key-value store service that can either be used as a cache, in addition to a data store.

Less features than Redis but is recognized for simplicity and speed/performance.

- Caching
- Session Store

Redis
A purely in-memory data store designed for high performance and again providing sub-millisecond latency on a huge scale to real-time applications.

More robust than Memcached but not as performant.

- Caching
- Session Store
- Chat and Messaging
- Gaming Leaderboards
- Geospacial
- Machine Learning
- Media Streaming
- Qiueues
- Real-Time Analytics


Components of Elasticache - Nodes, Shards, and Clusters

Node
A fixed sized chunk of secure, network-attached RAM. Essentially the buildling blocks of the Elasticache service and supports a cluster configuration.

Shard
Redis Shard (node group) a group of up to 6 nodes

Cluster 
Redis Cluster - a group of 1-90 shards. Data is distrubted across all the shards in the cluster.
Memcached Cluster - a collection of one or more cache nodes.

Common Use Cases of Elasticache

- Used a lot in online gaming where scores and other data need to be presented ASAP
- Social media for storing temp session data and management
- Real time analytics

These uses are very read-only heavy.

When to not use this?
- Not good for data persistence
- working with primary data records
- need write performance rather than read performance

