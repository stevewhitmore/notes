### List file sizes

To include hidden files and directories, as well was sort by size, use `du -hs $(ls -A) | sort -h`. 

### Find

```bash
find <directory> -iname <filename>                # find files
find <directory> -type d -iname <directory-name>  # find directory
find . -name "filename" -delete                   # delete all files with given name
```
### Grep

```bash
-i # ignore case
-r # recursive search
-l # only list files (instead of showing exactly in the file where match is found)
--include\*.fileextension # only show matches for files of specified extension
```

To find all java files with "some-pattern", use

```bash
grep -irl "some-pattern" --include=\*.java .
```
"grep: for every file ending in '.java' in this directory list those that include 'some-pattern', but only show the name and path of the file it's in and ignore case."

### MySQL/Apache operations

```bash
sudo /etc/init.d/mysql start
sudo /etc/init.d/mysql stop
sudo /etc/init.d/mysql restart
sudo /etc/init.d/mysql status
```
Replace "mysql" with whatever service name for the same operations on that service (e.g. apache2, bluetooth, etc)

### Package updates

```bash
sudo apt update        # Fetches the list of available updates
sudo apt upgrade       # Installs some updates; does not remove packages
sudo apt full-upgrade  # Installs updates; may also remove some packages, if needed
sudo apt autoremove    # Removes any old packages that are no longer needed
```

### OS Info

Any of the following will display info about the current OS

```bash
cat /etc/os-release
# or
lsb_release -a
# or
hostnamectl
```

To see kernel version

```bash
uname -r
```

### kde specific

```bash
kquitapp5 plasmashell # Kill plasma
kstart5 plasmashell # Start plasma
```


### xfce specific

```bash
xfce4-taskmanager    # Open up task manager
xfce4-panel -r       # Reset xfce panel (for after styling/layout change)
```
 
### Trash:

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

### tar

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

