# Overview
- 2 major kinds of unit tests: isolated and integration tests

- e2e is against live running application (selenium)

## Unit Testing
A single "unit" of code
- accepted interpretation of "unit" generally is a class

## Integration and Functional Testing
More than a unit, less than the complete application
- Usually 2 units working together

## Scope
e2e: Frontend > Webserver > DB
int: user component > user service
unit: user component

## Angular Integration tests
Component is made up of ts and html file, these are technically 2 units of code, so these would be int tests


# Mocking
Dummies: placeholder objects
Stubs: object with controllable behavior
Spy: keeps track of which of its methods were called, how many times, and what it was called with
True mock: complex object that verifies it was used exactly in a specific way


# Types of unit tests

## Isolated
- Single unit of code: usually class

## Integration test
Test components in the context of a module (component + template)
  - shallow
  - deep (with child components)


# Writing unit tests
  - beforeEach must contain code to reset conditions for each new test. Prevents pollution and bleed-over
  - Should be in the following order in each test ("it"): arrange -> act -> assert
  - The act should generally not be in the beforeEach()
 
## Structuring tests
AAA Pattern:
- Arrange all necessary preconditions and inputs
- Act on the object or class
- Assert that the expected results have occured

## DAMP vs DRY
- DRY (don't repeat yourself) remove duplication
- DAMP - mostly DRY but repeat yourself as necessary
  - A test shoould be a complete story, all within the it()
  - You shouldn't need to look around much to understand the test
  - Techniques:
    - Move less interesting setup into beforeEach()
    - Keep critical setup within the it()
    - Include Arrange, Act, and Assert inside the it()

  
# Isolated Unit Tests
- No template interaction. Purely calling on functions to test input/output behavior
- Separate out the 3 A's by grouping them together inside the it() and separating them by a blank space

## Mock Services
- Can mock a service by creating jasmine "spy" object ->
`mockService = jasmine.createSpyObj('method1', 'method2', 'method3')`

You can do a "state" test that checks on the state of the component (x = y after z happens) or do an interaction test (x called y in v class/service with the right arguments after z happened)


# Shallow Integration Tests
- Normally both shallow/isolated tests will be in the same .spec file. Only do one .spec file per component/template
- As of 09/2018 you still need to set source-maps to false in the test command because of how Karma and zone.js interact, producing misleading error messages that cover up the real cause of errors that may occur in unit tests

## The TestBed
Core testing utility used to create special module just for testing.
- TestBed.createComponent() returns a ComponentFixture which is a wrapper for a component which includes more properties to help with testing
- fixture.componentInstance is the actual instance of the component within the fixture wrapper
- __Only import the modules you need__ this will significantly speed up the time it takes for tests to run

## Using NO\_ERRORS\_SCHEMA
Testing module doesn't know about routerLink directive. We don't want to have to deal with this for 2 reasons:
  1. Creating a mock will be a big headache because AppRoutingModule is complex
  2. We don't want a live AppRoutingModule because the app may try to route for real during tests

NO\_ERRORS\_SCHEMA tells angular to ignore unknown attributes and elements. __USE SPARINGLY__ since this can very easily cover up issues and causes of issues. Other ways to deal with this routerLink error are mentioned below.

## Testing Rendered HTML
    fixture = TestBed.createComponent(HeroComponent);
    fixture.nativeElement
nativeElement gets a handle to the DOM element that represents the container for the template
- Better to use toContain vs toEqual to prevent tests from being brittle 
- After properties are set we need change detection to execute in order to pick up DOM properties

## NativeElement vs DebugElement
- NativeElement exposes DOM api
- DebugElement like NativeElement but is more of a wrapper that includes more properties to help with testing.

    fixture.debugElement.query(By.css('a')).nativeElement.textContent
    // is the same as
    fixture.nativeElement.querySelector('a').textContent
    // they do the same thing but get there different ways

Why bother with DebugElement then? You have more properties to help with testing. For example, if you wanted to test routerLink, you could access that property via DebugElement

## Mocking an Injected Services
How we've been doing it is good enough

    TestBed.configureTestingModule({
        ...
        providers: [
            { provide: RealService, useValue: mockService }
        ]
    })
    

# Deep Integration Tests
Tests interaction between component and child components
*In angular, a component is actually a subclass of a directive. Directives seem to be attributes like routerLink and components seem to be elements (<app-hero ...></app-hero>) but in the inner workings of angular a directive is actually the parent class for attirbute directives and compoennts.*

- Adding child components to the declarations will call into question their dependences too, so it might be best to import module instead of each individual component (if it's not too much bloat)

## Integration Tests of Services
- Need to import HttpClientTestingModule and HttpTestingController
- Need to get handle to mock http handle service so we can adjust it and control it inside our test. This is where HttpTestingController comes in

can do either of the following:

    beforeEach(() => {
    ...
        service = TestBed.get(YourService)
        httpTestingController = TestBed.get(HttpTestingController)
    });

    // or 
    
    it('should do something', 
        inject([YourService, HttpTestingController], 
            {service: YourService, controller: HttpTestingController} => {
        ...        
    }));

The latter is disgustingly verbose so stick with using TestBed.get()


# Testing DOM Interaction and Routing Components

## Triggering Events on Elements
This is a deep integration thing, unless we include the element in a mock child component. 
- Call spyOn component method you're testing 
  - Call with .and.callThrough() if we're setting a return value in the service stub, else call with .and.returnValue() and pass in of(mockData) with mockData being a require('some-json-file'). That way when we perform the act we can assert that the method we're testing on is called with the correct data.

- Trigger click event in child component with something like the following:
    const heroComponents = fixture.debugElement.queryAll(By.directive(HeroComponent));
    heroComponents[0].query(By.css('button'))
      .triggerEventHandler('click', {stopPropagation: () => {}})
      
    expect(fixture.componentInstance.delete).toHaveBeenCalledWith(HEROES[0]);

The TOH app is used in the tutorial as an example and they call $event.stopPropagation(). We wont need that since we make all our button types="button". We can just pass null instead of that empty stopPropagation dummy function inside triggerEventHandler.

## Emitting Events from Children
We care more about the bindings between the parent and child components, so triggering a button click in the child component is kinda redundant and unnecessary. 

A cleaner alternative is to trigger the event emit directly from the child's EventEmitter property:
    
    const heroComponents = fixture.debugElement.queryAll(By.directive(HeroComponent));
    (<HeroComponent>heroComponents[0].componentInstance).delete.emit(undefined);

This produces the exact same results as the button click above.
- ultimately we don't care about the value that's emitted up because that has nothing to do with the current (parent) component. We can set that ourselves and verify that once the method we're testing is called it's being called with the value we set earlier in the test

## Raising Events on Child Directives
3rd and final technique on testing interaction between parent and child components

    const heroComponents = fixture.debugElement.queryAll(By.directive(HeroComponent));
    heroComponents[0].triggerEventHandler('delete', null)
    
- This is the most shallow option since we're telling the DebugElement to trigger the event rather than telling the child component to raise or emit its event. We don't even have to know if the child component actually has a delete EventEmitter but we tell the DebugElement to call the delete EventEmitter, whether it's really there or not for the child, from the child. Another perk to using the DebugElement is the layer of abstraction gives us more flexibility so we can do things like this.

## Interacting with Input Boxes
This is the kind of thing we do in both shallow and deep integration tests.

Arrange: 
- getHeroes
- fixture.detectChanges()
- set "name" value to whatever string
- inputElement -> capture input box with fixture.debugElement.query(By.css(...)).nativeElement since we want the underlying DOM element
- addButton -> capture button's debugElement (don't want underlying DOM element since we're going to call debugElement triggerEventHandler
fixture.debugElement.queryAll(By.css('button'))[0];

Act: 
- call addHero
mockHeroService.addHero.and.returnValue(of({id: 5, name: name, strength: 4}));
- inputElement.value = name
- addButton.triggerEventHandler('click', null)
- To check if html elements are updated we need to set off change detection:
fixture.detectChanges()

Assert:
- Grab all textContent of <ul>
const heroText = fixture.debugElement.query(By.css('ul')).nativeElement.textContent;
- Check contents of returned string
expect(heroText).toContain(name);

## Testing with ActivatedRoute
Don't test the framework! Test your code. 
- Assume the framework works correctly. 
- You want to test to see if your code is interacting with the framework correctly.
- Test that you're calling or configuring routes correctly, not that the routes actually work.
- For this reason we don't let our code actually route

If we wanted to mock ActivatedRoute we could do the following:

To mock

    +this.route.snapshot.paramMap.get('id');

we would write

    const mockActivatedRoute = {
      snapshot: { paramMap: { get: () => { return '3': }}}
    }

In the beforeEach(), then in the TestBed.configureTestingModule

    providers: [
      {provide: ActivatedRoute, useValue: mockActivatedRoute},
      {provide: Location, useValue: mockLocation}
    ]

__Note: "Location" is a global variable in the javascript library (Window.location, Document.location). Don't use this since it's not part of the framework. Import it from @angular/common.__

*Random Side Note: If using ngModel in any othe templates and you get an error for it not being a property of "input", make sure you import FormsModule into your spec file. No configuration or anything should be needed.*

## Mocking the RouterLink
schemas: [NO\_ERRORS\_SCHEMA] in the TestBed.configureTestingModule method was stopping errors related to the routerLink. We should mock the routerLink instead of using this by creating a stub with a @Directive decorator:

    @Directive({
      selector: '[routerLink]',
      host: { '(click)': 'onClick()' }
    })
    
    export class RouterLinkDirectiveStub {
      @Input('routerLink') linkParams: any;
      navigatedTo: any = null;
      
      onClick() {
        this.naviagedTo = this.linkParams;
      }
    }

This is a template noted in the official documentation to use for testing directives. You can put it in its own file like we've been doing or you can include it directly in the spec file above the first describe() but after the imports.

Putting it to use:

    it('should have the correct route for the first hero', () => {
      mockHeroService.getHeroes.and.returnValue(of(HEROES)); // set initial data
      fixture.detectChanges(); // trigger change detection
      const heroComponents = fixture.debugElement.queryAll(By.directive(HeroComponent));
      
      let routerLink = heroComponents[0]
                        .query(By.directive(RouterLinkDirectiveStub))
                        .injector.get(RouterLinkDirectiveStub);
                        
      heroComponents[0].query(By.css('a')).triggerEventHandler('click', null);
      
      expect(routerLink.navigatedTo).toBe('/detail/1');
    });
    
.query(By.directive(RouterLinkDirectiveStub)) gets DebugElement for the anchor tag that has this directive
.injector.get(RouterLinkDirectiveStub) is a handle to the actual directive


# Advanced Topics

## Adding Async Code 
So far we've been making our observables behave syncronously so we haven't had to deal with testing asyncrounous code. 

We can pass "done" to our tests to tell jasmine we're dealing with asyncronous code. Here's one example if we were to use debounceTIme to prevent many clicks from causing a bunch of API calls for save(). save() calls heroService.updateHero but instead of calling it right away we'll wrap it in debounceTime with a 250ms delay. For the test we'd do something like the following:

    it('should call updateHero when save is called', (done) => {
      mockHeroService.updateHero.and.returnValue(of({})); 
      fixture.detectChanges();
     
      fixture.componentInstance.save();
     
      setTimeout(() => {
        expect(mockHeroService.updateHero).toHaveBeenCalled();
        done();
      }, 300);
    }); 

## fakeAsync
The problem with putting setTimeout places is it'll dramatically slow down tests. Angular provides a solution to this by giving us fakeAsync from @angular/core/testing

We would change the above to somethign like this:

    it('should call updateHero when save is called', fakeAsync(() => {
      mockHeroService.updateHero.and.returnValue(of({})); 
      fixture.detectChanges();
     
      fixture.componentInstance.save();
      tick(250);
      
      expect(mockHeroService.updateHero).toHaveBeenCalled();
    })); 

This is possible because Angular runs inside zone.js and the fakeAsync wrapped code runs inside a special zone that zone.js will create that allows us to control the clock inside of it.

What if we don't know exactly how long we need to wait?
We use flush() instead. It says "look at the zone and see if there are any tasks that are waiting. If there are, fast forward the clock until those tasks have been executed".

## async
This is meant to be used with Promises

We would change the above to something like this:

    it('should call updateHero when save is called', async(() => {
      mockHeroService.updateHero.and.returnValue(of({})); 
      fixture.detectChanges();
     
      fixture.componentInstance.save();
      
      fixture.whenStable().then(() => {
        expect(mockHeroService.updateHero).toHaveBeenCalled();
      });
    })); 


# Course Summary: Key Takeaways
- Isolated tests should be the preference because they're the quickest and least brittle to write
- If you want to test template interaction witck with shallow integration tests
- Only use deep integration tests when absolutely necessary
