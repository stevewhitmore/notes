# Identity & Access Management
- **Identity** is for Authentication
- **Access Management** is for Authorization (what they can access/do)
 
## Features of IAM
- Users
- Groups
- Roles
- Policy Permissions
- Access Control Mechanisms
 
IAM is a Global Service (as opposed to regional). It's the first service a user will interact with when using AWS whether its via browser or api.
 
Without IAM there is no way of maintaining security or control the access to your resources.
 
Provides the components to manage access but is only as strong as you configure it.
 
The responsibility of implementing secure, robust, and tight security within IAM is on us.

Settings can be found under Security, Identity & Compliance category

## Users, Groups, and Roles

### Users
Objects used to represent an identity
- For real people or for automation

#### Creating Users
- Can create users via AWS Management Console (web interface) or programmatically with the aws cli, tools for windows powershell, or IAM HTTP API

**7 steps**
1. Create username
2. Select AWS Access Type 
	- Programmatic -> Enables an *access key ID* and *secret access key* for the AWS API, CLI, SDK, and other dev tools and/or 
	- AWS Management Console -> Enables a password that allows uers to sign into the web interface
3. Define password 
4. Permission assignment
	- Attached to Users directly or inherited from a Group that the user can be assigned to. Using groups to assign permissions is considered best practice.
5. Review and confirm info
6. Create the user
7. Download security credentials within a .csv file

#### Access Keys
Required for programmatic access for authentication
- Access Key ID is made up of 20 random uppercase alphanumeric characters
- Secret Access Key ID is made up of 40 random upper/lowercase characters

*The Secret Access Key will only be visible upon creation and cannot be recoverred sine AWS doesn't store a copy. If it's lost a new set of keys will need to be created*

Keys must be applied and associated with application. 
- AWS CLI needs to be instructed to use Access Keys

#### User Summary Page
- Summary shows User ARN (Amazon Resource Number), Permissions, Groups, Security Credentials management, and Access Advisor

*MFA (Multi-factor Authentication) should be a minimum for admins*

*AWS CodeCommit: Self-hosted private git repositories*

### Groups
Objects similar to User objects.
- Not used in authentication process
- Used to authorize access through AWS Policies

- AWS managed polices and customer managed polices are assigned to groups. 
- Groups are usually for specific requirements or job roles. Individual users inherit policies from groups.

#### Creating Groups
1. Group Name
2. Attach Policies
3. Review

- AWS default is max 100 groups. Can be increased by contacting AWS.

- User can only be associated to 10 groups.

### Roles
Allow you to adopt a set of temporary IAM permissions

Beneficial in that you can assign a predefined Role to a process (like EC2) instead of a fresh set of credentials. This is considered best practice.

There are many advantages to IAM Roles:
- Don't have access to any keys or credentials associated with them. Credentials are dynamically assigned by AWS.

If you had a fleet of EC2 instances you could modify the Role/s associated with them to update all their permissions quickly and easily.

#### Roles and Users
There are circumstances where you need to grant temporary access to a resource for a particular user. In this case you can allow the user to assume a Role temporarily.

**There are 4 different types of Roles**
1. Service Role
	- Used by other services to assume the Role needed to perform their tasks
	- Examples: EC2, Lambda, etc
2. Service-Linked Role
	- Predefined by AWS for services with permissions that cannot be altered 
	- Examples: Lex - Bots, Lex - Channels
3. Role for Cross-Account Access
	- Provide access for account you own and a 3rd party
	- "Trusting" account has the resources that need to be accessed
	- "Trusted" account contains users that need to access the resouces in the "trusting" account
	1. A role is created in the Trusting account
	2. A Trust is established with the Role by the AWS Account number of the Trusted account
	3. Permissions are applied to the Role via policies
	4. The users in the Trusted account have a policy account
4. Role for Identity Provider Access
Offers 3 options:
	1. Grant access to web identity providers - creates a trust for users using Amazon Cognito, Amazon, Facebook, Google, or some other provider
	2. Grant Web Single Sign On to SAML providers - allows access to users coming from a Security Assertion Markup Language (SAML) provider
	3. Grant API access to SAML providers - allows access from SAML provider via the AWS CLI, SDKs, or API calls

### Demo
Create a new Group -> Attach permissions to the Group -> Create a new User -> Assign the user to the Group -> Set up a new Service Role -> Apply Service Role to a new EC2 instance

