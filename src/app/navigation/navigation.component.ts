import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { take } from 'rxjs/operators';

import {NoteService} from '../_shared/note.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {
  notesMap: any;
  filteredMenuItems: any[] = [];

  constructor(private noteService: NoteService,
              private router: Router) {
  }

  ngOnInit() {
    this.getRawNotesMap();
  }

  getRawNotesMap() {
    this.noteService.getRawNotesMap()
      .pipe(take(1))
      .subscribe(data => {
        this.notesMap = data;
      });
  }

  routeToPage(page) {
    this.router.navigate(['page/', page.name]); 
  }

  filterNotes(input) {
    if (!input) {
      this.filteredMenuItems = [];
      return;
    }

    const flattened = this.noteService.getFlattenedPageList();
    this.filteredMenuItems = flattened.filter(item => item.name.toLowerCase().indexOf(input.toLowerCase()) > -1);
    console.log(this.filteredMenuItems)
  }

  toggleCollapse(categoryButton) {
    categoryButton.classList.toggle('collapsed');
  }
}
