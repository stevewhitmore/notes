# Compute Fundametals

"Compute resources can be considered the brains and processing power required by applications and systems to carry out computational tasks via a series of instructions"

Closely related to common server components such as CPUs and RAM. A physical server within a data center is a Compute resource.

Compute services can:

- comprise of utilizing hundreds of EC2 instances (virtual servers) which may be used continuously for  months or years processing millions of instructions.
- utilize a few hundred milliseconds of compute resource to execute just a few lines of code within AWS Lambda before relinquishing that compute power.

Can be comsumed in different quantities for diffrerent liengs of time actross a range of categories offering a wide scope of performance and benefit options

<https://aws.amazon.com/products/compute> for examples on how some resources could be used

## Elastic Compute Cloud (EC2)

Allows you to deploy virtual servers within your AWS environment. Most people will require an EC2 instance wthin their environment as part of at least one of their solutions

Can be broken down into the following components:

- Amazon Machine Images (AMIs)
- Instance Types
- Instance Purchasing Options
- Tenancy
- User Data
- Storage options
- Security

### Amazon Machine Images (AMI)

Templates of pre-configured EC2 instances which allow you to quickly launch a new EC2 instance based on the configuration within the AMI

An AMI is an image baseline with an operating system and applications along with any custom configs.

> You can also create your own AMIs to help you speed up your own deployments

**AWS Marketplace**: You can also purchase AMIs from AWS Marketplace which is an online store of AMIs from trusted vendors. May be good since they could be configured to be optimized for certain security setups or for data migration.

**Community AMIs**: Same as above but non-proprietary.

### Instance Types

Once you have selected AMI from a source an instance type must be selected.

- Defines the instance size based on a number of different parameters, ECUs - EC2 Compute Units for the instnace, vCPUs: virtual CPUs

Generally we just pay attention to:

- vCPUs
- Memory
- Instance Storage
- Network Performance

There are preconfigured types for specific purposes:

- Micro instances
- General purpose
- Compute optimized
- etc

### Instance Purchasing Options

- On-Demand Instances
- Reserved Instances
- Scheduled Instances
- Spot Instances
- On-Demand Capacity Reservations

#### On-Demand Instances

- Can be launched at any time
- Can be used for as long as needed
- Flat rate determined on the instance type - paid by the second
- Typically used for short-term uses
- Best fit for testing and dev environments

#### Reserved Instances

Purchases for a set period of time for reduced cost

- All Upfront: Complete payment for 1 or 3 year time frame
- Partial Upfront: Smaller upfront payment for smaller discount
- No Upfront: smallest discount is applied

Best for long term, predictable workloads

#### Scheduled Instances

- You pay for the reservations on a recurring schedule, either daily, weekly, or monthly
- You could set up a scheduled instance to run durning a set time frame once a week
- Not that even if you didn't use the instance, you will be charged

#### Spot Instances

- Bid for a unused EC2 Compute resource
- No guarantees for a fixed period of time
- Fluctuation of prices based on supply and demand
- Can purchase large EC2 instances at a very low price
- Useful for processing data that can be suddenly interrupted (batch jobs, background processing of data)

#### On-Demand Capacity Reservations

- Reserve capacity based on different attributes such as instance type, platform and tenancy, within a particular availability zone for any period of time
- It could be used in conjunction with your reserved instances discount

### EC2 Tenancy

Relates to what underlying host your EC2 instance will reside on, so essentially the physical server within an AWS Data Center

#### Shared Tenancy

- EC2 instance is launched on any available host with the required resources
- The same host may be used by multiple customers
- AWS Security mechanisms prevent one EC2 instance accessing another in the same host

#### Dedicated Instances

- Hosted on hardware that no other customer can access
- May be required to meet compliance
- Dedicated instances incur additional chrages

#### Dedicated Hosts

- Additional visibility and control on the physical host
- Allows you to use existing licenses (per-VM, Windows server, etc)
- Allows you to use the same host for a number of instances
- May be required to meet compliance

### User Data

Allows you to enter commands that will run during the first boot cycle of that instance

- Perform functions upon boot such as to pull down any additional software you want
- Download latest OS updates (yum update -y)

