---
title: File Management
tags:
  - Linux
  - Bash
---
# File Management

## List file sizes

To include hidden files and directories, as well was sort by size, use `du -hs $(ls -A) | sort -h`. 

## Delete all node_modules

```bash
find . -name "node_modules" -exec rm -rvf '{}' +
```

## Trash:

To trash some_file (or folder) use

```bash
gio trash some_file
```

To go dumpster diving use

```bash
gio list trash://
```

To empty trash

```bash
gio trash --empty
```

## tar

```bash
-z # filter the archive through gzip
-v # verbose: what's in the archive
-t # list files in archive
-x # extract contents of archive
-c # tells tar to create archive file
-f # tells tar the name of the file that's being operated on
```
So to compress a file, use

```bash
tar -czvf projects.tar.gz ./project*
```

"tar: create an archive, run it through gzip, tell me what you're compressing, and the file's name is projects.tar.gz. Do this operation on files in this folder with names starting with 'projects' ".

To extract the contents of a file, use

```bash
tar -xzvf projects.tar.gz
```
"tar: extract, uncompress, and tell me what you're pulling out of projects.tar.gz"

## inodes

An inode is a data structure that stores various metadata about a file such as permissions, type, size, physical location, etc. A name of a file is a reference that points to the inode. 

```bash
myfile.txt -> (some inode ID)
```

## Copy vs Hard Link vs Soft (Symbolic) Link

### Copy

Creates a new file that points to a new memory location with the contents of the original file.

![copy image diagram](./../images/copy-diagram.png)

![copy image](./../images/copy.png)

### Hard Link

Creates a new file that points to the **same** memory location as the original file. This essentially "syncs" the two files

![hard link diagram](./../images/hardlink-diagram.png)

![hard link](./../images/hardlink.png)

#### Soft Link

a.k.a. symbolic link. Creates a file that points to another file. They are just pointers to the file reference (which in itself is pointing to the inode)

![soft link diagram](./../images/symlink-diagram.png)

![soft link](./../images/symlink.png)

## File Timestamps

### stat

This gives the history of a file. Output looks something like the following:

```bash
$ stat *.py
  File: ebay_is_us_store_sorter.py
  Size: 3488            Blocks: 8          IO Block: 4096   regular file
Device: fd02h/64770d    Inode: 2266        Links: 1
Access: (0664/-rw-rw-r--)  Uid: ( 1000/swhitmore)   Gid: ( 1000/swhitmore)
Context: unconfined_u:object_r:user_home_t:s0
Access: 2021-01-22 16:05:58.452056363 -0600
Modify: 2021-01-08 17:00:05.181712679 -0600
Change: 2021-01-08 17:00:05.181712679 -0600
 Birth: 2021-01-08 17:00:05.181712679 -0600
  File: surplus_automated_import.py
  Size: 3415            Blocks: 8          IO Block: 4096   regular file
Device: fd02h/64770d    Inode: 2348        Links: 1
Access: (0664/-rw-rw-r--)  Uid: ( 1000/swhitmore)   Gid: ( 1000/swhitmore)
Context: unconfined_u:object_r:user_home_t:s0
Access: 2021-04-23 09:34:19.442759029 -0500
Modify: 2021-01-08 17:00:05.183712768 -0600
Change: 2021-01-08 17:00:05.183712768 -0600
 Birth: 2021-01-08 17:00:05.183712768 -0600
```

If you want to adjust the format, refer to the man pages, since the output is OS-specific and varies under Linux/Unix.

### via `ls` command

Generally, you can get the times through a normal directory listing as well:

- `ls -l` outputs last time the file content was modified, the `mtime`
- `ls -lc` outputs last time of file status modification, the `ctime`
- `ls -lu` outputs last access time, the `atime`

### `mtime` vs `ctime` vs `atime`

`mtime`, or modification time, is when the file was last modified. When you change the contents of a file, its `mtime` changes.

`ctime`, or change time, is when the file's property changes. It will always be changed when the mtime changes, but also when you change the file's permissions, name or location.

`atime`, or access time, is updated when the file's contents are read by an application or a command such as `grep` or `cat`.

The easiest way to remember which is which is to read their alphabetical order:

- `atime` can be updated alone
- `ctime` will update `atime`
- `mtime` will update both `atime` and `ctime`

## Vim

### Find/replace multiple occurrences of same word

#### In editor

1. Put cursor over word
2. While in Read mode, press `*`. This selects the word.
3. Press `c` for change
4. Press `gn` for next match
5. Make change
6. Press `<esc>` to get out of Insert mode
7. Press `.` to replace next match

#### From command (sed style)

`%substitute//replacement/gc`

#### From command (regex style)

```bash
:%norm A*
```

This is what it means:

```bash
 %       = for every line

 norm    = type the following commands

 A*      = append '*' to the end of current line
 ```
