# Angular World Tour: Meetup AngularKC

Observables do nothing until we subscribe to them.

Routing emits params via paramMap Observable
FormControl emits value via valueChanges Observable
Http client emits the response via an Observable

What are observables looking for?
next, error, and complete

```
const sub = source$.subscribe({
  next: apple => console.log()
  err: 
})
```

```
// procedural approach to observables
this.sub = this.productData.getProducts().subscribe((data) => {
  this.products = data;
});

// declarative approach
this.products$ = this.productService.getProducts();
```

How to pass data in a declarative pattern?

## The State of Angular

In v12 `ng build` by default does production mode (finally)

goo.gle/angular-enableing-best-practices

View Engine is deprecated. Everything needs to migrate over to Ivy (this is more relavant for library authors)
**Look into how to do this** - goo.gle/improving-library-distribution


