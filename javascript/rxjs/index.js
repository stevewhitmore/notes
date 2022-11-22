import {
    debounceTime,
    distinct,
    filter,
    first,
    from,
    of,
    last,
    startWith,
    take,
    combineLatest,
} from 'rxjs'

const fromOperator = () => {
    // Creates an Observable from an Array, an array-like object, a Promise, an iterable object, or an Observable-like object.
    // This differs from 'of' because it'll emit each item one by one
    
    from(['Hello', 'world', 'this', 'is', 'a', 'string', 'array'])
        .subscribe(console.log);

    // Outputs
    // Hello
    // world
    // this
    // is
    // a
    // string
    // array
}

const ofOperator = () => {
    // Similar to 'from' but it'll emit an iterable all at once
    of(['Hello', 'world', 'this', 'is', 'a', 'string', 'array'])
        .subscribe(console.log);

    // Outputs
    // [
    //     'Hello', 'world',
    //     'this',  'is',
    //     'a',     'string',
    //     'array'
    //   ]
}

const filterOperator = () => {
    // Allows you to prevent values from being emitted based upon a supplied function. 
    // Use this when you want to control what values are emitted for further processing.
    of(1, 2, 3, 4, 5, 6, 7, 8, 9)
        .pipe(
            filter(x => x % 2 === 0),
        )
        .subscribe(console.log);

    // Outputs 
    // 2
    // 4
    // 6
    // 8
}

const firstOperator = () => {
    // Only emit the first value, after the first value the observable will complete. 
    // Use this if you only care about the first value.
    of(1, 2, 3, 4, 5, 6, 7, 8, 9)
        .pipe(
            first(),
        )
        .subscribe(console.log);

    // Outputs
    // 1

    // of(1, 2, 3, 4, 5, 6, 7, 8, 9)
    //     .pipe(
    //         take(1),
    //     )
    //     .subscribe(console.log);

    // Also outputs "1"

    // What's the difference?
    // `first()` will throw an error if no value is provided while `take(1)` will not
}

const lastOperator = () => {
    // Only emit the last value prior to completion. 
    // Use this if you only care about the last value.
    of(1, 2, 3, 4, 5, 6, 7, 8, 9)
        .pipe(
            last(),
        )
        .subscribe(console.log);

    // Outputs
    // 9
}

const startsWithOperator = () => {
    // Provides the ability to specify a value which will be the first value emitted by the observable.
    // Use this to seed your observable with a specific value.
    of(1, 2, 3, 4, 5)
        .pipe(
            startWith(1000),
        )
        .subscribe(console.log);

    // Outputs
    // 1000
    // 1
    // 2
    // 3
    // 4
    // 5
}

const debounceTimeOperator = () => {
    // Adds a time buffer to only emit when no other values have been emitted in the timeframe
    // specified. Use this to limit longer running processes that can be requested multiple times
    // when you only care about the most recent value. i.e. making HTTP calls for autocomplete
    of(1)
        .pipe(
            debounceTime(500),
        )
        .subscribe(console.log);
}

const distinctOperator = () => {
    // A given value is not emitted more than once. Use this when you do not want to reprocess
    // the same information more than once.
    of(1, 1, 2, 2, 2, 3, 4, 5, 5)
        .pipe(
            distinct(),
        )
        .subscribe(console.log);

    // Outputs
    // 1
    // 2
    // 3
    // 4
    // 5
}

const distinctUntilChangedOperator = () => {
    // Emits only when the current value is different than the previous value. Use this when you are
    // only interested in doing something when there is a new value. i.e. don’t validate a textbox
    // value if the user pasted the same value over itself.
}

const combineLatestOperator = () => {
    // Combines the values from all supplied observables. Use this when you need to
    // support one observable being dependent upon another.
    const obs1$ = of(1);
    const obs2$ = of('Banana');

    combineLatest([obs1$, obs2$])
        .subscribe(console.log)
    
    // Outputs
    // [ 1, 'Banana' ]
}

switch (process.argv[2]) {
    case 'from': fromOperator(); break;
    case 'of': ofOperator(); break;
    case 'filter': filterOperator(); break;
    case 'first': firstOperator(); break;
    case 'last': lastOperator(); break;
    case 'startsWith': startsWithOperator(); break;
    case 'debounceTime': debounceTimeOperator(); break;
    case 'distinct': distinctOperator(); break;
    case 'combineLatest': combineLatestOperator(); break;
    default:
        console.log(`Enter an argument (e.g. 'node index.js from'). Available arguments: 
        - from
        - of
        - filter`)
}