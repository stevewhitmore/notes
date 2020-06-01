# Defense Against the JavaScript dark arts

http://bit.ly/js-dark-arts - slides

## Equality
- Double equals checks for loose equality (faley == falsey, etc)
- Uses type coersion ('5' == 5 //true)
- Triple equals for deep equality

## Passing
- Pass by value on primitives
- Pass by reference on objects (kind of)

## Shallow copy vs deep copy in JS
Lodash is commonly used for deep copies (nested objects)

Poor man's deep copy `JSON.parse(JSON.stringify(obj))`

## Default params
ES5 use || in assignment. Es6 can do in fn definition
`
const func = (x = 1) => { ... }
`
Can also default value of fn instead of primitive or object
`
const func = (x = otherFunc()) => { ... }
`

## Asyncronous JS
- JS is single threaded

## Other good resources to look into
- "JavaScript 30" by Wes Bos (challenges in vanilla js)
- Youtube: "What the heck is the event loop anyway?"

## Promises
- Use axios library for http requests - better than callbacks becausecan chain promises


## Formatting and Linting
- Can prevent push to repo if set up

linting setup:
`install eslint`
`run -eslint --init` -> preconfiged w/big 3 frameworks
