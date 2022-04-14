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

> A kubelet handles communication between Master and Worker Nodes

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

Once both are installed you can verify with `kubectl cluster-info` and `minikube status`. The two tools work hand in hand so you can just run `kubectl` commands and Minikube will automatically be utilized.

> You can use the Docker driver as the hypervisor to create a cluster and this is the default for Linux machines. Run `docker ps` and you'll see a minicube container. 

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

### Deployment with the Imperative Approach

First you need to build your Docker image. Then you use `kubectl` to deploy your built image to your cluster. 

`kubectl create deployment app-name --image=some-image-name`

This isn't quite right though. It'll show a success message after creation but it'll show that the deployment failed when you check on the deployments in your cluster with `kubectl get deployments`


```bash
$ kubectl get deployments
NAME        READY   UP-TO-DATE   AVAILABLE   AGE
first-app   0/1     1            0           6s
```

Check on the Pods for a more precise picture:

```bash
$ kubectl get pods
NAME                         READY   STATUS             RESTARTS   AGE
first-app-7cbbc8669d-tg268   0/1     ImagePullBackOff   0          2m57s
```

By default Minikube looks for images in an image registery so we'll need to pull one down.

> It is possible to use a locally built image and that'll be covered a bit later

Delete the Pod, create a new repository in Dockerhub (or whereever), then tag the Docker image to be pushed up.

```bash
$ kubectl delete deployment first-app                                                                                         
deployment.apps "first-app" deleted
$ docker tag kub-first-app stevewhitmore/kub-first-app
$ docker push stevewhitmore/kub-first-app
```

Now it'll actually deploy successfully:

```bash
$ kubectl create deployment first-app --image=stevewhitmore/kub-first-app
deployment.apps/first-app created
$ kubectl get deployments
NAME        READY   UP-TO-DATE   AVAILABLE   AGE
first-app   1/1     1            1           37s
$ kubectl get pods
NAME                         READY   STATUS    RESTARTS   AGE
first-app-8575b449bf-vr48r   1/1     Running   0          76s
```

Now would be a good time to run `minkube dashboard` to monitor your new cluster.

#### kubectl: Behind the Scenes

`kubectl create deployment --image ...` is sent to the Master Node (Control Plane) where the Scheduler analyzes currently running Pods and finds the best Node for the new Pod(s). It sends it to the Worker Node where the kubelet manages the Pod and Containers. 

#### The Service Object

Exposes Pods to the Cluster or externally.

- Pods have an internal IP address by default - it changes whena  Pod is replaced
	- Finding Pods is hard if the IP changes all the time
- Services group Pods with a shared IP
- Services can allow external access to Pods
	- The default (internal only) can be overwritten

Without Services, Pods are very hard to reach and communication is difficult.

Reaching a Pod from outside a Cluster is not possible at all wihtout Services.

#### Exposing a Deployment with a Service

You can expose a deployment with the following command: `kubectl expose deployment app-name --port=8080`. You can also pass in a `--type` flag which has the default value `ClusterIP`. Other choices would be `NodePort` and `LoadBalancer`.

> LoadBalancer is the most common for external access

You'll see 2 services when you check. One was created by K8s with the default ClusterIP type. The other is the one we made:

```bash
$ kubectl get services
NAME         TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
first-app    LoadBalancer   10.103.106.200   <pending>     8080:32187/TCP   13s
kubernetes   ClusterIP      10.96.0.1        <none>        443/TCP          4h
```

External IP would have a different value if this were deployed somewhere remote like AWS. Since we're using Minikube it has the `<pending>` value.

An extra command is needed since we're using Minikube locally to expose a port to a custom local IP address: `minikube service first-app`. If no output is automatically showed you can run `minikube service list` to get the custom address (e.g. http://192.168.49.2:32187)

#### Restarting Containers

If a container crashes k8s will automatically restart it. We can see this with the example app we deployed `first-app` which returns an exit code 1 if the endpoint "error" is hit:

