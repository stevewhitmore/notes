# Ng Conf 2021: Performance Workshop

UAAO Performance Improvement
Understand, Audit, Analyze, and Optimize
- Basically the scientific method applied to performance analysis

Example app with more resources in the README - <https://github.com/jeffbcross/victor-videos>

> Don't test `ng serve` unless you run it in production mode.

## Understand

### Understanding priorities 

Users should be able to see video content ASAP, and be able to click on a video

### Key Content

This exercise will focus on loading the home page quickly, and getting users to the video they want to see.

URLs
- home is how most users arrive at the site
- detail/id is what users navigate to


## Audit

### Audit Configuration

All measurements collected via Lighthouse in an incognito window of Chrome v 87.0.4280.141 with **Desktop** configuration (Category: Performance, Device: Desktop) on Jeff Cross's Mac Pro (2019 Model) at his office (3.3GHz 12-core Intel Xeon W, 768 GB RAM).

> It's also a good idea to make sure any extensions are not enabled in incognito mode

All unnecessary programs and processes were shut down to increase consistency and reliability of measurements.

### Baseline Audit

The median of 3 samples is used for each audit

|                                    | Home Page | Video Detail Page |
| ---------------------------------- | --------- | ----------------- |
| First Contentful Paint (seconds)   | 
| Largest Contentful Paint (seconds) |
| Time to Interactive (seconds)      |
| Lighthouse Performance Score       |

Other metrics can be captured but generally if the above are improved then many others will as well.

> You can set "budgets" in `angular.json` to keep bundle sizes in check. Angular CLI will refuse to build if budgets are exceeded.

#### Performance Tab (Chrome Dev Tools)

Has everything in the context of your app. Has:
- Network
- Heap
- JS Call Tree
- Screenshots

Shows it all in a unified timeline where you can see things in context to better determine how things are related to each other. (which things happen, in what order, and dependant on what other things)

> Repeating patterns usually indicate a loop of some kind, such as CD getting called repeatedly or something happening repeatedly with a bunch of elements in it that's running an expensive task.

> Be weary of calling methods on the async pipe. It creates a lot more processes to use up memory than normal method calls.

> Pipes are memoized by default which helps with memory consumption.

#### Analyze

In the Network tab, check if 
- requests are sent over http/1.1 or http2 in the "Protocol" column. http2 is much better because all requests share a single connection instead of doing back and forth of initiating a new connection for each resource being loaded
- Look for compression under the "Size" column.

> Differential loading is for old ES versions/browsers (such as IE) and doesn't necessarily guarentee a performance boost. See about disabling it in Angular apps.

You can also emulate throttling in the Network tab (such as disabled cache, Fast 3G, etc)

> Consider adding the `defer` attribute to scripts in the index.html to stop scripts from blocking rendering.

CTRL + P in Chrome will open a prompt. You can type `>coverage` and a new tab will open. Click the refresh button in the new tab space and it'll show you where you have dead html/css/js.
If it's compressed you can use the npm package `source-map-explorer` to navigate through compressed files that pop up here.

```bash
$ source-map-explorer dist/path/to/offending/file.js
```

Be weary of stylesheets getting included more than once or styles being included that do not need to be included.
