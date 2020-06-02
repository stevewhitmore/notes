# Random JavaScript Goodies

Things I dont use often but come in handy.

```javascript
// get font size of selected element
const a = document.querySelectorAll('nav ul li a')[0]
const fontSize = window.getComputedStyle(a, null).getPropertyValue('font-size');
console.log(fontSize); // "16px"
```