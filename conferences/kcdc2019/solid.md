# SOLID – The five commandments of good software

## S - Single responsibility principle
Everything should do one thing.
There can only be one requirement when changed will cause a class to change.

Context can change the definition of this - e.g. repositories. Depends specifically on the
 project you're working on.

__Benefits?__
- Smaller classes which means less room for bugs and speghetti growth


## O - Open/Close 
Once it's done it's done. Only changes to fix bugs.
Changing requirements should mean new classes. 

_Meyer vs Polymorphic_
- Says to inherit existing (was before interfaces)
- Inheritence is easily brittle
- Polymorphic tends to lean towards interfaces

__Benefits?__
- By not changing code you're not able to break what exists


## L - Liskovs Substitution Prinicipal
A subclass should behave in such a way that it will not cause problems when used instead of a superclass.

__Rules__
- Contravariance (?) of method arguments in sub class
- Covariance (more precise) of return types in the sub class
- No new exception types are allowed to be thrown, unless they are sub classes of previously used ones
- Preconditions cannot be strengthened in a subtype
- Postconditions cannot be weakened in a subtype
- The history constraint - if you have a mutable object and you inherit it, you'r enot allowed to make it immutable and visa-versa

Clarification: should square inherit from rectangle or rectangle inherit from square?
Neither, they should inherit from super class.

__What about abstract base classes and interfaces?__
No proof of implemenetation so it's hard to follow this rule.

__Benefits?__
- Exception handling is more straight forward, so less bugs.


## I - Interface Implementation Primcipal
Breaking down interfaces in smallest peices possible to make easier to implement, and offers more control over who sees what.


## D - Dependency Inversion Principal
By making sure classes dont depend on specific implementaitons, it becomes easy to change things around.
> _Look into Dependency Inversion solutions_











