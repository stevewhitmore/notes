# Progressive Web Apps with Angular
Not a standard or web spec
Term coined by Google devs

__What is it?__
- Fast
- Integraged
- Reliable -> offline or bad connection
- Engaging -> native feel, push content# Progressive Web Apps with Angular


### Fast
- 53% of users leave if > 3 seconds load time
- 3G is still prevelant
- Mobile devices in general have limited CPU and memory


### Integrated
- Add to home screen (if meets standard)
- Geolocation
- Camera/mic
- App manifest


### Reliable
- App shell platform -> cache or install static content
- Handle offline routing
- Dynamic caching -> cache api connection


### Engaging
- Web payment API -> Google Pay
- Push notifications (done by Service Workers)
- Web Sockets / Live updates

> See pwastats.com

In general PWAs are way smaller than native apps

Lighthouse is a great tool for PWA development. Tests performance from simulated 3G network.


## Service Workers
- Proxy-ish JS file -> sits between application and network. Monitors/intercepts all network traffic for session.
- Enables add to home screen
- HTTPS
- Key feature for PWAs

> If working on a PWA, Go to Chrome devtools -> Application -> check the service worker refresh box


## Downsides
- Typical caching problems
- "Breaking" the refresh button
- Kill switch is needed for SWs that refuse to die and reinstall with fresh updates

Service workers dont disrupt browsers (like IE) that can't use them. They just go dormant. 

> Look at Workbox and @angular/service-worker


## How to develop PWA in Angular?
`ng new my-pwa`
`ng add @angular/pwa` (?)

ngsw-config.json

No impact on angular app, the app behaves as usual. SW sits between app and network.


## Performance
Toughest thing to perfect
- ng build --prod
- lazy routing /code splitting
- tree shakeable providers -> angular has certain syntax to weed out unused services
- Service Worker caching
- Server side rendering with Angular Universal

Update notification from SW instead of refresh button.

> See https://github.com/coryrylan/ng-pwa-lunch

