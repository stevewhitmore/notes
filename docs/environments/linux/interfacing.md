---
title: Interfacing
tags:
  - Linux
  - Bash
  - Git
  - SSH
  - SMTP
---
# Interfacing

## git

### How to merge feature branch to master branch (assuming you can push to master)

```bash
git checkout master
git pull origin master
git merge feature-branch
git push origin master
```

### Update feature branch with master contents

```bash
git checkout feature-branch
git merge origin/master
git push origin feature-branch
```


### How to replace the master branch with another branch

```bash
git checkout master
git reset --hard fixed-master-branch
git push origin master -f
```


### Setting local branch to exactly match the remote branch

```bash
git fetch origin
git reset --hard origin/<branch-name>
```

If you want to save your current branch's state before doing this (just in case), you can do:

```bash
git commit -a -m "Saving my work, just in case"
git branch my-saved-work
```

Alternatively you can stash your changes

```bash
git stash
```

do the reset, then

```bash
git stash pop
```

and then they're back with the local branch now synced up with the remote master.

### Undo last commit (before push)

```bash
git reset --soft HEAD~1
```

### Revert branch back to certain commit

```bash
git reset --hard <sha1>
git push --force origin <branch-name>
```

### Remember git credentials

Assuming you can't use an ssh key (which would be best)...

Install Gnome Keyring devel

```bash
sudo apt-get install libgnome-keyring-dev
sudo make --directory=/usr/share/doc/git/contrib/credential/gnome-keyring
```

Set up credential

```bash
git config --global credential.helper /usr/share/doc/git/contrib/credential/gnome-keyring/git-credential-gnome-keyring
```

If you're on a machine where you can't install any additional software then you can use the following for a temporary workaround

```bash
 git config --global credential.helper 'cache --timeout={number in seconds}'
```

### Local cleanup

```bash
git branch | grep -v \"master\" | xargs git branch -D
```

purge all local branches except master

```bash
git fetch -p
```

prunes remote branches which show locally that dont exist in remote anymore

```bash
git branch -d <doomed-branch-name>
```

To delete a branch locally. If there's something that needs to be committed on that a branch it should warn you and refuse to delete it. If you don't care about the changes for that branch then run it with -D instead of -d and it'll force the deletion.

```bash
git branch | grep -v "master" | xargs git branch -D
```

To delete all local branches except master. This comes in handy if something happens like special characters get into a branch name (like when I copy/pasted something from a word doc that one time).

```bash
git update-index --assume-unchanged path/to/file
```

To ignore future revisions to file and

```bash
git update-index --no-assume-unchanged path/to/file
```

to stop ignoring it

### Misc

Find out what your upstream url is

```bash
git remote -v
```

Reset remote url

```bash
git remote set-url origin <repo-url>
```

### Multiple Git Repos

If a project has to have multiple git repos (e.g. Bitbucket and
Github) then it's better that they remain in sync.

Usually this would involve pushing each branch to each repo in turn,
but actually Git allows pushing to multiple repos in one go.

If in doubt about what git is doing when you run these commands, just
edit ``.git/config`` (`git-config(1)`_) and see what it's put there.


#### Remotes

Suppose your git remotes are set up like this::

    git remote add github git@github.com:muccg/my-project.git
    git remote add bb git@bitbucket.org:ccgmurdoch/my-project.git

The ``origin`` remote probably points to one of these URLs.


#### Remote Push URLs

To set up the push URLs do this::

    git remote set-url --add --push origin git@github.com:muccg/my-project.git
    git remote set-url --add --push origin git@bitbucket.org:ccgmurdoch/my-project.git

It will change the ``remote.origin.pushurl`` config entry. Now pushes
will send to both of these destinations, rather than the fetch URL.

Check it out by running::

    git remote show origin


#### Per-branch

A branch can push and pull from separate remotes. This might be useful
in rare circumstances such as maintaining a fork with customizations
to the upstream repo. If your branch follows ``github`` by default::

    git branch --set-upstream-to=github next_release

(That command changed ``branch.next_release.remote``.)

Then git allows branches to have multiple ``branch.<name>.pushRemote``
entries. You must edit the ``.git/config`` file to set them.


#### Pull Multiple

You can't pull from multiple remotes at once, but you can fetch from
all of them::

    git fetch --all

Note that fetching won't update your current branch (that's why
``git-pull`` exists), so you have to merge -- fast-forward or
otherwise.

For example, this will octopus merge the branches if the remotes got
out of sync::

    git merge github/next_release bb/next_release

#### References

* `git-config(1)`_
* `git-remote(1)`_
* `git-branch(1)`_

.. _`git-config(1)`: https://www.kernel.org/pub/software/scm/git/docs/git-config.html
.. _`git-remote(1)`: https://www.kernel.org/pub/software/scm/git/docs/git-remote.html
.. _`git-branch(1)`: https://www.kernel.org/pub/software/scm/git/docs/git-branch.html

*credit https://gist.github.com/rvl/c3f156e117e22a25f242*

### GitLab compare commits

1. go to Repository > Compare
2. https://gitlab.com/$USER/$REPO/compare?from=$SHA1&to=$SHA2


## SSH

```bash
ssh-keygen -t ed25519 -C "your_email@example.com"
```

### Slow SSH Connection

There are several factors that could contribute to a slow SSH login and activity once logged in.

One fix would be to try setting `UseDNS` to `no` in `/etc/sshd_config` or `/etc/ssh/sshd_config`. This didn't fix it for me but it's good to keep in mind.

With systemd (which Debian 11 uses), logins and post-login activity may hang on dbus communication with logind after some upgrades, then you need to restart logind.

`systemctl restart systemd-logind`

## Kill a port

### Linux

`fuser 8080/tcp` will print the PID of the process bound to `8080`.

`fuser -k 8080/tcp` will kill the process. 

### macOs

`lsof -i :8080` print PID of process bound to `8080`.  

`kill -9 <pid>` kill the process.

## Send Email via Gmail SMTP Server

### Set up Gmail App Password

https://myaccount.google.com/apppasswords

### Configure Bash SMTP

`sudo apt-get install mutt`

`vim ~/.muttrc`

```bash
set from = "<gmail-id>@gmail.com"
set realname = "<Your Name>"
set smtp_url = "smtp://<gmail-id>@smtp.gmail.com:587/"
set smtp_pass = "<smtp password generated in the google link above>"

```

`echo "This is an email body." | mutt -s "This is an email subject" recipient@example.com`
