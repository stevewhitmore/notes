# AWS Fundamentals
S3 created in 2006. Preceded by Simple Queue Service (SQS) in 2004.

## 4 Major categories:
- Compute
- Network and CDN
- Storage
- Database

AWS offers storage services that replicates data to 3 different data centers in a region.


# Accessing AWS services
- AWS Managmenet Console (web interface)
  - Most commonly used
- AWS CLI
- APIs
  - All AWS services were designed to be accessed via API
- AWS SDKs
- Third party tools
  - S3Fox Organizer: Like a browser-based FTP client. Browser plugin.
- AWS mobile applications


# AWS Regions and Availability Zones
One of the most important concepts in AWS.
- Independent collections of data centers within a specific geographic area.
GovCloud and China are not automatically accessible for new users.
## Geolocation
May want to choose a region close to users, or close to you if you have to upload a lot of things.
- Not all services are available in every region. There's a list under "Products and Services by Region"

## Availability Zone 
- A data center, basically. 
- Every region has at least 2 availability zones (except Beijing, which has 1)

### Availability Zone Destinations
ap-southeast-1a
ap-southeast-1b

Each AZ is a separate datacenter but they're closely interconnected because:
- Low-latency connections
- Fault tolerant deployments


# The AWS acccount - and teh shared security responsibility model
- Requires credit card on file but you can request regular invoices from AWS instead of auto-billing.

Team-based accounts
- Dev
- QA
- Production

AWS consolidated billing.

## Shared Security Responsibility Model
AWS:
- Physical data center security
- Performs backups, patches
Us: 
- Access (Security Groups, ACLs, routing policies)

Varies by service


# Using AWS tools - and next steps
Most corporate intranet environements use N-Tier web application model

Can also set up Disaster Recovery models
- Raw data available to rebuild (cold site)
- Partially deployed data (warm site)
- Fully replicated data available to depoy (hot site)
Business Impact Assessment would be the best way to determine which to go with. Can go with multiple.

## Courses to to next
AWS 110: Technical Foundation
- AWS 120: Compute Fundamentals
- AWS 140: Storage Fundamentals
- AWS 160 Networking Fundamentals
- AWS 180: Database Fundamentals
- AWS 190: Security Fundamentals
