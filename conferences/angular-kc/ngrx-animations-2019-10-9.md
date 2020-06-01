# Ngrx Store & Angular Animations

Adaptive (sponsor) and Artisan (talk hosts) are both hiring

@angularkc is on slack, GitHub, and twitter

November 12th presentation on Apollo and GraphQL

## Ngrx Store
Redux truths
- 1 source of truth
- state is read-only - updated via dispatching actions
- state is updated via pure functions called reducers - initial state and an action and returns new state.

Look into Redux Chrome extension for debugging NgRx 

Make sure store is set up in unit tests or error messages will make no sense
Look at code to see how she handled subscriptions/subjects in product component

backend services need effects that kick off a dispatch/action from within effect so that reducer is able to update state
- Look up NgRx docs for more explanation and diagrams

"If you're not sure if you need Redux, then you don't need Redux" - Guy who came up with Redux

createSelector are memoized - they won't fire again unless state changes. More performant

## Angular animations 
- See https://github.com/alisaduncan/angular-animations for slides
- Look into RouterOutlet class used programmatically (outside of just <router-outlet> tags

## Links
https://github.com/johnpapa/angular-ngrx-data

https://github.com/jeansandatee/StoreSample

https://angular.io/api/router/RouterOutlet#description

https://github.com/alisaduncan/angular-animations-code/blob/master/package.json

https://github.com/alisaduncan/angular-animations

https://www.meetup.com/angularkc/events/265123424/
