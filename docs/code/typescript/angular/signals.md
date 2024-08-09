# Signals

*2023-06-11*

A new way to handle to handle reactivity in Angular. 

## How does it work?

Centers around 3 core concepts: signal, computed, and effect.

**Signal:** As it sounds. Sends out a signal whenever the indicated value changes  
**Computed:** Gets a derived values (like what the pipe operators do in RxJS)  
**Effects:** Sets off side-effects (like tap does with RxJS)

```typescript
export class AppComponent {
    count = signal(0);
    double = computed(() => this.count() * 2);

    changeCount() {
        this.count.set(5);
    }
}
```

## Why not just use RxJS Subjects?

- The syntax is simpler
- There are no Observables, so no subscription 
- It comparmentalizes change detection to that one component by default (Rather than messing with ChangeDetectionStrategy as is needed with RxJS)
- It closes the gap between imperative and reactive declarative coding styles in Angular
    - As of now you choose a path: imerative or declarative
    - With signals you start simple with reactive/imperative and add complexity later with reactive/declarative

This is a big deal for the framework because it makes reactive programming more approachable and it opens up the possibility of no longer needing to rely on Zone.js which is kind of a hack solution to get the framework to detect changes based on communication with the browser (interfaces with browser which tells Zone something in the Event Loop is happening).

## So is RxJS going away?

No

Signals is good for synchronous reactivity (incrementation or updates when the value is immediately known) but RxJS still handles asynchronous reactivity better.

There may be some cases where you want to translate a signal to an observable and vica-versa. That's possible:

```typescript
// signal
searchTerm = signal('');

// observable
employees$ = fromSignal(this.searchTerm.pipe(
    debounceTime(300),
    distinctUntilChanged(),
    switchMap((switchMap) => this.employeeSearch.search(searchTerm)),
    retryCount(5),
));

// signal (allows hooking back into signal change detection mechanism)
employees = fromObservable(this.employees$);
```

