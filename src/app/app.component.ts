import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {take} from 'rxjs/operators';

import {NotesService} from './notes.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  notesMap$: Observable<any>;

  constructor(private notesService: NotesService) {
  }

  ngOnInit() {
    this.getRawNotesMap();
  }

  getRawNotesMap() {
    this.notesMap$ = this.notesService.getRawNotesMap();
  }
}
