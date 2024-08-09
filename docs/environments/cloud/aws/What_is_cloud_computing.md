# What is Cloud Computing?

"A remote virtual pool of on-demand shared resources offering Compute, Storage, and Network services that can be rapidly deployed at scale"

## Understanding Virtualization

Allows for multiple VMs in one phsyical server. No crossover- VMs are unaware of eachother.

- Hypervisor sits between VMs and physical resources and creates a "pool" of them for the VMs to use as needed.
- Requests to the hardware from the VMs go through the Hypervisor.

### Benefits of Virtualization

- Reduced capital expenditure
  - Less hardware needed
- Reduced operational costs
- Less space required
- Optimization of resources

"Instance" is often referring to a specific VM.

## Compute

- The 'brains' to process your workload
- Virutalized hosts
- Computational ability to process requests
- CPU/RAM
-- Classic environment comparison: Physical Server

## Storage

- Acts as non-volitile/secondary storage
- Save your data across a shared environment
- Logically attach to instances
- Separate object for backup/DR
-- Classic environment comparision: Server disk storage/NAS (Network Attached Storage)/SAN (Storage Area Network)

## Network

- Provide connectivity for all resources to communicate with each other
- IP Subnets (Network segmentation)
- Route Tables
- Network Access Controll Lists (Security)
- Network Address Translation
-- Classic environment comparision: Routers/Switches/Firewalls