### Storage Options

Selecting storage for your EC2 instance will depend on the instance selected, what you intend to use the instance for, and how critical the data is.

Two types of storage:

#### Persistent Storage

- Available by attaching Elastic Beanstalk Storage (EBS) volumes
- EBS volumes are separated from the EC2 instances (not physically attached)
- These volumes are logically attached via AWS network. This is similar to attaching an external drive to a personal computer.
- You can disconnect the volume from the EC2 instance maintaining data
- You can implement encryption on these volumes and take backup snapshots of all data on the volumes

> The data on EBS volumes are automatically replicated to other EBS volumes within the same availibilty zone for resiliency which is managed by AWS

#### Ephemeral Storage

- Created by EC2 instances using local storage
- Physically attached to the underlying host (like built in storage on a laptop)
- When the instance is stopped or terminated, all saved data on disk is lost
- However, if you reboot your data will remain intact
- Unlike EBS volumes, you're unable to detach instance store volumes from the instance

### Security

During the creation of your EC2 instance you will be asked to select a Security Group for your instance

- A Security Group is essentially an instance-level firewall
- Allows you to control traffic
- At the end of your EC2 instance creation you will need to select an existing Key Pair or create and download a new one

A Key pair is made up of a Public Key and a Private Key.

The function is to encrypt the login information for Linux and Windows EC2 instances, and then decrypt the same information allowing you to authenticate onto an instance

- Public Key is held and kept by AWS
- Private key is the users responsibility
- It is possible to use the same Key Pair on multiple instances (be wary of this)
- You can set up additional, less privileged access controls (e.g. local Windows accounts)

> It is your responsibility to maintain and install the latest OS and security patches released by the OS vendor as dictated within the AWS shared responsibility model

## EC2 Container Service (ECS)

Allows you to run Docker-enabled applications packaged as containers across a cluster of EC2 instances without requiring you to manage a complex and administratively heavy cluster management system.

The burden of managing your own cluster management system is abstracted with the ECS service by passing that responsibility over to AWS, specifically through the use of AWS Fargate.

**AWS Fargate** is an engine used to enable ECS to run Containers without having to manage and provision instances and clusters for Containers

**Docker** is a piece of softare that allows you to automate the installation and distribution of applications inside Linux Containers

A **Container** holds everything an application needs to run from within its container package. They're decoupled from the OS, making Container apps very portable

### Launching an ECS Cluster

Two options:

- Fargate Launch
Far less configuration - requires you to specify the CPU and memory required, define networking and IAM policies, in addition to you having to package your application into containers

- EC2 Launch
Responsible for patching and scaling your instances and you can specify instance type and how many containers should be in a cluster

### Monitoring containers

Can be taken care of through Amazon CloudWatch. You can easily create alarms based off of these metrics, providing you notification when specific events occur, such as your cluster size scaling up or down

### Amazon ECS Cluster

Comprised of a collection of EC2 instances

Features such as Security Groups, Elastic Load Balancing, and Auto Scaling can be used with these instances

These instances still operate in much the same way as a single EC2 instance

- Clusters act as a resource pool, aggregating resources such as CPU and memory
- Clusters are dynamically scalable and mjultiple instances can be used
- Clusters can only scale in a single region
- Containers can be scheduled to be deployed across yoru cluster
- Instances within the cluster also have a Docker daemon and an ECS agent

> ECS agents communicate with eachother allowing ECS commands to be translated into Docker commands

## Elastic Container Registry (ECR)

Provides a secure location to store and manage your docker images.

This is a fully managed service so you dont need to provision any infra to allow you to create this registry of docker images.

This service allows developers to push, pull, and manage their library of docker images in a central and secure location.

Components used in ECR:

- Registry
- Auth Token
- Repo
- Repo Policy
- Image

### Registry

Allows you to host and store docker images as well as create image repos.

Your account will have both read and write access by default to any i mages you create within the registry and any repos.

Access for your registry and images can be controlled via *IAM policies* in addition to *repository policies*.

Before your docker client can access your registry it needs to be authenticated as an AWS user via an Authorization token.

