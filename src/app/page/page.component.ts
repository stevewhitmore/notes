import { ViewportScroller } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
              private noteService: NoteService,
              private viewPortScroller: ViewportScroller) {
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
  
  backToTop() {
    this.viewPortScroller.scrollToPosition([0, 0]);
  }

  ngOnDestroy() {
    if (this.routeParamsSub) { this.routeParamsSub.unsubscribe(); }
  }
}
