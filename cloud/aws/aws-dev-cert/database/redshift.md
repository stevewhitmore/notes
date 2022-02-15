# Amazon Redshift

Relational Database Management System based on Postgres. Designed for high performance and analysis of information capable of storing and processing petabytes of data and provide access to this data. It's considered a "data warehouse"

## Data Warehouse 

Used to consolidate data from multiple sources to allow for data driven business decisions

- By its nature needs to be able to store huge amounts of data
- Commonly has data cleansing and ETL operations

### ETL (Extract, Transform, and Load)

Common process by which data from multiple systems is combined into a single data store for legacy storage or analytics.

**Extraction** is the process of retrieving data from one or more sources. Either online, brick & mortar, legacy data, Salesforce data and many others. After retrieving the data, ETL is to compute work that loads it into a staging area and prepares it for the next phase.

**Transformation** is the process of mapping, reformatting, conforming, adding meaning and more to prepare the data in a way that is more easily consumed. One example of this is the transformation and computation where currency amounts are converted from US dollars to euros.

**Loading** involves successfully inserting the transform data into the target database data store, or in this case, a data warehouse. All of this work is processed in what the business intelligent developers call an ETL job.

## Redshift Architecture

Clusters contain Redshift engine which contains at least one database. Also contains a compute node. If more than one node is present a Leader node is designated. This acts as a gatekeeper to the cluster for external apps. Takes information from outside and then executes plans to the cluster. Then takes response and passes it back to external app.

### Dense Compute Node Types

The nodes are split into slices. The slices are partitions of memory and disk space. It's used to process operations given by the leader node where parallel operations can be performaed across all slices and all nodes at o nce for the same query. When a table is created it is possible to distribute rows of that table across different nodes slices based upon how the distribution case is defined for the table. 

## Performance

- 3x faster than other data warehouses
- Massively Parallel Processing (MPP)
- Columnar Data Storage - similar to caching where it reduces the number of disk I/O operations and allows for in-memory operations. 
- Result Caching - stores common results in the leader node memory to reduce query time

Redshift can be integrated with Amazon CloudWatch to monitor CPU utilization, throughput, and query/load performance.

You can assign up to 10 IAM roles to associate with your cluster during creation.
