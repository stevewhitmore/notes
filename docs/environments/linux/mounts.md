---
title: Mounts
tags:
  - Linux
  - Bash
---
# Mounts

## Mounting External Drives

1. **Identify the External Hard Drive**:
   - List all connected drives to identify the external hard drive.
   ```bash
   lsblk
   ```
   - The output will show a list of block devices. Look for a device that matches the size of your external hard drive, typically named something like `/dev/sdb` or `/dev/sdc`.

2. **Mount the External Hard Drive**:
   - Create a mount point (directory) where you want to access the external hard drive.
   ```bash
   sudo mkdir /mnt/external_drive
   ```
   - Mount the external hard drive to the mount point.
   ```bash
   sudo mount /dev/sdX1 /mnt/external_drive
   ```
   Replace `/dev/sdX1` with the actual device name identified in step 3 (e.g., `/dev/sdb1`).

3. **Access the External Hard Drive**:
   - Navigate to the mount point to access the files on the external hard drive.
   ```bash
   cd /mnt/external_drive
   ls
   ```

4. **Unmount the External Hard Drive (when done)**:
   - Unmount the drive when you are finished to safely remove it.
   ```bash
   sudo umount /mnt/external_drive
   ```

#### Example Commands

```bash
ssh user@whitmore_tower_ip
lsblk
sudo mkdir /mnt/external_drive
sudo mount /dev/sdb1 /mnt/external_drive
cd /mnt/external_drive
ls
sudo umount /mnt/external_drive
```

#### Summary
- Use `lsblk` to identify the external hard drive.
- Create a mount point using `mkdir`.
- Mount the drive with `mount`.
- Access the files and unmount when done with `umount`.

## Reformat a USB

1. Identify the volume 

```bash
$ df -h
Filesystem      Size  Used Avail Use% Mounted on
udev            3.4G     0  3.4G   0% /dev
tmpfs           695M  1.7M  693M   1% /run
/dev/nvme0n1p2  234G   19G  203G   9% /
tmpfs           3.4G  131M  3.3G   4% /dev/shm
tmpfs           5.0M  4.0K  5.0M   1% /run/lock
tmpfs           3.4G     0  3.4G   0% /sys/fs/cgroup
/dev/nvme0n1p1  511M  7.8M  504M   2% /boot/efi
tmpfs           695M   32K  694M   1% /run/user/1000
/dev/sda1       1.6G  1.6G     0 100% /media/swhitmore/some-usb-name
```

2. Make sure it's not mounted

```bash
sudo umount /dev/sda1
```

3. Format the filesystem (pick one of the following)

```bash
sudo mkfs.vfat /dev/sda1 # for vFAT filesystem
sudo mkfs.ntfs /dev/sda1 # for NTFS filesystem
sudo mkfs.ext4 /dev/sda1 # for EXT4 filesystem
```

## Make bootable USB

1. Identify the volume 

```bash
$ df -h
Filesystem      Size  Used Avail Use% Mounted on
udev            3.4G     0  3.4G   0% /dev
tmpfs           695M  1.7M  693M   1% /run
/dev/nvme0n1p2  234G   19G  203G   9% /
tmpfs           3.4G  131M  3.3G   4% /dev/shm
tmpfs           5.0M  4.0K  5.0M   1% /run/lock
tmpfs           3.4G     0  3.4G   0% /sys/fs/cgroup
/dev/nvme0n1p1  511M  7.8M  504M   2% /boot/efi
tmpfs           695M   32K  694M   1% /run/user/1000
/dev/sda1       1.6G  1.6G     0 100% /media/swhitmore/some-usb-name
```

2. Run the following command, replacing /dev/sdx with your drive, e.g. /dev/sda. (Do **not** append a partition number, so do **not** use something like /dev/sda**1**) 

 ```bash
 $ dd bs=4M if=path/to/archlinux.iso of=/dev/sdx status=progress oflag=sync
 ```

## Bluetooth Battery

There's no simple way to check the battery life of connected Bluetooth headphones in Fedora 34 Workstation for some reason. This snippet combines the useful Python script with a few commands into a single command to check the battery level of my Cowin E7 headphones. The MAC address will be exposed but I don't care much about that. What are those black hats going to do, hijack my headphones? Probably not?

--

[Bluetooth Headset Battery Level](https://github.com/TheWeirdDev/Bluetooth_Headset_Battery_Level) by [TheWeirdDev](https://github.com/TheWeirdDev). Other parts added by me.

```bash
# Assumes `bluetooth_battery.py` is chmod'd with 755 (+x) permissions
# Change "mac_address" and file path as needed

function bluetoothbattery {
    local mac_address="FC:58:FA:AC:5B:DF"

    cd ~/workspace/Bluetooth_Headset_Battery_Level || exit
    bluetoothctl disconnect "$mac_address"
    ./bluetooth_battery.py "$mac_address"
    cd -
}
```


