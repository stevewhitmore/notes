import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NoteService } from '../_shared/note.service';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.scss']
})
export class PageComponent implements OnInit {
  pageName: any;
  pageContent: any;
  routeParamsSub: Subscription;

  constructor(private route: ActivatedRoute,
              private noteService: NoteService) {
  }

  ngOnInit() {
    this.listenForRouteChange();
  }

  listenForRouteChange() {
    this.routeParamsSub = this.route.params.subscribe(params => {
      if (params && params['pageName']) {
        this.pageName = params['pageName'];
        this.getPageContent();
      }
    })
  }

  getPageContent() {
    this.noteService.getPageContent(this.pageName)
    .then(response => {
      this.pageContent = response;
    })
    .catch(err => {
      console.error(err);
      throw new Error(err);
    })
  }

  ngOnDestroy() {
    if (this.routeParamsSub) { this.routeParamsSub.unsubscribe(); }
  }

  

}
