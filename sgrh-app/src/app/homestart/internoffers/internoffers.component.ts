import {Component, ElementRef, HostBinding, OnInit, Renderer2, ViewChild} from '@angular/core';
import {OffreStageService} from "../../services/stages/offre-stage.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/authentification/auth.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-internoffers',
  templateUrl: './internoffers.component.html',
  styleUrls: [
    './internoffers.component.css',
  ],
})
export class InternoffersComponent implements OnInit {
  @HostBinding('attr.ngSkipHydration') skipHydration = true;

  private scriptUrls: string[] = [
    "https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js",
    "https://code.jquery.com/jquery-3.6.4.min.js"
  ];
  private styleUrls: string[] = [
    '../../../assets/interns/css/owl.carousel.min.css',
    '../../../assets/interns/css/flaticon.css',
    '../../../assets/interns/css/price_rangs.css',
    '../../../assets/interns/css/slicknav.css',
    '../../../assets/interns/css/animate.min.css',
    '../../../assets/interns/css/magnific-popup.css',
    '../../../assets/interns/css/fontawesome-all.min.css',
    '../../../assets/interns/css/themify-icons.css',
    '../../../assets/interns/css/slick.css',
    '../../../assets/interns/css/nice-select.css',
    '../../../assets/interns/css/style.css'
  ];

  offers:any;
  keyword: any;
  constructor(private renderer: Renderer2, private el: ElementRef, private offre :OffreStageService,private router: Router, private authService:AuthService, private alertService:AlertluncherService) {}

  ngOnInit(): void {
    this.authService.deleteToken();
    this.getOffreStage();
    // Dynamically load scripts for this component
    this.loadScripts(this.scriptUrls);
    // Dynamically load styles for this component
    this.loadStyles(this.styleUrls);
  }

  getOffreStage(){
    this.offre.getOffers()
      .subscribe({
        next: (data) => {
          this.offers = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  private loadScripts(scriptUrls: string[]): void {
    scriptUrls.forEach((scriptUrl) => {
      const script = this.renderer.createElement('script');
      script.type = 'text/javascript';
      script.src = scriptUrl;
      this.renderer.appendChild(this.el.nativeElement, script);
    });
  }
  private loadStyles(styleUrls: string[]): void {
    styleUrls.forEach((styleUrl) => {
      const style = this.renderer.createElement('link');
      style.rel = 'stylesheet';
      style.type = 'text/css';
      style.href = styleUrl;
      this.renderer.appendChild(this.el.nativeElement, style);
    });
  }

  searchOffers() {
    // console.log(this.keyword);
    if(this.keyword == ""){
      this.getOffreStage();
      const targetSection = this.el.nativeElement.querySelector('#targetSection');

      if (targetSection) {
        this.renderer.setProperty(targetSection, 'scrollTop', 0);
        targetSection.scrollIntoView({behavior: 'smooth', block: 'start', inline: 'nearest'});
      }
    } else {
      this.offre.searchOffer(this.keyword)
        .subscribe({
          next: (data) => {
            this.offers = data;
            const targetSection = this.el.nativeElement.querySelector('#targetSection');

            if (targetSection) {
              this.renderer.setProperty(targetSection, 'scrollTop', 0);
              targetSection.scrollIntoView({ behavior: 'smooth', block: 'start', inline: 'nearest' });
            }
          },
          error: (err) => {
            console.log(err);
          }
        });
    }
  }


}