### Authorization Token

To begin the authorization process to communicate your docker client with your default registry you can run the get-login command using the AWS CLI

```aws ecr get-login --region {region} --no-include-email```

This will produce an output response which will be a docker login command

```bash
docker login -u AWS -p {password}
https://{aws_account_id.dkr.ecr.{region}.amazonaws.com
```

This process produces an authorization token that can be used within the registry for 12 hours

### Repository

These are objects within your registry that allow you to group together and secure different docker images.

You can create multiple repositories with the registry allowing you to organize and manage your docker images into different categories.

Using policies from both IAM and repository policies you can assign set permissions to each repository.

### Repository Policy

You can control access to your Repositories through both IAM policies and Repository Policies.

There a number of different IAM managed policies to help you control access to ECR:
`AmazonEC2ContainerRegistryFullAccess`
`AmazonEC2ContainerRegistryPowerUser`
`AmazonEC2ContainerRegistryReadOnly`

#### Repository policies are resource-based policies

- You need to ensure you add a principal to the policy to determine who has access and what permissions they have
- For an AWS user to gain access to the registry they will require access to the ecr:GetAuthorizationToken API call
- Once they have this access repository policies can control what actions those users can perform on each of the repositories

### Image

Once you have configured your registry, repositories and security controls, and authenticated your docker client with ECR, you can then begin storing your docker images in the required repositories

To push and p image into ECR, you can use the `docker push` command, and to retrieve an image you can use the docker pull command

## Elastic Container Service for Kubernetes (EKS)

Kubernetes is an open-source container orchestration tool designed to automate, deploy, scale, and operate containerized qpplications

It can grow from tens, thousands, or even millions of containers

It is container-runtime agnostic which means you can use K8s to run Rocket and Docker images

AWS provides a managed service allowing you to run **run Kubernetes across your AWS Infrastructure** without having to take care of provisioning and running the Kubernetes management infrastructure in what's referred to as **the control plane**

You only need to provision and maintain the **worker nodes**

### K8s Control Plane

There are a number of different components that make up the control plane and these include **a number of differnet APIs, the kubelet processes and the K8s Master**

The control plane schedules containers onto nodes

The control plane also tracks teh state of all K8s objects by continually monitoring the objects

In EKS, AWS is responsible for provisioning, scaling and managing the control plane, and they do this by utilizing multiple availability zones for additional resilience

### Worker Nodes

#### K8s clusters are composed of nodes

A node is a worker machine in K8s. It runs as an on-demand EC2 instance and includes software to run containers

For each node created, a specific AMI is used, which also ensures Docker and kubelet in addition to the AWS IAM authenticator

Once the worker nodes are provisioned they can then connect to EKS using an endpoint

### Working with EKS

1. Create an EKS service role
Before you begin working with EKS you need to configure and create an IAM service role that allows EKS to provision and configure specific resources

The role needs to have the following permissions policies attached to the role:

- AmazonEKSServicePolicy
- AmazonEKSClusterPolicy

2. Create an EKS Cluster VPC
Create and run a CLoudFormation stack based on teh template below which will configure a new VPC for you to use with EKS

3. Install kubectl and the AWS-IAM-Authenticator
Kubectl is a command line utility for K8s and can be installed following the details supplied here.

The IAM-Authenticator is required to authenticate with the EKS cluster

4. Create your EKS Cluster
You can now create your EKS cluster using the details and info from the VPC created in step 1 and 2

5. Configure kkubectl for EKS
Using the `update-kubeconfig` command via the AWS CLI you need to create a kubeconfig file for your EKS cluster

6. Provision and configure Worker Nodes
Once your EKS cluster shows an 'Active' status you can launch your worker nodes using CloudFormation based on the following template

7. Configure the Worker Node to join the EKS Cluster
Using a configuration map downloaded here:
`curl -O https://amazon-eks.s3-us-west-2.amazonaws.com/cloudformation/2019-02-11/aws-auth-cm.yaml`

You must edit it wand replace the <ARN of instance role (not instance profile)> with the NodeInstanceRole value from step 6

## AWS Elastic Beanstalk

