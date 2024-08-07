---
title: RxJS
tags:
  - TypeScript
  - Angular
  - RxJS
---
# RxJS
## RxJS Must Knows

There are hundreds of RxJS functions and operators. These are 20 of the most commonly used ones and thus the most beneficial to master.

### Creational Operators

#### EMPTY

A simple Observable that emits no items to the Observer and immediately emits a complete notification.

```javascript
EMPTY.subscribe({
  next: () => console.log('Next'),
  complete: () => console.log('Complete!')
});

// Outputs
// Complete!
```

#### NEVER

An Observable that emits no items to the Observer and never completes.

```javascript
const info = () => console.log('Will not be called');

const result = NEVER.pipe();
result.subscribe({
  next: info,
  error: info,
  complete: info
});
```

#### from

Creates an Observable from an Array, an array-like object, a Promise, an iterable object, or an Observable-like object.  
This differs from 'of' because it'll emit each item one by one

```javascript
import { from } from 'rxjs';

const array = [10, 20, 30];
const result = from(array);

result.subscribe(x => console.log(x));

// Outputs:
// 10
// 20
// 30
```

#### of

Similar to 'from' but it'll emit an iterable all at once

```javascript
import { of } from 'rxjs';
    
of(10, 20, 30)
    .subscribe({
        next: value => console.log('next:', value),
        error: err => console.log('error:', err),
        complete: () => console.log('the end'),
    });
    
// Outputs:
// next: 10
// next: 20
// next: 30
// the end
```

### Pipeable Operators

#### combineLatest

Combines the values from all supplied observables.  
Use this when you need to support one observable being dependent upon another.

```javascript

const firstTimer = timer(0, 1000); // emit 0, 1, 2... after every second, starting from now
const secondTimer = timer(500, 1000); // emit 0, 1, 2... after every second, starting 0,5s from now
const combinedTimers = combineLatest([firstTimer, secondTimer]);
combinedTimers.subscribe(value => console.log(value));

// Outputs:
// [0, 0] after 0.5s
// [1, 0] after 1s
// [1, 1] after 1.5s
// [2, 1] after 2s
// ...and so on
```

#### concatWith

Emit values from provided observable after the first observable completes. Use this when  
you need to handle multiple sets of data in the same way in order

```javascript
const clicks$ = fromEvent(document, 'click');
const moves$ = fromEvent(document, 'mousemove');
 
clicks$.pipe(
  map(() => 'click'),
  take(1),
  concatWith(
    moves$.pipe(
      map(() => 'move')
    )
  )
)
.subscribe(x => console.log(x));
 
// Outputs:
// 'click'
// 'move'
// 'move'
// 'move'
// ...
```

#### concatMap

Allows for emitting values from an operation that creates a separate observable, it will emit  
values from the created observable. Original values will be emitted in order after each of their  
created observables have completed. Use this when you need to flatten an observable of  
observables when you want to handle each parent emit after the child observable completes.

```javascript
const clicks = fromEvent(document, 'click');
const result = clicks.pipe(
  concatMap(ev => interval(1000).pipe(take(4)))
);
result.subscribe(x => console.log(x));

// Results in the following:
// (results are not concurrent)
// For every click on the "document" it will emit values 0 to 3 spaced
// on a 1000ms interval
// one click = 1000ms-> 0 -1000ms-> 1 -1000ms-> 2 -1000ms-> 3
```

#### count

Tells how many values were emitted, when the stream completes. Use this when you need  
to find out how many items were in an observable stream.  
This example shows an operation via count that doesn't effect the values.

```javascript
const numbers = range(1, 7);
const result = numbers.pipe(count());
result.subscribe(x => console.log(x));

// Output
// 7
```

#### debounceTime

Adds a time buffer to only emit when no other values have been emitted in the timeframe  
specified. Use this to limit longer running processes that can be requested multiple times  
when you only care about the most recent value. i.e. making HTTP calls for autocomplete  

```javascript
const clicks = fromEvent(document, 'click');
const result = clicks.pipe(debounceTime(1000));
result.subscribe({
  next: () => console.log('foo'),
})

// Outputs 
// 'foo' after 1 second:
```

#### distinct

A given value is not emitted more than once. Use this when you do not want to reprocess  
the same information more than once.

```javascript
of(1, 1, 2, 2, 2, 3, 4, 5, 5)
    .pipe(
        distinct(),
    )
    .subscribe(console.log);

// Outputs:
// 1
// 2
// 3
// 4
// 5
```

#### distinctUntilChanged

Emits only when the current value is different than the previous value. Use this when you are  
only interested in doing something when there is a new value. i.e. don’t validate a textbox  
value if the user pasted the same value over itself.  

```javascript
of(1, 1, 1, 2, 2, 2, 1, 1, 3, 3)
  .pipe(distinctUntilChanged())
  .subscribe(console.log);

// Outputs:
// 1
// 2
// 1
// 3
```

#### endWith

Allows you to specify the last value to be emitted before completion.

