# CLI Intro

After creating an EC2 instance you'll be prompted to download a set of keys. It will come as a PEM file.

> Make sure to update the permissions of this file to 400. Anything else and the AWS CLI will reject your attempt at an ssh connection

`ssh -i ~/Downloads/keys.pem ec2-user@54.185.196.255`

Once logged in type `aws configure` to set the access ID and secrets along with the default region and output format.