---
title: LAMP stack
tags:
  - WordPress
  - PHP
  - MySQL
  - Apache
  - LAMP
---
# LAMP stack

## LAMP setup

### Install Apache and MySQL

```bash
sudo apt-get install apache2
sudo apt-get install mysql-server
```

If secure db setup, do the following (dont bother with local it's a bit of a PITA -

```bash
sudo mysql_secure_installation
```

#### Test MySQL

`sudo mysql -u root -p`

### Install PHP

```bash
sudo apt-get install php libapache2-mod-php
```

*libapache2-mod-php* provides the PHP module for the Apache 2 webserver

#### Testing PHP

Create `/var/www/html/phpinfo.php` with the contents

```bash
<?php
    phpinfo();
?>
```

#### MySQL/Apache operations

```bash
sudo /etc/init.d/mysql start
sudo /etc/init.d/mysql stop
sudo /etc/init.d/mysql restart
sudo /etc/init.d/mysql status
```
Replace "mysql" with whatever service name for the same operations on that service (e.g. apache2, bluetooth, etc)


## Running PHP locally

Assuming you have php installed, run

```bash
$ php -S localhost:8000
```

## WordPress

### Useful Queries

```sql
--Empty trash
DELETE p
FROM wp_posts p
LEFT OUTER JOIN wp_postmeta pm ON pm.post_id = p.ID
WHERE post_status = 'trash';

--Update with trash status
UPDATE wp_posts p 
JOIN wp_postmeta pm ON pm.post_id = p.ID
SET p.post_status = 'trash'
WHERE pm.meta_key = '_sku'
AND pm.meta_value IN ();

-- Verify product is present
SELECT *
FROM wp_posts p
JOIN wp_postmeta pm ON pm.post_id = p.ID
WHERE pm.meta_key = '_sku'
AND pm.meta_value = '';

-- Get all product eIDs
SELECT DISTINCT pm.meta_value
FROM wp_postmeta pm
JOIN wp_posts p ON p.ID = pm.post_id
WHERE pm.meta_key = '_sku'
AND p.post_status = 'publish';

-- Find dupicates
SELECT pm.meta_value, p.id, COUNT(*)
FROM wp_postmeta pm
JOIN wp_posts p ON p.ID = pm.post_id
WHERE pm.meta_key = '_sku'
AND p.post_status = 'publish'
GROUP BY pm.meta_value
HAVING COUNT(*) > 1;

```