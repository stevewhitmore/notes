# JS In Depth

## Can you name two programming paradigms important for JavaScript app developers?

- Prototypal inheritance (also: prototypes, OLOO).
- Functional programming (also: closures, first class functions, lambdas).

## Functional Programming

- Pure functions / function purity.
- Avoid side-effects.
- Simple function composition.
- Examples of functional languages: Lisp, ML, Haskell, Erlang, Clojure, Elm, F Sharp, OCaml, etc…
- Mention of features that support FP: first-class functions, higher order functions, functions as arguments/values.

A **higher order function** is a function that takes a function as an argument, or returns a function. Higher order function is in contrast to first order functions, which don’t take a function as an argument or return a function as output.

## What is the difference between classical inheritance and prototypal inheritance?

classical is like all OOP but 'this' works weird in JS and the context can get lost if not used properly (same with 'new'). Luckily JS allows for prototypal inheritance which uses factory functions to avoid the need for the keyword 'this' or the .bind() function to lock in the context of 'this'

## Memoization

A memoized function caches results from some set of specific inputs

## Proxies

Fancy getters and setters

## Pass by reference or value

JavaScript is always pass by value - they pass objects as references and those references as value. This can be explained by saying pass by reference is actually passing in the memory location of the object. If you change a property the original object changes because you changed it at the original memory location (that is not reassigned)

## call, apply, bind

All 3 give a function context for "this".

```javascript
const obj = { things: 3 }

const addThings = (a,b,c) => {
  return this.things + a + b + c;
}

console.log( addThings.call(obj, 1,2,3) );
// 9
```

.apply is the same except the 2nd argument is an array instead of individual arguments

.bind doesn't execute but returns a function bound to an object

```javascript
console.log( addThings.bind(obj, 1,2,3)() );
```

will execute returned function

## Explain the event loop

Every function called gets put on the stack. Asyncronous functions like setTimeout or XHR functions or any Web API function pulls the function out of the stack into the Event table while the stack (queue) continues to execute. Once the stack is cleared the event loop throws the pulled out function back onto the stack

## Arrow functions

All arrow functions are anonymous so nothing is bound: not 'this' or arguments passed in.
Benefits:

- smaller
- can be written inline and still highly readable
- easier to make single purpose

## 'this' keyword