Is a managed service that takes your uploaded code of your web app code and automatically provisions and deploys the required resources within AWS to make the web application operational

These resources include EC2, Auto Scaling, application health-monitoring and Elastic Load Balancing, in addition to capacity provisioning

An ideal service for engineers who may not have the familiarity or the necessary skills within AWS to deploy, provision, monitor, and scale the correct environment to run the developed applications

The responsibility is passed on to AWS Elastic Beanstalk to deploy the correct infrastructure to run the uploaded code

You can continue to support and maintain the environment as you would with a custom built environment

You can perform some maintenance tasks from the Elastic Beanstalk dashboard itself

It's able to operate with a variety of platforms and programming languages:

- Packer Builder
- Single Container Docker
- Multicontainer Docker
- Preconfigured Docker
- Go
- Java SE
- Java with Tomcat.NET on Windows Server with IIS
- Node.js
- PHP
- Python
- Ruby

**The service itself is free to use**
There is no cost associated with Elastic Beanstalk. However, any resources that are created on your application's behalf, such as EC2 instances, you will be charged for as per the standard pricing policies at the time of deployment.

### Elastic Beanstalk Core Components

#### Application Version

An application version is a very specific reference to a section of deployable code

The application version will point typically to S3, simple storage service to where the deployable code may reside

#### Environment

An environment refers to an application version that has been deployed on AWS resources which are configured and provisioned by AWS Elastic Beanstalk

At this stage, the application is deployed as a solution and becomes operational within your environment

The environment is comprised of ALL the resources created by Elastic Beanstalk and not just an EC2 instance with your uploaded code

#### Environment Configurations

This is a collection of parameters and settings that dictate how an environment will have its resources provisioned by Elastic Beanstalk and how these resources will behave

#### Environment Tier

Reflects on how Elastic Beanstalk provisions resources based on what the application is designed to do

If the application manages and handles HTTP requests then the app will be run in a **web server environment**

If the application does not process HTTP requests and instead perhaps pulls data from an Simple Queue Service (SQS) queue then it would run in a **worker environment**

#### Configuration Template

The template that provides the baseline for creating a new, unique, environment configuration

#### Platform

A columination of components in which you can build your application upon using Elastic Beanstalk

These comprise of the OS of the instance, the programming language, the server type (web or application) and components of Elastic Beanstalk itself, and as a whole can be defined as a platform

### Applications

An application is a collection of different elements, such as environments, environment configuration, and application versions

#### Web Server Environment

This is typically used for standard web apps that operate and serve requests over HTTP port 80

- Route 53
- Elastic Load Balancer
- Auto Scaling
- EC2
- Security Groups

#### Worker Environment

It is used by applications that will have a back-end proccessing task, that will interact with AWS SQS

- SQS Queue
- IAM Service Role
- Auto Scaling
- EC2

### Elastic Beanstalk Workflow

AWS Elastic Beanstalk operates a very simple workflow process for your application deployment and ongoing management

Create Application -> Upload Version -> Launch Environment -> Manage Environment

If the management of your applications have altered the environment configuration then your environemnt will automatically be updated to reflect the new code should additional resoruces be required

## AWS Lambda

A serverless compute service that allows you to run your app code wihtout having to manage EC2 instances.

**Serverless** means that you do not need to worry about provisioning and managing your own computer resource to run your own code, instead this is managed and provisioned by AWS.

The service does require compute power to carry out your code requests, but because the AWS user does not need to be concerned with managing this compute power or where its provisioned from, it's considered 'serverless' from the user perspective.

You only ever have to pay for compute power when Lambda is in use via Lambda Functions.

AWS Lambda charges computer power per 100ms of use only when your code is running, in addition to the number of ties your code runs.

### Working with AWS Lambda

There are essentially 4 steps to its operations:

1. You can either upload your code to Lambda, or write it within the code editors that Lambda provides. Supported languages: Node.js, Java, C#, Python, Go, PowerShell, and Ruby.
2. Configure your Lambda functions to execute upon specific triggers from supported event sources.
3. Once the specific trigger is initiated, Lambda will run your code (as per your Lambda function) using only the required compute power as defined.
4. AWS recoreds the compute time in Milliseconds and the quantity of Lambda functions run to ascertain the cost of the service

