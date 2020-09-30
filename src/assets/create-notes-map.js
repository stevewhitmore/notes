const mapFolder = require('map-folder');
const fs = require('fs');

const rawMap = mapFolder(`${__dirname}/notes`);

fs.writeFile(`${__dirname}/notes-map.json`, JSON.stringify(rawMap), function (err,data) {
  if (err) return console.log(err);
});
