import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotesService } from '../notes.service';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.scss']
})
export class PageComponent implements OnInit {
  markdownPath: any;
  pageContent: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private notesService: NotesService) {
  }

  ngOnInit(): void {
    // const pageName = this.route.snapshot.paramMap.get['pageName'];

    // console.log(this.route.snapshot.paramMap)

    // this.notesService.getPageContent(pageName)
    // .then(response => {
    //   this.pageContent = response;
    //   //console.log(this.pageContent)
    // })
    // .catch(err => {
    //   console.error(err);
    //   throw new Error(err);
    // })

    this.router.events.subscribe((event: any) => {
      let r = this.route;
      while (r.firstChild) {
          r = r.firstChild
      }
      r.params.subscribe(params => {
          console.log(params.pageName);
      });
    });
  }

}
