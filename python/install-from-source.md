# Install Python from source

1. Go to https://www.python.org/downloads/ and find the desired version
2. Click on Download link. Scroll to the bottom and find "Gzipped source tarball"
3. Run `wget <tarball url>` followed by `tar -xzf <tarball name>`
4. cd into untarred directory
5. Run `./configure --enable-optimizations` followed by `suco make altinstall`

That's it! Do a `which` on the version major and minor to to confirm it's present in the `/usr/local/bin` folder (e.g. `which python3.9` for version 3.9.10)

