
# RxJS Gotchas

## first vs take(1)

`first()` will throw an error if no value is provided while `take(1)` will not

## forkJoin vs merge vs zip

### forkJoin

`forkJoin` is an operator that takes any number of input observables which can be passed either as an array or a dictionary of input observables. If no input observables are provided (e.g. an empty array is passed), then the resulting stream will complete immediately.

`forkJoin` will wait for all passed observables to emit and complete and then it will emit an array or an object with last values from corresponding observables.

```javascript
const observable = forkJoin({
  foo: of(1, 2, 3, 4),
  bar: Promise.resolve(8),
  baz: timer(4000)
});
observable.subscribe({
 next: value => console.log(value),
 complete: () => console.log('This is how it ends!'),
});
 
// Outputs:
// { foo: 4, bar: 8, baz: 0 } after 4 seconds
// 'This is how it ends!' immediately after
```

also

```javascript
const observable = forkJoin([
  of(1, 2, 3, 4),
  Promise.resolve(8),
  timer(4000)
]);
observable.subscribe({
 next: value => console.log(value),
 complete: () => console.log('This is how it ends!'),
});
 
// Outputs:
// [4, 8, 0] after 4 seconds
// 'This is how it ends!' immediately after
```

### merge

`merge` subscribes to each given input Observable (as arguments), and simply forwards (without doing any transformation) all the values from all the input Observables to the output Observable. The output Observable only completes once all input Observables have completed. Any error delivered by an input Observable will be immediately emitted on the output Observable.

```javascript
const clicks = fromEvent(document, 'click');
const timer = interval(1000);
const clicksOrTimer = merge(clicks, timer);
clicksOrTimer.subscribe(x => console.log(x));
 
// Results in the following:
// timer will emit ascending values, one every second(1000ms) to console
// clicks logs MouseEvents to console every time the "document" is clicked
// Since the two streams are merged you see these happening
// as they occur.
```

### zip

If the last parameter is a function, this function is used to compute the created value from the input values. Otherwise, an array of the input values is returned.

```javascript
const age$ = of(27, 25, 29);
const name$ = of('Foo', 'Bar', 'Beer');
const isDev$ = of(true, true, false);
 
zip(age$, name$, isDev$).pipe(
  map(([age, name, isDev]) => ({ age, name, isDev }))
)
.subscribe(x => console.log(x));
 
// Outputs:
// { age: 27, name: 'Foo', isDev: true }
// { age: 25, name: 'Bar', isDev: true }
// { age: 29, name: 'Beer', isDev: false }
```

## flatMap vs mergeMap

They're the same thing. flatMap was renamed to mergeMap and is being removed in v8.

## switchMap vs mergeMap

`switchMap` cancels previous HTTP requests that are still in progress, while `mergeMap` lets all of them finish.
