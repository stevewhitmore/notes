# Mounting External Drives

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

### Example Commands

```bash
ssh user@whitmore_tower_ip
lsblk
sudo mkdir /mnt/external_drive
sudo mount /dev/sdb1 /mnt/external_drive
cd /mnt/external_drive
ls
sudo umount /mnt/external_drive
```

### Summary
- Use `lsblk` to identify the external hard drive.
- Create a mount point using `mkdir`.
- Mount the drive with `mount`.
- Access the files and unmount when done with `umount`.

