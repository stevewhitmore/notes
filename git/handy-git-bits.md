# Handy Git Bits

## How to merge feature branch to master branch (assuming you can push to master)

```bash
git checkout master
git pull origin master
git merge feature-branch
git push origin master
```

## Update feature branch with master contents

```bash
git checkout feature-branch
git merge origin/master
git push origin feature-branch
```


## How to replace the master branch with another branch

```bash
git checkout master
git reset --hard fixed-master-branch
git push origin master -f
```


## Setting local branch to exactly match the remote branch

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

## Undo last commit (before push)

```bash
git reset --soft HEAD~1
```

## Revert branch back to certain commit

```bash
git reset --hard <sha1>
git push --force origin <branch-name>
```

## Remember git credentials

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

## Local cleanup

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

## Misc

Find out what your upstream url is

```bash
git remote -v
```

Reset remote url

```bash
git remote set-url origin <repo-url>
```