is really weird in part from its origins starting out heavily influenced by functional language Scheme (where the concept doesn't exist) and then OOP language Java

```javascript
const dog = {
  sound: 'woof',
  talk: function() {
    console.log(this.sound)
  }
}

dog.talk() //"woof"
let talkFunction = dog.talk
talkFunction() //undefined
```

'this' loses context when the dog's method is assigned to a variable. The 'this' word in functions gets context from where it's called rather than where it's defined. We can bind it to the context we want with the 'bind' function

```javascript
let boundFunction = talkFunction.bind(dog)
boundFunction() //"woof"
```

'this' can also be given context by assigning function to object:

```javascript
let cat = {
  sound: 'meow',
  noise: talk
}
cat.noise() //"meow"
```

These are all referencing the same function; they are not making copies
let yowl = cat.noise
yowl() //undefined, because it's pointing to the original talk() function but without context now. This is because we never explicitly binded talk() to cat. 
let bark = boundFunction() //"woof"

## JS is an interpreted language

so everything is a runtime error - there is no such thing as compile time errors. Typescript is compiled (transpiled) down to JS so it can have compile errors like assignment to wrong type or tslint failure

## 'var' vs 'let'

Main difference is scoping rules. Variables declared by var keyword are scoped to the immediate function body (hence the function scope) while 'let' variables are scoped to the immediate enclosing block denoted by { } (hence the block scope).

let cannot be used to create a property on the global object (window) but var can

var can be redeclared with var but let cannot and will raise SyntaxError

## Hoisting

functions can be called before declaration.

variables declared with var keyword are "hoisted" to the top of the block. Note only the declaration is hoisted but the value assignment stays put. They will be 'undefined'. Doing the same with 'let' will give a ReferenceError

## async, await

Inside a function marked as 'async', you're allowed to place the 'await' keyword in front of an expression that returns a promise. When you do, the execution of the 'async' function is paused until the promise is resolved.
Allows the user to write asyncronous code in a more syncronous manner. Allows user to have promises resolve sequentially instead of in parallel like raw promises.

## LinkedList vs Array

They're stored in memory differently

- Arrays are stored contiguously so they're very easy to traverse
- LL's have sequential access and may be stored anywhere in memory, therefore must be traversed 1 at a time

The main advantage of a LL is adding/removing items is much faster. If something needs to be added to or removed from an array everything to the right of it must be moved over. LL you just change where pointers are pointing

## Prototypes

Classes are like blueprints while prototypes are like delegates in politics. Representative of the masses. You basically make an object as the prototype and bind it to another object using `Object.setPrototypeOf(newObj, oldObj)`. 
*It's important to note that we're not instantiating a new object from a blueprint or making a copy like we do with classical inheritance. In this case we're delegating access between objects by creating a reference to an original object. We're saying newObj has access to oldObj properties.*

```javascript
function talk() {
  console.log(this);
  console.log(this.sound);
}
let animal = {
  talk: talk
}
let cat = {
  sound: 'meow!'
}
Object.setPrototypeOf(cat, animal);
animal.talk = function() {
  console.log('Hello, I am animal');
}
cat.talk(); // prints "Hello, I am animal" because 'animal.talk' changed
```

See /home/swhitmore/Desktop/protoype.js for an example. *Note: Object.setPrototypeOf isn't typically used in real apps. It's usually 'new' or 'Object.create'*

## 'new' keyword

This was made for people who are more familiar with classical inheritance.

```javascript
// We're pretending this is ES5
function Person(saying) {
  this.saying = saying
}
Person.prototype.talk = function() {
  console.log('I say:', this.saying)
}

// recreate 'new' to understand what it does
function new(constructor) {
  var obj {}
  Object.setPrototypOf(obj, constructor.prototype)
  var argsArray = Array.from(arguments)
  //var argsArray = Array.prototype.slice.apply(arguments) //ES5 since Array.from is ES6+
  constructor.apply(obj, argsArray.slice(1))
  return obj
}

var crockford = new Person('SEMICOLONS!!!!!')
crockford.talk()
```

the 'new' keyword creates an object. JS looks at the name of the function you're calling new on and uses it as a constructor. Adding a method or property to that object after the fact you use the 'prototype' keyword.
'new' does 4 things:

1. Creates a new object
2. Sets the prototype
3. Execute constructor with "this"
4. Return the created object (assuming the constructor DOES NOT return something)

## __proto__

points to object's prototype object
`crockford.__proto__` will be the simple object the Person function creates.
The 'prototype' property (like in Person() above) only exists for constructor functions.

## Object.create

A static method on the Object prototype that creates a new object with the prototype set to a certain object.
**Why does it exist when there's the 'new' keyword?**
Because it's more natural to the prototype model. The problem with 'new' is it doesn't behave exactly the same as how you'd expect if you're used to regular OOP languages.
We should be using this instead of 'new' and 'Object.setPrototypeOf()' because the performance is bad compared to 'Object.create'
 One idea for prototypes is to have an 'init' function that returns the object. This will allow for easy chaining:

```javascript
const cat = {
  init: function(sound) {
    this.sound = sound
    return this
  },
  makeSound: function() {
    console.log(this.sound)
  }
}
const waffles = Object.create(cat).init('meeooww')
waffles.makeSound() //"meeooww"
```

## 'class' keyword

Does everything above under the hood and acts as syntatic sugar for OOP programmers
**Why use this keyword?**
Depending on how you design things. If you're making things with an 'is a' perspective then it should be classical inheritance. If it's a 'has a' perspective then it should be prototypal structure. Working with OOP devs (Java shop) and it being Angular which uses Typescript we always go with classical inheritance. 
