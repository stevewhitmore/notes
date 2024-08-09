---
title: Navigation
tags:
  - Linux
  - Bash
---
# Navigation

## Find

```bash
find <directory> -iname <filename>                # find files
find <directory> -type d -iname <directory-name>  # find directory
find . -name "filename" -delete                   # delete all files with given name
```
## Grep

```bash
-i # ignore case
-r # recursive search
-l # only list files (instead of showing exactly in the file where match is found)
--include\*.fileextension # only show matches for files of specified extension
```

To find all java files with "some-pattern", use

```bash
grep -irl "some-pattern" --include=\*.java .
```
"grep: for every file ending in '.java' in this directory list those that include 'some-pattern', but only show the name and path of the file it's in and ignore case."

## TMUX Shortcuts

### Attaching/detaching from sessions

`tmux ls`

`tmux a -t <session-id>`

### Resizing Panes

```bash
# This assumes that you've hit ctrl + b and : to get to the command prompt
:resize-pane -D (Resizes the current pane down)
:resize-pane -U (Resizes the current pane upward)
:resize-pane -L (Resizes the current pane left)
:resize-pane -R (Resizes the current pane right)
:resize-pane -D 10 (Resizes the current pane down by 10 cells)
:resize-pane -U 10 (Resizes the current pane upward by 10 cells)
:resize-pane -L 10 (Resizes the current pane left by 10 cells)
:resize-pane -R 10 (Resizes the current pane right by 10 cells)
```
