# REST APIs For The Long Haul

**API design is UX for Devs**

REST is a set of architectural constraints

## Six Constraints

- Client server
- Cacheable
- Stateless
- Layered
- Code on Demand (optional)
- Uniform Interface

Avoid maintaining State - big nono

- Should always be stateless

## A Few Simple Rules

- Dont be creative, be predictible
- Be consistent
- Get the important stuff right - avoid bikeshedding

> The act of wasting time on trivial details while important matters are inadequately attended is sometimes known as bikeshedding

## Basic Considerations

- Security

*Twilio (for texts) uses API keys*

Basic Auth

- Hash un/pwd
- API key -> **x-application-key: this is a secrete header**

## Beware of Leaky Abstractions

Join tables instead of multiple requests for 1 result

## General Error Handling Guyidlines

- Be as consistent as possible throughout API
- Provide enough info for caller to fix issue
- log info

## Versioning

header, url query string, or role (best)