### Components of AWS Lambda

**Lambda Function** is compiled of your own code that you want Lambda to invoke as per defined triggers.

**Event sources** are AWS services that can be used to trigger your Lambda functions.

**Trigger** is essentially an operation from an event source that causes the function to invoke (like a PUT request).

**Downstream Resources** are resources that are required during the execution of your Lambda Function.

**Log streams** help to identify issues and troubleshoot issues with your Lambda function.
These log streams would essentially be a sequence of events that all come from the same function and recorded in CloudWatch.

### Creating Lambda Functions

At a high level the configuration steps for creating a Lambda function via the AWS Management Console could consist of:

1. Selecting a Blueprint
Select a blueprint template provided by AWS Lambda
Ex: S3-get-object - an S3 trigger that retrieves metadata

2. Configure Triggers
Define the trigger for your Labmda function
Ex: specifying the S3 bucket for your function

3. Configure Functions
Upload code or edit it in-line
Define the required resources, maximum execution timeout, IAM Role and Handler Name

> AWS Lambda is a highly scalable serverless service, coupled with fantastic cost optimization compared to EC2 as you are only charged for Computer power while the code is running and for the number of cuntions called.

## AWS Batch

Used to manage and run batch computing workloads within AWS

Batch Computing is primarily used in specialist use cases which require a vast amount of compute power across a cluster of compute resources to complete batch processing, executing a series of tasks. This can be very costly and difficult to manage.

With AWS Batch many constraints, administration activities, and maintenance tasks are removed. The process is a lot more streamlined by taking advantage of the elasticity of AWS coping with any level of batch processing while optimizing the distribution of the workloads. Everything's taken care of by AWS.

Perfect if you have a requirement to run multiple jobs in parallel using Batch computing such as to analyze financial risk modles, perform media transcoding, or engineering simulations.

There are 5 components that make up AWS Batch Service:

### Jobs

A Job is classed as a unit of work tha tis to be run by AWS Batch.

- Can be an executable file, an app within an ECS Cluster, or a shell script.
- Run on EC2 instances as a containerized application
- Can have different states such as Submitted, Pending, Running, Failed, etc

### Job Definitions

These define specific parameters for the Jobs themselves and dictate how the Job will run and with what configuration.

Some examples may be:

- How many vCPUs to use for hte container
- Which data volumes hsould be used
- Which IAM role should be used to allow access for AWS Batch to communicate with other AWS services
- Mount points

### Job Queues

Jobs that are scheduled are placed into a Job Queue until they run.

You can have multiple queues with different priorities.

On Demand and Spot instances are supported.

AWS Batch can bid on your behalf for Spot instances.

### Job Scheduling

Takes care of when a Job should be run and from which Compute Environment

- Typically will operate on a FIFO basis
- Ensures that higher priority queues are run first

### Compute Environments

Environments containing the compute resources to carry out the Job.

#### Managed Environments

- The service will handle provisioning, scaling, and termination of Compute instances
- This environment is created as an Amazon ECS cluster

#### Unmanaged Environments

- These environments are povisioned, managed, and maintained by you
- Gives greater customization but requires greater administration and maintenance
- Requires you to create the necessary ECS Cluster

## Amazon Lightsail

Is essetially a Virtual Private Server (VPS) backed by AWS infrastructure, much like an EC2 instance but without as many configurable steps throughout its creation.

It has been designed to be simple, quick, and very easy to use at a low cost point for small scale use cases by small businesses or for single users

- It's commmonly used to host simple websites, small applications, and blogs
- You can run multiple Lightsail instances together allowing them to communicate
- It's possible to connect it to other AWS resources and to your existing VPC running within AWS via a peering connection

### Deploying a Lightsail instance

Can be deployed from a single page with just a few configuration options.

Can be accessed either via the AWS console under the Compute category or directly from the homepage of AWS Lightsail which sits outside of the AWS Management Console

Instance is charged on-demand only so you're only charged for when you're actually using the resource
