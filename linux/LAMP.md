# LAMP setup

## Install Apache and MySQL

```bash
sudo apt-get install apache2
sudo apt-get install mysql-server
```

If secure db setup, do the following (dont bother with local it's a bit of a PITA -

```bash
sudo mysql_secure_installation
```

### Test MySQL

`sudo mysql -u root -p`

## Install PHP

```bash
sudo apt-get install php libapache2-mod-php
```

*libapache2-mod-php* provides the PHP module for the Apache 2 webserver

### Testing PHP

Create `/var/www/html/phpinfo.php` with the contents

```bash
<?php
    phpinfo();
?>
```
