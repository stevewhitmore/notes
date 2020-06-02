# Send Email via Gmail SMTP Server

## Set up Gmail App Password

https://myaccount.google.com/apppasswords

## Configure Bash SMTP

`sudo apt-get install mutt`

`vim ~/.muttrc`

```bash
set from = "<gmail-id>@gmail.com"
set realname = "<Your Name>"
set smtp_url = "smtp://<gmail-id>@smtp.gmail.com:587/"
set smtp_pass = "<smtp password generated in the google link above>"

```

`echo "This is an email body." | mutt -s "This is an email subject" recipient@example.com`