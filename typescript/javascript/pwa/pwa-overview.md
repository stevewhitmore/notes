# Progressive Web Apps

A regular web app with a few layers added on

- Service Workers
- App shell
- Manifest

## Service Workers

Runs independently of frontend and server so it can operate even if the app is closed. 

> Requires HTTPS (unless running locally)

The Fetch API & Promises are an essential part of working with Service Workers in a modern Progressive Web App.

### Registering a service worker

```javascript
if (navigator.serviceWorker) {

  // Register the SW
  navigator.serviceWorker.register('/sw.js').then(function(registration){

    console.log('SW Registered');

  }).catch(console.log);

}
```

### Intercepting requests

*main.js*

```javascript
fetch('camera_feed.html')
  .then((res) => {
  return res.text();
}).then((html) => {
  document.getElementById('camera').innerHTML = html;
});
```

*sw.js*

```javascript
  if (e.request.url.endsWith('/camera_feed.html')) {

    e.respondWith(
      fetch(e.request)
        .then((res) => {

          if( res.ok ) return res;

          return new Response('Camera feed currently not available.')
        })
    )
  }
```

The above will intercept the fetch request, return it if 200 or print alternative text if failed.

### Scope

Service Workers can be scoped to certain directories. For example, say you've got the following project structure:

```
|-- posts
  |-- index.html
  |-- post1.html
|-- index.html
|-- main.js
|-- style.css
|-- sw.js
```

This will scope it to just "posts"

```javascript
if (navigator.serviceWorker) {

  // Register the SW
  navigator.serviceWorker.register('/sw.js', {scope: '/posts/'}).then(function(registration){

    console.log('SW Registered');

  }).catch(console.log);

}
```

### ServiceWorkerRegistration

Represents the service worker registration and has methods to tap into the Service Worker lifecycle.

*main.js*

```javascript
if (navigator.serviceWorker) {

  // Register the SW
  navigator.serviceWorker.register('/sw.js').then(function(registration){

    registration.onupdatefound = () => {
      console.log("New SW Found");
      let newSW = registration.installing;

      newSW.onstatechange = () => {
        console.log(newSW.state);
      }
    };

  }).catch(console.log);

}
```

*sw.js*

```javascript
self.addEventListener('install', (e) => {

  e.waitUntil(new Promise((resolve) => {
    setTimeout(resolve, 5000);
  }))
});

self.addEventListener('activate', () => {
  console.log('SW2 Active');
});
```

Outputs:

New SW Found
// After 5 seconds:
SW2 Active
installed
activating
activated

### Messages

Service Workers can send messages to their clients.

*main.js*

```javascript
if (navigator.serviceWorker) {

  // Register the SW
  navigator.serviceWorker.register('/sw.js').then(function(registration){

    if (registration.active) {
      registration.active.postMessage('respond to this');
    }

  }).catch(console.log);

  navigator.serviceWorker.addEventListener('message', (e) => {
    console.log(e.data);
  });

}
```

*sw.js*

```javascript
self.addEventListener('message', (e) => {

  // Respond to all clients
  self.clients.matchAll().then((clients) => {

    clients.forEach((client) => {

      client.postMessage('moo')

      // Only respond to sending client
      if (e.source.id === client.id) {
        client.postMessage("Private Hello from Service Worker");
      }
    });
  });

});
```

The above will output "moo" to all clients (tabs or windows). The final if block will only send a message to the active tab.

## Push Notifications

There are usually 3 settings for Notification API:

1. granted
2. denied
3. default (whatever the browser default is)

*main.js*

```javascript
if (window.Notification) {

  function showNotification() {

    let notificationOpts = {
      body: 'Some notification information.',
      icon: '/icon.png'
    }

    let n = new Notification('My new Notification.', notificationOpts);

    n.onclick = () => {
      console.log('Notification Clicked');
    }
  }

  // Manage permission
  if (Notification.permission === 'granted') {
    showNotification();

  } else if (Notification.permission !== 'denied') {

    Notification.requestPermission( (permission) => {

      if (permission === 'granted') {
        showNotification();
      }
    });

  }
}
```
### Server Push

Client has a public key. You subscribe to the service worker's push manager where you recieve a private key. Post it to the push server for it to use to push notifications:

Generate the keys with the `web-push` package:

```bash
$ npm i web-push
```

Then generate the keys by calling its CLI script directly:

