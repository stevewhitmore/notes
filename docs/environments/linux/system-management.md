---
title: System Management
tags:
  - Linux
---
# System Management

## Package updates

```bash
sudo apt update        # Fetches the list of available updates
sudo apt upgrade       # Installs some updates; does not remove packages
sudo apt full-upgrade  # Installs updates; may also remove some packages, if needed
sudo apt autoremove    # Removes any old packages that are no longer needed
```

## OS Info

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

## Manual Package Installation

Sometimes applications are not available in a distro's package manager and only come as tar balls. In that case, follow the directions below for proper installation:

### Example: Postman

1. Download **Postman** from <https://www.postman.com/downloads/>
2. Extract contents of file with `tar -xzvf Postman-whatever-version.tar.gz`
3. Move the extracted directory to somewhere public like `/opt`
4. Check which directories are in the path with `echo $PATH`
5. Create a symbolic link in one of the path directories (I used `/usr/local/bin`) with `sudo ln -s /opt/Postman/Postman /usr/local/bin/postman`
6. Create a desktop file with `touch ~/.local/share/applications/postman.desktop`
7. Enter the below contents into *postman.desktop*

```bash
[Desktop Entry]
Name=Postman
GenericName=API Client
X-GNOME-FullName=Postman API Client
Comment=Make and view REST API calls and responses
Keywords=api;
Exec=/usr/local/bin/postman
Terminal=false
Type=Application
Icon=/opt/Postman/app/resources/app/assets/icon.png
Categories=Development;Utilities;
```

