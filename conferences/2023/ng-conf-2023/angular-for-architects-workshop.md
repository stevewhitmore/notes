# Angular For Architects Workshop

## Types, Interfaces, Classes, and Enums

- <https://quicktype.io/typescript> paste in json to generate interface

- using `const enum` translates each value to numerical value

> **Look into utility types [ReadOnly](https://www.typescriptlang.org/docs/handbook/utility-types.html#readonlytype) and [Partial](https://www.typescriptlang.org/docs/handbook/utility-types.html#partialtype).**

### Using Types vs Interfaces

interface definitions can be used to represent the shape of an object-like data structure

```typescript
interface Account {
    accountNumber: number;
    balance: number;
}
```

type alias declarations can be used for primitive types and object-like data structures

```typescript
type account = string | { accountNumber: string, balance: number };
```

### Additional Uses for type

A Mapped Type

Computed properties can be used to create a mapped type

```typescript
type Keys = "firstname" | "lastname"

type PersonType = {
  [key in Keys]: string
}

const person: PersonType = {
  firstname: "John",
  lastname: "Doe"
}
```

Types are good for union types e.g. `type Pet = 'dog' | 'cat' | 'fish'`.

You could use a union in a type instead of an optional property in an interface.

### Benefits of interfaces/types vs classes

- Can describe the "shape" of data for properties, parameters, custom types, and more
- Can help drive consistency
- Types can define primitives AND object-like data structures
- Neither create instances
- No impact to bundle size

## Component View Models

2-way data binding can make model/view too tightly coupled - especially in more complex applications. One way around this is cloning the model. Creates a ViewModel to bind to instead of the actual data model. 

> spread operator only does shallow clones  
> you can use Lodash or Clone-Deep npm package for deep clone  
> **Look into this more ^**

ViewModel class (rather than a full clone) is good for parsing out pieces of the api response data or adding properties to it for what's needed in the view.  

- Assuming you have a model for the full response dataset, you can use Partial<T> in your ViewModel class.  

ViewModel acts as a scratchpad until the data is in a state ready for sending up to the api. The model is the single source of truth. Don't change it until it's ready to persist.

### ViewModel Services

Good for when your component represent several connected models

- Keep all the logic in it for a single source of truth
- Hides assembly of complex ViewModels
- Shares ViewModels with collaborating components
- Easier to test than a component
- Lets the Presenters focus on presenting
- Keeps the Views and ViewModels clean

## HttpClient and RxJS operators

When possible, make the response data how it needs to be on the backend and avoid using RxJS  

For making http calls that rely on the response from other http calls - switchMap and mergeMap are commonly used for this

### Understanding switchMap

```typescript
 this.searchCharacters$ = this.formGroup.get('characterName').valueChanges.pipe( // <-- outer observable
    debounceTime(500), // this is necessary in this example because it lets the user type more before an http call is made
    switchMap(name => {
      return this.dataService.getCharacter(name); // <-- inner observable (the data we actually care about)
    })
 );
```

switchMap cancels last response and returns latest.  

This is especially useful when you need individual values from an array.  

Switch between streams with switchMap - go from outer observable to inner.

### mergeMap, concatMap, and forkJoin

mergeMap doesn't preserve order while concatMap does.  

forkJoin no longer uses array syntax - instead uses object syntax -

```typescript
return forkJoin({
    characters: this.getCharacters(),
    planets: this.getPlanets(),
})
```

> **Look up differences between forkJoin and combineLatest**

## RxJS Subjects