```bash
node_modules/web-push/src/cli.js generate-vapid-keys --json
```

*main.js*

```javascript
if (navigator.serviceWorker) {

  // Convert key to Uint8Array
  function urlBase64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding)
      .replace(/\-/g, '+')
      .replace(/_/g, '/');

    const rawData = window.atob(base64);
    const outputArray = new Uint8Array(rawData.length);

    for (let i = 0; i < rawData.length; ++i) {
      outputArray[i] = rawData.charCodeAt(i);
    }
    return outputArray;
  }

  // Register the SW
  navigator.serviceWorker.register('/sw.js').then((registration) => {

    // Server public key
    let pubKey = 'BA_kcDJ9MyfRQ1QBYmrrBv-PzcUfmBFfm_9UebAp1nm5WK5VFgUgLYsMgda0539pVuUXMf3O4gHfUI5kjHGNteM';

    registration.pushManager.getSubscription().then((sub) => {

      // If subscription found, return
      if (sub) return sub;

      let applicationServerKey = urlBase64ToUint8Array(pubKey);

      // Subscribe
      return registration.pushManager.subscribe({userVisibleOnly: true, applicationServerKey});

    }).then( sub => sub.toJSON() )
      .then(console.log)
      .catch(console.log);

  }).catch(console.log);

}
```

*sw.js*

```javascript
self.addEventListener('push', (e) => {

  let n = self.registration.showNotification('A notification from the SW.');
  e.waitUntil(n);
});
```

A simple example of a push server would be the following:

*push-server.js*

```javascript
const webpush = require('web-push');
const vapid = require('./vapid.json');

// Configure keys
webpush.setVapidDetails(
  'mailto:ray@stackacademy.tv',
  vapid.publicKey,
  vapid.privateKey
);

const pushSubscription = {
  // these values are from the console output provided by main.js
  endpoint: 'https://fcm.googleapis.com/fcm/send/dGXO0NzfwcI:APA91bHrEAx4iw5gstMW_532y2UlphKe2cIb2jI4ukhgbZ6VLWmHy4-AmFPyHxWYJdV3nS7VTmfxElI9Fh7W9hf8PudqFtuXEyRxxVXXp7TWfYzYM_f889bnVoyvvHEve512_BqCeyNA',
  keys: {
    auth: 'aAScQk97nu6MBAeC4PTWkw',
    p256dh: 'BPrlv6gx-ij00Z6c0HG5xtPl0nvwNqsV1lsCly6xq-ckKW-GpMltph6Pd5fK7f0aTUjzJS8OdWt36rIjDKA_suo'
  }
};

webpush.sendNotification(pushSubscription, 'A notification from the push server');
console.log('Push sent to client');
```

Run `node push-server.js` and a push notification will be sent to the client.

## Caching

Don't use localStorage because it's a synchronous store.

pouchdb (based off of CouchDB) will allow you to sync data with the cloud and in turn other devices.

localForage is good at handling data too large for the SW and works well with older devices.

Cache API is the newest from HTML5. Will work only with browsers supporting SWs.

### ChacheStorage

Interface for Cache APi.

```javascript
if (window.caches) {
  caches.open('test');
}
```

The above creates a cache.

Print out the cache keys with `caches.keys();`.

Check for existing keys with `caches.has('test');`.

Delete cache with `caches.delete('test1');` or `caches.deleteAll([ ... ]);`.

These all return promises, so print out the results by appending `.then(console.log)`;

You can add stuff to the cache with the following:

```javascript
if (window.caches) {
  caches.open('pwa-v1.1').then((cache) => {
    cache.addAll([
      '/index.html',
      '/style.css',
      '/main.js'
    ]);

    // cache.add('/index.html');
  });
}
```

These resources need to be present because if the promise fails, it fails and there's no recourse. Best to only use this with resources located in the same origin.

You can control the responses by directly calling `put`: 

```javascript
if (window.caches) {

  caches.open('pwa-v1.1').then((cache) => {

    cache.put('input.html', new Response('My own HTML'));

    // cache.match('index.html').then((res) => {
    //    res.text().then(console.log);
    // });
  });
}
```

When the commented out code above is uncommented, then the output will be "My own HTML".

### Caching in the Storage Worker

*sw.js*

