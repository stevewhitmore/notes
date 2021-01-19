
### Safe dnf update

There's a risk with `dnf update` from a graphical terminal session.. This almost always works fine, but in extremely rare cases it can destroy your operating system. This can happen when a process in the desktop environment crashes while the update is running. To safely run dnf update without worry, switch to a virtual terminal `Ctrl+Alt+F3` and run it there, where it doesn't stand any risk of being killed mid-operation. Use `Ctrl+Alt+F2` to return to your desktop. Other online updaters, like `apt-get`, are no different; this advice applies to any program that you can use to update your system.


### Turn of machine beeps (Fedora)

su -
modprobe -r pcspkr
echo "install pcspkr :" >> /etc/modprobe.conf
