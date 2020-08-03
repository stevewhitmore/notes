# Angular Basics *(needs revising and cleanup)*

Firebase -
Google developed database system
- handles setting up authentication system setup
- JSON format

Webpack to handle individual components of the app
- bundles them into one file to deploy

Protect sensitive areas using Routing

Angular CLI: allows you to generate components, services, and routes

karma.conf.js is a testing library to use with the project
package.json installs libraries, used by npm

‘let’ block scopes our variables, doesn’t let values leak outside enclosing blocks
‘const’ has same rules as ‘let’ but can’t just reassign value, takes more to change value


**TypeScript**
- Can assign type 'any' if dont care or unsure what type
	- if array can be 'any[]'


**Decorators**
A function that adds metadata to a class, its members, or its methods arguments
- used a lot, preceded by ‘@‘ symbol
- immediately precede the classes they apply to
- technically a function getting an object passed into it 
- double curly braces indicate data binding 

- you add the export statement to the class declaration to make it available to be imported to other components in the app
- ‘export’ breaks up code into modules to import into other files.

app.module.ts - the root module
- launches app.component.ts (the meat of the app)

main.ts - runs the bootstrap method against app.module.ts
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic'; - converts code to be displayed in browser at runtime

package.json requires ‘name’ and ‘version’ properties, the rest is for the users benefit
- ‘scripts’ - ‘postinstall’ will run immediately after ‘npm install’ so it will install everything you need

SystemJS was used but Webpack seems to be more popular now for bundling modules

** Gulp tasks
- If you do not return a stream, then the asynchronous result of each task will not be awaited by its caller, nor any dependent tasks.

‘event-stream’ - uses streaming (nodes philosophy) instead of temp directory to keep src and compiled files separate 
** task managers invoked where? webpack? that + other?


**Componenet Checklist**
	Class -> Code
	- Clear name (PascalCasing)
	- Append "Component" to the name
	export keyword
	Data in properties
	- Approprate data type and dfault value - camelCase with first letter lowercase
	Logic in methods
	- also use camelCase
	
	Decorator -> Metadata
	Component decorator
	- Prefix with @; suffix with ()
	selector: component name in HTML
	- prefix for clarity
	template: view's HTML
	- correct HTML syntax
	
	Import what we need (from modules or angular itself)
	Defines where to find the members that this component needs
	import keyword
	Member name
	- correct spelling/casing
	Module path
	- enclose in quotes
	- correct spelling/casing - don't need to specify file extension
	
	Debug checklist:
	- View console errors
	- HTML
		- close tags
		- angular directives are case sensitive
	- TypeScript
		- close braces
		- case sensitive


# Component = Template + Class[Properties + Methods] + Metadata


**Data Binding** 
Coordinates communication between the components class and its templalte and often involves passing data.
Template <- Class - Values for display
Template -> Class - Raises events or user input to give back to the class


# Interpolation
DOM <-- Component
<h1>{{pageTitle}}</h1>
one way binding - from the class property to the template
- Can do more than display a property
{{'Title: ' + pageTitle}}
{{2*20+1}}
{{'Title: ' + getTitle()}}
<h1 innerText={{pageTitle}}></h1>
- Text inside of double curlies are called 'Template Expressions'
We use Interpolation if we want to display READ ONLY data 

# Property Binding
DOM <-- Component
<img [src]='product.imgUrl'>
[] - binding target
'' - binding source
This is generally the prefferred method of one way data binding, but in some cases we need to use interpolation:
<img src='http://somewebsite.com/{{product.imageUrl}}'>

# Event Binding
DOM --> Component
<button (click)='toggleImage()'>
() - target event
'' - template statement - usually a component class method enclosed in quotes
https://dveloper.mozila.org/en-US/docs/Web/Events for a list of standard events and documentation

# Two-way Binding
DOM <--> Component
Template <-> Class
<input [(ngModel)]='listFilter'>
We need to use ngModel directive for two-way binding.
[] - property binding from the class property to the input element
() - to indicate event binding to send notification of the user entered data back to the class property
we assign this directive to a template expression
think of a banana in a box! [()]
** for ngModel make sure to import FormsModule from forms and include in imports array in app.module.ts


