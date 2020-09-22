import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import {tap} from 'rxjs/operators';

import {HttpClient} from '@angular/common/http';

const testFolder = '../assets/notes';
const fs = require('fs');

@Injectable({
  providedIn: 'root'
})
export class NotesService {

  constructor(private http: HttpClient) {
  }

  getRawNotesMap() {
    return of(require('../assets/notes-map.json'))
      .pipe(tap(data => {
        delete data.entries['.git']
        delete data.entries['README.md']
        // return (({entries}) => ({entries}))(data)
      }));
  }

  getFolders() {

    // const data = require('../assets/notes/aws/intro.md')
    // console.log(data)

    return this.http.get(`/assets/notes/aws/intro.md`, { responseType: 'text'}).toPromise();

  }

}
