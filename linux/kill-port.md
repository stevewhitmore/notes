# Kill a port

`fuser 8080/tcp` will print the PID of the process bound to `8080`.

`fuser -k 8080/tcp` will kill the process.


