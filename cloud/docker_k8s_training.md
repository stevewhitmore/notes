# Docker & Kubernetes: The Practical Guide [2022 Edition]

## The Basics

Course on Udemy - https://naic.udemy.com/course/docker-kubernetes-the-practical-guide

Docker is a container technology: A tool for creating and managing containers.

Container is a standardized unit of software - a package of code and dependencies to run that codce. Container always yields the exact same application and execution behavior.

### Virtual Machines vs Virtual Operating Systems

VMs can do a lot of what containers can do BUT they have way more overhead than containers. Wastes a lot of space on hard drive and tends to be slow.

Instead of emulating an entire system, containers have OS built-in emulated container support and a docker engine to spin up containers. Much more light weight (by orders of magnitude).
The container may have a small OS layer but it's still way smaller than a VM.

### Docker Tools & Building Blocks

The following are installed when Docker app is installed

- Docker Engine
- Docker Desktop (Daemon and CLI)

### Dockerfile vs Docker Compose

#### Dockerfile

A Dockerfile is a simple text file that contains the commands a user could call to assemble an image.

#### Docker Compose

- is a tool for defining and running multi-container Docker applications.
- define the services that make up your app in docker-compose.yml so they can be run together in an isolated environment.
- get an app running in one command by just running docker-compose up

## Docker Images & Containers