## IAM Policies
Formatted as JSON with at least one structure with this structure:
```
{
	"Version": 2012-10-17"
	"Statement": [
		"Sid": "Stmt1494509737040"
		"Action": "cloudtrail:*",
		"Effect": "Allow",
		"Resource": "*",
		"Condition": {
			"IpAddress": {
				"aws:SourceIp": "10.10.0.0/16"
			}
		}
	]
}
```
**Version**: The policy language version
**Statement**: The main element of the policy which includes: 
    **Sid**: The Statement ID is a unique identifier within the Stgatement array
    **Action**: What action will be allowed or denied depending on the value entered for the Effect element - effectively api calls
    **Effect**: Can be set to "Allow" or "Deny" for previously defined Action. All access to your resources are denied by default. 
    **Resource**: This element specifies the actual resource you wish the "Action" and "Effect" to be applied to. AWS uses ARNs to specify resources following the sytnax - arn:partition:service:region:account-id:resource
    **Condition**: Optional element that allows control over when the permission will be effective

There can be multiple Sids per statement, each granding different levels of access

### IAM Policy Types
Two kinds available:
- Managed Policies
- In-line Policies

#### Managed Policies
These policies can be associated with Groups, Roles, or Users
- AWS Managed Polices
-- Preconfigured by AWS
-- Covers most common permissions
- Customer Managed Polices
-- Configured by you

**Creating a Customer Managed Policy**
- *Copy an AWS Managed Policy*: Copy any existing AWS Managed Policy and edit it to create a new policy
- *Policy Generator*: Create a poicy by selecting options from dropdown boxes
- *Create Your Own Policy*: Write your own poicies from scratch or paste in a JSON policy from another source

> It's a good idea to click "Validate Policy" when editing/creating policies to make sure syntax is correct

#### Inline Policies
Polices which are directly embedded into a specific User, Group, or Role. Cannot be used by multiple identities.
- Created and attached directly to IAM object
- Do not show up under Policies list as they are not publicly available for other identities
- Typically used when you dont want to run the risk of the permission being used by any other identity

**What if there are conflicting permissions assigned to the same User?**
- By default, all access is denied
- Access will only be allowed if an explicit "Allow" has been specified
- A single "Deny" will overrule any "Allow

## Multi-Factor Authentication (MFA)
Utilizes a random 6 digit number generated by an MFA device
- No additional charge for MFA
- You need your own MVA device (Google Authenticator on phone)
- It can be a virtual token

Sometimes you can use MFA to increase security when making API calls to other resources.

## Identity Federation
Allows you to access and manage AWS resources even if you don't have a user account within IAM
- Identity providers allow users to access AWS resources securely
- Can be any OpenID Connect web providers (facebook, Google, Amazon, etc)

**Benefits**: 
- Minimizes the amount of administration required within IAM
- Allows for a SSO solution

There must be a trust relationship between IdP and your AWS account. AWS supports two types of IdP:
- OpenID: Allows auth between AWS resources and any public OpenID Connect web providers (facebook, Google, Amazon, etc). IdP credentials are used to exchange an authentication token for temporary authentication credentials
- Security Assertion Markup Language (SAML) 2.0
Allows your existing Microsoft Active Directory (MS-AD) users to authenticate to your QWS resources on a SSO approach. Lets the exchange of security data,k including authentication and authorization tokens to take place between a IdP and a service provider

### Active Directory Authentication
A user within an organization requires API access to S3, EC2, and RDS

Security Token Service (STS) allows you to gain temporary security credentials for federated users via IAM

Example:
1. The user initiates a request to authenticate against the ADFS Server via a web browser using a SSO URL
2. If their authentication is successful by the AD credentials, SAML issues an assertion back to the users client requesting federated access
3. The SAML assertion is sent to the AWS STS to assume a role within IAM using the AssumeRoleWithSAML API
4. STS response to the user requesting federated access with temporary security credentials with an assumed role and associated permissions
5. The user has federated access to the necessary AWS services

### Creating an Identity Provider
**OpenID**:
- A client ID (audience) that you receive once you register your app with your IdP
- A Thumbprint to verify the certificate of your IdP
**SAML**:
- A SAML Metadata doc that you get by using the identity management software from your IdP
- This document includes information such as the issuer's name, expiration data, and security keys

## Features of IAM
- Can set specific guidelines for password rules (min length, what chars are allowed/required, prevent reuse, etc) 
- Can deactivate Security Token Service Regions (like Asia) for added security

### Credential Report
Can generate downloadable CSV with all IAM users and credentials. 
- Will only be generated once in 4 hours

### Key Management Service (KMS)
Enables you to easily manage encryption keys to secure data
- You can control how the keys can be used to encrypt your data
- If you lose or delete your keys they cannot be recovered
- You can manage your KMS Custom Master Keys (CMK) from within the IAM console
