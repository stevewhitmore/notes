---
title: Types vs Interfaces
tags:
  - TypeScript
---
# Types vs Interfaces

Both are functionally similar, so when to use each?

## Interfaces

Are generally used to define the shape of an object. You define the properties you expect to see in the object assigned to the interface.

## Types

Are good for when you have a group of interfaces to choose from:

```typescript
export type ResponseModels =
    m.EntityAddressModel
    | m.EntityIndDemographicModel
    | m.EntityOrgDemographicsModel;
```

or for having a group of primitives to choose from:

```typescript
export type EntityType = 'Beneficiary'
    | 'Defendant'
    | 'Legal Team'
    | 'Plaintiff';
```

