import { Injectable } from '@angular/core';
import { of, Subject } from 'rxjs';
import {tap} from 'rxjs/operators';

import {HttpClient} from '@angular/common/http';

const testFolder = '../assets/notes';
const fs = require('fs');

@Injectable({
  providedIn: 'root'
})
export class NotesService {

  private notesMapSource = new Subject();
  notesMap$ = this.notesMapSource.asObservable();

  constructor(private http: HttpClient) {
  }

  getRawNotesMap() {
    const data = require('../assets/notes-map.json')
    delete data.entries['.git']
    delete data.entries['README.md']

    return this.buildTidyMap(data);
  }

  buildTidyMap(data) {
    const notes = [];
    
    for (const [key, value] of Object.entries(data)) {
      for (const [key2, value2] of Object.entries(value)) {
        if (key === 'entries') {
          notes.push({
            category: key2,
            tempPages: value2['entries'],
            subcategories: [],
            pages: []
          })
        }
      }
    }

    notes.forEach(n => {
      for (const [key, value] of Object.entries(n.tempPages)) {
        if (key.substring(key.length - 3) !== '.md') {
          n.subcategories.push({ [key]: value['entries'] });
        } else {
          n.pages.push({ name: key, path: value['path'] })
        }
      }
    });

    this.notesMapSource.next(notes);
    
    return of(notes);
  }

  getPageContent(path: string) {
    console.log('path:', path)

    return this.http.get(`/assets/notes/aws/intro.md`, { responseType: 'text'}).toPromise();

  }

}