```javascript
const pwaCache = 'pwa-cache-2';

self.addEventListener('install', (e) => {

  let cacheReady = caches.open(pwaCache).then((cache) => {

    console.log('New cache ready.');
    return cache.addAll([
      '/',
      'style.css',
      'thumb.png',
      'main.js'
    ]);
  });

  e.waitUntil(cacheReady);
});


self.addEventListener('activate', (e) => {

  let cacheCleaned = caches.keys().then((keys) => {

    keys.forEach((key) => {
      if( key !== pwaCache ) return caches.delete(key);
    });
  });

  e.waitUntil(cacheCleaned);

});


self.addEventListener('fetch', (e) => {

  // Skip for remote fetch
  if ( !e.request.url.match(location.origin) )  return;

  // Serve local fetch from cache
  let newRes = caches.open(pwaCache).then((cache) => {
    return cache.match(e.request).then((res) => {

      // Check request was found in cache
      if (res) {
        console.log(`Serving ${res.url} from cache.`);
        return res;
      }

      // Fetch on behalf of client and cache
      return fetch(e.request).then((fetchRes) => {

        cache.put(e.request, fetchRes.clone());
        return fetchRes;
      });
    });
  });

  e.respondWith(newRes);

});
```

### Caching Strategies

It's a good idea to have a network fallback if resources get deleted from the cache from the user or the OS

```javascript
// 1. Cache only. Static assets - App Shell
 e.respondWith(caches.match(e.request));

// 2. Cache with Network Fallback
 e.respondWith(
  caches.match(e.request).then( (res) => {
    if(res) return res;

    // Fallback
    return fetch(e.request).then( (newRes) => {
      // Cache fetched response
      caches.open(pwaCache).then( cache => cache.put(e.request, newRes) );
      return newRes.clone();
    })
  })
);

// 3. Network with cache fallback
e.respondWith(
  fetch(e.request).then( (res) => {

    // Cache latest version
    caches.open(pwaCache).then( cache => cache.put(e.request, res) );
    return res.clone();

  // Fallback to cache
  }).catch( err => caches.match(e.request) )
);

// 4. Cache with Network Update
e.respondWith(
  caches.open(pwaCache).then( (cache) => {

    // Return from cache
    return cache.match(e.request).then( (res) => {

      // Update
      let updatedRes = fetch(e.request).then( (newRes) => {
        // Cache new response
        cache.put(e.request, newRes.clone());
        return newRes;
      });

      return res || updatedRes;
    })
  })
);

// 5. Cache & Network Race with offline content
let firstResponse = new Promise((resolve, reject) => {

  // Track rejections
  let firstRejectionReceived = false;
  let rejectOnce = () => {
    if (firstRejectionReceived) {

      if (e.request.url.match('thumb.png')) {
        resolve(caches.match('/placeholder.png'));
      } else {
        reject('No response received.')
      }
    } else {
      firstRejectionReceived = true;
    }
  };

  // Try Network
  fetch(e.request).then( (res) => {
    // Check res ok
    res.ok ? resolve(res) : rejectOnce();
  }).catch(rejectOnce);

  // Try Cache
  caches.match(e.request).then( (res) => {
    // Check cache found
    res ? resolve(res) : rejectOnce();
  }).catch(rejectOnce);

});
e.respondWith(firstResponse);
```

## Native App Features

This part is pretty simple. It just needs a `manifest.json` with contents like below:

```json
{
  "name": "Progressive Web App",
  "short_name": "PWA",
  "start_url": "/index.html",
  "display": "standalone",
  "background_color": "#2D91F8",
  "description": "Progressive Web Apps - The Complete Guide",
  "icons": [
    {
      "src": "/icons/icon-72.png",
      "sizes": "72x72",
      "type": "image/png"
    },
    {
       "src": "/icons/icon-96.png",
       "sizes": "96x96",
       "type": "image/png"
     },
     {
       "src": "/icons/icon-128.png",
       "sizes": "128x128",
       "type": "image/png"
     },
     {
       "src": "/icons/icon-144.png",
       "sizes": "144x144",
       "type": "image/png"
     },
     {
       "src": "/icons/icon-152.png",
       "sizes": "152x152",
       "type": "image/png"
     },
     {
       "src": "/icons/icon-192.png",
       "sizes": "192x192",
       "type": "image/png"
     }
  ]
}

```

You can generate a manifest at <https://app-manifest.firebaseapp.com/>.

