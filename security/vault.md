# HashiCorp Vault Quick Reference

Source: https://learn.hashicorp.com/collections/vault/getting-started

## Dev Server

`vault server -dev`

`export VAULT_ADDR='http://127.0.0.1:8200'`

Token will be different. See output on server setup.

`export VAULT_TOKEN="s.XmpNPoi9sRhYtdKHaQhkHP6x"`

Check status

`vault status`

### Working with key/value pairs

`vault kv -help`

`vault kv put secret/hello foo=world`
