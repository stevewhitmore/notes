# RxJS Must Knows

There are hundreds of RxJS functions and operators. These are the most commonly used ones and thus the most beneficial to master.

## Creational Operators

### from

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

### of

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

## Pipeable Operators

### combineLatest

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

### concatWith

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

### concatMap

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

### count

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

### debounceTime

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

### distinct

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

### distinctUntilChanged

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

### filter

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

### first

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

### last

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

### map

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

### mergeMap

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

### startsWith

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

### switchmap

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

### take

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

### takeUntil

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

### tap

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
