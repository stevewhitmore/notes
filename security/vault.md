# HashiCorp Vault Quick Reference

Source: https://learn.hashicorp.com/collections/vault/getting-started

## Dev Server

`vault server -dev`

`export VAULT_ADDR='http://127.0.0.1:8200'`

Token will be different. See output on server setup.

`export VAULT_TOKEN="s.XmpNPoi9sRhYtdKHaQhkHP6x"`

Check status

`vault status`

### Preventing exposure during interaction

Q: How do I enter my secrets without exposing the secret in my shell's history?

There are 3 options. 

The first option is good but will still expose the path and key. You can add a dash after the `key=`, hit enter, write the value on the next line, then press `Ctrl+d` to end the pipe which will write the secret to Vault.

The second option involves storing the key-value pairs in a file which I'm never going to do.

The simplest one is the 3rd one - Disable all vault command history

`export HISTIGNORE="&:vault*"`

https://learn.hashicorp.com/tutorials/vault/static-secrets#q-how-do-i-enter-my-secrets-without-exposing-the-secret-in-my-shell-s-history

### Working with key/value pairs

`vault kv -help`

`vault kv put secret/hello foo=world`

`vault kv get -field=excited secret/hello`

`vault kv delete secret/hello` 

`vault kv undelete -versions=2 secret/hello`

Use `destroy` to do a hard delete.

## Enabling a secrets engine

`vault secrets enable -path=foo kv` starts a new secrets path at `foo/`

`vault secrets list` to confirm this

`vault secrets disable foo` will remove it

`vault kv list foo` to show all paths following `foo`

