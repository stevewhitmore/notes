# ng-conf 2021

## Keynote

Stop using node-sass. It will be gone in Angular v12. Switch to just npm package sass.

### Why don't you just add types to forms?

Most of hte pain of the last 5 releases has been TypeScript, not Angular.

### Why don't you just make Angular more/less reactive?

Different devs have different needs
- Experience level
- Comfort zone
- Project size

They decided to stay in the middle because removing reactivity loses out on benefits but going all the way reactive is not approachable to new/novice devs.

You can go all in either on your own or use stuff like NgRx or other tools

### Why not get rid of zones and modules

- Would leave all existing code behind
- Easier debugging
- Less magic
- Less complex simple projects (Module)
- More comples simple projects (Zones
- More complex components

They want to eventually move to optional zones and modules

### Opinionated where it counts

Angular team has weak opinions about things that matter less (css/linting/testing frameworks)

> They're trying to move forward towards more consistency and inclusion of users (encourage RFC's etc)


## Increase Your Productivity with Component Sandboxes and Stories

**Angular Playground** allows you to isolate components while testing in real time

"An open source tool for building enterprise angular components, directives, and pipes _in isolation_"

**Storybook** is basically a web app that's more immersive and takes it a bit further.

Use teh `npx sb init` command to install Storybook into an existing project

```bash
npx -p @storybook/cli sb init --type angular
```

A Storybook script will be added to package.json

```bash
npm run storybook
```

## Routeception: How to use the Angular router in microfrontends

https://github.com/kylecannon/angular-dream-stack

## Federated Angular - Why and how

### Microfrontends

#### Advantages

- Helps with lots of business domains where multiple teams are needed to handle everything.
- Good for huge product/product suite
- Scaling teams & domains
- Frees us up to switch out technologies
- Accommodating for rapidly changing requirements

#### Disadvantages

- Lots of effort around building a "meta framework" to handle all the microfrontends
- Sharing code at runtime and bundle size - they want to load Angular 1 time, not 5, etc
- Difficult to achieve common look and feel (not super valid of a concern)

### Module Federation

Webpack 5 Module Federation (Angular 12 will natively support Webpack 5)

In theory we can load a module from another domain but in practice it doesn't work because of how the current bundling solutions work. Even lazy loaded parts must be known at compile time (think of tree shaking). 

Shell (Host) -> Microfrontend (Remote)

With module federation Angular thinks that these federated modules are lazy loaded modules - it doesn't realize webpack is doing the heavy lifting.

This removes the need for a "meta framework" to manage microfrontends.

### Using with Angular

1. ng add @angular-architects/module-federation
2. Adjust generated configuration
3. ng serve

## Using Webassembly to build fast & secure web apps

This dude used his 20 minutes for a shameless plug for Stackblitz. TLDW; Q2 will introduce backend environments entirely contained in a browser window. Really cool, but not quite what they were advertising for this talk...

## Reactive error-handling in Angular

What to tell the end user about the errors?
- Known (validation) errors: the user has the chance to fix it by re-entering correct data -> Be as specific as possible. Let the user know what and how to correct.
- Known errors: the expected data data cannot be loaded/updated -> Explain what was not successful
- Known errors: the user wouldn't notice the error -> Poker face (don't show any error message)
- Unkown errors: yes, they *do* exist! -> Fall-back scenario (e.g. redirect to an error page)

### Why reactive?

- In reactive programming, everything can be **a stream of data**
- Data is being **pushed** so that you have to **react** to it (instead of intentionally **pulling** it in imperative programming)
- Errors are **undesirable interruption** of the data stream - you **react** to it too!


