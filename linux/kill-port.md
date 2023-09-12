# Kill a port

## Linux

`fuser 8080/tcp` will print the PID of the process bound to `8080`.

`fuser -k 8080/tcp` will kill the process. 

## macOs

`lsof -i :8080` print PID of process bound to `8080`.  

`kill -9 <pid>` kill the process.