```javascript
const ticker$ = interval(5000).pipe(
  map(() => 'tick')
);
 
const documentClicks$ = fromEvent(document, 'click');
 
ticker$.pipe(
  startWith('interval started'),
  takeUntil(documentClicks$),
  endWith('interval ended by click')
)
.subscribe(x => console.log(x));
 
// Result (assuming a user clicks after 15 seconds)
// 'interval started'
// 'tick'
// 'tick'
// 'tick'
// 'interval ended by click'
```

#### filter

Allows you to prevent values from being emitted based upon a supplied function.  
Use this when you want to control what values are emitted for further processing.

```javascript
of(1, 2, 3, 4, 5, 6, 7, 8, 9)
    .pipe(
        filter(x => x % 2 === 0),
    )
    .subscribe(console.log);

// Outputs:
// 2
// 4
// 6
// 8
```

#### first

Only emit the first value, after the first value the observable will complete.  
Use this if you only care about the first value.

```javascript
of(1, 2, 3, 4, 5, 6, 7, 8, 9)
    .pipe(
        first(),
    )
    .subscribe(console.log);

// Outputs:
// 1
```

#### last

Only emit the last value prior to completion.  
Use this if you only care about the last value.

```javascript
of(1, 2, 3, 4, 5, 6, 7, 8, 9)
    .pipe(
        last(),
    )
    .subscribe(console.log);

// Outputs:
// 9
```

#### map

Allows the values to be modified to a new value.  
Use this when you want to change values being emitted.

```javascript
of(1,2,3,4)
  .pipe(map(x => x *= 2))
  .subscribe(console.log);

// Outputs:
// 2
// 4
// 6
// 8
```

#### mergeMap

Returns an Observable that emits items based on applying a function that you supply to each item emitted by the source Observable, where that function returns an Observable, and then merging those resulting Observables and emitting the results of this merger.

```javascript
of('a', 'b', 'c')
  .pipe(
    mergeMap(x => interval(1000).pipe(map(i => x + i)))
  )
  .subscribe(console.log);
 
// Results in the following:
// a0
// b0
// c0
// a1
// b1
// c1
// continues to list a, b, c every second with respective ascending integers
```

#### startsWith

Provides the ability to specify a value which will be the first value emitted by the observable.  
Use this to seed your observable with a specific value.

```javascript
of(1, 2, 3, 4, 5)
    .pipe(
        startWith(1000),
    )
    .subscribe(console.log);

// Outputs:
// 1000
// 1
// 2
// 3
// 4
// 5
```

#### switchmap

Allows for emitting values from an operation that creates a separate observable, it will emit  
values from the created observable. Values from the created observable will be emitted only  
from the most recent emitted source observable. Use this when you only care about the  
most recent parent emit value’s child values, because a new parent emit will cancel the
previous child observable

```javascript
const switched = of(1, 2, 3).pipe(switchMap(x => of(x, x ** 2, x ** 3)));
switched.subscribe(x => console.log(x));

// Outputs:
// 1
// 1
// 1
// 2
// 4
// 8
// 3
// 9
// 27
```

#### take

Allows specification of the number of emits that occur before the observable completes.  
Use this when you want to limit the number of values.

```javascript
const intervalCount = interval(1000);
const takeFive = intervalCount.pipe(take(5));
takeFive.subscribe(x => console.log(x));

// Outputs:
// 0
// 1
// 2
// 3
// 4
```

#### takeUntil

Allows specifying when an observable will complete based upon the emission of separate  
observable. Use this when you want one event to signal the completion of another.  

```javascript
const source = interval(1000);
const clicks = fromEvent(document, 'click');
const result = source.pipe(takeUntil(clicks));
result.subscribe(x => console.log(x));

// Outputs:
// 0
// 1
// 2
// ...etc until a click
```

#### tap

Allows for side-effects based upon the source observable, but does not have an effect on the
values being emitted. Use this to use the emit of an observable to trigger something outside
the scope of the observable. A common use case is to place debugging statements such as
logging.

```javascript
of(3).pipe(
  tap(console.log),
).subscribe();

// Outputs:
// 3
```

#### withLatestFrom

Allows “pulling” latest value from another observable when the source observable emits. The
source value is combined with the other observable in an array.  
Use this when you need information from another observable, but may not care when that observable emits.

```javascript
const clicks = fromEvent(document, 'click');
const timer = interval(1000);
const result = clicks.pipe(withLatestFrom(timer));
result.subscribe(x => console.log(x));
```

## RxJS Gotchas

### first vs take(1)

`first()` will throw an error if no value is provided while `take(1)` will not

### forkJoin vs merge vs zip

#### forkJoin

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

#### merge

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

#### zip

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

### flatMap vs mergeMap

They're the same thing. flatMap was renamed to mergeMap and is being removed in v8.

### switchMap vs mergeMap

`switchMap` cancels previous HTTP requests that are still in progress, while `mergeMap` lets all of them finish.
