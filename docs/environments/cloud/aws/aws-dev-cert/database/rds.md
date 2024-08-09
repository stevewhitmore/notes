# Amazon Relational Database Service

Is a managed service which means amazon handles backups, OS patches, and DB software patches.

## Database engines

- MySQL (The most popular open source relational database managment system)
- MariaDB (Community developed sql
- PostreSQL (Close 2nd behind MySQL for open source RDMS)
- Amazon Aurora (AWS fork of mysql)
- Oracle (commonly used in corporate because they enjoy pain)
- SQL Server (Microsoft)

## Multi AZ

You can deploy your RDS instance in a single availability zone. However, if high availability and resiliency is of importance then consider Multi AZ (multi availability zones) 

When configured a secondary RDS instance is deployed within a different availability zone within the same region as the primary instance. 

The primary purpose of the second instance is to provide a failover option for your primary RDS instance. The replication of data between the primary RDS database and the secondary replica instance happens synchronously. This happens automatically and doesn't require any intervention by you. RDS will update the DNS record to point to the secondary instance. Takes 1-2 minutes depending on size, number of transactions, and activity at the time of failover.

Failover happens if:

- patching maintenance has been performed in the primary instance 
- the instance of the primary database has a host failure 
- the availability zone of the primary database fails
- the primary instance was rebooted with failover
- the primary database instance class on the primary database is modified

## Scaling Your Database

MySQL, PostgreSQL, MariaDB, Oracle, and SQL Server all use Elastic Block Store, EBS volumes, for both data and log storage. However, Amazon Aurora uses a shared cluster storage architecture and does not use EBS.

The engines that use EBS support General Purpose SSD Storage, Provisioned IOPS (SSD) Storage, and Magnetic Storage.

### General Purpose SSD Storage

- A good option for a broad range of use cases 
- Provides single-digit millisecond latencies 
- Offers a cost-effective storage solution
- The minimum SSD storage volume for your primary dataset is 20 gibibytes with a maximum of 64 tebibytes for MySQL, 
  - PostgreSQL, MariaDB, and Oracle. However, the maximum for SQL Server is 16 tebibytes. 

### Provisioned IOPS (SSD) Storage

- Good for workloads that operate at a very high I/O
- **IOPS:** Minimum 8,800, max 80,000 (SQL Server 40,000)
- **Storage for primary data:** Minimum 100GiB, max 64TiB (SQL Server 16TiB)

### Magnetic Storage

Provided for backwards compatibility. AWS recommends General Purpose instead.

### Shared Cluster Storage
- Aurora uses this
- Automatically managed by the service itself
- The option to configure and select storage options does not exist
- Your storage will scale automatically as your database grows

## Compute Scaling

- Vertical scaling will enhance the performance of your database instance. For example, scaling up from an m4.large to an m4.2xlarge. 
    - Can make the change immediately or wait for a scheduled maintenance window.
- Horizontal scaling will see an increase in the quantity of your current instance. For example, moving from a single m4.large to three m4.large instances in your environment through the means of read replicas.
    - Can make a read replica to help share the load of heavy read traffic

## Automated Services

Backups and software patches are handled automatically.

As Amazon RDS is a managed service, and from a shared responsibility model is considered a container service where you have no access to the underlying operating system on which your database runs on.

### Consumer Responsibility

- Customer data
- Client side data encryption & data integrity authentication
- Network traffic protection (encryption/ integrity/identity)
- Customer IAM
- Firewall configuration

### AWS Responsibility

- Platform & Application management
- OS & Network configuration
- AWS Endpoints
- AWS Foundation Services (Compute, Storate, Database, Network)
- AWS Global Infrastructure (Regions, Availability Zones, Edge Locations)
- AWS IAM