**Directives**
Custom HTML element or attribute used to power up and extend our HTML

Built in directives
	- Structural Directives
		* marks the directive as structural
		*ngIf: if logic
		*ngFor: for loops
		*ngFor='let product of products' - the 'product' is a template input variable that iterates
		
		Why 'product of products' and not 'product in products'?
			for...of iterates over iterable objects, such as an array
				let things = ['foo', 'bar', 'baz'];
				for (let thing of things) {
					console.log(nickname)
				}
				result: foo, bar, baz

			for...in iterates over the properties of an object
				let things = ['foo', 'bar', 'baz'];
				for (let thing of things) {
					console.log(nickname)
				}
				result: 0, 1, 2

	- if *ngIf evaluates to false, it and its children are removed from the DOM
	BrowserModule enables our Components to utilize these structural directives


**Template Checklist**
Inline Template
- For short templates
	- specify template property, use es2015 back ticks for multiple lines, watch syntax
Linked Templates
- For longer tempates
	speciry the temlpateUrl perperty, define the path to the HTML file


**Component as a Directive Checklist**
1. Use the directive as an element in the template for any other component. We use the directive component selector as the directive name.
2. We then declare the component so it's available to any template associated to this module. 
3. Pass the component into the declarations array in th NgModule of the app.module.ts file


**Transforming Data with Pipes**
- Transform bound properties before display
- Built-in pipes
	* Date
	* Number, decimal, percent, currency
	* json, slice
	* etc
- Custom pipes

Examples:
<img [src]='product.imageUrl'
	 [title]='product.productName | uppercase'>

{{ product.price | currency | lowercase }}

Some pipes allow for arguments
{{ product.price | currency:'USD':true:'1.2-2' }}


**Defining an Interface**
A specification identifying a related set of properties and methods
A class commits to supporting the specification by implemeting th interface
Use the interface as a data type
es2015 does not support interfaces, but typescript does, so its only used during development

Interface is a specification -

export interface IProduct {
	productID: number;
	productName: string;
	releaseDate: Date;
	calculateDiscount(percent:number): number;
}

Using an interface as a data type -

import { IProduct } from './product';

export class ProductListComponent {
	pageTitle: string = 'Product List';
	...
	products: IProduct = [...];

	toggleImage(): void {
		this.showImage = !this.showImage;
	}
}

Defines custom types
Creating interfaces:
- interface keyword
- export it
Implementing interfaces
- implements keyword and interface name
- write code for each property and method


**Encapsulating Component Styles**
Rather than have an external stylesheet linked to index.html we use 'styles' and 'styleUrls' in the component decorator
@Component({
	selector: 'pm-products',
	templateUrl: './product-list.component.html',
	styleUrls: ['./product-list.component.file1.css', 
				'./product-list.component.file2.css']
})


**Using Lifecycle Hooks**
Component lifecycle
Create > Render > Create and render children > process changes > destroys it before removing its template from the DOM.

OnInit: Perform component initialization, retrieve data
OnChanges: Perform action after change to input properties
OnDestroy: perform cleanup

to use lifecycle hook, you need to implement its interface


**Bulding a Custom Pipe**
import { PipeTransform } from '@angular/core';
@Pipe({
	name: 'convertToSpaces';
})
export class ConvertToSpacesPipe implements PipeTransform {
	transform(value: string, character: string): string {

	}
}

Using a Custom Pipe
Template -
<td>{{ product.productCode | convertToSpaces:'-' }}</td>

Pipe -
transform(value: string, character: string): string { }

product.productCode == 1st argument passed into transform()
convertToSpaces == pipe name
'-' == pipe parameter, the 2nd argument passed into transform()


**Nesting Components**
There are 2 ways to use a component and display its template. 
1. As a directive
	<body>
		<pm-root></pm-root>
	</body>
in the index.html file
2. as a Routing target
- appears to user that they've traveled to another view

A component is nest-able if:
- Its template only manages a portion of a larger view
- It has a selector
- It communicates with its container (optional)

