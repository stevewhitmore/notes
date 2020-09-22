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