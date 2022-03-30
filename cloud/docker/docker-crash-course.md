# Docker Crash Course

From Udemy's [Docker & Kubernetes: The Practical Guide (2022 Edition)](https://www.udemy.com/course/docker-kubernetes-the-practical-guide/)

- [The Basics](#the-basics)
  * [Virtual Machines vs Virtual Operating Systems](#virtual-machines-vs-virtual-operating-systems)
  * [Docker Tools & Building Blocks](#docker-tools--building-blocks)
  * [Dockerfile vs Docker Compose](#dockerfile-vs-docker-compose)
    + [Dockerfile](#dockerfile)
    + [Docker Compose](#docker-compose)
- [Docker Images & Containers](#docker-images--containers)
  * [Managing Images & Containers](#managing-images--containers)
    + [Images](#images)
    + [Containers](#containers)
    + [Attached and Detached states](#attached-and-detached-states)
    + [Entering interactive mode](#entering-interactive-mode)
    + [Deleting images and containers](#deleting-images-and-containers)
    + [Copying to and from a container](#copying-to-and-from-a-container)
    + [Naming & Tagging Containers and Images](#naming--tagging-containers-and-images)
    + [Sharing Image & Containers](#sharing-image--containers)
      - [Pushing to Dockerhub](#pushing-to-dockerhub)
      - [Pulling from docker](#pulling-from-docker)
- [Managing Data & Working with Volumes](#managing-data--working-with-volumes)
  * [Types of data](#types-of-data)
    + [Application](#application)
    + [Temporary](#temporary)
    + [Permanent](#permanent)
  * [Volumes](#volumes)
  * [Two types of external data storages](#two-types-of-external-data-storages)
  * [Bind Mounts](#bind-mounts)
    + [Summing up Anonymous/Named Volumes vs Bind Mounts](#summing-up-anonymous-named-volumes-vs-bind-mounts)
  * [Read-Only Volumes](#read-only-volumes)
  * [Managing Volumes](#managing-volumes)
  * [.dockerignore](#dockerignore)
  * [Working with Environment Variables & Files](#working-with-environment-variables--files)
  * [Using build arguments (ARG)](#using-build-arguments--arg-)
- [Networking & Cross-Container Communication](#networking--cross-container-communication)

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

## Docker Images & Containers: The Core Building Blocks

*Dockerfile in a nodejs app*

```yaml
FROM node

# Context is set so all work done in this file will happen here
WORKDIR /app

# Can also be "COPY . ." since the WORKDIR was set
COPY . /app

RUN npm install

# This instruction doesn't do anything but it's best practice to add it for readability
# You need to expose the port via the `docker run` command:
#   1. `docker build` and point to dir with Dockerfile. Output has image image name that'll be a sha.
#   2. `docker run -p <local port>:<container port> <image name> e.g. `docker run -p 3000:80 d31c8fdfbcd8`
EXPOSE 80

#RUN node server.js
CMD ["node", "server.js"]

# The difference between RUN and CMD is the latter will only happen once a container is run based on the image.
# If you don't specify a CMD, the CMD of the base image will be executed. With no base image and no CMD, you'd get an error.

# - List running containers with `docker ps`
# - Show all docker containers with `docker ps -a`
# - Stop a running container with `docker stop <container name>`
```

### Managing Images & Containers

#### Images

Is the "blueprints" of a Docker container. It builds in layers so it may have a layer for OS, dependencies, and commands.

- Can be tagged (named) `-t, docker tag...`
- Can be listed `docker images`
- Can be analyzed `docker image inspect`
- Can be removed `docker rmi, docker prune`

#### Containers

- Can be named `--name`
- Can be configured in detail `see --help`
- Can be listed `docker ps`
- Can be removed `docker rm`

#### Attached and Detached states

The `docker run` command starts a new container based on the image in the foreground. To start up a stopped container, you simply run `docker start <container id or name>`. However, the container will be detached in this state and running in the background. You can start a stopped container in attached mode by using the `-d` flag: `docker start -a <container id or name>`.

You can run a container as detached right away by running `docker run -p <local port>:<container port> -d <image name>`.

You'll miss the logs if you detatch and reattach using the above methods. To reattach AND see the logs you can use
`docker logs -f <container id or name>` to "follow" the past and future logs. This essentially is the same as reattaching.

#### Entering interactive mode

Some projects require user interaction. You can start a stopped container in interactive mode by using the `-i` flag: `docker start -i <container id or name>`

#### Deleting images and containers

We need to stop the container before we can remove it; we can't remove running containers. Run `docker ps -a` to get all existing container names. Run `docker rm <container id or name>` with each id/name seperated by a space. Alternatively, you can run `docker container prune` to get rid of all stopped containers.

Listing images is similar to listing containers. You use `docker images` instead of `docker ps`. When you delete an image, you run `docker rmi <image id>` instead of `docker rm <container id or name>`. It'll output the layers it deleted when you delete an image.

> You can only remove images that are not being used by a container. That includes containers that are stopped.

Remove all non-tagged images not being used with `docker image prune`. You can remove unused tagged images with `docker image prune -a`.

> It's not uncommon to start a container you intend to delete after it stops running. A shortcut for this would be
> `docker run -p 3000:80 -d --rm <image id>` which translates to "docker run on exposed local port 3000 in detached mode and remove container when it's stopped"

Once built upon, images become read-only because it contains layers that the container lays on top of. Many containers can be made from a single image. A container is just one layer of the whole stack.

#### Copying to and from a container

`docker cp <to> <from>` which looks like the following in practice: `docker cp my_folder container_name:/destination`.

Works both ways just like with `scp` command.

This isn't super useful for updating code since the container will need to stop/start again but it could be very useful for retrieving things like log files from a container to inspect locally.

#### Naming & Tagging Containers and Images

Name a container with the `--name` flag followed by the name you want and the image id: `docker run -p 3000:80 -d --rm --name foobar d31c8fdfbcd8`. 

Images follow the `name:tag` format. The name defines a group of possible more specialized images whereas the tag defines a specialized image within a group of images.

To name and tag an image, run `docker build -t <name>:<tag> .`.

#### Sharing Image & Containers

You dont share containers, you share images. You can do this 1 of 2 ways:

1. Share the Dockerfile. You'll need any accompanying files and to run `docker build .`.
2. Share the build image. You can directly run a container based on it. Nothing else needed. You do this via Dockerhub or a private repository.

##### Pushing to Dockerhub

1. In Dockerhub click on "Repositories"
2. In Dockerhub click "Create Repository". Follow prompts.
3. Build image or rename existing one locally: `docker tag oldname:tag yourusername/reponame`. Adding `:tag` after `yourusername/reponame` is optional.
4. Run `docker login`. Log in
5. Run `docker push yourusername/reponame`.

##### Pulling from docker

If it's public you can simply run `docker pull yourusername/reponame`. 

## Managing Data & Working with Volumes

### Types of data

There are 3 kinds: Application, Temporary, and Permanent

#### Application

Code + environment (everything we've covered up until now)

- Written & provided by developer
- Added to image and container in build phase
- Fixed: can't be changed once image is built
- Read-only, hense stored in images

#### Temporary

e.g. entered user input

- Fetched/produced in running container
- Stored in memory or temporary files
- Dynamic and changing but cleared regularly
- Read + write, temporary, stored in containers

#### Permanent

e.g. user accounts

- Fetched/produced in running container
- Stored in files or a database
- Must not be lost if container stops/restarts
- Read + write, permanent, stored with containers & volumes

Files written to during container run is lost when containers are removed, but not if container is simply stopped and started again. This is where volumes come in.

### Volumes

Volumes are folders on our host machine hard drive which are mounted (mapped or made available) into containers. It acts as a 2-way sync between container and host.

Volumes persist if a container shuts down. If a container (re)starts and mounts a volume, any data inside of that volume is available in the container.

### Two types of external data storages

- Volumes (managed by Docker)
    - Anonymous
    - Named
- Bind Mounts (managed by you)

Managed via `docker volume` commands.

Anonymous volumes are created if your Dockerfile only specifies the path for the volume `VOLUME [ "/app/feedback" ]`. Docker sets up a folder or path on your host machine; exact location is unknown to you. These are deleted when the container stops, so they're not great for persisting data.

Named volumes are great for data which should be persistent but you don't need to edit directly. They remain after a container is removed.

Running a container with `-v local/path:container/path` is the trick. As long as a new container points to the same named volume the data will persist. 

`docker run -d -p 3000:80 --rm --name feedback-app -v feedback:/app/feedback feedback-node:volumes`

### Bind Mounts

Similar to Volumes but you define a folder/path on your host machine. Bind Mounts are perfect for development because instead of copying a snapshot of the code it syncs your changes to the code without you needing to stop, build again, run a new container based off that new build. Named Volumes are good for persisting data but are no good for ongoing development because you have no idea where the local copy is stored.

You can set up Bind Mounts by adding another `-v` flag and point to the absolute path for your local directory:

`docker -run -d -p 3000:80 --rm --name feedback-app -v feedback:/app/feedback -v "/full/path/to/local/project:/app" feedback-node:volumes`

> You don't have to write out the full path. Use `-v $(pwd):/app` for sanity

The syntax of the second `-v` flag means you're copying all contents of that folder to the container's `/app` folder.

This renders the following steps in the Dockerfile moot:

```yaml
COPY package.json .

RUN npm instal

COPY . .
```

This is because the second `-v` flag overwrites everything in the container's `/app` folder.

#### Summing up Anonymous/Named Volumes vs Bind Mounts

```bash
docker run -v /app/data ... # Anonymous Volume
docker run -v data:/app/data ... # Named Volume
docker run -v /path/to/code:/app/code ... # Bind Mount
```

Anonymous Volumes
- Created specifically for a single container
- Survives container shutdown/restart unless --rm is used
- Cannot be shared across containers
- Since it's anonymous, it can't be re-used (even on same image)

Named Volumes
- Created in general - not tied to any specific container
- Survives container shutdown/restart - removal via Docker CLI
- Can be shared across congtainers
- Can be reused for same container (across restarts)

Bind Mounts
- Location on host file syustem, not tied to any specific container
- Survives container shutdown/restart - removal on host fs
- Can be shared across containers
- Can be reused for same container (across restarts)

### Read-Only Volumes

We can enforce read-only on a container level by adding `:ro` to the end of the container path. We'll still be able to write from the local fs but not from the container:

```bash
docker run -d --rm -p 3000:80 --name feedback-app -v feedback:/app/feedback -v "$(pwd):/app:ro" -v /app/node_modules -v feedback-node:volumes
```

### Managing Volumes

View the volumes created with `docker volume ls`. This shows the volumes but NOT the bind mounts, because bind mounts are managed by the user, not Docker.

The volumes are created automatically with the `-v` flag, so it's not usually necessary to manually create them with `docker volume create`.

Volumes in use cannot be removed. You need to stop the container using the volume before it can be removed. You can get rid of unused volumes with `docker volume prune`.

If a volume is removed, the data in it will be lost. Recreating one with the same name will not bring back the data.

### .dockerignore

Acts like `.gitignore` but for docker. In general, you want to add anything which isn't required by your application to execute correctly (such as `Dockerfile` or `.git`).

### Working with Environment Variables & Files

Docker supports build-time ARGuments and runtime ENVironment variables

ARG:
- Available inside of Dockerfile, NOT accessible in CMD or any application code
- Set an iamge build (docker build) via --build-arg

ENV:
- Available inside of Dockerfile and in application code
- Set via ENV in Dockerfile or via `--env` or `-e` on docker run

```yaml
ENV PORT 80 # This will be accessible from within the application

EXPOSE $PORT
```

You can also point to a file to define environment variables

*.env*

```
PORT=8000
```

`docker run -d -p 3000:8000 --env-file ./.env ...`

This is advantageous because it allows you to run the same command over and over and you only need to update a file if vars need to change.

> One important note about environment variables and security: Depending on which kind of data you're storing in your environment variables, you might not want to include the secure data directly in your Dockerfile.
> 
> Instead, go for a separate environment variables file which is then only used at runtime (i.e. when you run your container with docker run).
>
> Otherwise, the values are "baked into the image" and everyone can read these values via docker history <image>.
> 
> For some values, this might not matter but for credentials, private keys etc. you definitely want to avoid that!
>
> If you use a separate file, the values are not part of the image since you point at that file when you run docker run. But make sure you don't commit that separate file as part of your source control repository, if you're using source control.

### Using build arguments (ARG)

```yaml
ARG DEFAULT_PORT=80

ENV PORT $DEFAULT_PORT

EXPOSE $PORT
```

## Networking & Cross-Container Communication

Containers can commiunicate with the www out of the box.

Instead of using `localhost` when talking to another service on the same machine, use `host.docker.internal` if a domain is needed. This will be recognized by Docker as a service on the host machine.

Point to the IP address of the other container. To find out the IP address run `docker container inspect <container name>`. You will also need to point to the port which can be found in the same output as the inspect command or `docker ps -a` with the port being listed next to the name of the container.


