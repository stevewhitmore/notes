# Kubernetes Crash Course

From Udemy's [Docker & Kubernetes: The Practical Guide (2022 Edition)](https://www.udemy.com/course/docker-kubernetes-the-practical-guide/)

[[_TOC_]]

## The Basics

Manual deployment of containers is hard to maintain, error-prone, and annoying (even beyond security and configuration concerns!)

Other issues:

- Containers might crash/go down and need to be replaced.
- We might need more container instances upon traffic spikes
- Incoming traffic should be distrubted equally

AWS ECS can help. It provides the following:

- Container health checks and automatic re-deployment
- Autoscaling
- Load balancer

The problem is, this locks us into a specific service by a specific cloud provider. If you want to migrate to something else you need to learn about the specifics, services, and config options of another provider.

This is where Kubernetes comes to the rescue.

### What exactly is Kubernetes?

It's an open source (and de-facto standard) for orchestrating container deployments. It helps with:

- Automatic deployment
- Scaling and load balancing
- Management

**Also known as K8s**. It's derived by replacing the eight letters of “ubernete” with the digit 8 because less typing.

You can set up the Kubernetes configs via a file, pass that to some cloud provider or even your own datacenter, and you're good to go.

> You can also include cloud-provider-specific settings in your Kubernetes configs which makes it all the more flexible/powerful

You define the end result in your Kubernetes configs and the cloud provider will take care of it for you.

### What it IS and IS NOT

- IT'S NOT a cloud service provider
    - IT IS an open source project
- IT'S NOT a service by a cloud service provider
    - IT IS something that can be used by any provider
- IT'S NOT just an application you run on some machine
    - IT IS a collection of concepts and tools
- IT'S NOT an alternative to Docker
    - IT IS something that works with Docker containers
- IT'S NOT a paid service
    - It's free and open source


One way to think of it: Kubernetes is like Docker-compose for multiple machines.

### Core Concepts and Architecture

**Pod:** The smallest possible unit in the k8s world which you can create in a config file. A pod simply holds one more more containers.

Pods run in Worker Nodes.

**Worker Node:** run the containers of your application. Nodes are your machines/virtual instances. An EC2 instance could be a worker node.

**Proxy/Config:** Another tool k8s sets up for you on a Workker Node to control the network traffic of the pods on that Worker Node.

You'll typically have multiple worker nodes for larger applicaitons because you might need more than one server to have enough compute power to run your application.

> Multiple pods can be created and removed to scale your app

**Master Node:** Has a Control Plane that orchestrates all the Worker Nodes. Does the heavy lifting with scaling and replacing pods.

The Control Plane is a collection of tools/componsnets to help with managing the worker nodes.

All of this together (Master Node and Worker Nodes) forms a Cluster. The Master Node sends instructions to the CLoud Provider API about what resources are needed to support the Cluster network.

### Your work vs k8s work

What you need to do / set up (i.e. what k8s requires):

- Create the Cluster and the Node Instances (Worker and Master Nodes)
- Set up API server, kubelet and other k8s services/software on Nodes
- Create other (cloud) provider resources that might be needed (e.g. Load Balancer, Filesystems)

What k8s will do:

- Create your objects (e.g. Pods) and manage them
- Monitor Pods and re-create them, scale Pods, etc
- Kuberenetes utilizes the provided (cloud) resource to apply your configuration/goals

It's important to remember that **Kubernetes does not create resources for you**. You need to do this first and k8s will utilize it. 

### A closer look at Worker Nodes

Think of them as a computer/machine/virtual instance. It's managed by the Master Node.

The Pod inside hosts one or more application containers and their resources (volumes, IP, run config). They are created and managed by Kubernetes.

You would have Docker, kubelet, and kube-proxy installed on the Worker Node. 

> A kubelete handles communication between Master and Worker Nodes

> A kube-proxy us used to manage Node and Pod network communication (traffic to and from the Pod and Worker Node)

### A closer look at the Master Node

The most important software in the Master Node is the **API Server**. This is the counterpart to the kubletes in the Worker Node.

**Scheduler:** Watches for new Pods, selects Workerk Nodes to run them on.

**Kube-Controller-Manager:** Watches and controsl Worker Nodes, correct number of Pods, and more.**Scheduler:** Watches for new Pods, selects Workerk Nodes to run them on.

**Cloud-Controller-Manager:** Like Kube-Controller-Manager BUT for a specific Cloud Provider: Knows how to interact with Cloud Provider resources.

### Important Terms and Concepts

- **Cluster:** A set of Node machines which are running the containerized application (Worker Nodes) or control other Nodes (Master Node)
- **Nodes:** Physical or virtual machine with a certain hardware capacity which hosts one or multiple Pods and communicates with the Cluster
    - **Master Node:** Cluster Control Plane, managing the Pods across Worder Nodes
    - **Worker Node:** Hosts Pods, running app container (+ resources)
- **Pods:** Hold the actual running app containers and their required resources (e.g. volumes)
- **Containers:** Normal (Docker) containers
- **Services:** A logical set (group) of Pods with unique, Pod and Container independent IP addresses

## Deep Dive Into Core Concepts

### Installation

You need two tools to get started: kubectl and a cluster.

**kubectl:** A tool for sending instructions to the cluster (e.g. a new deployment). kubectl sends commands to the Master Node which then passes them on to the Worker Nodes.

You can use a tool called **Minikube** to get things going locally and creating clusters.

Install the tools by following the docs:

- [kubectl](https://kubernetes.io/docs/tasks/tools/)
- [minikube](https://minikube.sigs.k8s.io/docs/start/)

Once both are installed you can verify with `kubectl cluster-info` and `minikube status`. 

> You can use the Docker driver to create a cluster and this is the default for Linux machines. Run `docker ps` and you'll see a minicube container. 

Minikube also provides a web interface that runs on your localhost. Run `minikube dashboard` and a new tab will open in your default browser with info about your new cluster.

### Understanding Kubernetes Objects

K8s works with and sees all resources as Objects. Pods, Deployments, Services, Volumes, etc are all objects.

Objects can be created in two ways: **Imperatively** or **Declaratively**.

#### The Pod Object

The smallest "unit" k8s interacts with. They act as a tiny wrapper for containers.

- Contains and runs one more multiple containers though it's most common for there to be one container per Pod.
- Contain shared resources (e.g. volumes) for all Pod containers.
- Has a cluster-internal IP by default
	- Containers inside a Pod can communicate via localhost

Pods are designed to be ephemeral: K8s will start, stop, and replace them as needed.

For Pods to be managed for you, you need a "Controller" (e.g. a "Deployment")

#### The Deployment Object

Controls one or more Pods

- You set a desired state, K8s then changes the actual state
	- Define which Pods and containers to run and the number of instances
- Deployments can be paused, deleted, and rolled back
- Deployments can be scaled dynamically and automatically

Deployments manage a Pod for you, you can also create multiple Deployments.

> To clarify: One kind of Pod (e.g. a Pod with two specific containers). Multiple instances of that Pod are possible.

You typically don't directly control Pods, instead you use Deployments to set up the desired end state.
