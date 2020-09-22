import { Component, OnInit } from '@angular/core';
import {take} from 'rxjs/operators';

import {NotesService} from './notes.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  data: any;

  notes: any;

  constructor(private notesService: NotesService) {
  }

  ngOnInit() {
    this.getRawNotesMap();
  }

  getRawNotesMap() {
    this.notesService.getRawNotesMap()
      .subscribe(data => {
        this.parseRawData(data);
      })
  }

  parseRawData(data) {
    const notes = [];
    for (const [key, value] of Object.entries(data)) {
      for (const [key2, value2] of Object.entries(value)) {
        if (key === 'entries') {
          notes.push({
            category: key2,
            pages: value2['entries']
          })
        }
        // console.log('key2:', key2)
        // console.log('value2:', value2)
      }
      console.log(key, value)
    }

    console.log('notes:', notes)
  }

  getNote() {
    this.notesService.getFolders()
      .then(response => {
        this.data = response;
        console.log(this.data)
      })
      .catch(err => {
        console.error(err);
        throw new Error(err);
      })
  }

}