```bash
$ kubectl get pods
NAME                         READY   STATUS    RESTARTS   AGE
first-app-8575b449bf-vr48r   1/1     Running   0          110m
$ curl http://192.168.49.2:32187/error
curl: (52) Empty reply from server
$ kubectl get pods
NAME                         READY   STATUS    RESTARTS     AGE
first-app-8575b449bf-vr48r   1/1     Running   1 (5s ago)   111m
```

Each time it crashes there will be a longer delay before a restart to avoid infinite loops in case there is a container that always crashes for some reason. Saves resources that way. 

#### Scaling in Action

Suppose we don't have auto-scaling set up. We can manually do so by running something like `kubectl scale deployment/first-app --replicas=3`. A replica is simply an instance of a Pod/Container. 3 replicas means that the same Pod/Container is running 3 times.

```bash
$ kubectl scale deployment/first-app --replicas=3
deployment.apps/first-app scaled
$ kubectl get pods
NAME                         READY   STATUS    RESTARTS      AGE
first-app-8575b449bf-c8cmv   1/1     Running   0             4s
first-app-8575b449bf-qh7fq   1/1     Running   0             4s
first-app-8575b449bf-vr48r   1/1     Running   2 (11m ago)   124m
```

This is very useful because if one crashes the other 2 will keep going while the crashed one restarts.

#### Updating Deployments

If you have a change to some code you'll of course rebuild the image, push it up, then run 
the `set` comand.

> New images are only pulled if there is a new tag

```bash
$ docker build -t stevewhitmore/kub-first-app:2 .
Sending build context to Docker daemon   5.12kB
Step 1/7 : FROM node:14-alpine
 ---> a310e5ff0582
Step 2/7 : WORKDIR /app
 ---> Using cache
 ---> e75867f2c578
Step 3/7 : COPY package.json .
 ---> Using cache
 ---> e61d6b9449ce
Step 4/7 : RUN npm install
 ---> Using cache
 ---> bd7bf69b3e48
Step 5/7 : COPY . .
 ---> Using cache
 ---> 258e32889b80
Step 6/7 : EXPOSE 8080
 ---> Using cache
 ---> f550f81c3a4f
Step 7/7 : CMD [ "node", "app.js" ]
 ---> Using cache
 ---> 0c57ed032282
Successfully built 0c57ed032282
Successfully tagged stevewhitmore/kub-first-app:2
$ docker push stevewhitmore/kub-first-app:2
The push refers to repository [docker.io/stevewhitmore/kub-first-app]
c31229021370: Layer already exists 
52968ea7d37a: Layer already exists 
21a603489ada: Layer already exists 
d496fe641acc: Layer already exists 
05accea593f7: Layer already exists 
542676b737d2: Layer already exists 
27d39fe7f7fa: Layer already exists 
a1c01e366b99: Layer already exists 
2: digest: sha256:985609b9873e5af5f46e0878856f36db25752a7b23a61def1610ab486a86d6c3 size: 1989
$ kubectl set image deployment/first-app kub-first-app=stevewhitmore/kub-first-app:2
deployment.apps/first-app image updated
```

#### Deployment Rollbacks & History

If a Pod fails to start for some reason you can roll it back by running `kubectl rollout undo deployment/first-app`. This will undo the last Pod deployed.

You can see the rollout history with `kubectl rollout history deployment/first-app`. To see specifics about a previous rollout you can use the `--revision` flag: `kubectl rollout history deployment/first-app --revision=1`

You can go back to a specific rollout by running `kubectl rollout undo deployment/first-app --to-revision=1`

### Imperative vs Declarative Deployments

Like with Docker you can use a yaml file to make it easier to control things. You create a resource definition file to make working with your cluster easier.

**Imperative:**

- You use commands like `kubectl create deployment ...`
- Individual commands are executed to trigger certain Kubernetes actions
- Comparable to using `docker run` only

**Declarative:**

- You use commands like `kubectl apply -f config.yaml`
- A config file is defined and applied to change the desired state
- Comparable to use Docker Compose with compose files

### Creating a Deployment Configuration File (Declarative Approach)

First, make sure there are no Deployments, Pods, or Services (outside of the default ClusterIP one)

