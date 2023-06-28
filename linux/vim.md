# Vim

## Find/replace multiple occurrences of same word

### In editor

1. Put cursor over word
2. While in Read mode, press `*`. This selects the word.
3. Press `c` for change
4. Press `gn` for next match
5. Make change
6. Press `<esc>` to get out of Insert mode
7. Press `.` to replace next match

### From command (sed style)

`%substitute//replacement/gc`

### From command (regex style)

```bash
:%norm A*
```

This is what it means:

```bash
 %       = for every line

 norm    = type the following commands

 A*      = append '*' to the end of current line
 ```
