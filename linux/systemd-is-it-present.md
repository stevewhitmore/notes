# systemd: is it present?

It's not always apparent that systemd is being used by a system and it shouldn't be assumed that it is (even though it seems most distros are utilizing it now). To confirm you can check what `/sbin/init` is; `file /sbin/init` will tell you if it's a real executable or if it's a symbolic link to some other package's executable. If systemd is present you'll see the following output:

```bash
user@hostname ~ $ file /sbin/init
/sbin/init: symbolic link to ../lib/systemd/systemd
```

For more information, see: <https://en.wikipedia.org/wiki/Linux_startup_process>

Another way of seeing exactly what you have on your system is typing `man init` and seeing which program's man page you end up on.

*Credit: <https://superuser.com/a/1018046/514856>*

