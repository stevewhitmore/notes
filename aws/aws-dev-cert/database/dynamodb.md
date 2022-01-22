# Amazon DynamoDB

NoSQL Database
Key-value store

You can look up data:
- Using a primary key for each item
- Through the use of indexes

For ultra high performance

used for gaming web mobile and iot

fully managed service - aws handles everything. Very easy to set up

Set tables up, config provisioned throughput and that's it

DynamoDB tables are considered schemaless
As long as the table has the appropriate primary key the item can contain varying sets of attributes
The record doesn't need to have the same attributes or the same number of attributes
It doesn't care about type for individual attributes either

As a general rule, you should maintain as few tables as possible in a DynamoDB application. Most well designed applications require only one table, unless there is a specific reason for using multiple tables.

Exceptions are cases where high-volume time series data are involved, or datasets that have very different access patterns—but these are exceptions. A single table with inverted indexes can usually enable simple queries to create and retrieve the complex hierarchical data structures required by your application.

You can add secondary indexes to search by different attributes

1 query 1 index
If you want to query and match on two differnet columns you need to create an index that can do that properly
When you write your qureies you need to specify exactly which index should be used for each query

Secondary indexes
global
lets you query across the entire table to find any record that matches a particular vlue

local
can help find data within a single partition key

Read/write capacity mode
This is for memory not disk space. AWS will allocate more disk space as needed but you do need to specify a capacity for input/output - amazon charges by this metric
By default you're given 5 read capacity units and 5 write capacity units. Don't go crazy with it because it will get very expensive.

2 modes for this: Provisioned and On-Demand

Provisioned
- Allows you to provision set read and writes allowed against your db per second by your app.
- Measured in capcaity units RCU, WCU. Depending on the transaction, each action will use 1 or more RCUs or WCUs.
- Generally used when you have a predicted and forecasted workload of traffic

On-Demand
- Does not provision any RCUs or WCUs, instead they are scaled on demand. Not as cost effective.
- Generally used if you do not know how much workload you are expected to experience
- With more understanding of load y9ou can then change your mode across to Provisioned


Advantages of DynamoDB
- Fully-managed
- Schemaless
- Highly Available. Can be distributed across multiple AZs.
- Fast. Does not slow down no matter how big a table gets (unlike RDBs)

Disadvantages
- Eventual consistency - Data is automatically copied across 3 AZs which can lead ot inconsistencies across zones for a short time. Usually this only takes a few miliseconds but can sometimes take a bit longer.
- Queries are less flexible than SQL. You can't write advanced queries with joins, groupings, and summaries. More computation will have to happen in the app code.
- Workflow limitations. Maximum record size is 400kb. Max indexes per table is 20 global, 5 secondary. This can be adjusted by contacting AWS support.
- Provisioned throughput. You need to manually scale if there's an uptick in traffic otherwise queries will fail with a ProvisionedThroughputExceededException.