```bash
$ kubectl delete service first-app
service "first-app" deleted
$ kubectl delete deployment first-app
deployment.apps "first-app" deleted
$ kubectl get deployments
No resources found in default namespace.
$ kubectl get pods
No resources found in default namespace.
$ kubectl get services
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   100m
```

Next, create a yaml file. It can be called anything. There is a specific syntax you need to be conscious of. Below is the bare minimum needed to create a Deployment with one Pod with one Container:

```yaml
apiVersion: apps/v1  # You can find versions in the docs. Be careful about pointing to the latest version for obvious reasons.
kind: Deployment  # You need to specify what kind of resource you're defining. It could also be Service or Job or something like that.
metadata:
  name: second-app-deployment  # This is the equivalent of `kubectl create deployment app-name ...`
spec:
  replicas: 1  # Defaults to 1. 0 is also a possibility if you don't want any Pods right away.
  selector:  # This is essential for declaritively deployed Pods. A Deployment continuously watches the Pods out there and sees which ones it should control. It selects these pods with the selector. This is true for all K8s resources. The "matchLabels" below are for the Deployment to know which labels to match against. These labels are defined below under "template" for the Pods. If other labels are below "template" but not also defined under "matchLabels" then they will not be controlled by this Deployment.
    matchLabels:
      app: second-app
      tier: backend
  template:  # This is where we define the Pod that should be created. You don't use "kind" here because a template of a Deployment will always be a Pod. Pod templates can only have 2 next level keys: metadata and spec.
    metadata:  # We're creating a new Object here so we need new metadata.
      labels:  # We can put "name" here instead but we chose label
        app: second-app  # This key can be "deployment" or "depl" instead.
        tier: backend
    spec: 
      containers:  #  You can have multiple Containers per Pod. Each defined below with a dash.
        - name: second-node  # Arbitrary name of app
          image: stevewhitmore/kub-first-app:3
        # - name: ...
        #   image: ...
```

Execute this with `kubectl apply -f=/path/to/file.yaml`.

### Creating a Service Declaratively

You can have multiple config files. In our case we have a `deployment.yaml` and a `service.yaml` which is defined below.

```yaml
apiVersion: v1  # This is just "v1" since it comes from the core package (unlike the one in "apps" like we used for deployments.yaml)
kind: Service
metadata:
  name: backend
spec:
  selector:  # This is a bit different than for Deployment objects because this API is a bit older. The labels are defined directly instead of using "matchLabel" like we do with `deployment.yaml`.
    app: second-app
  ports:  # We use the below instead of `kubectl expose deployment first-app --port=... --type=LoadBalancer`
    - protocol: 'TCP'  # Defaults to "TCP"
      port: 80  # The port within the container
      targetPort: 8080  # The port on the host machine
    # - protocol: 'TCP'
    #   port: 443
    #   targetPort: 443
  type: LoadBalancer
```

Deploy the Pod and expose it with the service by running the following: `kubectl apply -f=deployment.yaml -f=service.yaml`. Find the service name with `kubectl get services` and then the local IP with `minikube service (service name)`, followed by `minikube list services`:

```bash
$ kubectl apply -f=deployment.yaml -f=service.yaml
deployment.apps/second-app-deployment unchanged
service/backend created
$ kubectl get services
NAME         TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)        AGE
backend      LoadBalancer   10.96.108.148   <pending>     80:31665/TCP   14s
kubernetes   ClusterIP      10.96.0.1       <none>        443/TCP        3h8m
$ minikube service backend
$ minikube service list
|----------------------|---------------------------|--------------|---------------------------|
|      NAMESPACE       |           NAME            | TARGET PORT  |            URL            |
|----------------------|---------------------------|--------------|---------------------------|
| default              | backend                   |           80 | http://192.168.49.2:31665 |
| default              | kubernetes                | No node port |
| kube-system          | kube-dns                  | No node port |
| kubernetes-dashboard | dashboard-metrics-scraper | No node port |
| kubernetes-dashboard | kubernetes-dashboard      | No node port |
|----------------------|---------------------------|--------------|---------------------------|
```

