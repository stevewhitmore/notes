# Slow SSH Connection

There are several factors that could contribute to a slow SSH login and activity once logged in.

One fix would be to try setting `UseDNS` to `no` in `/etc/sshd_config` or `/etc/ssh/sshd_config`. This didn't fix it for me but it's good to keep in mind.

With systemd (which Debian 11 uses), logins and post-login activity may hang on dbus communication with logind after some upgrades, then you need to restart logind.

`systemctl restart systemd-logind`

