import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map, mergeMap, switchMap } from 'rxjs/operators';
import { NotesService } from '../notes.service';

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
              private router: Router,
              private notesService: NotesService) {
  }

  ngOnInit(): void {    
    this.routeParamsSub = this.route.params.subscribe(params => {
      if (params && params['pageName']) {
        this.pageName = params['pageName'];
        this.getPageContent();
      }
    })
  }

  getPageContent() {
    this.notesService.getPageContent(this.pageName)
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
