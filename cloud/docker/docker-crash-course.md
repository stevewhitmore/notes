# Docker Crash Course

From Udemy's [Docker & Kubernetes: The Practical Guide (2022 Edition)](https://www.udemy.com/course/docker-kubernetes-the-practical-guide/)

- [Docker Crash Course](#docker-crash-course)
  * [The Basics](#the-basics)
    + [Virtual Machines vs Virtual Operating Systems](#virtual-machines-vs-virtual-operating-systems)
    + [Docker Tools & Building Blocks](#docker-tools---building-blocks)
    + [Dockerfile vs Docker Compose](#dockerfile-vs-docker-compose)
      - [Dockerfile](#dockerfile)
      - [Docker Compose](#docker-compose)
  * [Docker Images & Containers: The Core Building Blocks](#docker-images---containers--the-core-building-blocks)
    + [Managing Images & Containers](#managing-images---containers)
      - [Images](#images)
      - [Containers](#containers)
      - [Attached and Detached states](#attached-and-detached-states)
      - [Entering interactive mode](#entering-interactive-mode)
      - [Deleting images and containers](#deleting-images-and-containers)
      - [Copying to and from a container](#copying-to-and-from-a-container)
      - [Naming & Tagging Containers and Images](#naming---tagging-containers-and-images)
      - [Sharing Image & Containers](#sharing-image---containers)
        * [Pushing to Dockerhub](#pushing-to-dockerhub)
        * [Pulling from docker](#pulling-from-docker)
  * [Managing Data & Working with Volumes](#managing-data---working-with-volumes)
    + [Types of data](#types-of-data)
      - [Application](#application)
      - [Temporary](#temporary)
      - [Permanent](#permanent)
    + [Volumes](#volumes)
    + [Two types of external data storages](#two-types-of-external-data-storages)
    + [Bind Mounts](#bind-mounts)
      - [Summing up Anonymous/Named Volumes vs Bind Mounts](#summing-up-anonymous-named-volumes-vs-bind-mounts)
    + [Read-Only Volumes](#read-only-volumes)
    + [Managing Volumes](#managing-volumes)
    + [.dockerignore](#dockerignore)
    + [Working with Environment Variables & Files](#working-with-environment-variables---files)
    + [Using build arguments (ARG)](#using-build-arguments--arg-)
  * [Networking & Cross-Container Communication](#networking---cross-container-communication)
    + [Container Networks](#container-networks)
    + [Docker Network Drivers](#docker-network-drivers)
  * [Building Multi-Container Applications](#building-multi-container-applications)
    + [Adding Data Persistence to MongoDB with Volumes](#adding-data-persistence-to-mongodb-with-volumes)
  * [Using Docker Compose for Multi-Container Orchestration](#using-docker-compose-for-multi-container-orchestration)
  * [Working with Utility Containers & Executing Commands In Containers](#working-with-utility-containers---executing-commands-in-containers)
    + [Executing Commands](#executing-commands)

## The Basics

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

Anonymous volumes are created if your Dockerfile only specifies the path for the volume `VOLUME [ "/app/feedback" ]`. Docker sets up a folder or path on your host machine; exact location is unknown to you. These are deleted when the container is deleted, so they're not great for persisting data.

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

Internal container paths take precedence so for `-v $(pwd):/app` your local changes will overwrite the container's files, where as `-v logs:/app/logs` your local will not. If you have a situation like
you don't have `node_modules` locally but you do in the container, you can add an anonymous volume to protect the folder in the container `-v /app/node_modules`.

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

### Container Networks

Within a Docker network, all containers can commuincate with each other and IPs are automatically resolved.

They need to be manually set up (unlike volumes). `docker network create <network name>` and then you can run a container with it `docker run <options> --network <network name>`.

If more than one container are part of the same network, you can plug in the name of the container for the domain when trying to connect. Example:

Say you want to connect a node app to a mongodb instance. You can put the mongodb instance in a container and change the node code to the following:

```javascript
mongoose.connect(
  'mongodb://mongodb-instance:27017/swfavorites',
  { useNewUrlParser: true },
  (err) => {
    if (err) {
      console.log(err);
    } else {
      app.listen(3000);
    }
  }
);
```

Followed by the below commands:

```bash
docker network create favorites-net
docker run -d --name mongodb-instance --network favorites-net mongo
docker run -d -p 3000:3000 --rm --name favorites --network favorites-net favorites-node
```

> There's no need to expose ports if you're using a container network. It's only needed if you're trying to connect to the container from outside the container (like from local machine) or container network.
> This is mostly helpful for server-side microservices. If you have a backend service talking with a frontend app, you'll need to expose ports to the frontend app since it runs in a browser and you'll need to
> expose ports for the backend because the browser will make requests to it. You can use a container network AND expose ports in this case.

> When using a container network or host.docker.internal, Docker will **NOT replace your source code.** It semply detects outgoing requests and resolves the IP for such requests.

### Docker Network Drivers

Docker Networks actually support different kinds of "Drivers" which influence the behavior of the Network.

The default driver is the "bridge" driver - it provides the behavior shown in this module (i.e. Containers can find each other by name if they are in the same Network).

The driver can be set when a Network is created, simply by adding the `--driver` option.

```bash
docker network create --driver bridge my-net
```

Of course, if you want to use the "bridge" driver, you can simply omit the entire option since "bridge" is the default anyways.

Docker also supports these alternative drivers - though you will use the "bridge" driver in most cases:

- host: For standalone containers, isolation between container and host system is removed (i.e. they share localhost as a network)
- overlay: Multiple Docker daemons (i.e. Docker running on different machines) are able to connect with each other. Only works in "Swarm" mode which is a dated / almost deprecated way of connecting multiple containers
- macvlan: You can set a custom MAC address to a container - this address can then be used for communication with that container
- none: All networking is disabled.
- Third-party plugins: You can install third-party plugins which then may add all kinds of behaviors and functionalities

As mentioned, the "bridge" driver makes most sense in the vast majority of scenarios.

## Building Multi-Container Applications

### Adding Data Persistence to MongoDB with Volumes

Look up where MongoDB stores things and then created a named volume for it `docker run --name mongodb -v data:/data/db --rm -d --network goals-net mongo`.

You can add credentials by using environment variables.

The rest of this section was rehashing what was already taught above.

## Using Docker Compose for Multi-Container Orchestration

Great for managing multiple containers on the same host. You can build and run with a single command as well as stop with a single command.

Docker compose is NOT:

- A replacement for Dockerfiles for custom images
- A replacement for images or containers
- Suited for managing multiple containers on different hosts

Compose files must define services (a.k.a. containers) and also define published ports, environment variables, volumes, and networks.

*docker-compose.yaml*  

```yaml
version: "3.8" # This is the version of the docker compose specification we want to use, NOT the app.
services:
  mongodb:
    image: "mongo"  # This is a premade image from Dockerhub so no need to build
    volumes:
      - data:/app/data
    # environment:
    #   - MONGO_INITDB_ROOT_USERNAME: foo  # Can also use the syntax MONGO_INITDB_ROOT_USERNAME=foo
    #   - MONGO_INITDB_ROOT_password: password 
    env_file:
      - ./env/mongo.env  # This needs the "key=value" syntax rather than "key: value"
    # networks:  # You can specify your network, but Docker will do this automatically with compose. Specifying will add this service to both this and the auto-generated network
    #   - goals-net
  backend:
    build: ./backend  # Point to the folder where the Dockerfile is. Short form.
    # build:
    #   context: ./backend
    #   dockerfile: Dockerfile-dev  # Only needed if named something other than "Dockerfile"
    #   args:
    #     some-arg: 1
    ports:
      - "80:80"  # Can expose multiple ports per container
    volumes:
      - logs:/app/logs
      - ./backend:/app  # Relative paths allowed for bind mounts in a compose file
      - /app/node_modules
    env_file:
      - ./env/backend.env
    depends_on:
      - mongodb
  frontend:
    build: ./frontend
    ports: 
      - '3000:3000'
    volumes: 
      - ./frontend/src:/app/src
    stdin_open: true  # the "-i" in the "docker run" command
    tty: true  # the "-t" in the "docker run" command
    depends_on: 
      - backend

# If you have named volumes you need to add volumes to the top level and specify the name with no value. It's a thing Docker needs to recognize named volumes.
# You can share the volume between containers if you specify i8t for each service.
# Anonymous volumes and bind mounts dont need to be specified here.
volumes:
  data:
  logs:
```

By default the `--rm` flag applies when using `docker compoase`.

You still need to add the `-d` flag to run in detached mode: `docker-compose up -d`.

The `docker-compose down` command will NOT remove volumes. For that you need to run `docker-compose down -v`.

You can use docker-compose to just build but not run by using `docker-compose build`.

If you want docker-compose to build a fresh image you can force it with the `--build` flag. That's for when you have an existing image but maybe have had a code change since the last build. `docker-compose up --build`.

You can also force a container name through the yaml file by using `container_name`:

```yaml
...
services:
    mongodb:
        image: "mongo"
        volumes:
            - data:/data/db
        container_name: mongodb

    ...
```

This will cause the container to be named "mongodb" instead of the auto-generated name "project-name_mongodb_01".

## Working with Utility Containers & Executing Commands In Containers

A container with an environment but no application. Useful for setting up a dev environment without having to install a bunch of stuff on your host machine.
- Use a bind mount to save files from the container to the local machine. You could make a super slim node image, run `npm init`, and have the newly generated package.json file appear in your local directory.

### Executing Commands

`docker exec -it <container name> <command>`

To "ssh" into a container, simply run `docker exec -it <container name> /bin/bash`.

Using `ENTRYPOINT` is very similar to `CMD` in the Docker file. The main difference being that if you use `CMD`, you can override the default `CMD` by adding it to the end of the command `docker run -it node npm init`.
With `ENTRYPOINT` whatever you add to the end of your run command is appended to whatever's inside ENTRYPOINT.

Example:

```yaml
FROM node:14-alpine

WORKDIR /app

ENTRYPOINT [ "npm" ]
```

Then you can run whatever npm command you want with the run command: `docker run -it -v $(pwd):/app myimage init` will run `npm init` on startup.

We can do this with `docker-compose`:

```yaml
version: "3.8"
services:
    node:
        build: ./
        stdin_open: true
        tty: true
        volumes:
            - ./:/app
```

The command you'd use is `docker-compose run npm init`.

> **NOTE**: Containers will not be removed if you use `run`. You still need that `--rm` flag: `docker-compose run --rm npm init`.


