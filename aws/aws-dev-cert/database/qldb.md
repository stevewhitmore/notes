# Quantum Ledger Database (QLDB)

Just came out in September 2019. Is another fully managed and severless database service designed to be a ledger database.

Highly secure

Is great for maintaining an immutable ledger with cryptographic abilities to enable the verifiable tracking of changes over time.

---

Use case example: Recording financial data over a period of time.

Plain text -> hash function -> hashed text

Maintains a complete hisstory of accounting and transactional data between parties in an immutable, transparent, and cryptographic way.

---

Is append only and uses a database journal. This is to ensure that no existing data is changed. Essentially, the immutable transaction log that records all entries in a sequenced manner over time. This service therefore negates the need for an organization to develop and implement their own ledger applications.

Similar to blockchain, but blockchain is distributed across multiple hosts in a decentralized environment. QLDB is owned and managed by a central and trusted authority.

QLDB is serverless: Infrastructure administration and maintenance is managed by AWS. All scaling is managed by AWS.

> This would be a good service for the insurance industry (especially Market Regulation). By its nature it can be a long winded and extensive process involving many different parties and operations over a long time period. QLDB's immutable nature would help prevent fraud.

## Concepts and Components

Is based on a ledger.

Data is placed in Amazon Ion documents which is internally created by Amazon. Ion documents are an open-source self-describing data serialization format and is a superset of JSON, meaning any JSON doc is also considered a valid Ion document.

- Each time a change is committed to the journal, a sequence number is added to identify its place in the change history. 
- Can verify the integrity of the changes because of the SHA-256 bit hash used which creates a cryptographic digest file of the journal.
- This helps to ensure that the data within your document  has not been altered

### Storage

Split into two categories: journal and index

**Journal storage**: used to hold the history of changes made within the ledger database. Holds immutable changes and history for the Ion documents within your table.

**Index storage**: Used to provision the tables and indexes within your ledger. Optimized for querying.

## Amazon Kinesis Integration

Integrates really well with Amazon Kinesis, which is a service that makes it easy to collect, process, and analyze real-time streaming data so you can get timely insights and react quickly to new information. Ingest real-time data such as application logs, website clickstreams, IoT telemetry data, and more into your databases, your data lakes and data warehouses, or build your own real-time applications using this data. It allows you to analyze the data in real time rather than having to wait for all your data to be collected before processing can begin.

