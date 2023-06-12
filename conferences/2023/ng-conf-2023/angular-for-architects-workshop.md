# Angular For Architects Workshop

## Types, Interfaces, Classes, and Enums

- <https://quicktype.io/typescript> paste in json to generate interface

- using `const enum` translates each value to numerical value

Look into utility types [ReadOnly](https://www.typescriptlang.org/docs/handbook/utility-types.html#readonlytype) and [Partial](https://www.typescriptlang.org/docs/handbook/utility-types.html#partialtype).

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

Types are good for union types e.g. `type Pet = 'dog' | 'cat' | 'fish'`;

### Benefits of interfaces/types vs classes

- Can describe the "shape" of data for properties, parameters, custom types, and more
- Can help drive consistency
- Types can define primitives AND object-like data structures
- Neither create instances
- No impact to bundle size

