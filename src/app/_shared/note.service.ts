import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private _notesMap;
  private _flattendPages;

  constructor(private http: HttpClient) {
  }

  getRawNotesMap() {
    
    if (!this._notesMap) {
      const data = require('../../assets/notes-map.json')
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
    
    return of(nestedPagesMapped);
  }

  getPageContent(pageName: string) {
    let absolutePath, path;

    // TODO: fix leak
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

  getFlattenedPageList() {
    // TODO: fix leak and cleanup
    if (!this._flattendPages) {
      const flattenedPages = [];

      this.getRawNotesMap().subscribe((notes: any[]) => {
        for (let note of notes) {
          for (let page of note['pages']) {
            flattenedPages.push(page)
          } 
          for (let subcat of note['subcategories']) {
            for (let page of subcat['pages']) {
              flattenedPages.push(page)
            } 
          }
        }
      });

      this._flattendPages = flattenedPages;
    }

    return this._flattendPages;
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

}