If a nested component wants to receive input from its container it must expose a property to its container using @Input -
# star.component.ts
export class ChildComponent {
	@Input() rating: number;
	starWidth: number;
}
# product-list.component.html
<td>
	<pm-star [rating]='product.starRating'></pm-star>
</td>

Container component uses property binding to pass value into nested component. The only time we can specify a nested components property as a property binding target on the left side of an equals sign is when that properity is decorated with the input decorator.
The example above the @Input is combined with 'rating'.

Riasing an Event (@Output)
- Nested component sends info back to container with @Output
	- The only way this can happen is if the property type is an event. The event to pass becomes the event payload. In Ang, an event is defined with an event emmitter object. 

The only time we can specify a nested components property as an event binding target on the left side of an equals sign is when that properity is decorated with the output decorator.

# star.component.ts
export class StarComponent {
  @Input() rating: number;
  starWidth: number;
  @Output() notify: EventEmitter<string> = new EventEmitter<string>();
  // TS allows for generics (see Java notes from cs200)
  // the 'string' is identified as the type of the event payload

  onClick() {
  	this.notify.emit('clicked');
  }
  // uses notify event property and uses the emit method to raise the event to the container. If we want to pass data in the event payload, we do so through the emit method.

# star.component.html
<div (click)='onClick()'>
  ... stars ...
</div>

Input decorator - used whenever info needs to be passed from container to nested
- Attachd to a property of any type
- Prefix with @; Suffix with () since it's a function
Output decorator - used whenever info needs to be passed from nested to container
- Attached to a property declared as an Event Emitter
- Use the generic argument to define the event payload type
- Use the new keyword to create an instance of Event Emitter
- Prefix with @; Suffix with () since it's also a function

Container Component
Use nested component as a directive
- Directive name -> nested component's name
Use property binding to pass data to the nested component
Use evnt binding to respond to events from the nested component
- Use $event to access the event payload passed from the nested component


**Services and Dependency Injection**
# Service
A cass with a focused purpose.
Used for features that:
- Are independent from any particular component
- Provide shared data or logic across components
- Encapsulate external interactions

Service -> Injector -> Component



**Retrieving Data using HTTP**
# Setup
Add HttpClientModule to the imports array of one of the application's Angular Modules.

# Service
- Import what we need
- Define a dependency for the http client 
	- use constructor parameter
- Ceate a method for each http request
- Call the desired http method, such as get
	- pass in the url
- use generics to specify the returned type -- this will transform the raw http response to the specified type
- do error handling as desired

# Subscribing
- Call the subscribe method of the returned observable
- Provide a function to handle the emitted item
	- Normally assgns a property to the returned JSON object -- if property is bound to a template the retrieved data appears in the view
- Provide an error function to handle any returned errors


**Navigation and Routing basics**
# Creating a component via Angular CLI
ng g c path/name/product-detail.component --flat
ng // angular cli
g // generate
c // component
then name of component (starts with path leading to if applicable)
--flat // stops auto generated folder from generated component

How to prevent undefined or NULL property errors due to asyncronous loading --
{{pageTitle + ': ' + product?.productName}}
? // safe navigation operator
if property is not loaded, '?' returns undefined or NULL and prevents error

This is good, but doesn't work with 2 way binding --
[(ngModel)]='product?.productName' // this dont work
it's also tedious af if displaying many properties

better to use *ngIf='product' on parent div *


# How Routing Works
Many views take turns appearing on the same page -- we control this with routing.
- Configure a route for each component
- Define options/options
- Tie a route to each option/action
- Activate the route basec on the user action
- Activating a route displayed the component's view

<a [routerLink]="['/welcome']">Home</a> // need to configure server to do url rewrites in the html5 format -- no old style internal links 'http://someurl.com/page#link'

router looks for route definition --
{ path: 'welcome', component: WelcomeComponent }

displays template whereever we specify with built in element 
<router-outlet></router-outlet>

# Configuring Routes
In app.module.ts --
import { RouterModule } from '@angular/router';
- registers the router service
- declares the router directives
- exposes configured routes

We add RouterModule to imports array but also need to ensure the routes are available to the app by passing routes to RouterModule

@NgModule({
	imports: [
		...
		RouterModule.forRoot([
    	    { path: 'welcome', component: WelcomeComponent },
    	    { path: '', redirectTo: 'welcome', pathMatch: 'full'}, // defines a default route
    		{ path: '**', redirectTo: 'welcome', pathMatch: 'full'} /* this is usually the following --
			{ path: '**', component: PageNotFoundComponent } but for simple example it just redirects home */
		])
	...

*Things to note - 
	- There are no leading '/'s
	- Order matters. The router uses a 'first match wins' strategy. This means more specific routes should always come before less specific ones. 

There must be a <base href="some path"> tag in the <head> of index.html. AngularCLI will do this for us.



**Displaying Components**
Route or nest?
	Nest-able components
	- define a selector
	- nest in another component
	- no route
	Routed components
	- No selector
	- Configure routes
	- Tie routes to actions, which activate the route to display the view


**Doing Routing**
1. Configuring the routes
Define base element in the index.html file 

Add RouterModule to NgModule imports array
	- Add each route (RouterModule.forRoot)
	- Order matters
	path: url segment for the rout
		- No leading slash
		- '' for default rout
		- '**' for wildcard rout

Most routes include a component; reference to component -- not a string name, not enclosed in quotes

2. Tying routes to actions
Add the [routerLnk] directive as an attribute
	- Clickable element
	- Encose in square brackets

Bind to a link parameters array
	- First element is the path
	- All other elements are route parameters 

3. Placing the View
Add the <router-outlet> directive
	- Identifies where to display the routed component's view

**More Routing Techniques**
# Passing Parameters to a Route
Example: specific product detail page (product ID)
{ path: 'products/:id', component: ProductDetaiComponent }

# Activating a Route with Code
# Protcting Routes with Guards


**Modules**
# Bootstrap Array
Defines the component that is the starting point of the application.
- This array should only be used in the root application module, AppModule

# Declarations Array
Every component, directive, and pipe we create is declared by a module. 
We use this array of the NgModule decorator to define the components, directives, and pipes that belong to this module.
- Every component, directive, and pipe we create must belong to ONE AND ONLY ONE module
- Only declare components, directives and pipes. Don't add other classes, services, or modules to the declarations array.
- Never re-declare components, drectives, or pipes that belong to another module
- All declared components, directives, and pipes are private by default
	- only accessible to other components, directives, and pipes declared in the same module
- Modules provide the template resolution environment for its component templates


# Exports Array
Allows us to share components, directives, and pipes with other modules.
- Export any component, directive, or pipe if other components need it
- Re-export modules to re-export their components, directives, and pipes. This is useful if consolidating features for multiple modules to build a convenience or shared module.
- We can re-export something without importing it first. A module only needs to import The 3 that are required by the components declared in the module, but the module can still provide capabilities to other modules that import it by re-exporting.
- Never export a service. Services added to the Providers Array are registered with the root application injector, making them available for injection for any class in the app. 

# Imports Array
We can extend a module by importing capabilities from other modules.
- Importing a module makes available any exported components, directives, and pipes from that module.
- Only import what this module needs. 
- Importing a module does NOT provid access to its imported modules. In other words, imports are not inherited. However, if the shared module re-exports what it is importing, then it does pass on its imported modules.
Think of the structure of module relationships as a box rather than a tree structure

# Providers Array
- Any service provider added to the providers array is registered at the root of the application. If you want to encapsulate a service, add it to the component, not the module.
- Don't add services to the providers array of a shared module. Consider building a CoreModule for services and importing it once in the AppModule.
- Routing guards must be added to the providers array of a module. Must be at module level for navigation. 


**Generate A Module via Angular CLI**
ng g m folder/name-of-module --flat -m some-name.module
-m flag says to import it into "some-name.module" (assuming it exists already). Generated module then gets added to Imports Array and its file is created and placed in specified location

In the Imports Array --
RouterModule.foroot:
- Registers Router service
- Declares router directives
- Exposes configured routes
RouterModule.forChild:
- Declares router directives
- Exposes configured routes

Using the forChild, RouterModule knows not to reregister the router service.


# Shared Modules
Saves us from having to continuously import CommonModule, FormsModule, RouterModule, etc
