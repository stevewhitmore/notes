<<<<<<< HEAD
# Fefora 

For Fedora-specific good-to-knows.

## DNF

## Safe dnf update

There's a risk with `dnf update` from a graphical terminal session.. This almost always works fine, but in extremely rare cases it can destroy your operating system. This can happen when a process in the desktop environment crashes while the update is running. To safely run dnf update without worry, switch to a virtual terminal `Ctrl+Alt+F3` and run it there, where it doesn't stand any risk of being killed mid-operation. Use `Ctrl+Alt+F2` to return to your desktop. Other online updaters, like `apt-get`, are no different; this advice applies to any program that you can use to update your system.

### When to reboot after update

Install `dnf-plugin-tracer` to have dnf show what needs restarting after every transaction. It's much faster and friendlier than `needs-restarting`.

## Misc

### Installing ChromeDriver

For running headless browser instances.

*`chromedriver.x86_64` is now in `dnf` but if for some reason this needs to be installed manually do the following*:

1. `sudo dnf install chromium`
2. Double check the installed version. If for some reason it's not obvious from
the install output you can run `dnf list installed chromium`.
3. Navigate to the ChromeDriver [downloads page](https://sites.google.com/a/chromium.org/chromedriver/downloads) and find the version that matches the version of Chromium
that was just installed. Only major version really matters.
4. Download the zip file for Linux.
5. `unzip chromedriver_linux64.zip `
6. Move the executable to `/usr/bin`, change ownership to root, and give it execute permission:

```bash
sudo mv chromedriver /usr/bin/chromedriver 
sudo chown root:root /usr/bin/chromedriver 
sudo chmod +x /usr/bin/chromedriver
```

### Turn off machine beeps (Fedora)

su -
modprobe -r pcspkr
echo "install pcspkr :" >> /etc/modprobe.conf
