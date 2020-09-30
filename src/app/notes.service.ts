import { Injectable } from '@angular/core';
import { of, Subject } from 'rxjs';
import {tap} from 'rxjs/operators';

import {HttpClient} from '@angular/common/http';
import { networkInterfaces } from 'os';
const _ = require('lodash');

const testFolder = '../assets/notes';
const fs = require('fs');

@Injectable({
  providedIn: 'root'
})
export class NotesService {

  private _notesMap;

  constructor(private http: HttpClient) {
  }

  getRawNotesMap() {
    
    if (!this._notesMap) {
      const data = require('../assets/notes-map.json')
      delete data.entries['.git']
      delete data.entries['README.md']

      this._notesMap = this.buildTidyMap(data);
    }

    return this._notesMap;
  }

  buildTidyMap(data) {
    const dirtyMap = this.buildDirtyMap(data);    
    const subCatsMapped = this.mapSubcategoryPages(dirtyMap);
    const nestedPagesMapped = this.mapNestedPages(subCatsMapped);
    
    console.log(nestedPagesMapped)
    return of(nestedPagesMapped);
  }

  private buildDirtyMap(data) {
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

    return notes;
  }

  private mapSubcategoryPages(notes) {
    notes.forEach(n => {
      for (const [key, value] of Object.entries(n.tempPages)) {
        if (key.substring(key.length - 3) !== '.md') {
          n.subcategories.push({ 
            name: key, 
            rawPages: value['entries'],
            pages: []
          });
        } else {
          n.pages.push({ 
            name: key, 
            path: value['path'] 
          })
        }
        delete n.tempPages;
      }
    });

    return notes;
  }

  private mapNestedPages(notes) {
    notes.forEach(n => {
      for (let subcategory of n.subcategories) {
        if (subcategory.rawPages) {
          for (let [key, value] of Object.entries(subcategory.rawPages)) {
            subcategory.pages.push({ 
              name: key, 
              path: value['path'] 
            });

            delete subcategory.rawPages;
          }
        }
      }
    })

    return notes;
  }

  getPageContent(pageName: string) {
    let absolutePath, path;

    this.getRawNotesMap().subscribe((notes: any[]) => {
      for (let note of notes) {
        for (let page of note['pages']) {
          if (pageName === page.path.substring(page.path.lastIndexOf('/') + 1)) {
            absolutePath = page.path;
          }
        } 
        for (let subcat of note['subcategories']) {
          for (let page of subcat['pages']) {
            if (pageName === page.path.substring(page.path.lastIndexOf('/') + 1)) {
              absolutePath = page.path;
            }
          } 
        }
      }
    });

    path = absolutePath.substring(absolutePath.lastIndexOf('/assets'));

    return this.http.get(path, { responseType: 'text'}).toPromise();

  }

}
